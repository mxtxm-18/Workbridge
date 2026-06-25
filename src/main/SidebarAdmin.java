package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class SidebarAdmin extends JPanel {

    private final WorkBridgeApp app;

    static final Color NAVY  = new Color(0x24, 0x3A, 0x69);
    static final Color STEEL = new Color(0x5B, 0x88, 0xA5);
    static final Color MAUVE = new Color(0x9B, 0x73, 0xA6);
    static final Color WHITE = Color.WHITE;

    private final String activo;

    public SidebarAdmin(WorkBridgeApp app, String activo) {
        this.app = app;
        this.activo = activo;

        setPreferredSize(new Dimension(320, 0));
        setBackground(NAVY);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(20));

        JLabel logo = new JLabel("WorkBridge");
        logo.setFont(new Font("SansSerif", Font.BOLD, 22));
        logo.setForeground(WHITE);
        logo.setAlignmentX(CENTER_ALIGNMENT);
        add(logo);

        add(Box.createVerticalStrut(25));

        add(menu("Dashboard", "dashboardAdmin"));
        add(menu("Usuarios", "gestionUsuarios"));
        add(menu("Empresas", "empresas"));
        add(menu("Habilidades", "habilidades"));
        add(menu("Documentos", "documentos"));
        add(menu("Notificaciones", "notificaciones"));
        add(menu("Comunicaciones", "comunicaciones"));

        add(Box.createVerticalGlue());

        JButton salir = new JButton("Cerrar sesión");
        salir.setAlignmentX(CENTER_ALIGNMENT);
        salir.setBackground(MAUVE);
        salir.setForeground(Color.WHITE);
        salir.setFocusPainted(false);
        salir.addActionListener(e -> app.cerrarSesion());
        add(salir);

        add(Box.createVerticalStrut(20));
    }

    private JButton menu(String texto, String pantalla) {
        JButton btn = new JButton(texto);

        btn.setMaximumSize(new Dimension(280, 45));
        btn.setAlignmentX(CENTER_ALIGNMENT);

        boolean isActive = pantalla.equals(activo);

        btn.setBackground(isActive ? MAUVE : STEEL);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        btn.addActionListener(e -> app.mostrarPantalla(pantalla));

        return btn;
    }
}