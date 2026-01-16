/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecotrack.modelo.tda;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * TDA de Lista Circular Doblemente Enlazada.
 * CORREGIDO: Separa la lógica finita (para tablas) de la infinita (para botones).
 */
public class DoublyCircularLinkedList<E> implements List<E>, Iterable<E>, Serializable {
    private static final long serialVersionUID = 1L;
    private DoublyCircularNodeList<E> tail;
    private int size;

    public DoublyCircularLinkedList() {
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void insertar(E data) {
        DoublyCircularNodeList<E> nuevo = new DoublyCircularNodeList<>(data);
        if (isEmpty()) {
            tail = nuevo;
            tail.setNext(tail);
            tail.setPrev(tail);
        } else {
            DoublyCircularNodeList<E> head = tail.getNext();
            nuevo.setPrev(tail);
            nuevo.setNext(head);
            tail.setNext(nuevo);
            head.setPrev(nuevo);
            tail = nuevo;
        }
        size++;
    }

    @Override
    public boolean eliminar(E data) {
        if (isEmpty()) return false;
        DoublyCircularNodeList<E> actual = tail.getNext();
        for (int i = 0; i < size; i++) {
            E contenido = actual.getData();
            if ((contenido == null && data == null) || (contenido != null && contenido.equals(data))) {
                if (size == 1) {
                    tail = null;
                } else {
                    DoublyCircularNodeList<E> anterior = actual.getPrev();
                    DoublyCircularNodeList<E> siguiente = actual.getNext();
                    anterior.setNext(siguiente);
                    siguiente.setPrev(anterior);
                    if (actual == tail) tail = anterior;
                }
                size--;
                return true;
            }
            actual = actual.getNext();
        }
        return false;
    }

    @Override
    public int size() { return size; }
    @Override
    public boolean isEmpty() { return tail == null; }

    // --- ITERADOR FINITO (Para la Tabla y Ordenamientos) ---
    // Este iterador SÍ se detiene cuando count == size.
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private DoublyCircularNodeList<E> actual = (tail != null) ? tail.getNext() : null;
            private int count = 0; // CONTADOR DE SEGURIDAD

            @Override
            public boolean hasNext() {
                return count < size && tail != null;
            }

            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                E data = actual.getData();
                actual = actual.getNext();
                count++; // Aumentamos contador
                return data;
            }
        };
    }

    // --- ITERADOR INFINITO (Para los Botones de la GUI) ---
    // Este iterador NO tiene contador, permite navegar r3 -> r1 -> r2 eternamente.
    @Override
    public BidirectionalIterator<E> bidirectionalIterator() {
        return new BidirectionalIterator<E>() {
            private DoublyCircularNodeList<E> actual = (tail != null) ? tail.getNext() : null;

            @Override
            public boolean hasNext() {
                return tail != null; // Siempre true si hay lista
            }

            @Override
            public E next() {
                if (tail == null) throw new NoSuchElementException("Lista vacía");
                E data = actual.getData();
                actual = actual.getNext(); // Círculo infinito
                return data;
            }

            @Override
            public boolean hasPrevious() {
                return tail != null; // Siempre true si hay lista
            }

            @Override
            public E previous() {
                if (tail == null) throw new NoSuchElementException("Lista vacía");
                actual = actual.getPrev(); // Círculo infinito inverso
                return actual.getData();
            }
        };
    }
}