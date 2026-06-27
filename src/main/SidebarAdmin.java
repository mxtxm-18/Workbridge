package main;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;

public class SidebarAdmin extends JPanel {

    static final Color NAVY              = new Color(0x24, 0x3A, 0x69);
    static final Color STEEL             = new Color(0x5B, 0x88, 0xA5);
    static final Color MAUVE             = new Color(0x9B, 0x73, 0xA6);
    static final Color BEIGE             = new Color(0xD4, 0xCD, 0xC5);
    static final Color WHITE             = Color.WHITE;
    static final Color SIDEBAR_ACTIVE_BG = MAUVE;

    private static final int SIDEBAR_WIDTH = 340;

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
        add(crearTagline());
        add(Box.createVerticalStrut(24));

        // Navegación principal
        add(crearMenuItem("Dashboard",    "dashboardAdmin"));
        add(crearMenuItem("Usuarios",     "gestionUsuarios"));
        add(crearMenuItem("Empresas",     "empresas"));

        // Sección Gestión
        add(crearSeccionLabel("Gestión"));
        add(crearMenuItem("Habilidades",    "habilidades"));
        add(crearMenuItem("Documentos",     "documentos"));
        add(crearMenuItem("Notificaciones", "notificaciones"));
        add(crearMenuItem("Comunicaciones", "comunicaciones"));

        add(Box.createVerticalGlue());
        add(crearFooter());
    }

    public void setNombreUsuario(String nombre) {
        this.nombreUsuario = nombre;
        repaint();
    }

    // ─── Fondo NAVY ─────────────────────────────────────────────────────────

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(NAVY);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    // ─── Logo ───────────────────────────────────────────────────────────────

    private JPanel crearLogoPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 24));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(SIDEBAR_WIDTH, 180));

        URL urlLogo = getClass().getResource("/Recursos/logo.png");
        JLabel lblLogo;
        if (urlLogo != null) {
            Image img = new ImageIcon(urlLogo).getImage()
                    .getScaledInstance(250, -1, Image.SCALE_SMOOTH);
            lblLogo = new JLabel(new ImageIcon(img));
        } else {
            lblLogo = new JLabel("WorkBridge");
            lblLogo.setFont(new Font("SansSerif", Font.BOLD, 22));
            lblLogo.setForeground(WHITE);
        }
        lblLogo.setPreferredSize(new Dimension(250, 120));
        panel.add(lblLogo);
        return panel;
    }

    // ─── Tagline ────────────────────────────────────────────────────────────

    private JLabel crearTagline() {
        JLabel lbl = new JLabel("CONECTAMOS TALENTO, GENERAMOS OPORTUNIDADES");
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lbl.setForeground(BEIGE);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    // ─── Etiqueta de sección ────────────────────────────────────────────────

    private JLabel crearSeccionLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl.setForeground(new Color(0xAA, 0xBB, 0xCC));
        lbl.setBorder(new EmptyBorder(16, 24, 4, 0));
        lbl.setMaximumSize(new Dimension(SIDEBAR_WIDTH, 30));
        return lbl;
    }

    // ─── Ítem de menú ───────────────────────────────────────────────────────

    private JPanel crearMenuItem(String texto, String pantalla) {
        boolean esActivo = pantalla.equals(activo);

        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 11)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (esActivo) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(SIDEBAR_ACTIVE_BG);
                    g2.fill(new RoundRectangle2D.Double(
                            10, 3, getWidth() - 20, getHeight() - 6, 14, 14));
                }
            }
        };
        item.setOpaque(false);
        item.setMaximumSize(new Dimension(SIDEBAR_WIDTH, 52));
        item.setPreferredSize(new Dimension(SIDEBAR_WIDTH, 52));
        item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", esActivo ? Font.BOLD : Font.PLAIN, 18));
        lbl.setForeground(WHITE);
        item.add(lbl);

        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                app.mostrarPantalla(pantalla);
            }
            @Override public void mouseEntered(MouseEvent e) { item.repaint(); }
            @Override public void mouseExited(MouseEvent e)  { item.repaint(); }
        });

        return item;
    }

    // ─── Footer ─────────────────────────────────────────────────────────────

    private JPanel crearFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 16));
        footer.setOpaque(false);
        footer.setMaximumSize(new Dimension(SIDEBAR_WIDTH, 86));
        footer.setBorder(new MatteBorder(1, 0, 0, 0, new Color(0x40, 0x50, 0x80)));

        JLabel avatar = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(STEEL);
                g2.fillOval(0, 0, 44, 44);
                g2.setColor(WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 16));
                String inicial = nombreUsuario.isEmpty() ? "A"
                        : String.valueOf(nombreUsuario.charAt(0)).toUpperCase();
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(inicial, (44 - fm.stringWidth(inicial)) / 2, 28);
                g2.dispose();
            }
        };
        avatar.setPreferredSize(new Dimension(44, 44));

        JLabel lblNombre = new JLabel(nombreUsuario);
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNombre.setForeground(WHITE);

        JButton btnSalir = new JButton("Cerrar sesión");
        btnSalir.setFont(new Font("SansSerif", Font.PLAIN, 11));
        btnSalir.setForeground(WHITE);
        btnSalir.setBackground(MAUVE);
        btnSalir.setBorderPainted(false);
        btnSalir.setFocusPainted(false);
        btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSalir.addActionListener(e -> app.cerrarSesion());

        footer.add(avatar);
        footer.add(lblNombre);
        footer.add(btnSalir);
        return footer;
    }
}