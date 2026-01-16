/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ecotrack.controlador;

import ecotrack.modelo.EcoTrackModelo;
import ecotrack.modelo.Residuo;
import ecotrack.modelo.Ruta;
import ecotrack.vista.LoginGUI;
import ecotrack.modelo.tda.BidirectionalIterator;
import ecotrack.vista.EcoTrackGUI;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 * Cerebro del sistema EcoTrack. 
 * Orquesta la comunicación entre el Modelo 
 */
public class EcoTrackControlador {
    private EcoTrackModelo modelo;
    private EcoTrackGUI vistaDashboard;
    private LoginGUI vistaLogin;
    
    // Atributo para la navegación bidireccional en la lista circular
    private BidirectionalIterator<Residuo> iteradorNavegacion;

    public EcoTrackControlador(EcoTrackModelo m, EcoTrackGUI vDash, LoginGUI vLog) {
        this.modelo = m;
        this.vistaDashboard = vDash;
        this.vistaLogin = vLog;
        configurarEventos();
    }

    /**
     * Vincula los botones de la interfaz con sus funciones lógicas.
     */
    private void configurarEventos() {
        // --- Eventos de Login/Registro ---
            vistaLogin.btnEntrar.addActionListener(e -> handleLogin());
            vistaLogin.btnIrRegistro.addActionListener(e -> vistaLogin.mostrarRegistro());
            vistaLogin.btnRegistrar.addActionListener(e -> handleRegistro());
            vistaLogin.btnVolver.addActionListener(e -> vistaLogin.mostrarLogin());
        // --- Eventos del Dashboard ---
        vistaDashboard.btnRegistrarResiduo.addActionListener(e -> handleRegistrarResiduo());
        vistaDashboard.btnListar.addActionListener(e -> handleListarResiduos());
        vistaDashboard.btnDespachar.addActionListener(e -> handleDespacharRuta());
        vistaDashboard.btnReciclaje.addActionListener(e -> handleProcesarReciclaje());
        vistaDashboard.btnGuardar.addActionListener(e -> handleGuardarDatos());
        vistaDashboard.btnCargar.addActionListener(e -> handleCargarDatos());
        vistaDashboard.btnRegistrarRuta.addActionListener(e -> handleRegistrarRuta());
        vistaDashboard.btnReporte.addActionListener(e -> handleMostrarReporte());

        // --- VINCULACIÓN DE NAVEGACIÓN ---
        vistaDashboard.btnAnterior.addActionListener(e -> handleAnterior()); // Conecta con previous()
        vistaDashboard.btnSiguiente.addActionListener(e -> handleSiguiente()); // Conecta con next()
    }

    public void iniciarAplicacion() {
        // Aseguramos que el mapa de usuarios no sea null (por retrocompatibilidad)
        modelo.asegurarMapaUsuarios();
        
        // Iniciamos mostrando SOLO el login
        vistaLogin.setVisible(true);
        vistaDashboard.setVisible(false);
    }
    
    // --- LÓGICA DE AUTENTICACIÓN ---

    private void handleLogin() {
        String u = vistaLogin.txtLoginUser.getText().trim();
        String p = new String(vistaLogin.txtLoginPass.getPassword()).trim();

        if (u.isEmpty() || p.isEmpty()) {
            mostrarError(vistaLogin, "Por favor, llene todos los campos.");
            return;
        }

        if (modelo.validarCredenciales(u, p)) {
            // Login Exitoso
            vistaLogin.setVisible(false);     // Ocultar Login
            vistaDashboard.setVisible(true);  // Mostrar Dashboard
            actualizarTablaVisual();          // Cargar datos
            vistaDashboard.escribir("[ACCESO] Usuario " + u + " ha iniciado sesión.");
        } else {
            mostrarError(vistaLogin, "Credenciales inválidas.");
        }
    }
    
    private void handleRegistro() {
        String u = vistaLogin.txtRegUser.getText().trim();
        String p = new String(vistaLogin.txtRegPass.getPassword()).trim();

        if (u.isEmpty() || p.isEmpty()) {
            mostrarError(vistaLogin, "No puede dejar campos vacíos.");
            return;
        }

        if (modelo.registrarUsuario(u, p)) {
            JOptionPane.showMessageDialog(vistaLogin, "¡Registro exitoso! Ahora inicie sesión.");
            modelo.guardarEstado(); // Guardar usuario nuevo en el archivo .bin
            vistaLogin.mostrarLogin(); // Volver al login
        } else {
            mostrarError(vistaLogin, "El usuario ya existe. Intente otro.");
        }
    }
    
