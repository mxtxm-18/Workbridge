package main;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class WorkBridgeApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Map<String, Component> screens;

    private String usuarioIdSesion = null;
    private String rolSesion = null;

    public WorkBridgeApp() {
        setTitle("Work Bridge");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(1920, 1080));
        setMinimumSize(new Dimension(1920, 1080));
        setSize(1920, 1080);

        setLocationRelativeTo(null);
        setResizable(true);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        screens = new HashMap<>();

        initializeScreens();
        setContentPane(mainPanel);
        mostrarPantalla("registro");
    }

    private void initializeScreens() {
        screens.put("registro", new Registro(this));
        screens.put("publicaciones", new Publicaciones(this));
        screens.put("perfilTrabajador", new PerfilPublicoTrabajador(this));
        screens.put("documentos", new GestionDocumentos(this));
        screens.put("notificaciones", new Notificaciones(this));
        screens.put("comunicaciones", new Comunicaciones(this));
        screens.put("dashboardAdmin", new DashboardAdmin(this));
        screens.put("gestionUsuarios", new GestionUsuarios(this));
        screens.put("empresas", new VerificacionEmpresas(this));
        screens.put("habilidades", new GestionHabilidades(this));
        screens.put("dashboardEmpresa", crearDashboardEmpresa());
        screens.put("dashboardModerador", new DashboardModerador(this));

        for (Map.Entry<String, Component> entry : screens.entrySet()) {
            mainPanel.add(entry.getValue(), entry.getKey());
        }
    }

    public void reemplazarPantalla(String nombre, JPanel nuevoPanel) {
        if (screens.containsKey(nombre)) {
            mainPanel.remove(screens.get(nombre));
        }
        screens.put(nombre, nuevoPanel);
        mainPanel.add(nuevoPanel, nombre);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void mostrarPantalla(String nombrePantalla) {
        if (screens.containsKey(nombrePantalla)) {
            cardLayout.show(mainPanel, nombrePantalla);
        } else {
            System.err.println("Pantalla no encontrada: " + nombrePantalla);
        }
    }

    public void iniciarSesion(String usuarioId, String rol) {
        this.usuarioIdSesion = usuarioId;
        this.rolSesion = rol;

        reemplazarPantalla("publicaciones", new Publicaciones(this));
        reemplazarPantalla("perfilTrabajador", new PerfilPublicoTrabajador(this));
        reemplazarPantalla("documentos", new GestionDocumentos(this));
        reemplazarPantalla("notificaciones", new Notificaciones(this));
        reemplazarPantalla("comunicaciones", new Comunicaciones(this));
        reemplazarPantalla("dashboardAdmin", new DashboardAdmin(this));
        reemplazarPantalla("gestionUsuarios", new GestionUsuarios(this));
        reemplazarPantalla("empresas", new VerificacionEmpresas(this));
        reemplazarPantalla("habilidades", new GestionHabilidades(this));
        reemplazarPantalla("dashboardModerador", new DashboardModerador(this));

        switch (rol) {
            case "admin" -> mostrarPantalla("dashboardAdmin");
            case "reclutador" -> mostrarPantalla("dashboardEmpresa");
            case "moderador" -> mostrarPantalla("dashboardModerador");
            default -> mostrarPantalla("publicaciones");
        }
    }

    public String getUsuarioIdSesion() {
        return usuarioIdSesion;
    }

    public String getRolSesion() {
        return rolSesion;
    }

    private JPanel crearDashboardEmpresa() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel lblLogo = Recursos.crearLabelLogo(180, 70, "WorkBridge");
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        panel.add(lblLogo, BorderLayout.NORTH);

        JLabel label = new JLabel("Dashboard Empresarial");
        label.setFont(new Font("Segoe UI", Font.BOLD, 28));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);

        JButton btnSalir = new JButton("Cerrar sesión");
        btnSalir.addActionListener(e -> cerrarSesion());
        btnSalir.setPreferredSize(new Dimension(160, 36));

        JPanel sur = new JPanel();
        sur.add(btnSalir);
        panel.add(sur, BorderLayout.SOUTH);

        return panel;
    }

    public void cerrarSesion() {
        usuarioIdSesion = null;
        rolSesion = null;
        mostrarPantalla("registro");
    }
}