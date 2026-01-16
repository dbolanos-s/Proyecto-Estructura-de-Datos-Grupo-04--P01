/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecotrack.modelo.comparadores;

import ecotrack.modelo.Residuo;
import java.util.Comparator;

/**
 *
 * @author mildr
 */
public class ComparadorPorTipo implements Comparator<Residuo>{
    @Override
    public int compare(Residuo r1, Residuo r2){
        // Utilizamos el metodo compareTo de la clase String
        return r1.getTipo().compareTo(r2.getTipo());
    }
}
