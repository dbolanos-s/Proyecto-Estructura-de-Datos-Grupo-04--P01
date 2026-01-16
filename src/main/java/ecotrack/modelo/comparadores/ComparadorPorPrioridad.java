/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecotrack.modelo.comparadores;

import ecotrack.modelo.Residuo;
import java.util.Comparator;
import java.io.Serializable;

public class ComparadorPorPrioridad implements Comparator<Residuo>, Serializable {
    @Override
    public int compare(Residuo r1, Residuo r2) {
        // Ahora r1 y r2 ya reconocen getPrioridad()
        return Integer.compare(r1.getPrioridad(), r2.getPrioridad());
    }
}