/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecotrack.modelo.tda;

import java.util.NoSuchElementException;

/**
 * Iterador para navegación infinita en Listas Circulares.
 * Permite recorrer la lista en bucle sin detenerse al completar una vuelta.
 * Implementa BidirectionalIterator para soportar previous() y hasPrevious().
 */
public class DoublyCircularIterator<E> implements BidirectionalIterator<E> {
    
    private DoublyCircularNodeList<E> actual;

    // Mantenemos el constructor con 'size' para compatibilidad, pero no lo usamos para frenar.
    public DoublyCircularIterator(DoublyCircularNodeList<E> tail, int size) {
        if (tail == null || size == 0) {
            this.actual = null;
        } else {
            // En una lista circular, el primero es el siguiente del tail
            this.actual = tail.getNext();
        }
    }

    @Override
    public boolean hasNext() {
        // En un carrusel infinito, siempre hay siguiente si la lista no está vacía
        return actual != null;
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException("La lista está vacía.");
        }
        
        E data = actual.getData();
        actual = actual.getNext(); // Avanza el puntero
        // No incrementamos ningún contador para permitir vueltas infinitas
        return data;
    }

    @Override
    public boolean hasPrevious() {
        // En un carrusel infinito, siempre hay anterior si la lista no está vacía
        return actual != null;
    }

    @Override
    public E previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException("La lista está vacía.");
        }
        
        // Retrocede el puntero primero
        actual = actual.getPrev(); 
        return actual.getData();
    }
}