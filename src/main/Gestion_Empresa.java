import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Gestion_Empresa extends JFrame {

    static final Color NAVY      = new Color(0x24, 0x3A, 0x69);
    static final Color STEEL     = new Color(0x5B, 0x88, 0xA5);
    static final Color MAUVE     = new Color(0x9B, 0x73, 0xA6);
    static final Color BEIGE     = new Color(0xD4, 0xCD, 0xC5);
    static final Color WHITE     = Color.WHITE;
    static final Color BG_MAIN   = new Color(0xF2, 0xF0, 0xED);
    static final Color TEXT_DARK = new Color(0x1A, 0x1A, 0x2E);
    static final Color TEXT_MID  = new Color(0x55, 0x55, 0x77);
    static final Color TEXT_LINK = STEEL;
    static final Color SIDEBAR_ACTIVE_BG = MAUVE;

    private static final int SIDEBAR_WIDTH = 340;

    private JLabel lblNombreEmpresa;
    private JLabel lblCorreo;
    private JLabel lblTelefono;
    private JLabel lblSitioWeb;
    private JLabel lblDireccion;
    private JLabel lblNIT;
    private JTextArea txtSobreEmpresa;
    private JLabel lblLinkedIn;
    private JLabel lblFacebook;
    private JLabel lblInstagram;

    private JLabel lblVacantesActivas;
    private JLabel lblPostulaciones;
    private JLabel lblEntrevistas;
    private JLabel lblContratados;
    private JLabel lblVisitas;

    public Gestion_Empresa() {
        setTitle("WorkBridge");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1366, 768));
        setLayout(new BorderLayout());

        add(crearSidebar(), BorderLayout.WEST);
        add(crearContenido(), BorderLayout.CENTER);

        cargarDatos(
            "TechCorp S.A.",
            "rrhh@techcorp.com.gt",
            "+502 2222-3333",
            "www.techcorp.com.gt",
            "4a Av. 12-45 Zona 10, Ciudad de Guatemala",
            "1234567-8",
            "TechCorp S.A. es una empresa líder en desarrollo de software y soluciones tecnológicas para el mercado centroamericano. Nos especializamos en transformación digital, desarrollo de aplicaciones empresariales y consultoría en tecnología de la información.\n\nCreemos en el talento local y la innovación constante. Nuestro equipo multidisciplinario trabaja en proyectos retadores para clientes en Guatemala, El Salvador y Honduras.",
            "linkedin.com/company/techcorp-gt",
            "facebook.com/techcorpgt",
            "@techcorpgt",
            3, 47, 8, 2, 312
        );
    }

    private JPanel crearSidebar() {
        JPanel sidebar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(NAVY);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebar.setPreferredSize(new Dimension(SIDEBAR_WIDTH, 1080));
        sidebar.setMinimumSize(new Dimension(SIDEBAR_WIDTH, 1080));
        sidebar.setMaximumSize(new Dimension(SIDEBAR_WIDTH, Integer.MAX_VALUE));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setOpaque(false);

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 24));
        logoPanel.setOpaque(false);
        logoPanel.setMaximumSize(new Dimension(SIDEBAR_WIDTH, 180));

        URL urlLogo = getClass().getResource("/Recursos/logo.png");
        JLabel lblLogo;
        if (urlLogo != null) {
            Image logoImg = new ImageIcon(urlLogo).getImage()
                    .getScaledInstance(250, -1, Image.SCALE_SMOOTH);
            lblLogo = new JLabel(new ImageIcon(logoImg));
        } else {
            lblLogo = new JLabel("WorkBridge");
            lblLogo.setFont(new Font("SansSerif", Font.BOLD, 22));
            lblLogo.setForeground(WHITE);
        }
        lblLogo.setPreferredSize(new Dimension(250, 120));

        logoPanel.add(lblLogo);
        sidebar.add(logoPanel);

        JLabel tagline = new JLabel("CONECTAMOS TALENTO, GENERAMOS OPORTUNIDADES");
        tagline.setFont(new Font("SansSerif", Font.PLAIN, 10));
        tagline.setForeground(BEIGE);
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(tagline);
        sidebar.add(Box.createVerticalStrut(24));

        String[][] items = {
            {"Inicio",        "INICIO"},
            {"Mi empresa",    "MI_EMPRESA"},
            {"Mis Vacantes",  "VACANTES"},
            {"Postulaciones", "POSTULACIONES"},
        };

        for (String[] item : items) {
            sidebar.add(crearMenuItem(item[0], item[1].equals("MI_EMPRESA")));
        }

        sidebar.add(crearSeccionLabel("Gestión"));
        sidebar.add(crearMenuItem("Entrevistas",    false));
        sidebar.add(crearMenuItem("Comunicaciones", false));
        sidebar.add(crearMenuItem("Notificaciones", false));

        sidebar.add(crearSeccionLabel("Sistema"));
        sidebar.add(crearMenuItem("Configuracion",  false));

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(crearSidebarFooter());

        return sidebar;
    }

    private JLabel crearSeccionLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl.setForeground(new Color(0xAA, 0xBB, 0xCC));
        lbl.setBorder(new EmptyBorder(16, 24, 4, 0));
        lbl.setMaximumSize(new Dimension(SIDEBAR_WIDTH, 30));
        return lbl;
    }

    private JPanel crearMenuItem(String texto, boolean activo) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 11)) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (activo) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(SIDEBAR_ACTIVE_BG);
                    g2.fill(new RoundRectangle2D.Double(10, 3, getWidth() - 20, getHeight() - 6, 14, 14));
                }
            }
        };
        item.setOpaque(false);
        item.setMaximumSize(new Dimension(SIDEBAR_WIDTH, 52));
        item.setPreferredSize(new Dimension(SIDEBAR_WIDTH, 52));
        item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", activo ? Font.BOLD : Font.PLAIN, 18));
        lbl.setForeground(WHITE);
        item.add(lbl);

        if (!activo) {
            item.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { item.repaint(); }
                public void mouseExited(MouseEvent e)  { item.repaint(); }
            });
        }
        return item;
    }

    private JPanel crearSidebarFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 16));
        footer.setOpaque(false);
        footer.setMaximumSize(new Dimension(SIDEBAR_WIDTH, 86));
        footer.setBorder(new MatteBorder(1, 0, 0, 0, new Color(0x40, 0x50, 0x80)));

        JLabel fotoPequena = new JLabel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(STEEL);
                g2.fillOval(0, 0, 44, 44);
                g2.setColor(WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 16));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString("T", (44 - fm.stringWidth("T")) / 2, 28);
                g2.dispose();
            }
        };
        fotoPequena.setPreferredSize(new Dimension(44, 44));

        lblNombreEmpresa = new JLabel("TechCorp S.A.");
        lblNombreEmpresa.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNombreEmpresa.setForeground(WHITE);

        footer.add(fotoPequena);
        footer.add(lblNombreEmpresa);
        return footer;
    }

    private JPanel crearContenido() {
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(BG_MAIN);
        contenido.add(crearTopBar(), BorderLayout.NORTH);

        JPanel cards = crearAreaTarjetas();
        JScrollPane scroll = new JScrollPane(cards);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        contenido.add(scroll, BorderLayout.CENTER);

        return contenido;
    }

    private JPanel crearTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG_MAIN);
        bar.setBorder(new EmptyBorder(20, 40, 12, 40));

        JPanel titulo = new JPanel(new GridLayout(2, 1));
        titulo.setOpaque(false);

        JLabel lblTitulo = new JLabel("Mi empresa");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setForeground(TEXT_DARK);

        JLabel lblFecha = new JLabel(getFechaActual());
        lblFecha.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblFecha.setForeground(TEXT_MID);

        titulo.add(lblTitulo);
        titulo.add(lblFecha);

        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        derecha.setOpaque(false);

        JTextField buscador = new JTextField("Buscar candidatos");
        buscador.setPreferredSize(new Dimension(240, 34));
        buscador.setFont(new Font("SansSerif", Font.PLAIN, 13));
        buscador.setForeground(TEXT_MID);
        buscador.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BEIGE, 1, true),
            new EmptyBorder(0, 8, 0, 8)
        ));
        buscador.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (buscador.getText().equals("Buscar candidatos")) buscador.setText("");
            }
            public void focusLost(FocusEvent e) {
                if (buscador.getText().isEmpty()) buscador.setText("Buscar candidatos");
            }
        });

        JButton btnCampana = crearIconBtn("🔔");
        JButton btnUsuario = crearIconBtn("👤");

        derecha.add(buscador);
        derecha.add(btnCampana);
        derecha.add(btnUsuario);

        bar.add(titulo, BorderLayout.WEST);
        bar.add(derecha, BorderLayout.EAST);
        return bar;
    }

    private JButton crearIconBtn(String emoji) {
        JButton btn = new JButton(emoji);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel crearAreaTarjetas() {
        JPanel area = new JPanel(new GridBagLayout());
        area.setBackground(BG_MAIN);
        area.setBorder(new EmptyBorder(15, 40, 35, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.58; gbc.weighty = 0.5;
        area.add(crearCardInfoGeneral(), gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.42; gbc.weighty = 0.5;
        area.add(crearCardResumenActividad(), gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.58; gbc.weighty = 0.5;
        area.add(crearCardSobreEmpresa(), gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.42; gbc.weighty = 0.5;
        area.add(crearCardRedes(), gbc);

        return area;
    }

    private JPanel crearCardInfoGeneral() {
        JPanel card = crearCard();
        card.setLayout(new BorderLayout(0, 12));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel titulo = crearTituloCard("INFORMACION GENERAL");

        JButton btnEditar = new JButton("✎ Editar");
        btnEditar.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnEditar.setForeground(STEEL);
        btnEditar.setBorderPainted(false);
        btnEditar.setContentAreaFilled(false);
        btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        header.add(titulo,    BorderLayout.WEST);
        header.add(btnEditar, BorderLayout.EAST);
        card.add(header, BorderLayout.NORTH);

        JPanel campos = new JPanel();
        campos.setOpaque(false);
        campos.setLayout(new BoxLayout(campos, BoxLayout.Y_AXIS));

        lblCorreo    = crearValorTexto("rrhh@techcorp.com.gt", false);
        lblTelefono  = crearValorTexto("+502 2222-3333", true);
        lblSitioWeb  = crearValorTexto("www.techcorp.com.gt", false);
        lblDireccion = crearValorTexto("4a Av. 12-45 Zona 10, Ciudad de Guatemala", false);
        lblNIT       = crearValorTexto("1234567-8", true);

        campos.add(crearFilaConLabel("✉",  "Correo de Contacto", lblCorreo));
        campos.add(Box.createVerticalStrut(12));
        campos.add(crearFilaConLabel("📞", "Teléfono",           lblTelefono));
        campos.add(Box.createVerticalStrut(12));
        campos.add(crearFilaConLabel("🌐", "Sitio web",          lblSitioWeb));
        campos.add(Box.createVerticalStrut(12));
        campos.add(crearFilaConLabel("📍", "Dirección",          lblDireccion));
        campos.add(Box.createVerticalStrut(12));
        campos.add(crearFilaConLabel("🪪", "NIT/Registro legal", lblNIT));

        card.add(campos, BorderLayout.CENTER);
        return card;
    }

    private JLabel crearValorTexto(String valor, boolean negrita) {
        JLabel lbl = new JLabel(valor);
        lbl.setFont(new Font("SansSerif", negrita ? Font.BOLD : Font.PLAIN, 15));
        lbl.setForeground(negrita ? TEXT_DARK : TEXT_LINK);
        return lbl;
    }

    private JPanel crearFilaConLabel(String icono, String etiqueta, JLabel valorLabel) {
        JPanel fila = new JPanel();
        fila.setOpaque(false);
        fila.setLayout(new BoxLayout(fila, BoxLayout.X_AXIS));
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel ico = new JLabel(icono + "  ");
        ico.setFont(new Font("SansSerif", Font.PLAIN, 18));
        ico.setForeground(STEEL);

        JPanel texto = new JPanel();
        texto.setOpaque(false);
        texto.setLayout(new BoxLayout(texto, BoxLayout.Y_AXIS));

        JLabel lEtiqueta = new JLabel(etiqueta);
        lEtiqueta.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lEtiqueta.setForeground(STEEL);

        texto.add(lEtiqueta);
        texto.add(valorLabel);

        fila.add(ico);
        fila.add(texto);
        fila.add(Box.createHorizontalGlue());
        return fila;
    }

    private JPanel crearCardResumenActividad() {
        JPanel card = crearCard();
        card.setLayout(new BorderLayout(0, 10));
        card.add(crearTituloCard("RESUMEN DE ACTIVIDAD"), BorderLayout.NORTH);

        JPanel filas = new JPanel();
        filas.setOpaque(false);
        filas.setLayout(new BoxLayout(filas, BoxLayout.Y_AXIS));

        lblVacantesActivas = new JLabel("0");
        lblPostulaciones   = new JLabel("0");
        lblEntrevistas     = new JLabel("0");
        lblContratados     = new JLabel("0");
        lblVisitas         = new JLabel("0");

        filas.add(crearFilaActividad("📄", "Vacantes activas",      lblVacantesActivas));
        filas.add(Box.createVerticalStrut(8));
        filas.add(crearFilaActividad("👥", "Postulaciones totales", lblPostulaciones));
        filas.add(Box.createVerticalStrut(8));
        filas.add(crearFilaActividad("📅", "Entrevistas este mes",  lblEntrevistas));
        filas.add(Box.createVerticalStrut(8));
        filas.add(crearFilaActividad("✓",  "Contratados",           lblContratados));
        filas.add(Box.createVerticalStrut(8));
        filas.add(crearFilaActividad("👁",  "Visitas al perfil",     lblVisitas));

        card.add(filas, BorderLayout.CENTER);
        return card;
    }

    private JPanel crearFilaActividad(String icono, String etiqueta, JLabel lblNumero) {
        JPanel fila = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0xF5, 0xF3, 0xF0));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            }
        };
        fila.setOpaque(false);
        fila.setBorder(new EmptyBorder(10, 14, 10, 14));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

        JLabel izq = new JLabel(icono + "  " + etiqueta);
        izq.setFont(new Font("SansSerif", Font.PLAIN, 14));
        izq.setForeground(TEXT_DARK);

        lblNumero.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblNumero.setForeground(TEXT_DARK);

        fila.add(izq,      BorderLayout.WEST);
        fila.add(lblNumero, BorderLayout.EAST);
        return fila;
    }

    private JPanel crearCardSobreEmpresa() {
        JPanel card = crearCard();
        card.setLayout(new BorderLayout(0, 12));
        card.add(crearTituloCard("SOBRE LA EMPRESA"), BorderLayout.NORTH);

        txtSobreEmpresa = new JTextArea();
        txtSobreEmpresa.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtSobreEmpresa.setForeground(TEXT_DARK);
        txtSobreEmpresa.setLineWrap(true);
        txtSobreEmpresa.setWrapStyleWord(true);
        txtSobreEmpresa.setEditable(false);
        txtSobreEmpresa.setOpaque(false);
        txtSobreEmpresa.setBorder(null);

        card.add(txtSobreEmpresa, BorderLayout.CENTER);
        return card;
    }

    private JPanel crearCardRedes() {
        JPanel card = crearCard();
        card.setLayout(new BorderLayout(0, 10));
        card.add(crearTituloCard("REDES Y PRESENCIA"), BorderLayout.NORTH);

        JPanel redes = new JPanel();
        redes.setOpaque(false);
        redes.setLayout(new BoxLayout(redes, BoxLayout.Y_AXIS));

        lblLinkedIn  = new JLabel();
        lblFacebook  = new JLabel();
        lblInstagram = new JLabel();

        redes.add(crearFilaRed("in", "LinkedIn",  lblLinkedIn,  new Color(0x0A, 0x66, 0xC2)));
        redes.add(Box.createVerticalStrut(14));
        redes.add(crearFilaRed("f",  "Facebook",  lblFacebook,  new Color(0x18, 0x77, 0xF2)));
        redes.add(Box.createVerticalStrut(14));
        redes.add(crearFilaRed("ig", "Instagram", lblInstagram, MAUVE));

        card.add(redes, BorderLayout.CENTER);
        return card;
    }

    private JPanel crearFilaRed(String sigla, String red, JLabel lblUrl, Color color) {
        JPanel fila = new JPanel();
        fila.setOpaque(false);
        fila.setLayout(new BoxLayout(fila, BoxLayout.X_AXIS));
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel ico = new JLabel(sigla) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillRoundRect(0, 0, 36, 36, 36, 36);
                g2.setColor(WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                String t = getText();
                g2.drawString(t, (36 - fm.stringWidth(t)) / 2, 24);
                g2.dispose();
            }
        };
        ico.setPreferredSize(new Dimension(36, 36));
        ico.setMaximumSize(new Dimension(36, 36));

        JPanel texto = new JPanel();
        texto.setOpaque(false);
        texto.setLayout(new BoxLayout(texto, BoxLayout.Y_AXIS));

        JLabel lRed = new JLabel(red);
        lRed.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lRed.setForeground(STEEL);

        lblUrl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblUrl.setForeground(TEXT_LINK);

        texto.add(lRed);
        texto.add(lblUrl);

        fila.add(ico);
        fila.add(Box.createHorizontalStrut(10));
        fila.add(texto);
        fila.add(Box.createHorizontalGlue());
        return fila;
    }

    private JPanel crearCard() {
        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.setColor(BEIGE);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(22, 24, 22, 24));
        return card;
    }

    private JLabel crearTituloCard(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(new Color(0x5A, 0x6A, 0x85));
        return lbl;
    }

    private String getFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d 'de' MMMM 'de' yyyy", new Locale("es", "GT"));
        return capitalize(sdf.format(new Date()));
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public void cargarDatos(
            String nombreEmpresa, String correo, String telefono,
            String sitioWeb, String direccion, String nit, String descripcion,
            String linkedin, String facebook, String instagram,
            int vacantesActivas, int postulaciones, int entrevistas,
            int contratados, int visitas) {

        SwingUtilities.invokeLater(() -> {
            lblNombreEmpresa.setText(nombreEmpresa);
            lblCorreo.setText(correo);
            lblTelefono.setText(telefono);
            lblSitioWeb.setText(sitioWeb);
            lblDireccion.setText(direccion);
            lblNIT.setText(nit);
            txtSobreEmpresa.setText(descripcion);
            lblLinkedIn.setText(linkedin);
            lblFacebook.setText(facebook);
            lblInstagram.setText(instagram);
            lblVacantesActivas.setText(String.valueOf(vacantesActivas));
            lblPostulaciones.setText(String.valueOf(postulaciones));
            lblEntrevistas.setText(String.valueOf(entrevistas));
            lblContratados.setText(String.valueOf(contratados));
            lblVisitas.setText(String.valueOf(visitas));
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new Gestion_Empresa().setVisible(true));
    }
}