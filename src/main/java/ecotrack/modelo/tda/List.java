/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecotrack.modelo.tda;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Interfaz base para los TDAs de lista en EcoTrack.
 * Se añade Serializable para asegurar que cualquier lista pueda guardarse en el backup.
 */
public interface List<E> extends Iterable<E>, Serializable {

    /**
     * Inserta un elemento al final de la lista.
     * Complejidad esperada: O(1) para listas circulares con tail.
     */
    void insertar(E data);

    /**
     * Elimina un elemento específico de la lista.
     * Complejidad esperada: O(n).
     */
    boolean eliminar(E data);

    /**
     * Retorna la cantidad de elementos.
     */
    int size();

    /**
     * Verifica si la lista no tiene elementos.
     */
    boolean isEmpty();

    /**
     * Método para obtener el iterador bidireccional.
     * Permite recorrer la lista hacia adelante y hacia atrás.
     */
    BidirectionalIterator<E> bidirectionalIterator();
}