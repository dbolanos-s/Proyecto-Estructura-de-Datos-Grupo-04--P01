/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecotrack.modelo.comparadores;

import ecotrack.modelo.Ruta;
import java.util.Comparator;
import java.io.Serializable;

public class ComparadorPrioridadRuta implements Comparator<Ruta>, Serializable {
    @Override
    public int compare(Ruta r1, Ruta r2) {
        /**
         * Lógica EcoTrack: Una zona con utilidad baja (foco crítico) debe 
         * ser atendida PRIMERO[cite: 250, 264].
         * Por lo tanto, se comporta como un Min-Heap basado en la prioridad (u_z).
         */
        if (r1.getPrioridad() < r2.getPrioridad()) {
            return -1; // r1 sale primero
        } else if (r1.getPrioridad() > r2.getPrioridad()) {
            return 1;
        } else {
            return 0;
        }
    }
}