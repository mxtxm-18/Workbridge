package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SidebarAdmin extends JPanel {

    static final Color NAVY = new Color(0x24, 0x3A, 0x69);
    static final Color STEEL = new Color(0x5B, 0x88, 0xA5);
    static final Color MAUVE = new Color(0x9B, 0x73, 0xA6);
    static final Color BEIGE = new Color(0xD4, 0xCD, 0xC5);
    static final Color WHITE = Color.WHITE;
    static final Color SIDEBAR_ACTIVE_BG = STEEL;

    private static final Font FONT_NAV = new Font("SansSerif", Font.PLAIN, 13);
    private static final Font FONT_NAV_ACT = new Font("SansSerif", Font.BOLD, 13);
    private static final Font FONT_LABEL = new Font("SansSerif", Font.BOLD, 9);
    private static final Font FONT_SMALL = new Font("SansSerif", Font.PLAIN, 9);

    private static final int SIDEBAR_WIDTH = 220;

    private final WorkBridgeApp app;
    private final String activo;
    private String nombreUsuario = "Admin";
    private String rolEtiqueta = "Administrador";

    public SidebarAdmin(WorkBridgeApp app, String activo) {
        this.app = app;
        this.activo = activo;

        setPreferredSize(new Dimension(SIDEBAR_WIDTH, 0));
        setMinimumSize(new Dimension(SIDEBAR_WIDTH, 0));
        setMaximumSize(new Dimension(SIDEBAR_WIDTH, Integer.MAX_VALUE));
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(crearLogoPanel());
        add(Box.createVerticalStrut(4));

        String rol = (app != null) ? app.getRolSesion() : null;
        cargarDatosUsuario(app, rol);

        if ("admin".equals(rol)) {
            // El rol admin solo debe ver accesos a estas 3 pantallas.
            add(crearNavGroup("PRINCIPAL", new String[][]{
                    {"⊠", "Dashboard Moderador", "dashboardModerador", null},
                    {"◎", "Usuarios", "gestionUsuarios", null},
                    {"▣", "Verificación Empresas", "empresas", null},
            }));
        } else if ("moderador".equals(rol)) {
            // El moderador conserva el set completo de accesos administrativos.
            add(crearNavGroup("PRINCIPAL", new String[][]{
                    {"⊞", "Dashboard", "dashboardAdmin", null},
                    {"⚠", "Reportes", "reportes", "12"},
            }));

            add(crearNavGroup("MODERACIÓN", new String[][]{
                    {"◎", "Usuarios", "gestionUsuarios", "3"},
                    {"▣", "Empresas", "empresas", null},
                    {"⊟", "Vacantes", "vacantes", null},
                    {"◱", "Comunicaciones", "comunicaciones", "5"},
            }));

            add(crearNavGroup("SISTEMA", new String[][]{
                    {"◈", "Estadísticas", "estadisticas", null},
                    {"⚙", "Configuración", "configuracion", null},
            }));

            add(crearNavGroup("CONTENIDO", new String[][]{
                    {"▤", "Publicaciones", "publicaciones", null},
                    {"⎙", "Documentos", "documentos", null},
                    {"✎", "Habilidades", "habilidades", null},
            }));

            add(crearNavGroup("PANELES", new String[][]{
                    {"⌂", "Gestión Empresa", "gestionEmpresa", null},
                    {"⊡", "Dashboard Empresa", "dashboardEmpresa", null},
                    {"⊠", "Dashboard Moderador", "dashboardModerador", null},
                    {"↺", "Registro", "registro", null},
            }));
        } else if ("reclutador".equals(rol)) {
            // Rol empresa: únicamente los accesos que le corresponden.
            add(crearNavGroup("PRINCIPAL", new String[][]{
                    {"⊞", "Dashboard Administrativo", "dashboardAdmin", null},
                    {"⊡", "Dashboard Empresa", "dashboardEmpresa", null},
            }));

            add(crearNavGroup("GESTIÓN", new String[][]{
                    {"⌂", "Gestión Empresa", "gestionEmpresa", null},
                    {"▤", "Publicaciones", "publicaciones", null},
            }));
        } else {
            // Rol usuario (trabajador): únicamente los accesos que le corresponden.
            add(crearNavGroup("PRINCIPAL", new String[][]{
                    {"▤", "Publicaciones", "publicaciones", null},
                    {"⎙", "Documentos", "documentos", null},
                    {"✎", "Habilidades", "habilidades", null},
            }));
        }

        add(Box.createVerticalGlue());
        add(crearFooter());
    }

    public void setNombreUsuario(String nombre) {
        this.nombreUsuario = nombre;
        repaint();
    }

    // Convierte el valor de la columna "rol" en un texto legible para el footer.
    private String etiquetaParaRol(String rol) {
        if (rol == null) return "Usuario";
        return switch (rol) {
            case "admin" -> "Administrador";
            case "moderador" -> "Moderador";
            case "reclutador" -> "Empresa";
            case "trabajador" -> "Trabajador";
            default -> rol.substring(0, 1).toUpperCase() + rol.substring(1);
        };
    }

    // Consulta en la base de datos el nombre real del usuario en sesión
    // (tabla "usuarios") y actualiza el nombre y la etiqueta de rol del footer.
    private void cargarDatosUsuario(WorkBridgeApp app, String rolSesion) {
        rolEtiqueta = etiquetaParaRol(rolSesion);

        String usuarioId = (app != null) ? app.getUsuarioIdSesion() : null;
        if (usuarioId == null) {
            return; // No hay sesión activa; se conservan los valores por defecto.
        }

        String sql = "SELECT nombre, apellido, rol FROM usuarios WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    String nombreCompleto = (nombre != null ? nombre : "").trim();
                    if (apellido != null && !apellido.isBlank()) {
                        nombreCompleto = (nombreCompleto + " " + apellido).trim();
                    }
                    if (!nombreCompleto.isEmpty()) {
                        nombreUsuario = nombreCompleto;
                    }
                    String rolBd = rs.getString("rol");
                    if (rolBd != null && !rolBd.isBlank()) {
                        rolEtiqueta = etiquetaParaRol(rolBd);
                    }
                }
            }
        } catch (SQLException ex) {
            // Si falla la consulta (BD no disponible, etc.) se conservan los
            // valores por defecto para no romper la interfaz.
            System.err.println("No se pudo cargar el usuario del sidebar: " + ex.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(NAVY);
        g2.fillRect(0, 0, getWidth(), getHeight());

        GradientPaint gp = new GradientPaint(
                0, getHeight() - 200, new Color(155, 115, 166, 0),
                0, getHeight(), new Color(155, 115, 166, 80));
        g2.setPaint(gp);
        g2.fillRect(0, getHeight() - 200, getWidth(), 200);

        g2.setColor(new Color(91, 136, 165, 40));
        g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{4, 6}, 0));
        for (int y = 20; y < getHeight(); y += 12) {
            g2.drawLine(getWidth() - 1, y, getWidth() - 1, y + 4);
        }
    }

    private JPanel crearLogoPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 14)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(91, 136, 165, 50));
                g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            }
        };
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(SIDEBAR_WIDTH, 90));

        URL urlLogo = getClass().getResource("/Recursos/logo.png");
        JLabel lblLogo;
        if (urlLogo != null) {
            Image img = new ImageIcon(urlLogo).getImage().getScaledInstance(160, -1, Image.SCALE_SMOOTH);
            lblLogo = new JLabel(new ImageIcon(img));
        } else {
            lblLogo = new JLabel("WorkBridge");
            lblLogo.setFont(new Font("SansSerif", Font.BOLD, 16));
            lblLogo.setForeground(WHITE);
        }
        panel.add(lblLogo);
        return panel;
    }

    private JPanel crearNavGroup(String seccion, String[][] items) {
        JPanel group = new JPanel();
        group.setOpaque(false);
        group.setLayout(new BoxLayout(group, BoxLayout.Y_AXIS));
        group.setBorder(BorderFactory.createEmptyBorder(14, 0, 0, 0));
        group.setMaximumSize(new Dimension(SIDEBAR_WIDTH, 999));

        JLabel lbl = new JLabel(seccion, SwingConstants.CENTER);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(new Color(180, 190, 210, 140));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        lbl.setMaximumSize(new Dimension(SIDEBAR_WIDTH, 20));
        group.add(lbl);

        for (String[] item : items) {
            group.add(crearNavItem(item[0], item[1], item[2], item[3]));
        }
        return group;
    }

    private JPanel crearNavItem(String icono, String texto, String pantalla, String badge) {
        boolean esActivo = pantalla.equals(activo);

        JPanel row = new JPanel(new BorderLayout()) {
            boolean hovered = false;

            {
                setOpaque(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
                    @Override public void mouseExited(MouseEvent e) { hovered = false; repaint(); }
                    @Override public void mouseClicked(MouseEvent e) {
                        if (app != null) {
                            app.mostrarPantalla(pantalla);
                        }
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (esActivo) {
                    g2.setColor(SIDEBAR_ACTIVE_BG);
                    g2.fillRoundRect(8, 2, getWidth() - 16, getHeight() - 4, 10, 10);
                    g2.setColor(BEIGE);
                    g2.fillRoundRect(8, getHeight() / 2 - 10, 3, 20, 2, 2);
                } else if (hovered) {
                    g2.setColor(new Color(255, 255, 255, 18));
                    g2.fillRoundRect(8, 2, getWidth() - 16, getHeight() - 4, 10, 10);
                }
            }
        };

        row.setMaximumSize(new Dimension(SIDEBAR_WIDTH, 38));
        row.setPreferredSize(new Dimension(SIDEBAR_WIDTH, 38));
        row.setBorder(BorderFactory.createEmptyBorder(2, 16, 2, 12));

        JLabel iconLabel = new JLabel(icono + "  " + texto);
        iconLabel.setFont(esActivo ? FONT_NAV_ACT : FONT_NAV);
        iconLabel.setForeground(esActivo ? WHITE : new Color(255, 255, 255, 170));
        row.add(iconLabel, BorderLayout.CENTER);

        if (badge != null) {
            JLabel badgeLbl = new JLabel(badge) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(MAUVE);
                    int size = Math.min(getWidth(), getHeight());
                    g2.fillOval((getWidth() - size) / 2, (getHeight() - size) / 2, size, size);
                    super.paintComponent(g);
                }
            };
            badgeLbl.setFont(new Font("SansSerif", Font.BOLD, 10));
            badgeLbl.setForeground(WHITE);
            badgeLbl.setHorizontalAlignment(SwingConstants.CENTER);
            badgeLbl.setOpaque(false);
            int sz = 22;
            badgeLbl.setPreferredSize(new Dimension(sz, sz));
            badgeLbl.setMinimumSize(new Dimension(sz, sz));
            badgeLbl.setMaximumSize(new Dimension(sz, sz));

            JPanel badgeWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 7));
            badgeWrap.setOpaque(false);
            badgeWrap.add(badgeLbl);
            row.add(badgeWrap, BorderLayout.EAST);
        }

        return row;
    }

    private JPanel crearFooter() {
        JPanel footer = new JPanel(new BorderLayout(10, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(91, 136, 165, 50));
                g.drawLine(0, 0, getWidth(), 0);
            }
        };
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createEmptyBorder(12, 14, 16, 14));
        footer.setMaximumSize(new Dimension(SIDEBAR_WIDTH, 70));

        JLabel avatar = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, MAUVE, getWidth(), getHeight(), STEEL));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 13));
                String ini = nombreUsuario.isEmpty() ? "A" : String.valueOf(nombreUsuario.charAt(0)).toUpperCase();
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(ini, (getWidth() - fm.stringWidth(ini)) / 2, (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        avatar.setPreferredSize(new Dimension(34, 34));
        avatar.setOpaque(false);

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel lblNombre = new JLabel(nombreUsuario);
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblNombre.setForeground(WHITE);

        JLabel lblRol = new JLabel(rolEtiqueta);
        lblRol.setFont(FONT_SMALL);
        lblRol.setForeground(new Color(212, 205, 197, 150));

        info.add(lblNombre);
        info.add(lblRol);

        JButton btnSalir = new JButton("Salir") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? MAUVE.darker() : MAUVE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                super.paintComponent(g);
            }
        };
        btnSalir.setFont(FONT_LABEL);
        btnSalir.setForeground(WHITE);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setBorderPainted(false);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSalir.addActionListener(e -> { if (app != null) app.cerrarSesion(); });

        footer.add(avatar, BorderLayout.WEST);
        footer.add(info, BorderLayout.CENTER);
        footer.add(btnSalir, BorderLayout.EAST);
        return footer;
    }
}