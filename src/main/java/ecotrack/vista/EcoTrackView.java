/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecotrack.vista;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Componente pasivo responsable de la interacción con el usuario[cite: 127, 128].
 */
public class EcoTrackView {
    private Scanner scanner;

    public EcoTrackView() {
        this.scanner = new Scanner(System.in); // [cite: 130]
    }

    /**
     * Menú actualizado con la opción de rutas.
     */
    public void mostrarMenuPrincipal() {
        System.out.println("\n===== SISTEMA INTELIGENTE ECOTRACK =====");
        System.out.println("1. Registrar nuevo residuo");
        System.out.println("2. Listar residuos (con ordenamiento)");
        System.out.println("3. Despachar proxima ruta (Prioridad)");
        System.out.println("4. Procesar residuo en reciclaje (LIFO)");
        System.out.println("5. Guardar estado del sistema");
        System.out.println("6. Cargar estado del sistema");
        System.out.println("7. Registrar nueva ruta de recoleccion"); // Nueva opción
        System.out.println("0. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    public int getOpcionUsuario() {
        try {
            int opcion = Integer.parseInt(scanner.nextLine()); // [cite: 134]
            return opcion;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Solicita datos para un nuevo residuo[cite: 135].
     */
    public Map<String, String> pedirDatosResiduo() {
        Map<String, String> datos = new HashMap<>();
        System.out.println("\n--- Registro de Nuevo Residuo ---");
        System.out.print("ID unico: ");
        datos.put("id", scanner.nextLine());
        System.out.print("Nombre: ");
        datos.put("nombre", scanner.nextLine());
        System.out.print("Tipo: ");
        datos.put("tipo", scanner.nextLine());
        System.out.print("Peso (kg): ");
        datos.put("peso", scanner.nextLine());
        System.out.print("Zona: ");
        datos.put("zona", scanner.nextLine());
        System.out.print("Prioridad ambiental (1-10): ");
        datos.put("prioridad", scanner.nextLine());
        return datos;
    }

    /**
     * NUEVO MÉTODO: Solicita datos para una nueva ruta[cite: 114].
     * Captura los atributos: zona, prioridad (utilidad ambiental) e ID del vehículo.
     */
    public Map<String, String> pedirDatosRuta() {
        Map<String, String> datos = new HashMap<>();
        System.out.println("\n--- Registro de Nueva Ruta de Recoleccion ---");
        System.out.print("Zona a atender: ");
        datos.put("zona", scanner.nextLine()); // [cite: 115]
        System.out.print("ID del Vehiculo asignado: ");
        datos.put("idVehiculo", scanner.nextLine()); // [cite: 117]
        System.out.print("Prioridad/Utilidad Ambiental (uz): ");
        datos.put("prioridad", scanner.nextLine()); // [cite: 116, 246]
        return datos;
    }

    public void mostrarLista(List<?> lista) {
        System.out.println("\n--- Listado de Elementos ---");
        if (lista.isEmpty() == true) { // Condición sin símbolos complejos
            System.out.println("No hay elementos para mostrar.");
        } else {
            for (Object item : lista) {
                System.out.println(item.toString());
            }
        }
    }

    public int elegirCriterioOrden() {
        System.out.println("\nCriterios de ordenamiento:");
        System.out.println("1. Por Peso");
        System.out.println("2. Por Tipo");
        System.out.println("3. Por Prioridad Ambiental");
        System.out.print("Seleccione: ");
        return getOpcionUsuario();
    }

    public void mostrarMensaje(String msg) {
        System.out.println("[INFO]: " + msg);
    }

    public void mostrarError(String msg) {
        System.err.println("[ERROR]: " + msg);
    }

    public void cerrarScanner() {
        scanner.close(); // [cite: 141]
    }
}
