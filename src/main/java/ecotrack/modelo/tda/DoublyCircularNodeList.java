/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecotrack.modelo.tda;

import java.io.Serializable;

/**
 *
 * @author Grupo 04
 */
public class DoublyCircularNodeList<E> implements Serializable {
    private E data;
    private DoublyCircularNodeList<E> next;
    private DoublyCircularNodeList<E> prev;
    
    /*
        CONSTRUCTOR
        - Inicializa el nodo con data
    */
    public DoublyCircularNodeList(E data){
        this.data = data;
        this.next = this;
        this.prev = this;
    }
    // GETTERS Y SETTERS
    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public DoublyCircularNodeList<E> getNext() {
        return next;
    }
    
    /**
     * Setea el siguiente nodo. No fuerza circularidad global (eso lo gestiona la lista),
     * pero evita dejar el nodo apuntando a null por accidente.
     */
    public void setNext(DoublyCircularNodeList<E> next) {
        //Caso base: Si el siguiente es nulo lanza una excepcion
        if(next == null){
            throw new IllegalArgumentException("Next no puede ser null en una lista circular");
        }
        this.next = next;
    }

    public DoublyCircularNodeList<E> getPrev() {
        return prev;
    }

    public void setPrev(DoublyCircularNodeList<E> prev) {
        if(prev == null){
            throw new IllegalArgumentException("Prev no puede ser null en una lista circular");
        }
        this.prev = prev;
    }
    
    /**
     * Devuelve true si el nodo está aislado como ciclo de 1 (next y prev apuntan a sí mismo).
     */
    public boolean isAloneCycle(){
        return this.next == this && this.prev == this;
    }
    
    /**
     * Desconecta el nodo del ciclo dejándolo como ciclo de 1.
     * Útil al remover un nodo desde la lista para "limpiar" referencias.
     */
    public void detachToAloneCycle(){
        this.next = this;
        this.prev = this;
    }
    
    @Override
    public String toString(){
        return String.valueOf(data);
    }
    
}
