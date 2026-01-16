/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecotrack.modelo.comparadores;

import ecotrack.modelo.Residuo;
import java.util.Comparator;

/**
 *
 * @author 
 */
public class ComparadorPorPeso implements Comparator<Residuo>{
    @Override
    public int compare(Residuo r1, Residuo r2){
        //Compara los valores de tipo double
        if(r1.getPeso() < r2.getPeso()){
            return -1;
        } else if (r1.getPeso() > r2.getPeso()){
            return 1;
        } else {
            return 0;
        }
    }
}
