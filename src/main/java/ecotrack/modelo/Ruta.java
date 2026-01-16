/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecotrack.modelo;

import java.io.Serializable;

/**
 *
 * @author 
 */
public class Ruta implements Serializable {
    private String zona;
    private int prioridad;
    private String idVehiculo;

    public Ruta(String zona, int prioridad, String idVehiculo) {
        this.zona = zona;
        this.prioridad = prioridad;
        this.idVehiculo = idVehiculo;
    }

    public String getZona() { 
        return zona;
    }
    public int getPrioridad() { 
        return prioridad;
    }
    public String getIdVehiculo() { 
        return idVehiculo;
    }

    @Override
    public String toString() {
        return "Ruta en " + zona + " (Vehículo: " + idVehiculo + ") - Prioridad: " + prioridad;
    }
}  