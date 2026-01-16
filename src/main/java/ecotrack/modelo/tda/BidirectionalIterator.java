/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecotrack.modelo.tda;

import java.util.Iterator;

/**
 * Interface que extiende el Iterator estándar para permitir 
 * el recorrido hacia atrás, propio de una lista doble.
 */
public interface BidirectionalIterator<T> extends Iterator<T> {
    
    /**
     * Verifica si existe un elemento anterior.
     * En una lista circular, siempre será true si la lista no está vacía.
     */
    boolean hasPrevious();

    /**
     * Retorna el elemento anterior y retrocede el puntero.
     */
    T previous();
}
