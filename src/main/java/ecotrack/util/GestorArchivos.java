/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecotrack.util;

import ecotrack.modelo.EcoTrackModelo;
import java.io.*;

/**
 * Clase de utilidad para manejar la persistencia en archivos binarios[cite: 122, 289].
 */
public class GestorArchivos {
    private static final String ARCHIVO_DATOS = "datos_ecotrack.bin";

    /**
     * Guarda el estado del modelo. Retorna boolean para feedback en GUI.
     */
    public static boolean guardarEstado(EcoTrackModelo modelo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DATOS))) {
            out.writeObject(modelo);
            out.flush();
            return true; // Éxito
        } catch (IOException e) {
            e.printStackTrace(); // Mantiene registro en consola para el desarrollador
            return false; // Fallo
        }
    }

    /**
     * Carga el estado del modelo.
     */
    public static EcoTrackModelo cargarEstado() {
        File archivo = new File(ARCHIVO_DATOS);
        if (!archivo.exists()) return null;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ARCHIVO_DATOS))) {
            return (EcoTrackModelo) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}