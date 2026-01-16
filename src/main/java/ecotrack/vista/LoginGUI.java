/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecotrack.vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Interfaz de Acceso
 */
public class LoginGUI extends JFrame {
    
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // --- Componentes Públicos para el Controlador ---
    // Panel Login
    public JTextField txtLoginUser;
    public JPasswordField txtLoginPass;
    public JButton btnEntrar;
    public JButton btnIrRegistro;

    // Panel Registro
    public JTextField txtRegUser;
    public JPasswordField txtRegPass;
    public JButton btnRegistrar;
    public JButton btnVolver;

    // Colores del Tema
    private final Color COLOR_FONDO = new Color(33, 37, 41); // Gris oscuro sidebar
    private final Color COLOR_TEXTO = Color.WHITE;
    private final Color COLOR_BTN = new Color(60, 63, 65);
    private final Color COLOR_INPUT = new Color(240, 240, 240);

    public LoginGUI() {
        setTitle("Acceso - EcoTrack");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Crear las dos vistas
        mainPanel.add(crearPanelLogin(), "LOGIN");
        mainPanel.add(crearPanelRegistro(), "REGISTRO");

        add(mainPanel);
    }

    private JPanel crearPanelLogin() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("Iniciar Sesión");
        estilizarTitulo(title);

        txtLoginUser = new JTextField();
        txtLoginPass = new JPasswordField();
        estilizarInput(txtLoginUser);
        estilizarInput(txtLoginPass);

        btnEntrar = new JButton("Ingresar al Sistema");
        btnIrRegistro = new JButton("Crear nueva cuenta");
        estilizarBoton(btnEntrar, true);   // Botón principal
        estilizarBoton(btnIrRegistro, false); // Botón secundario

        // Añadir componentes con espaciado
        panel.add(Box.createVerticalGlue());
        panel.add(title);
        panel.add(Box.createVerticalStrut(40));
        panel.add(crearLabel("Usuario / Correo:"));
        panel.add(txtLoginUser);
        panel.add(Box.createVerticalStrut(20));
        panel.add(crearLabel("Contraseña:"));
        panel.add(txtLoginPass);
        panel.add(Box.createVerticalStrut(40));
        panel.add(btnEntrar);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnIrRegistro);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("Registro");
        estilizarTitulo(title);

        txtRegUser = new JTextField();
        txtRegPass = new JPasswordField();
        estilizarInput(txtRegUser);
        estilizarInput(txtRegPass);

        btnRegistrar = new JButton("Confirmar Registro");
        btnVolver = new JButton("<< Volver al Login");
        estilizarBoton(btnRegistrar, true);
        estilizarBoton(btnVolver, false);

        panel.add(Box.createVerticalGlue());
        panel.add(title);
        panel.add(Box.createVerticalStrut(40));
        panel.add(crearLabel("Nuevo Usuario:"));
        panel.add(txtRegUser);
        panel.add(Box.createVerticalStrut(20));
        panel.add(crearLabel("Nueva Contraseña:"));
        panel.add(txtRegPass);
        panel.add(Box.createVerticalStrut(40));
        panel.add(btnRegistrar);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnVolver);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    // --- Métodos de Estilo---
    
    private void estilizarTitulo(JLabel lbl) {
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lbl.setForeground(COLOR_TEXTO);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private JLabel crearLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(new Color(200, 200, 200)); // Gris claro
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    private void estilizarInput(JComponent input) {
        input.setMaximumSize(new Dimension(300, 35));
        input.setBackground(COLOR_INPUT);
        input.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ((JComponent)input).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void estilizarBoton(JButton btn, boolean principal) {
        btn.setMaximumSize(new Dimension(300, 40));
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        if (principal) {
            btn.setBackground(new Color(60, 140, 180)); // Azul acero
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(new Color(50, 50, 50)); // Gris oscuro
            btn.setForeground(Color.WHITE);
        }
    }

    // Navegación entre paneles
    public void mostrarLogin() { cardLayout.show(mainPanel, "LOGIN"); }
    public void mostrarRegistro() { cardLayout.show(mainPanel, "REGISTRO"); }
}
