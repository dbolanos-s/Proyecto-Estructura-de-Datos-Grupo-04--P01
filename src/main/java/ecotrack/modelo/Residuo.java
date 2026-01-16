/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecotrack.modelo;

import java.io.Serializable;
import java.util.Date;

public class Residuo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String nombre;
    private String tipo;
    private double peso;
    private Date fechaRecoleccion;
    private String zona;
    private int prioridad; // Nombre estandarizado

    public Residuo(String id, String nombre, String tipo, double peso, String zona, int prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.peso = peso;
        this.fechaRecoleccion = new Date();
        this.zona = zona;
        this.prioridad = prioridad;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public double getPeso() { return peso; }
    public String getZona() { return zona; }
    
    // ESTE MÉTODO ES EL QUE BUSCA LA GUI
    public int getPrioridad() { 
        return prioridad; 
    }

    @Override
    public String toString() {
        return String.format("Residuo[ID=%s, Tipo=%s, Peso=%.2fkg, Zona=%s, Prioridad=%d]", 
                id, tipo, peso, zona, prioridad);
    }
}
