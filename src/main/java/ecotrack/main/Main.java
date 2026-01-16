/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecotrack.main;

import ecotrack.controlador.EcoTrackControlador;
import ecotrack.modelo.EcoTrackModelo;
import ecotrack.vista.EcoTrackGUI;
import ecotrack.vista.LoginGUI; // Importar

public class Main {
    public static void main(String[] args) {
        // 1. Instanciar Modelo
        EcoTrackModelo modelo = new EcoTrackModelo();
        
        // 2. Intentar cargar persistencia
        EcoTrackModelo cargado = ecotrack.util.GestorArchivos.cargarEstado();
        if (cargado != null) {
            modelo = cargado; // Recuperamos datos y usuarios
        }

        // 3. Instanciar Vistas
        EcoTrackGUI dashboard = new EcoTrackGUI();
        LoginGUI login = new LoginGUI(); // Nueva pantalla oscura

        // 4. Iniciar Controlador
        EcoTrackControlador ctrl = new EcoTrackControlador(modelo, dashboard, login);
        
        // 5. Arrancar
        ctrl.iniciarAplicacion();
    }
}
