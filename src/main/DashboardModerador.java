package main;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.RenderingHints;

/*
 * Colores institucionales (Sección 3.2):
 *   Naranja claro  #D4CDC5
 *   Celeste apagado #5B88A5
 *   Azul oscuro    #243A69
 *   Lavanda apagado #9B73A6
 */
public class DashboardModerador extends JPanel {

    // ═══════ PALETA INSTITUCIONAL (Manual 3.2) ═══════
    static final Color AZUL_OSCURO  = new Color(0x24, 0x3A, 0x69);
    static final Color CELESTE      = new Color(0x5B, 0x88, 0xA5);
    static final Color NARANJA_CLARO= new Color(0xD4, 0xCD, 0xC5);
    static final Color LAVANDA      = new Color(0x9B, 0x73, 0xA6);

    // Secundarios (Manual 3.2)
    static final Color SEC_AZUL     = new Color(0x20, 0x33, 0x5B);
    static final Color SEC_CELESTE  = new Color(0x53, 0x7B, 0x94);
    static final Color SEC_LAVANDA  = new Color(0x84, 0x64, 0x8D);

    // Complementarios (Manual 3.3) — uso limitado ≤20%
    static final Color MUSTAR       = new Color(0xFD, 0xBD, 0x2D);
    static final Color VERDE_MENTA  = new Color(0x5C, 0xE1, 0xE6);
    static final Color TURQUOISE    = new Color(0x00, 0xA4, 0xBD);

    // Neutros y estados
    static final Color FONDO_PAGINA = new Color(0xF0, 0xEE, 0xE9);
    static final Color FONDO_CARD   = Color.WHITE;
    static final Color BORDE        = new Color(0xE0, 0xDB, 0xD4);
    static final Color TEXTO_OSCURO = new Color(0x1A, 0x20, 0x35);
    static final Color TEXTO_MEDIO  = new Color(0x3D, 0x4A, 0x5F);
    static final Color TEXTO_SUAVE  = new Color(0x7A, 0x86, 0x99);
    static final Color EXITO        = new Color(0x2E, 0x9E, 0x6E);
    static final Color ALERTA       = new Color(0xD4, 0x92, 0x0A);
    static final Color PELIGRO      = new Color(0xC0, 0x39, 0x2B);

    // ═══════ TIPOGRAFÍA (Manual 2.1 — Poppins / Aileron → fallback SansSerif) ═══════
    static final Font FONT_TITULO    = new Font("SansSerif", Font.BOLD,   14);
    static final Font FONT_SUBTIT    = new Font("SansSerif", Font.BOLD,   12);
    static final Font FONT_BODY      = new Font("SansSerif", Font.PLAIN,  11);
    static final Font FONT_SMALL     = new Font("SansSerif", Font.PLAIN,   9);
    static final Font FONT_KPI       = new Font("SansSerif", Font.BOLD,   26);
    static final Font FONT_LABEL     = new Font("SansSerif", Font.BOLD,    9);
    static final Font FONT_NAV       = new Font("SansSerif", Font.PLAIN,  12);
    static final Font FONT_NAV_ACT   = new Font("SansSerif", Font.BOLD,   12);
    static final Font FONT_BTN       = new Font("SansSerif", Font.BOLD,   10);
    static final Font FONT_BRAND     = new Font("SansSerif", Font.BOLD,   16);

    // Referencia a la app para navegación (CardLayout) y sesión
    private final WorkBridgeApp app;

    // Referencias para navegación interna (scroll a secciones)
    private JScrollPane scrollContenido;
    private JPanel panelReportes;
    private JPanel panelGrafico;
    private DefaultTableModel modeloUsuarios;
    private DefaultTableModel modeloVacantes;

    public DashboardModerador(WorkBridgeApp app) {
        this.app = app;
        setBackground(FONDO_PAGINA);

        // Layout principal: sidebar + contenido
        setLayout(new BorderLayout());
        add(buildSidebar(), BorderLayout.WEST);
        add(buildMain(),    BorderLayout.CENTER);
    }

    // ═══════════════════════════════════════════
    //  NAVEGACIÓN — conecta el sidebar con WorkBridgeApp
    // ═══════════════════════════════════════════
    private void navegarA(String destino) {
        if (destino == null || destino.isEmpty() || destino.equals("dashboardModerador")) {
            return; // Ya estamos en el dashboard
        }
        if (destino.equals("CONFIG")) {
            mostrarConfiguracion();
            return;
        }
        if (destino.startsWith("SCROLL:")) {
            String seccion = destino.substring(7);
            JPanel objetivo = seccion.equals("reportes") ? panelReportes
                             : seccion.equals("grafico")  ? panelGrafico
                             : null;
            if (objetivo != null) {
                SwingUtilities.invokeLater(() -> objetivo.scrollRectToVisible(
                        new Rectangle(0, 0, objetivo.getWidth(), objetivo.getHeight())));
            }
            return;
        }
        if (app != null) {
            app.mostrarPantalla(destino);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Pantalla no disponible en modo de prueba: " + destino);
        }
    }

