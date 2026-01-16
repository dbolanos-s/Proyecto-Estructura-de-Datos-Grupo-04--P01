/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecotrack.vista;

import ecotrack.modelo.Residuo;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List; // Soluciona el error de "cannot find symbol List"
import java.util.Map;

/**
 * Interfaz Gráfica Profesional tipo Dashboard para EcoTrack.
 * Sustituye a la versión de consola para cumplir con criterios de HCI.
 */
public class EcoTrackGUI extends JFrame {
    // Botones de control
    public JButton btnRegistrarResiduo, btnListar, btnDespachar, btnReciclaje, btnGuardar, btnCargar, btnRegistrarRuta, btnReporte;
    public JButton btnAnterior, btnSiguiente;
    
    // Componentes de visualización
    public JTable tablaResiduos;
    public DefaultTableModel modeloTabla;
    public JTextArea areaLog;

    // Paleta de Colores Dashboard Profesional
    private final Color COLOR_SIDEBAR = new Color(33, 37, 41);   // Dark Sidebar
    private final Color COLOR_FONDO = new Color(245, 246, 250);   // Light Background
    private final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 22);
    private final Font FUENTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);

    public EcoTrackGUI() {
        // Establecer el aspecto nativo del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        setTitle("EcoTrack - Dashboard Ambiental Inteligente");
        setSize(1150, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarComponentes();
        setLocationRelativeTo(null); // Centrar ventana
    }

    private void inicializarComponentes() {
        // --- BARRA LATERAL (SIDEBAR) ---
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(COLOR_SIDEBAR);
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.setBorder(new EmptyBorder(30, 20, 30, 20));

        JLabel tituloApp = new JLabel("ECOTRACK");
        tituloApp.setFont(FUENTE_TITULO);
        tituloApp.setForeground(Color.WHITE);
        sidebar.add(tituloApp);
        sidebar.add(Box.createVerticalStrut(40));

        // Creación de botones con estilo moderno
        btnRegistrarResiduo = crearBotonSidebar("Registrar Residuo");
        btnListar = crearBotonSidebar("Inventario General");
        btnAnterior = crearBotonSidebar("<< Ver Anterior");
        btnSiguiente = crearBotonSidebar(">> Ver Siguiente");
        btnDespachar = crearBotonSidebar("Despachar Rutas");
        btnReciclaje = crearBotonSidebar("Centro de Reciclaje");
        btnRegistrarRuta = crearBotonSidebar("Gestionar Zonas");
        btnGuardar = crearBotonSidebar("Guardar Backup");
        btnCargar = crearBotonSidebar("Restaurar Datos");
        btnReporte = crearBotonSidebar("Mostrar Reporte");

        sidebar.add(btnRegistrarResiduo);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnListar);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnDespachar);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnReciclaje);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnRegistrarRuta);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnGuardar);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnCargar);
        sidebar.add(btnListar);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnAnterior); // Botón para retroceder
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnSiguiente); // Botón para avanzar
        sidebar.add(btnReporte);
        sidebar.add(Box.createVerticalGlue());
       

        // --- PANEL DE TRABAJO (CENTRAL) ---
        JPanel panelContenedor = new JPanel(new BorderLayout(25, 25));
        panelContenedor.setBackground(COLOR_FONDO);
        panelContenedor.setBorder(new EmptyBorder(35, 35, 35, 35));

        JLabel lblSeccion = new JLabel("Resumen del Sistema de Gestion");
        lblSeccion.setFont(new Font("Segoe UI", Font.BOLD, 26));
        panelContenedor.add(lblSeccion, BorderLayout.NORTH);

        // TABLA DE INVENTARIO (TDA Lista Circular Doble)
        String[] columnas = {"ID", "Nombre", "Tipo", "Peso (kg)", "Zona", "Prioridad"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaResiduos = new JTable(modeloTabla);
        tablaResiduos.setRowHeight(32);
        tablaResiduos.setFont(FUENTE_NORMAL);
        tablaResiduos.setSelectionBackground(new Color(232, 240, 254));
        
        JScrollPane scrollTabla = new JScrollPane(tablaResiduos);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Vista de Residuos (TDA Circular)"));

        // CONSOLA DE REGISTRO (LOG)
        areaLog = new JTextArea(10, 0);
        areaLog.setEditable(false);
        areaLog.setFont(new Font("Consolas", Font.PLAIN, 12));
        areaLog.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollLog = new JScrollPane(areaLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Historial de Actividad"));

        // Organizar Tabla y Log en vertical
        JPanel panelDatos = new JPanel(new GridLayout(2, 1, 0, 25));
        panelDatos.setOpaque(false);
        panelDatos.add(scrollTabla);
        panelDatos.add(scrollLog);

        panelContenedor.add(panelDatos, BorderLayout.CENTER);

        add(sidebar, BorderLayout.WEST);
        add(panelContenedor, BorderLayout.CENTER);
    }

    private JButton crearBotonSidebar(String texto) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(240, 48));
        btn.setFont(FUENTE_NORMAL);
        btn.setForeground(new Color(190, 190, 190));
        btn.setBackground(new Color(45, 50, 55));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMargin(new Insets(0, 20, 0, 0));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(60, 65, 70));
                btn.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(45, 50, 55));
                btn.setForeground(new Color(190, 190, 190));
            }
        });
        return btn;
    }

    // --- MÉTODOS DE ENTRADA (HCI) ---

    public Map<String, String> pedirDatosResiduo() {
        return mostrarFormulario("Registro de Nuevo Residuo", 
                new String[]{"ID", "Nombre", "Tipo", "Peso", "Zona", "Prioridad"});
    }

    public Map<String, String> pedirDatosRuta() {
        return mostrarFormulario("Nueva Ruta de Recoleccion", 
                new String[]{"Zona", "Vehiculo", "Prioridad Ambiental (uz)"});
    }

    private Map<String, String> mostrarFormulario(String titulo, String[] campos) {
        JPanel p = new JPanel(new GridLayout(0, 2, 10, 10));
        Map<String, JTextField> inputs = new HashMap<>();
        for (String c : campos) {
            p.add(new JLabel(c + ":"));
            JTextField t = new JTextField(15);
            p.add(t);
            inputs.put(c, t);
        }
        int r = JOptionPane.showConfirmDialog(this, p, titulo, 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (r == JOptionPane.OK_OPTION) {
            Map<String, String> res = new HashMap<>();
            // Mapeo dinámico según el número de campos
            if(campos.length > 3) { // Es un residuo
                res.put("id", inputs.get("ID").getText());
                res.put("nombre", inputs.get("Nombre").getText());
                res.put("tipo", inputs.get("Tipo").getText());
                res.put("peso", inputs.get("Peso").getText());
                res.put("zona", inputs.get("Zona").getText());
                res.put("prioridad", inputs.get("Prioridad").getText());
            } else { // Es una ruta
                res.put("zona", inputs.get("Zona").getText());
                res.put("idVehiculo", inputs.get("Vehiculo").getText());
                res.put("prioridad", inputs.get("Prioridad Ambiental (uz)").getText());
            }
            return res;
        }
        return null;
    }

    // --- MÉTODOS DE ACTUALIZACIÓN ---

    public void escribir(String msj) {
        areaLog.append("> " + msj + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }

    /**
     * Sincroniza la JTable visual con los datos del TDA.
     * @param lista Lista de residuos obtenida del modelo.
     */
    public void cargarDatosEnTabla(List<Residuo> lista) {
        if (modeloTabla == null) return;
        modeloTabla.setRowCount(0); // Limpiar filas para evitar duplicados
        for (Residuo r : lista) {
            Object[] fila = {
                r.getId(),
                r.getNombre(),
                r.getTipo(),
                String.format("%.2f", r.getPeso()),
                r.getZona(),
                r.getPrioridad() // Se asume que en Residuo.java el método es getPrioridad()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    //Método que genera un reporte visual simple usando rectángulos (BarChart) dentro de un JOptionPane.
    public void mostrarReporteEstadistico(Map<String, Double> datos) {
        if (datos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay datos para generar el reporte.");
            return;
        }

        StringBuilder reporte = new StringBuilder("--- Resumen de Recolección ---\n");
        // Usamos caracteres para simular barras de progreso (HCI eficiente)
        datos.forEach((tipo, peso) -> {
            reporte.append(String.format("%-12s: ", tipo));
            int barras = (int) (peso / 5); // 1 barra por cada 5kg
            reporte.append("█".repeat(Math.max(0, barras)));
            reporte.append(String.format(" %.2f kg\n", peso));
        });

        JOptionPane.showMessageDialog(this, new JScrollPane(new JTextArea(reporte.toString())), 
                "Reporte Estadístico Ambiental", JOptionPane.INFORMATION_MESSAGE);
    }
}