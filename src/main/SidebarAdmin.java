package main;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.net.URL;

public class SidebarAdmin extends JPanel {

    // ─── Paleta ─────────────────────────────────────────────────────────────
    static final Color NAVY              = new Color(0x24, 0x3A, 0x69);
    static final Color STEEL             = new Color(0x5B, 0x88, 0xA5);
    static final Color MAUVE             = new Color(0x9B, 0x73, 0xA6);
    static final Color BEIGE             = new Color(0xD4, 0xCD, 0xC5);
    static final Color WHITE             = Color.WHITE;
    static final Color SIDEBAR_ACTIVE_BG = STEEL; // celeste como en la imagen

    private static final Font FONT_NAV     = new Font("SansSerif", Font.PLAIN, 13);
    private static final Font FONT_NAV_ACT = new Font("SansSerif", Font.BOLD,  13);
    private static final Font FONT_LABEL   = new Font("SansSerif", Font.BOLD,   9);
    private static final Font FONT_SMALL   = new Font("SansSerif", Font.PLAIN,  9);

    private static final int SIDEBAR_WIDTH = 220;

    private final WorkBridgeApp app;
    private final String activo;
    private String nombreUsuario = "Admin";

    public SidebarAdmin(WorkBridgeApp app, String activo) {
        this.app    = app;
        this.activo = activo;

        setPreferredSize(new Dimension(SIDEBAR_WIDTH, 0));
        setMinimumSize(new Dimension(SIDEBAR_WIDTH, 0));
        setMaximumSize(new Dimension(SIDEBAR_WIDTH, Integer.MAX_VALUE));
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(crearLogoPanel());
        add(Box.createVerticalStrut(4));

        // ── PRINCIPAL ──
        add(crearNavGroup("PRINCIPAL", new String[][]{
            {"⊞", "Dashboard",      "dashboardAdmin",  null},
            {"⚠", "Reportes",       "reportes",        "12"},
        }));

        // ── MODERACIÓN ──
        add(crearNavGroup("MODERACIÓN", new String[][]{
            {"◎", "Usuarios",       "gestionUsuarios", "3"},
            {"▣", "Empresas",       "empresas",        null},
            {"⊟", "Vacantes",       "vacantes",        null},
            {"◱", "Comunicaciones", "comunicaciones",  "5"},
        }));

        // ── SISTEMA ──
        add(crearNavGroup("SISTEMA", new String[][]{
            {"◈", "Estadísticas",  "estadisticas",    null},
            {"⚙", "Configuración", "configuracion",   null},
        }));

        add(Box.createVerticalGlue());
        add(crearFooter());
    }

    public void setNombreUsuario(String nombre) {
        this.nombreUsuario = nombre;
        repaint();
    }

    // ─── Fondo NAVY + degradado lavanda + líneas decorativas ────────────────

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        // Base NAVY
        g2.setColor(NAVY);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Degradado lavanda inferior
        GradientPaint gp = new GradientPaint(
                0, getHeight() - 200, new Color(155, 115, 166, 0),
                0, getHeight(),       new Color(155, 115, 166, 80));
        g2.setPaint(gp);
        g2.fillRect(0, getHeight() - 200, getWidth(), 200);