    private void mostrarError(java.awt.Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Sincroniza la JTable de la vista con los datos del TDA Lista Circular.
     */
    private void actualizarTablaVisual() {
    
    java.util.List<ecotrack.modelo.Residuo> lista = modelo.getListaResiduosOrdenada(ecotrack.modelo.EcoTrackModelo.getComparadorPorPrioridad());
    vistaDashboard.cargarDatosEnTabla(lista);
    
    if (modelo.getListaResiduos() != null && !modelo.getListaResiduos().isEmpty()) {
        this.iteradorNavegacion = modelo.getListaResiduos().bidirectionalIterator();
        // Opcional: Escribir en log para depuración interna (no visible al usuario)
        System.out.println("Iterador de navegación actualizado/reiniciado."); 
    } else {
        this.iteradorNavegacion = null;
    }
}

    // --- MANEJADORES DE RESIDUOS (TDA Lista Circular + Pila) ---

    private void handleRegistrarResiduo() {
        Map<String, String> datos = vistaDashboard.pedirDatosResiduo();
        if (datos == null) return;

        try {
            Residuo nuevo = new Residuo(
                datos.get("id"),
                datos.get("nombre"),
                datos.get("tipo"),
                Double.parseDouble(datos.get("peso")),
                datos.get("zona"),
                Integer.parseInt(datos.get("prioridad"))
            );

            // 1. Insertar en Lista Circular (Visualización en Tabla)
            modelo.registrarResiduo(nuevo); 
            
            // 2. Insertar en Pila LIFO (Centro de Reciclaje)
            modelo.registrarEnReciclaje(nuevo); 

            vistaDashboard.escribir("[OK] " + nuevo.getId() + " registrado en Inventario y Pila de Reciclaje.");
            actualizarTablaVisual(); 
            
            // Verificación automática de alertas 
            String alerta = modelo.verificarAlertas();
            if (alerta != null) {
                vistaDashboard.escribir("[SISTEMA] " + alerta);
                JOptionPane.showMessageDialog(vistaDashboard, alerta, "Umbral Superado", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaDashboard, "Error en los datos: " + e.getMessage());
        }
    }
    
    private void handleMostrarReporte() {
        // Obtenemos los datos del Mapa (TDA obligatorio)
        Map<String, Double> stats = modelo.getEstadisticas();
        vistaDashboard.mostrarReporteEstadistico(stats);
        vistaDashboard.escribir("[SISTEMA] Reporte estadístico generado.");
    }

    private void handleListarResiduos() {
        String[] opciones = {"Peso", "Tipo", "Prioridad"};
        int seleccion = JOptionPane.showOptionDialog(vistaDashboard, "Seleccione criterio de orden", "Ordenamiento",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion == -1) return;

        Comparator<Residuo> comp;
        if (seleccion == 0) comp = EcoTrackModelo.getComparadorPorPeso();
        else if (seleccion == 1) comp = EcoTrackModelo.getComparadorPorTipo();
        else comp = EcoTrackModelo.getComparadorPorPrioridad();

        List<Residuo> lista = modelo.getListaResiduosOrdenada(comp);
        vistaDashboard.cargarDatosEnTabla(lista); // Actualiza la tabla visualmente con el nuevo orden
        
        vistaDashboard.escribir("\n--- Listado Ordenado en Pantalla ---");
        for (Residuo r : lista) {
            vistaDashboard.escribir(r.toString());
        }
    }

    private void handleProcesarReciclaje() {
        Residuo r = modelo.procesarResiduoEnReciclaje();
        if (r != null) {
            vistaDashboard.escribir("[RECICLAJE LIFO] Procesando último elemento: " + r.toString());
            // No es obligatorio borrarlo de la lista circular, pero si se desea sincronizar:
            // modelo.getListaResiduos().eliminar(r);
            // actualizarTablaVisual();
        } else {
            vistaDashboard.escribir("[ERROR] Pila vacia. Registre residuos primero.");
        }
    }

    // --- MANEJADORES DE RUTAS (TDA Cola de Prioridad) ---

    private void handleRegistrarRuta() {
        Map<String, String> datos = vistaDashboard.pedirDatosRuta();
        if (datos == null) return;
        try {
            Ruta nueva = new Ruta(datos.get("zona"), Integer.parseInt(datos.get("prioridad")), datos.get("idVehiculo"));
            modelo.registrarRuta(nueva);
            vistaDashboard.escribir("[OK] Ruta en " + nueva.getZona() + " añadida a la cola de despacho.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaDashboard, "Error en datos de ruta.");
        }
    }

    private void handleDespacharRuta() {
        Ruta r = modelo.despacharProximaRuta();
        if (r != null) vistaDashboard.escribir("[DESPACHO] " + r.toString());
        else vistaDashboard.escribir("[ERROR] No hay rutas pendientes.");
    }

    // --- MANEJADORES DE PERSISTENCIA (Gestor de Archivos) ---

    private void handleGuardarDatos() {
        boolean exito = modelo.guardarEstado(); // Llama al método que retorna boolean

        if (exito) {
            vistaDashboard.escribir("[SISTEMA] Backup binario creado correctamente.");
            JOptionPane.showMessageDialog(vistaDashboard, "Datos guardados con éxito.", 
                    "Backup", JOptionPane.INFORMATION_MESSAGE);
        } else {
            vistaDashboard.escribir("[ERROR] No se pudo guardar el backup.");
            JOptionPane.showMessageDialog(vistaDashboard, "Error al guardar los datos en el disco.", 
                    "Error de Archivo", JOptionPane.ERROR_MESSAGE);
        }
}

    private void handleCargarDatos() {
        boolean exito = modelo.cargarEstado();

        if (exito) {
            actualizarTablaVisual();
            vistaDashboard.escribir("[SISTEMA] Datos restaurados correctamente.");
            JOptionPane.showMessageDialog(vistaDashboard, "Datos cargados con éxito.");
        } else {
            vistaDashboard.escribir("[AVISO] No se encontró un backup previo.");
            JOptionPane.showMessageDialog(vistaDashboard, "No hay datos para restaurar.", "Info", JOptionPane.WARNING_MESSAGE);
        }
    }

    // --- MANEJADORES DE NAVEGACIÓN (BidirectionalIterator) ---

    public void handleSiguiente() {
        // Si el iterador es nulo, intenta inicializarlo al momento
        if (iteradorNavegacion == null && !modelo.getListaResiduos().isEmpty()) {
            this.iteradorNavegacion = modelo.getListaResiduos().bidirectionalIterator();
        }

        if (iteradorNavegacion != null && iteradorNavegacion.hasNext()) {
            Residuo r = iteradorNavegacion.next();

            // Feedback en el Log del Dashboard
            // vistaDashboard.escribir("[NAVEGACIÓN] Siguiente >>: " + r.getNombre() + " (" + r.getTipo() + ")");
            // Feedback visual (Popup)
            JOptionPane.showMessageDialog(vistaDashboard, "Visualizando: " + r.toString());
        } else {
            vistaDashboard.escribir("[AVISO] No hay más residuos o la lista está vacía.");
        }
    }

    public void handleAnterior() {
        // Si el iterador es nulo, intenta inicializarlo al momento
        if (iteradorNavegacion == null && !modelo.getListaResiduos().isEmpty()) {
            this.iteradorNavegacion = modelo.getListaResiduos().bidirectionalIterator();
        }

        if (iteradorNavegacion != null && iteradorNavegacion.hasPrevious()) {
            Residuo r = iteradorNavegacion.previous();

            // Feedback en el Log del Dashboard
            //vistaDashboard.escribir("[NAVEGACIÓN] << Anterior: " + r.getNombre() + " (" + r.getTipo() + ")");
            // Feedback visual (Popup)
            JOptionPane.showMessageDialog(vistaDashboard, "Visualizando: " + r.toString());
        } else {
            vistaDashboard.escribir("[AVISO] No hay más residuos o la lista está vacía.");
        }
    }
}