    // ═══════════════════════════════════════════
    //  BÚSQUEDA — barra superior
    // ═══════════════════════════════════════════
    private void ejecutarBusqueda(String texto) {
        if (texto == null || texto.isBlank() || texto.equals("Buscar...")) return;
        String q = texto.trim().toLowerCase();

        int coincidenciasUsuarios = 0;
        if (modeloUsuarios != null) {
            for (int r = 0; r < modeloUsuarios.getRowCount(); r++) {
                String nombre = String.valueOf(modeloUsuarios.getValueAt(r, 0)).toLowerCase();
                String email  = String.valueOf(modeloUsuarios.getValueAt(r, 1)).toLowerCase();
                if (nombre.contains(q) || email.contains(q)) coincidenciasUsuarios++;
            }
        }
        int coincidenciasVacantes = 0;
        if (modeloVacantes != null) {
            for (int r = 0; r < modeloVacantes.getRowCount(); r++) {
                String tituloV  = String.valueOf(modeloVacantes.getValueAt(r, 0)).toLowerCase();
                String empresaV = String.valueOf(modeloVacantes.getValueAt(r, 1)).toLowerCase();
                if (tituloV.contains(q) || empresaV.contains(q)) coincidenciasVacantes++;
            }
        }

        if (coincidenciasUsuarios == 0 && coincidenciasVacantes == 0) {
            JOptionPane.showMessageDialog(this,
                    "No se encontraron resultados para \"" + texto + "\".",
                    "Buscar", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder msg = new StringBuilder("Resultados para \"" + texto + "\":\n");
        if (coincidenciasUsuarios > 0) msg.append("• ").append(coincidenciasUsuarios).append(" usuario(s)\n");
        if (coincidenciasVacantes > 0) msg.append("• ").append(coincidenciasVacantes).append(" vacante(s)\n");
        JOptionPane.showMessageDialog(this, msg.toString(),
                "Resultados de búsqueda", JOptionPane.INFORMATION_MESSAGE);

        if (coincidenciasUsuarios > 0) navegarA("gestionUsuarios");
        else navegarA("publicaciones");
    }

    private void mostrarConfiguracion() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(6, 4, 4, 4));

        JLabel titulo = new JLabel("Preferencias de moderación");
        titulo.setFont(FONT_SUBTIT);
        titulo.setForeground(AZUL_OSCURO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JCheckBox chkEmail   = new JCheckBox("Recibir notificaciones por correo", true);
        JCheckBox chkAlertas = new JCheckBox("Alertarme de reportes de alta prioridad", true);
        JCheckBox chkResumen = new JCheckBox("Enviarme un resumen diario de actividad", false);
        for (JCheckBox chk : new JCheckBox[]{chkEmail, chkAlertas, chkResumen}) {
            chk.setAlignmentX(Component.LEFT_ALIGNMENT);
            chk.setOpaque(false);
        }

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(chkEmail);
        panel.add(chkAlertas);
        panel.add(chkResumen);

        int resultado = JOptionPane.showConfirmDialog(this, panel,
                "Configuración del Moderador",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(this,
                    "Preferencias guardadas correctamente.",
                    "Configuración", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ═══════════════════════════════════════════
    //  SIDEBAR — fondo azul oscuro (neg. logo)
    // ═══════════════════════════════════════════
    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Fondo azul oscuro
                g2.setColor(AZUL_OSCURO);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // Degradado lavanda en parte inferior (fondos corporativos 4.4)
                GradientPaint gp = new GradientPaint(0, getHeight()-200,
                        new Color(155,115,166,0), 0, getHeight(), new Color(155,115,166,80));
                g2.setPaint(gp);
                g2.fillRect(0, getHeight()-200, getWidth(), 200);
                // Líneas punteadas decorativas (motivo visual del manual)
                g2.setColor(new Color(91,136,165,40));
                g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                        0, new float[]{4,6}, 0));
                for (int y = 20; y < getHeight(); y += 12) {
                    g2.drawLine(getWidth()-1, y, getWidth()-1, y+4);
                }
            }
        };
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // BRAND
        sidebar.add(buildBrand());
        sidebar.add(Box.createVerticalStrut(8));