        // Líneas punteadas en borde derecho
        g2.setColor(new Color(91, 136, 165, 40));
        g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND, 0, new float[]{4, 6}, 0));
        for (int y = 20; y < getHeight(); y += 12)
            g2.drawLine(getWidth() - 1, y, getWidth() - 1, y + 4);
    }

    // ─── Logo ────────────────────────────────────────────────────────────────

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
            Image img = new ImageIcon(urlLogo).getImage()
                    .getScaledInstance(160, -1, Image.SCALE_SMOOTH);
            lblLogo = new JLabel(new ImageIcon(img));
        } else {
            lblLogo = new JLabel("WorkBridge");
            lblLogo.setFont(new Font("SansSerif", Font.BOLD, 16));
            lblLogo.setForeground(WHITE);
        }
        panel.add(lblLogo);
        return panel;
    }

    // ─── Grupo de navegación ─────────────────────────────────────────────────

    private JPanel crearNavGroup(String seccion, String[][] items) {
        JPanel group = new JPanel();
        group.setOpaque(false);
        group.setLayout(new BoxLayout(group, BoxLayout.Y_AXIS));
        group.setBorder(BorderFactory.createEmptyBorder(14, 0, 0, 0));
        group.setMaximumSize(new Dimension(SIDEBAR_WIDTH, 999));

        // Etiqueta de sección — centrada, gris tenue como en la imagen
        JLabel lbl = new JLabel(seccion, SwingConstants.CENTER);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(new Color(180, 190, 210, 140));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        lbl.setMaximumSize(new Dimension(SIDEBAR_WIDTH, 20));
        group.add(lbl);

        for (String[] item : items)
            group.add(crearNavItem(item[0], item[1], item[2], item[3]));

        return group;
    }

    // ─── Ítem de navegación ──────────────────────────────────────────────────

    private JPanel crearNavItem(String icono, String texto,
                                 String pantalla, String badge) {
        boolean esActivo = pantalla.equals(activo);

        JPanel row = new JPanel(new BorderLayout()) {
            boolean hovered = false;
            {
                setOpaque(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                    @Override public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
                    @Override public void mouseClicked(MouseEvent e) {
                        app.mostrarPantalla(pantalla);
                    }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                if (esActivo) {
                    // Fondo celeste redondeado (igual que la imagen)
                    g2.setColor(SIDEBAR_ACTIVE_BG);
                    g2.fillRoundRect(8, 2, getWidth() - 16, getHeight() - 4, 10, 10);
                    // Barra BEIGE izquierda
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

        // Icono + texto
        JLabel iconLabel = new JLabel(icono + "  " + texto);
        iconLabel.setFont(esActivo ? FONT_NAV_ACT : FONT_NAV);
        iconLabel.setForeground(esActivo ? WHITE : new Color(255, 255, 255, 170));
        row.add(iconLabel, BorderLayout.CENTER);

        // Badge circular morado (igual que en la imagen)
        if (badge != null) {
            JLabel badgeLbl = new JLabel(badge) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);
                    // Círculo morado
                    g2.setColor(MAUVE);
                    int size = Math.min(getWidth(), getHeight());
                    g2.fillOval((getWidth() - size) / 2,
                                (getHeight() - size) / 2,
                                size, size);
                    super.paintComponent(g);
                }
            };
            badgeLbl.setFont(new Font("SansSerif", Font.BOLD, 10));
            badgeLbl.setForeground(WHITE);
            badgeLbl.setHorizontalAlignment(SwingConstants.CENTER);
            badgeLbl.setOpaque(false);
            // Tamaño fijo cuadrado para que el círculo quede perfecto
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

    // ─── Footer ──────────────────────────────────────────────────────────────

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

        // Avatar con degradado MAUVE → STEEL
        JLabel avatar = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, MAUVE,
                                              getWidth(), getHeight(), STEEL));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 13));
                String ini = nombreUsuario.isEmpty() ? "A"
                        : String.valueOf(nombreUsuario.charAt(0)).toUpperCase();
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(ini,
                        (getWidth()  - fm.stringWidth(ini)) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
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

        JLabel lblRol = new JLabel("Administrador");
        lblRol.setFont(FONT_SMALL);
        lblRol.setForeground(new Color(212, 205, 197, 150));

        info.add(lblNombre);
        info.add(lblRol);

        JButton btnSalir = new JButton("Salir") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
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
        btnSalir.addActionListener(e -> app.cerrarSesion());

        footer.add(avatar,   BorderLayout.WEST);
        footer.add(info,     BorderLayout.CENTER);
        footer.add(btnSalir, BorderLayout.EAST);
        return footer;
    }
}