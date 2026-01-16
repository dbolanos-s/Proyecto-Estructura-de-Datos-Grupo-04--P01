/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecotrack.modelo;

import ecotrack.modelo.tda.DoublyCircularLinkedList;
import ecotrack.modelo.tda.BidirectionalIterator; 
import ecotrack.modelo.comparadores.*;
import java.util.*;
import java.io.Serializable;

/**
 * Fachada del Modelo corregida.
 * Se asegura de usar iteradores FINITOS para generar las tablas
 * y evita el bloqueo por bucles infinitos.
 */
public class EcoTrackModelo implements Serializable {
    private DoublyCircularLinkedList<Residuo> listaResiduos; 
    private PriorityQueue<Ruta> colaRutas; 
    private Deque<Residuo> centroReciclaje; 
    private Map<String, Double> estadisticas; 
    private Map<String, Double> pesoPorZona = new HashMap<>();
    private Map<String, String> usuarios; 
    private final double UMBRAL_CRITICO = 50.0; 

    public EcoTrackModelo() {
        this.listaResiduos = new DoublyCircularLinkedList<>();
        this.colaRutas = new PriorityQueue<>(getComparadorPrioridadRuta());
        this.centroReciclaje = new ArrayDeque<>(); 
        this.estadisticas = new HashMap<>();
        // Inicializar usuarios por defecto para evitar nulls
        this.usuarios = new HashMap<>();
        this.usuarios.put("admin", "admin");
    }
    
    // --- MÉTODOS DE SEGURIDAD ---

    public boolean registrarUsuario(String correo, String password) {
        if (usuarios.containsKey(correo)) return false; 
        usuarios.put(correo, password);
        return true; 
    }

    public boolean validarCredenciales(String correo, String password) {
        if (usuarios.containsKey(correo)) {
            String passReal = usuarios.get(correo);
            return passReal.equals(password);
        }
        return false;
    }
    
    public void asegurarMapaUsuarios() {
        if (this.usuarios == null) {
            this.usuarios = new HashMap<>();
            this.usuarios.put("admin", "admin");
        }
    }

    // --- MÉTODOS DE OPERACIÓN ---

    public void registrarResiduo(Residuo r) {
        listaResiduos.insertar(r);
        
        // Actualizar estadísticas y pesos
        double pesoActual = estadisticas.getOrDefault(r.getTipo(), 0.0);
        estadisticas.put(r.getTipo(), pesoActual + r.getPeso());
        
        double pesoZona = pesoPorZona.getOrDefault(r.getZona(), 0.0);
        pesoPorZona.put(r.getZona(), pesoZona + r.getPeso());
    }

    public void registrarRuta(Ruta nuevaRuta) {
        colaRutas.add(nuevaRuta); 
    }

    public Ruta despacharProximaRuta() {
        return colaRutas.poll(); 
    }

    public void registrarEnReciclaje(Residuo r) {
        centroReciclaje.push(r); 
    }

    public Residuo procesarResiduoEnReciclaje() {
        if (!centroReciclaje.isEmpty()) {
            return centroReciclaje.pop(); 
        }
        return null;
    }

    // --- CORRECCIÓN CRÍTICA: ITERADORES SEGUROS ---
    
    public List<Residuo> getListaResiduosOrdenada(Comparator<Residuo> c) {
        List<Residuo> copia = new ArrayList<>();
        
        // Usamos el iterator() (El que tiene contador/finito en DoublyCircularLinkedList)
        Iterator<Residuo> it = listaResiduos.iterator();
        while (it.hasNext()) {
            copia.add(it.next());
        }
        
        copia.sort(c); 
        return copia;
    }

    public List<Residuo> getListaResiduosInversa() {
        List<Residuo> copiaInversa = new ArrayList<>();
        
        // CORREGIDO: Antes usabas bidirectionalIterator (Infinito) y eso colgaba el programa.
        // Ahora usamos el iterator() (Finito) y luego invertimos la lista con Java.
        Iterator<Residuo> it = listaResiduos.iterator();
        while (it.hasNext()) {
            copiaInversa.add(it.next());
        }
        
        Collections.reverse(copiaInversa); // Inversión segura
        return copiaInversa;
    }
    
    public String verificarAlertas() {
        for (Map.Entry<String, Double> entry : pesoPorZona.entrySet()) {
            if (entry.getValue() > UMBRAL_CRITICO) {
                return "ALERTA: La zona " + entry.getKey() + " ha superado el umbral con " + entry.getValue() + "kg.";
            }
        }
        return null;
    }

    // --- GETTERS ---
    public DoublyCircularLinkedList<Residuo> getListaResiduos() { return listaResiduos; }
    public PriorityQueue<Ruta> getColaRutas() { return colaRutas; }
    public Deque<Residuo> getCentroReciclaje() { return centroReciclaje; }
    public Map<String, Double> getEstadisticas() { return estadisticas; }

    // --- COMPARADORES ---
    public static Comparator<Residuo> getComparadorPorPeso() { return new ComparadorPorPeso(); }
    public static Comparator<Residuo> getComparadorPorTipo() { return new ComparadorPorTipo(); }
    public static Comparator<Residuo> getComparadorPorPrioridad() { return new ComparadorPorPrioridad(); }
    public static Comparator<Ruta> getComparadorPrioridadRuta() { return new ComparadorPrioridadRuta(); }

    // --- PERSISTENCIA ---
    public boolean guardarEstado() {
        return ecotrack.util.GestorArchivos.guardarEstado(this); 
    }

    public boolean cargarEstado() {
        EcoTrackModelo cargado = ecotrack.util.GestorArchivos.cargarEstado();
        if (cargado != null) {
            this.listaResiduos = cargado.getListaResiduos();
            this.colaRutas = cargado.getColaRutas();
            this.centroReciclaje = cargado.getCentroReciclaje();
            this.estadisticas = cargado.getEstadisticas();
            // Recuperar usuarios de forma segura
            if (cargado.usuarios != null) this.usuarios = cargado.usuarios;
            else { 
                this.usuarios = new HashMap<>(); 
                this.usuarios.put("admin", "admin");
            }
            return true; 
        }
        return false; 
    }
}