        // NAV grupos
        // Cada fila: {icono, texto, meta, destino}
        // destino puede ser: una clave de pantalla registrada en WorkBridgeApp,
        // "SCROLL:<panel>" para desplazarse a una sección del propio dashboard,
        // o "CONFIG" para abrir el diálogo de configuración.
        sidebar.add(buildNavGroup("PRINCIPAL", new String[][]{
            {"⊞", "Dashboard",      "activo",   "dashboardModerador"},
            {"⚠", "Reportes",       "badge:12", "SCROLL:reportes"},
        }));
        sidebar.add(buildNavGroup("MODERACIÓN", new String[][]{
            {"◎", "Usuarios",       "badge:3",  "gestionUsuarios"},
            {"▣", "Empresas",       "",         "empresas"},
            {"⊟", "Vacantes",       "",         "publicaciones"},
            {"◱", "Comunicaciones", "badge:5",  "comunicaciones"},
        }));
        sidebar.add(buildNavGroup("SISTEMA", new String[][]{
            {"◈", "Estadísticas",   "",         "SCROLL:grafico"},
            {"⚙", "Configuración",  "",         "CONFIG"},
        }));

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(buildSidebarFooter());
        return sidebar;
    }

   private JPanel buildBrand() {
    JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)) {
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(new Color(91,136,165,50));
            g2.setStroke(new BasicStroke(1f));
            g2.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
        }
    };
    p.setOpaque(false);
    p.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
    p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

    // Logo real desde /Recursos/logo.png
    java.net.URL urlLogo = getClass().getResource("/Recursos/logo.png");
    if (urlLogo != null) {
        Image img = new ImageIcon(urlLogo).getImage()
                .getScaledInstance(180, -1, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(img));
        p.add(lblLogo);
    } else {
        // fallback texto
        JLabel name = new JLabel("WorkBridge");
        name.setFont(FONT_BRAND);
        name.setForeground(Color.WHITE);
        p.add(name);
    }
    return p;
}
    private ImageIcon buildBriefcaseIcon() {
        int sz = 36;
        java.awt.image.BufferedImage img =
            new java.awt.image.BufferedImage(sz, sz, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Maletín blanco
        g.setColor(new Color(255,255,255,200));
        g.fillRoundRect(4, 14, 28, 18, 4, 4);
        g.fillRoundRect(11, 8, 14, 8, 3, 3);
        g.setColor(AZUL_OSCURO);
        g.fillRoundRect(13, 10, 10, 5, 2, 2);
        // Arco puente blanco
        g.setColor(new Color(255,255,255,230));
        g.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        GeneralPath arc = new GeneralPath();
        arc.moveTo(2, 34);
        arc.curveTo(6, 22, 13, 28, 18, 22);
        arc.curveTo(23, 28, 30, 22, 34, 34);
        g.draw(arc);
        g.dispose();
        return new ImageIcon(img);
    }

    private JPanel buildNavGroup(String label, String[][] items) {
        JPanel group = new JPanel();
        group.setOpaque(false);
        group.setLayout(new BoxLayout(group, BoxLayout.Y_AXIS));
        group.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        group.setMaximumSize(new Dimension(Integer.MAX_VALUE, 999));

        // Etiqueta de sección
        JLabel lbl = new JLabel("  " + label);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(new Color(212,205,197,90));
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 12, 4, 0));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        group.add(lbl);

        for (String[] item : items) {
            String destino = item.length > 3 ? item[3] : null;
            group.add(buildNavItem(item[0], item[1], item[2], destino));
        }
        return group;
    }

    private JPanel buildNavItem(String icon, String text, String meta, String destino) {
        boolean activo = meta.equals("activo");
        String badge = meta.startsWith("badge:") ? meta.substring(6) : null;

        JPanel row = new JPanel(new BorderLayout()) {
            boolean hovered = false;
            { // init block
                setOpaque(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hovered=true; repaint(); }
                    @Override public void mouseExited(MouseEvent e)  { hovered=false; repaint(); }
                    @Override public void mouseClicked(MouseEvent e) {
                        repaint();
                        navegarA(destino);
                    }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (activo) {
                    g2.setColor(CELESTE);
                    g2.fillRoundRect(8, 2, getWidth()-16, getHeight()-4, 8, 8);
                    // Barra izquierda naranja claro
                    g2.setColor(NARANJA_CLARO);
                    g2.fillRoundRect(8, getHeight()/2-10, 3, 20, 2, 2);
                } else if (hovered) {
                    g2.setColor(new Color(255,255,255,20));
                    g2.fillRoundRect(8, 2, getWidth()-16, getHeight()-4, 8, 8);
                }
            }
        };
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        row.setBorder(BorderFactory.createEmptyBorder(2, 18, 2, 12));

        JLabel iconL = new JLabel(icon + "  " + text);
        iconL.setFont(activo ? FONT_NAV_ACT : FONT_NAV);
        iconL.setForeground(activo ? Color.WHITE : new Color(255,255,255,130));
        row.add(iconL, BorderLayout.CENTER);

        if (badge != null) {
            JLabel b = new JLabel(badge) {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(LAVANDA);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                    super.paintComponent(g);
                }
            };
            b.setFont(FONT_LABEL);
            b.setForeground(Color.WHITE);
            b.setHorizontalAlignment(SwingConstants.CENTER);
            b.setBorder(BorderFactory.createEmptyBorder(1, 6, 1, 6));
            b.setOpaque(false);
            row.add(b, BorderLayout.EAST);
        }
        return row;
    }

    private JPanel buildSidebarFooter() {
        JPanel p = new JPanel(new BorderLayout(10, 0)) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(91,136,165,50));
                g.drawLine(0, 0, getWidth(), 0);
            }
        };
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(12, 14, 16, 14));
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // Avatar con degradado
        JLabel av = new JLabel("MR") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0,0,LAVANDA,getWidth(),getHeight(),CELESTE);
                g2.setPaint(gp);
                g2.fillOval(0,0,getWidth(),getHeight());
                super.paintComponent(g);
            }
        };
        av.setFont(FONT_LABEL);
        av.setForeground(Color.WHITE);
        av.setHorizontalAlignment(SwingConstants.CENTER);
        av.setPreferredSize(new Dimension(32,32));
        av.setOpaque(false);

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        JLabel nombre = new JLabel("María Rodríguez");
        nombre.setFont(new Font("SansSerif", Font.BOLD, 11));
        nombre.setForeground(Color.WHITE);
        JLabel rol = new JLabel("Moderadora Senior");
        rol.setFont(FONT_SMALL);
        rol.setForeground(new Color(212,205,197,130));
        info.add(nombre);
        info.add(rol);

        JLabel salir = new JLabel("Cerrar sesión");
        salir.setFont(FONT_SMALL);
        salir.setForeground(new Color(212,205,197,180));
        salir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        salir.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { salir.setForeground(Color.WHITE); }
            @Override public void mouseExited(MouseEvent e)  { salir.setForeground(new Color(212,205,197,180)); }
            @Override public void mouseClicked(MouseEvent e) {
                int r = JOptionPane.showConfirmDialog(DashboardModerador.this,
                        "¿Deseas cerrar la sesión?", "Cerrar sesión",
                        JOptionPane.YES_NO_OPTION);
                if (r == JOptionPane.YES_OPTION) {
                    if (app != null) app.cerrarSesion();
                }
            }
        });
        info.add(salir);

        p.add(av, BorderLayout.WEST);
        p.add(info, BorderLayout.CENTER);
        return p;
    }

    // ═══════════════════════════════════════════
    //  MAIN — topbar + contenido scroll
    // ═══════════════════════════════════════════
    private JPanel buildMain() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(FONDO_PAGINA);
        main.add(buildTopbar(), BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setBackground(FONDO_PAGINA);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));

        content.add(buildKpiRow());
        content.add(Box.createVerticalStrut(14));
        content.add(buildFila1());
        content.add(Box.createVerticalStrut(14));
        content.add(buildFila2());
        content.add(Box.createVerticalStrut(14));
        content.add(buildFila3());
        content.add(Box.createVerticalStrut(14));

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getVerticalScrollBar().setBackground(FONDO_PAGINA);
        scroll.setBackground(FONDO_PAGINA);
        this.scrollContenido = scroll;
        main.add(scroll, BorderLayout.CENTER);
        return main;
    }

    private JPanel buildTopbar() {
        JPanel bar = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(BORDE);
                g.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
            }
        };
        bar.setBackground(Color.WHITE);
        bar.setBorder(BorderFactory.createEmptyBorder(0, 22, 0, 22));
        bar.setPreferredSize(new Dimension(0, 56));

        // Título
        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        JLabel titulo = new JLabel("Dashboard Moderador");
        titulo.setFont(FONT_TITULO);
        titulo.setForeground(AZUL_OSCURO);
        JLabel fecha = new JLabel("Domingo, 7 de junio de 2026  ·  WorkBridge Guatemala");
        fecha.setFont(FONT_SMALL);
        fecha.setForeground(TEXTO_SUAVE);
        left.add(titulo);
        left.add(fecha);
        bar.add(left, BorderLayout.WEST);

        // Acciones derecha
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        right.setOpaque(false);

        // Buscador
        JPanel search = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(FONDO_PAGINA);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),8,8);
                g2.setColor(BORDE);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,8,8);
            }
        };
        search.setOpaque(false);
        search.setPreferredSize(new Dimension(200, 30));
        JLabel searchIco = new JLabel("⌕");
        searchIco.setFont(new Font("SansSerif", Font.PLAIN, 13));
        searchIco.setForeground(TEXTO_SUAVE);
        JTextField searchField = new JTextField("Buscar...", 14);
        searchField.setFont(FONT_BODY);
        searchField.setForeground(TEXTO_SUAVE);
        searchField.setBorder(null);
        searchField.setOpaque(false);
        final String placeholder = "Buscar...";
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusGained(java.awt.event.FocusEvent e) {
                if (searchField.getText().equals(placeholder)) {
                    searchField.setText("");
                    searchField.setForeground(TEXTO_OSCURO);
                }
            }
            @Override public void focusLost(java.awt.event.FocusEvent e) {
                if (searchField.getText().isBlank()) {
                    searchField.setText(placeholder);
                    searchField.setForeground(TEXTO_SUAVE);
                }
            }
        });
        searchField.addActionListener(e -> ejecutarBusqueda(searchField.getText()));
        search.add(searchIco);
        search.add(searchField);

        // Chip moderador (degradado institucional)
        JLabel chip = new JLabel("MODERADOR") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0,0,AZUL_OSCURO,getWidth(),0,LAVANDA);
                g2.setPaint(gp);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),getHeight(),getHeight());
                super.paintComponent(g);
            }
        };
        chip.setFont(FONT_LABEL);
        chip.setForeground(Color.WHITE);
        chip.setBorder(BorderFactory.createEmptyBorder(5,12,5,12));
        chip.setOpaque(false);

        right.add(search);
        right.add(chip);
        bar.add(right, BorderLayout.EAST);
        return bar;
    }

    // ═══════════════════════════════════════════
    //  KPI ROW — 5 tarjetas
    // ═══════════════════════════════════════════
    private JPanel buildKpiRow() {
        JPanel row = new JPanel(new GridLayout(1, 5, 12, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        row.add(buildKpi("TOTAL USUARIOS",     "1,847", "+23 esta semana",  AZUL_OSCURO, EXITO));
        row.add(buildKpi("EMPRESAS ACTIVAS",   "342",   "+8 este mes",      CELESTE,     EXITO));
        row.add(buildKpi("VACANTES PUBLICADAS","519",   "12 en revisión",   LAVANDA,     ALERTA));
        row.add(buildKpi("REPORTES ACTIVOS",   "12",    "4 alta prioridad", PELIGRO,     PELIGRO));
        row.add(buildKpi("ACCIONES HOY",       "38",    "+12% vs ayer",     EXITO,       EXITO));
        return row;
    }

    private JPanel buildKpi(String label, String value, String sub,
                             Color accentTop, Color subColor) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(FONDO_CARD);
                g2.fillRoundRect(0, 3, getWidth(), getHeight()-3, 10, 10);
                g2.setColor(BORDE);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 3, getWidth()-1, getHeight()-4, 10, 10);
                // Barra superior color institucional
                g2.setColor(accentTop);
                g2.fillRoundRect(0, 3, getWidth(), 4, 3, 3);
                g2.fillRect(0, 4, getWidth(), 3);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(14, 14, 10, 14));

        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXTO_SUAVE);

        JLabel val = new JLabel(value);
        val.setFont(FONT_KPI);
        val.setForeground(AZUL_OSCURO);

        JLabel s = new JLabel(sub);
        s.setFont(FONT_SMALL);
        s.setForeground(subColor);

        JPanel inner = new JPanel();
        inner.setOpaque(false);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.add(lbl);
        inner.add(Box.createVerticalStrut(4));
        inner.add(val);
        inner.add(Box.createVerticalStrut(2));
        inner.add(s);
        card.add(inner, BorderLayout.CENTER);
        return card;
    }

    // ═══════════════════════════════════════════
    //  FILA 1 — Reportes (70%) + Actividad (30%)
    // ═══════════════════════════════════════════
    private JPanel buildFila1() {
        JPanel row = new JPanel(new GridBagLayout());
        row.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.gridy = 0; gc.weighty = 1;
        gc.insets = new Insets(0,0,0,12);

        gc.gridx=0; gc.weightx=0.65;
        row.add(buildReportes(), gc);
        gc.gridx=1; gc.weightx=0.35; gc.insets=new Insets(0,0,0,0);
        row.add(buildActividad(), gc);
        return row;
    }

    private JPanel buildReportes() {
        JPanel panel = buildPanel("⚠  Reportes Pendientes de Moderación",
                                  "Requieren acción del moderador — ordenados por prioridad",
                                  () -> JOptionPane.showMessageDialog(this,
                                      "Bandeja completa de reportes: 12 pendientes en total.\n" +
                                      "(4 de alta prioridad, 5 de media, 3 de baja).",
                                      "Reportes de Moderación", JOptionPane.INFORMATION_MESSAGE));

        JPanel cuerpo = new JPanel();
        cuerpo.setOpaque(false);
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBorder(BorderFactory.createEmptyBorder(10,14,14,14));

        // Datos de reportes
        Object[][] data = {
            {"🚫", "Spam masivo — 'Ofertas Rápidas GT'",         "3 usuarios · hace 20 min", "Alta",  PELIGRO},
            {"⚠",  "Vacante falsa — Dev Full Stack TechCore",    "hace 1h · TechCore",       "Alta",  PELIGRO},
            {"◉",  "Contenido inapropiado en mensaje directo",   "@javier.s · hace 2h",      "Media", ALERTA},
            {"💬",  "Conducta inapropiada en entrevista virtual", "DataInsights · hace 3h",   "Media", ALERTA},
            {"📌",  "Empresa sin verificar publicando masivamente","Creative Web · hace 5h",  "Baja",  TEXTO_SUAVE},
        };

        for (Object[] r : data) {
            cuerpo.add(buildReporteItem(
                (String)r[0], (String)r[1], (String)r[2],
                (String)r[3], (Color)r[4]));
            cuerpo.add(Box.createVerticalStrut(4));
        }

        panel.add(cuerpo, BorderLayout.CENTER);
        this.panelReportes = panel;
        return panel;
    }

    private JPanel buildReporteItem(String ico, String titulo,
                                     String meta, String prioridad, Color prioColor) {
        JPanel item = new JPanel(new BorderLayout(8, 0)) {
            boolean hover = false;
            {
                setOpaque(false);
                addMouseListener(new MouseAdapter(){
                    @Override public void mouseEntered(MouseEvent e){hover=true; repaint();}
                    @Override public void mouseExited(MouseEvent e) {hover=false;repaint();}
                });
            }
            @Override protected void paintComponent(Graphics g) {
                if (hover) {
                    g.setColor(new Color(240,238,235));
                    g.fillRoundRect(0,0,getWidth(),getHeight(),6,6);
                }
            }
        };
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        item.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 4));

        // Icono
        JLabel icoL = new JLabel(ico) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = prioColor.equals(PELIGRO) ? new Color(249,232,232)
                         : prioColor.equals(ALERTA)  ? new Color(253,244,224)
                         : new Color(234,241,247);
                g2.setColor(bg);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),8,8);
                super.paintComponent(g);
            }
        };
        icoL.setFont(new Font("SansSerif", Font.PLAIN, 14));
        icoL.setHorizontalAlignment(SwingConstants.CENTER);
        icoL.setPreferredSize(new Dimension(34,34));
        icoL.setOpaque(false);

        // Texto
        JPanel txt = new JPanel();
        txt.setOpaque(false);
        txt.setLayout(new BoxLayout(txt, BoxLayout.Y_AXIS));
        JLabel tit = new JLabel(titulo);
        tit.setFont(new Font("SansSerif", Font.BOLD, 11));
        tit.setForeground(TEXTO_OSCURO);
        JLabel m = new JLabel(meta + "  ·  Prioridad: " + prioridad);
        m.setFont(FONT_SMALL);
        m.setForeground(TEXTO_SUAVE);
        txt.add(tit);
        txt.add(m);

        // Botones acción
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));
        btns.setOpaque(false);

        JButton btnAprobar = buildBtn("✓ Aprobar", EXITO);
        btnAprobar.addActionListener(e -> {
            int r = JOptionPane.showConfirmDialog(this,
                    "¿Marcar este reporte como resuelto?\n\"" + titulo + "\"",
                    "Resolver reporte", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                Container padre = item.getParent();
                if (padre != null) { padre.remove(item); padre.revalidate(); padre.repaint(); }
            }
        });

        JButton btnQuitar = buildBtn("✕ Quitar", PELIGRO);
        btnQuitar.addActionListener(e -> {
            int r = JOptionPane.showConfirmDialog(this,
                    "¿Eliminar el contenido reportado?\n\"" + titulo + "\"",
                    "Quitar contenido", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                Container padre = item.getParent();
                if (padre != null) { padre.remove(item); padre.revalidate(); padre.repaint(); }
            }
        });

        btns.add(btnAprobar);
        btns.add(btnQuitar);

        item.add(icoL, BorderLayout.WEST);
        item.add(txt,  BorderLayout.CENTER);
        item.add(btns, BorderLayout.EAST);
        return item;
    }

    private JPanel buildActividad() {
        JPanel panel = buildPanel("Actividad Reciente", "Últimas 24 horas",
                                  () -> JOptionPane.showMessageDialog(this,
                                      "El historial completo de actividad estará disponible\n" +
                                      "próximamente en la sección de Estadísticas.",
                                      "Actividad Reciente", JOptionPane.INFORMATION_MESSAGE));

        JPanel cuerpo = new JPanel();
        cuerpo.setOpaque(false);
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBorder(BorderFactory.createEmptyBorder(10,14,14,14));

        Object[][] acts = {
            {"TechCorp S.A. verificada",              "hace 5 min",  EXITO},
            {"Vacante eliminada por fraude",           "hace 22 min", PELIGRO},
            {"@spam_user suspendido 7 días",           "hace 45 min", ALERTA},
            {"3 empresas pendientes verificación",     "hace 1h",     CELESTE},
            {"Comunicación bloqueada por lenguaje",    "hace 2h",     LAVANDA},
            {"12 vacantes aprobadas (revisión masiva)","hace 3h",     EXITO},
            {"Nuevo reporte de spam asignado",         "hace 4h",     AZUL_OSCURO},
        };

        for (Object[] a : acts) {
            JPanel row = new JPanel(new BorderLayout(8,0));
            row.setOpaque(false);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            row.setBorder(BorderFactory.createEmptyBorder(3,4,3,4));

            // Punto de color
            JLabel dot = new JLabel() {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2=(Graphics2D)g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor((Color)a[2]);
                    g2.fillOval(0,4,8,8);
                }
            };
            dot.setPreferredSize(new Dimension(12,20));
            dot.setOpaque(false);

            JPanel info = new JPanel();
            info.setOpaque(false);
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
            JLabel text = new JLabel((String)a[0]);
            text.setFont(FONT_BODY);
            text.setForeground(TEXTO_MEDIO);
            JLabel hora = new JLabel((String)a[1]);
            hora.setFont(FONT_SMALL);
            hora.setForeground(TEXTO_SUAVE);
            info.add(text);
            info.add(hora);

            row.add(dot, BorderLayout.WEST);
            row.add(info, BorderLayout.CENTER);
            cuerpo.add(row);
        }

        panel.add(cuerpo, BorderLayout.CENTER);
        return panel;
    }

    // ═══════════════════════════════════════════
    //  FILA 2 — Tabla usuarios + Vacantes/Gráfica
    // ═══════════════════════════════════════════
    private JPanel buildFila2() {
        JPanel row = new JPanel(new GridBagLayout());
        row.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.gridy = 0; gc.weighty = 1;

        gc.gridx=0; gc.weightx=0.55; gc.insets=new Insets(0,0,0,12);
        row.add(buildUsuarios(), gc);

        gc.gridx=1; gc.weightx=0.45; gc.insets=new Insets(0,0,0,0);
        JPanel right = new JPanel(new GridLayout(2,1,0,12));
        right.setOpaque(false);
        right.add(buildVacantes());
        right.add(buildGrafico());
        row.add(right, gc);
        return row;
    }

    private JPanel buildUsuarios() {
        JPanel panel = buildPanel("Gestión de Usuarios",
                                  "Usuarios con actividad reciente o pendientes",
                                  () -> navegarA("gestionUsuarios"));

        String[] cols = {"Usuario","Email","Tipo","Estado","Últ. acceso",""};
        Object[][] data = {
            {"María Rodas",    "m.rodas@gmail.com",  "Trabajador","Activo",   "04/06/2025", "Ver"},
            {"José Molina",    "j.molina@gmail.com", "Trabajador","Activo",   "07/03/2025", "Ver"},
            {"Brenda Nadh",    "brenda@gmail.com",   "Empresa",   "Inactivo", "08/04/2025", "Suspender"},
            {"Dilan Chitun",   "dilan.c@gmail.com",  "Empresa",   "Revisión", "01/05/2025", "Aprobar"},
            {"Ana Patrus",     "ana.p@gmail.com",    "Trabajador","Activo",   "04/06/2025", "Ver"},
        };

        DefaultTableModel model = new DefaultTableModel(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return c == 5; }
        };
        this.modeloUsuarios = model;
        JTable table = new JTable(model) {
            @Override public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                c.setBackground(isRowSelected(row) ? new Color(234,241,247) : (row%2==0?Color.WHITE:new Color(250,249,247)));
                c.setForeground(TEXTO_OSCURO);
                if (c instanceof JLabel) ((JLabel)c).setBorder(BorderFactory.createEmptyBorder(0,8,0,8));
                return c;
            }
        };
        table.setFont(FONT_BODY);
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0,0));
        table.getTableHeader().setFont(FONT_LABEL);
        table.getTableHeader().setForeground(TEXTO_SUAVE);
        table.getTableHeader().setBackground(FONDO_PAGINA);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0,0,1,0,BORDE));
        table.setSelectionBackground(new Color(234,241,247));

        // Colorear columna Estado
        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t,v,sel,foc,row,col);
                String val = v.toString();
                Color bg   = val.equals("Activo")  ? new Color(229,245,238)
                           : val.equals("Inactivo") ? new Color(240,240,240)
                           : new Color(253,244,224);
                Color fg   = val.equals("Activo")  ? new Color(46,110,72)
                           : val.equals("Inactivo") ? new Color(100,100,100)
                           : new Color(147,104,14);
                lbl.setBackground(bg); lbl.setForeground(fg);
                lbl.setFont(new Font("SansSerif", Font.BOLD, 9));
                lbl.setBorder(BorderFactory.createEmptyBorder(3,8,3,8));
                lbl.setOpaque(true);
                return lbl;
            }
        });

        // Columna de acción (Ver / Suspender / Aprobar) con botón funcional
        instalarColumnaAccion(table, 5, (row, accion) -> {
            String usuario = String.valueOf(model.getValueAt(row, 0));
            switch (accion) {
                case "Ver" -> JOptionPane.showMessageDialog(this,
                        "Usuario: " + usuario +
                        "\nEmail: " + model.getValueAt(row, 1) +
                        "\nTipo: " + model.getValueAt(row, 2) +
                        "\nÚlt. acceso: " + model.getValueAt(row, 4),
                        "Detalle de usuario", JOptionPane.INFORMATION_MESSAGE);
                case "Suspender" -> {
                    int r = JOptionPane.showConfirmDialog(this,
                            "¿Suspender la cuenta de " + usuario + "?",
                            "Suspender usuario", JOptionPane.YES_NO_OPTION);
                    if (r == JOptionPane.YES_OPTION) {
                        model.setValueAt("Inactivo", row, 3);
                        model.setValueAt("Ver", row, 5);
                    }
                }
                case "Aprobar" -> {
                    int r = JOptionPane.showConfirmDialog(this,
                            "¿Aprobar la cuenta de " + usuario + "?",
                            "Aprobar usuario", JOptionPane.YES_NO_OPTION);
                    if (r == JOptionPane.YES_OPTION) {
                        model.setValueAt("Activo", row, 3);
                        model.setValueAt("Ver", row, 5);
                    }
                }
                default -> {}
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(null);
        sp.setBackground(Color.WHITE);
        panel.add(sp, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildVacantes() {
        JPanel panel = buildPanel("Vacantes en Revisión", "12 pendientes de aprobación",
                                  () -> navegarA("publicaciones"));

        String[] cols = {"Vacante","Empresa","Estado",""};
        Object[][] data = {
            {"Dev. Java Backend","TechCore",    "Revisión",  "✓"},
            {"Diseñador UI/UX",  "Creative Web","Revisión",  "✓"},
            {"Analista de Datos","DataInsights","Rechazada",  "👁"},
            {"Dev. Mobile iOS",  "AppStudio",   "Revisión",  "✓"},
        };
        DefaultTableModel model = new DefaultTableModel(data, cols){
            @Override public boolean isCellEditable(int r,int c){return c == 3;}
        };
        this.modeloVacantes = model;
        JTable t = new JTable(model);
        t.setFont(FONT_BODY); t.setRowHeight(28);
        t.setShowGrid(false); t.setIntercellSpacing(new Dimension(0,0));
        t.getTableHeader().setFont(FONT_LABEL);
        t.getTableHeader().setForeground(TEXTO_SUAVE);
        t.getTableHeader().setBackground(FONDO_PAGINA);
        t.setBackground(Color.WHITE);

        // Columna de acción (✓ Aprobar / 👁 Ver detalle) con botón funcional
        instalarColumnaAccion(t, 3, (row, accion) -> {
            String vacante = String.valueOf(model.getValueAt(row, 0));
            String empresa = String.valueOf(model.getValueAt(row, 1));
            switch (accion) {
                case "✓" -> {
                    int r = JOptionPane.showConfirmDialog(this,
                            "¿Aprobar la vacante \"" + vacante + "\" de " + empresa + "?",
                            "Aprobar vacante", JOptionPane.YES_NO_OPTION);
                    if (r == JOptionPane.YES_OPTION) {
                        model.setValueAt("Aprobada", row, 2);
                        model.setValueAt("👁", row, 3);
                    }
                }
                case "👁" -> JOptionPane.showMessageDialog(this,
                        "Vacante: " + vacante +
                        "\nEmpresa: " + empresa +
                        "\nEstado: " + model.getValueAt(row, 2),
                        "Detalle de vacante", JOptionPane.INFORMATION_MESSAGE);
                default -> {}
            }
        });

        JScrollPane sp = new JScrollPane(t);
        sp.setBorder(null);
        panel.add(sp, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildGrafico() {
        JPanel panel = buildPanel("Crecimiento de Usuarios", "Ene — Jun 2026");

        // Canvas de gráfico SVG-like con degradados institucionales
        JPanel canvas = new JPanel() {
            final int[] usuariosY = {68,62,50,42,24,16,4};
            final int[] empresasY = {74,71,66,61,50,42,30};
            final String[] meses  = {"Ene","Feb","Mar","Abr","May","Jun","Jul"};

            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRect(0,0,getWidth(),getHeight());

                int w = getWidth(), h = getHeight();
                int padL=30, padR=10, padT=8, padB=22;
                int gw = w-padL-padR, gh = h-padT-padB;

                // Escalar puntos
                int n = usuariosY.length;
                int[] ux = new int[n]; int[] uy = new int[n];
                int[] ex = new int[n]; int[] ey = new int[n];
                for (int i=0;i<n;i++){
                    ux[i] = padL + (i*(gw/(n-1)));
                    uy[i] = padT + (int)(usuariosY[i]/80.0 * gh);
                    ex[i] = padL + (i*(gw/(n-1)));
                    ey[i] = padT + (int)(empresasY[i]/80.0 * gh);
                }

                // Área usuarios — degradado azul oscuro (institucional 3.2)
                GeneralPath areaU = new GeneralPath();
                areaU.moveTo(ux[0], uy[0]);
                for(int i=1;i<n;i++) areaU.lineTo(ux[i],uy[i]);
                areaU.lineTo(ux[n-1], padT+gh);
                areaU.lineTo(ux[0], padT+gh);
                areaU.closePath();
                GradientPaint gpU = new GradientPaint(0,padT,new Color(36,58,105,80),
                                                       0,padT+gh,new Color(36,58,105,0));
                g2.setPaint(gpU); g2.fill(areaU);

                // Área empresas — degradado lavanda
                GeneralPath areaE = new GeneralPath();
                areaE.moveTo(ex[0], ey[0]);
                for(int i=1;i<n;i++) areaE.lineTo(ex[i],ey[i]);
                areaE.lineTo(ex[n-1], padT+gh);
                areaE.lineTo(ex[0], padT+gh);
                areaE.closePath();
                GradientPaint gpE = new GradientPaint(0,padT,new Color(155,115,166,60),
                                                       0,padT+gh,new Color(155,115,166,0));
                g2.setPaint(gpE); g2.fill(areaE);

                // Línea usuarios
                g2.setColor(AZUL_OSCURO);
                g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                for(int i=1;i<n;i++) g2.drawLine(ux[i-1],uy[i-1],ux[i],uy[i]);

                // Línea empresas (punteada)
                g2.setColor(LAVANDA);
                g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND, 0, new float[]{4,3}, 0));
                for(int i=1;i<n;i++) g2.drawLine(ex[i-1],ey[i-1],ex[i],ey[i]);

                // Etiquetas eje X
                g2.setColor(TEXTO_SUAVE);
                g2.setFont(FONT_SMALL);
                g2.setStroke(new BasicStroke(1f));
                FontMetrics fm = g2.getFontMetrics();
                for(int i=0;i<n;i++){
                    int lx = ux[i] - fm.stringWidth(meses[i])/2;
                    g2.drawString(meses[i], lx, h-5);
                }

                // Leyenda
                g2.setColor(AZUL_OSCURO);
                g2.setStroke(new BasicStroke(2f));
                g2.drawLine(padL, h-3, padL+14, h-3);
                g2.setColor(TEXTO_SUAVE); g2.setFont(FONT_SMALL);
                g2.drawString("Usuarios", padL+16, h-1);
                g2.setColor(LAVANDA);
                g2.setStroke(new BasicStroke(1.5f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,new float[]{4,3},0));
                g2.drawLine(padL+80, h-3, padL+94, h-3);
                g2.setColor(TEXTO_SUAVE); g2.setFont(FONT_SMALL);
                g2.drawString("Empresas", padL+96, h-1);
            }
        };
        canvas.setBackground(Color.WHITE);
        panel.add(canvas, BorderLayout.CENTER);
        this.panelGrafico = panel;
        return panel;
    }

    // ═══════════════════════════════════════════
    //  FILA 3 — Habilidades + Acciones rápidas
    // ═══════════════════════════════════════════
    private JPanel buildFila3() {
        JPanel row = new JPanel(new GridLayout(1,2,12,0));
        row.setOpaque(false);
        row.add(buildHabilidades());
        row.add(buildAccionesRapidas());
        return row;
    }

    private JPanel buildHabilidades() {
        JPanel panel = buildPanel("Candidatos por Habilidad",
                                  "Tecnologías más demandadas esta semana",
                                  () -> navegarA("habilidades"));

        JPanel cuerpo = new JPanel();
        cuerpo.setOpaque(false);
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBorder(BorderFactory.createEmptyBorder(12,14,14,14));

        Object[][] skills = {
            {"React / Web", 82, AZUL_OSCURO, "41"},
            {"Python",      68, CELESTE,      "34"},
            {"Node.js",     56, LAVANDA,      "28"},
            {"Figma / UI",  40, MUSTAR,       "20"},
            {"SQL",         34, TURQUOISE,    "17"},
            {"Swift / iOS", 22, NARANJA_CLARO,"11"},
        };

        for (Object[] sk : skills) {
            cuerpo.add(buildSkillRow((String)sk[0], (int)sk[1], (Color)sk[2], (String)sk[3]));
            cuerpo.add(Box.createVerticalStrut(8));
        }

        panel.add(cuerpo, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildSkillRow(String nombre, int pct, Color color, String count) {
        JPanel row = new JPanel(new BorderLayout(8,0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        JLabel lbl = new JLabel(nombre);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 11));
        lbl.setForeground(TEXTO_OSCURO);
        lbl.setPreferredSize(new Dimension(100,16));

        JPanel barWrap = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(FONDO_PAGINA);
                g2.fillRoundRect(0,4,getWidth(),5,3,3);
                int fw = (int)(getWidth()*pct/100.0);
                g2.setColor(color);
                g2.fillRoundRect(0,4,fw,5,3,3);
            }
        };
        barWrap.setOpaque(false);
        barWrap.setPreferredSize(new Dimension(0,16));

        JLabel cnt = new JLabel(count);
        cnt.setFont(FONT_SMALL);
        cnt.setForeground(TEXTO_SUAVE);
        cnt.setPreferredSize(new Dimension(20,16));
        cnt.setHorizontalAlignment(SwingConstants.RIGHT);

        row.add(lbl,     BorderLayout.WEST);
        row.add(barWrap, BorderLayout.CENTER);
        row.add(cnt,     BorderLayout.EAST);
        return row;
    }

    private JPanel buildAccionesRapidas() {
        JPanel panel = buildPanel("Acciones Rápidas",
                                  "Herramientas de moderación directa");

        JPanel grid = new JPanel(new GridLayout(3,2,8,8));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(12,14,14,14));

        String[][] acciones = {
            {"🔍","Auditar Empresas",   "3 pendientes"},
            {"📋","Revisar Vacantes",   "12 en cola"},
            {"🚫","Bloquear Usuario",   "Acción directa"},
            {"📨","Enviar Aviso",       "A usuario o empresa"},
            {"✅","Verificar Empresa",  "Otorgar insignia"},
            {"📊","Generar Reporte",    "Exportar datos"},
        };

        Runnable[] callbacks = {
            () -> navegarA("empresas"),
            () -> navegarA("publicaciones"),
            () -> navegarA("gestionUsuarios"),
            () -> navegarA("comunicaciones"),
            () -> navegarA("empresas"),
            () -> JOptionPane.showMessageDialog(this,
                    "Generando reporte de actividad de moderación...\n" +
                    "El archivo se descargará como PDF en unos instantes.",
                    "Generar Reporte", JOptionPane.INFORMATION_MESSAGE),
        };

        for (int i=0; i<acciones.length; i++) {
            String[] a = acciones[i];
            boolean destacado = (i==5);
            grid.add(buildAccionCard(a[0], a[1], a[2], destacado, callbacks[i]));
        }

        panel.add(grid, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildAccionCard(String ico, String titulo, String sub, boolean destacado, Runnable onClick) {
        JPanel card = new JPanel(new BorderLayout()) {
            boolean hover = false;
            {
                setOpaque(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter(){
                    @Override public void mouseEntered(MouseEvent e){hover=true;repaint();}
                    @Override public void mouseExited(MouseEvent e) {hover=false;repaint();}
                    @Override public void mousePressed(MouseEvent e) { repaint(); }
                    @Override public void mouseClicked(MouseEvent e) { if (onClick != null) onClick.run(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (destacado) {
                    // Degradado corporativo azul→lavanda (sección 4.4)
                    GradientPaint gp = new GradientPaint(0,0,AZUL_OSCURO,getWidth(),getHeight(),LAVANDA);
                    g2.setPaint(gp);
                    g2.fillRoundRect(0,0,getWidth(),getHeight(),10,10);
                } else {
                    g2.setColor(hover ? new Color(234,241,247) : FONDO_PAGINA);
                    g2.fillRoundRect(0,0,getWidth(),getHeight(),10,10);
                    g2.setColor(hover ? CELESTE : BORDE);
                    g2.setStroke(new BasicStroke(1f));
                    g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,10,10);
                }
            }
        };
        card.setBorder(BorderFactory.createEmptyBorder(10,12,10,12));

        JLabel icoL = new JLabel(ico);
        icoL.setFont(new Font("SansSerif", Font.PLAIN, 18));

        JLabel titL = new JLabel(titulo);
        titL.setFont(new Font("SansSerif", Font.BOLD, 11));
        titL.setForeground(destacado ? Color.WHITE : AZUL_OSCURO);

        JLabel subL = new JLabel(sub);
        subL.setFont(FONT_SMALL);
        subL.setForeground(destacado ? new Color(255,255,255,160) : TEXTO_SUAVE);

        JPanel txt = new JPanel();
        txt.setOpaque(false);
        txt.setLayout(new BoxLayout(txt, BoxLayout.Y_AXIS));
        txt.add(titL); txt.add(subL);

        card.add(icoL, BorderLayout.NORTH);
        card.add(txt,  BorderLayout.CENTER);
        return card;
    }

    // ═══════════════════════════════════════════
    //  UTILIDADES
    // ═══════════════════════════════════════════
    private JPanel buildPanel(String titulo, String sub) {
        return buildPanel(titulo, sub, null);
    }

    private JPanel buildPanel(String titulo, String sub, Runnable onVerTodos) {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(FONDO_CARD);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),12,12);
                g2.setColor(BORDE);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,12,12);
            }
        };
        panel.setOpaque(false);

        // Header
        JPanel head = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(BORDE);
                g.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
            }
        };
        head.setOpaque(false);
        head.setBorder(BorderFactory.createEmptyBorder(11,14,9,14));

        JPanel headLeft = new JPanel();
        headLeft.setOpaque(false);
        headLeft.setLayout(new BoxLayout(headLeft, BoxLayout.Y_AXIS));
        JLabel titL = new JLabel(titulo);
        titL.setFont(FONT_SUBTIT);
        titL.setForeground(AZUL_OSCURO);
        JLabel subL = new JLabel(sub);
        subL.setFont(FONT_SMALL);
        subL.setForeground(TEXTO_SUAVE);
        headLeft.add(titL); headLeft.add(subL);

        JLabel ver = new JLabel("Ver todos →");
        ver.setFont(FONT_LABEL);
        ver.setForeground(CELESTE);
        if (onVerTodos != null) {
            ver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            ver.addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { ver.setForeground(AZUL_OSCURO); }
                @Override public void mouseExited(MouseEvent e)  { ver.setForeground(CELESTE); }
                @Override public void mouseClicked(MouseEvent e) { onVerTodos.run(); }
            });
        }

        head.add(headLeft, BorderLayout.CENTER);
        // El enlace "Ver todos" solo se muestra si tiene una acción real asociada;
        // si no, se omite para no dejar un texto que aparente ser clicable sin serlo.
        if (onVerTodos != null) {
            head.add(ver, BorderLayout.EAST);
        }
        panel.add(head, BorderLayout.NORTH);
        return panel;
    }

    private JButton buildBtn(String texto, Color bg) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? bg.darker() : bg);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),6,6);
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(3,8,3,8));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ═══════════════════════════════════════════
    //  COLUMNA DE ACCIÓN — convierte texto de una celda
    //  en un botón real dentro de un JTable
    // ═══════════════════════════════════════════
    private JButton crearBotonAccion(String texto) {
        Color color = switch (texto) {
            case "Suspender", "Rechazar" -> PELIGRO;
            case "Aprobar", "✓" -> EXITO;
            default -> CELESTE;
        };
        JButton b = buildBtn(texto, color);
        b.setMargin(new Insets(2, 6, 2, 6));
        return b;
    }

    private void instalarColumnaAccion(JTable table, int colIndex,
                                        java.util.function.BiConsumer<Integer, String> onClick) {
        TableColumn col = table.getColumnModel().getColumn(colIndex);
        col.setCellRenderer((t, value, isSelected, hasFocus, row, column) ->
                crearBotonAccion(String.valueOf(value)));
        col.setCellEditor(new BotonCellEditor(onClick));
    }

    private class BotonCellEditor extends AbstractCellEditor implements TableCellEditor {
        private JButton boton;
        private final java.util.function.BiConsumer<Integer, String> onClick;

        BotonCellEditor(java.util.function.BiConsumer<Integer, String> onClick) {
            this.onClick = onClick;
        }

        @Override public Object getCellEditorValue() { return boton.getText(); }

        @Override public Component getTableCellEditorComponent(JTable t, Object value,
                boolean isSelected, int row, int col) {
            String texto = String.valueOf(value);
            boton = crearBotonAccion(texto);
            boton.addActionListener(e -> {
                fireEditingStopped();
                onClick.accept(row, texto);
            });
            return boton;
        }
    }

    // ═══════════════════════════════════════════
    //  MAIN — solo para pruebas aisladas de esta pantalla.
    //  En la app real, WorkBridgeApp crea este panel y lo
    //  muestra dentro de su CardLayout mediante mostrarPantalla().
    // ═══════════════════════════════════════════
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("WorkBridge — Dashboard Moderador (prueba)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1280, 800);
            frame.setMinimumSize(new Dimension(1100, 700));
            frame.setLocationRelativeTo(null);
            frame.add(new DashboardModerador(null));
            frame.setVisible(true);
        });
    }
}

