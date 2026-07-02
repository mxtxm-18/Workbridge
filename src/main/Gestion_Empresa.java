package main;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.sql.*;

public class Gestion_Empresa extends JPanel {

    static final Color NAVY      = new Color(0x24, 0x3A, 0x69);
    static final Color STEEL     = new Color(0x5B, 0x88, 0xA5);
    static final Color MAUVE     = new Color(0x9B, 0x73, 0xA6);
    static final Color BEIGE     = new Color(0xD4, 0xCD, 0xC5);
    static final Color WHITE     = Color.WHITE;
    static final Color BG_MAIN   = new Color(0xF2, 0xF0, 0xED);
    static final Color TEXT_DARK = new Color(0x1A, 0x1A, 0x2E);
    static final Color TEXT_MID  = new Color(0x55, 0x55, 0x77);
    static final Color TEXT_LINK = STEEL;

    private final WorkBridgeApp app;
    private String empresaIdActual;

    private JLabel lblCorreo, lblTelefono, lblSitioWeb, lblDireccion, lblNIT;
    private JTextArea txtSobreEmpresa;
    private JLabel lblLinkedIn, lblFacebook, lblInstagram;
    private JLabel lblVacantesActivas, lblPostulaciones, lblEntrevistas, lblContratados, lblVisitas;
    private JLabel lblTituloEmpresa; // nombre en el topbar

    public Gestion_Empresa(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());

        add(new SidebarAdmin(app, "empresas"), BorderLayout.WEST);
        add(crearContenido(), BorderLayout.CENTER);
    }

    /** Llamar esto al navegar a la pantalla, pasando el id de la empresa a mostrar. */
    public void cargarEmpresaPorId(String empresaId) {
        this.empresaIdActual = empresaId;
        String sql = "SELECT nombre_empresa, nit, sector, descripcion, sitio_web, " +
                     "correo_contacto, telefono, direccion, linkedin_url, facebook_url, " +
                     "instagram_url, visitas_perfil FROM empresas WHERE id = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, empresaId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "No se encontró la empresa.");
                    return;
                }

                int vacantesActivas = contar(con,
                    "SELECT COUNT(*) FROM vacantes WHERE empresa_id=? AND estado='activa'", empresaId);
                int postulaciones = contar(con,
                    "SELECT COUNT(*) FROM postulaciones p JOIN vacantes v ON p.vacante_id=v.id " +
                    "WHERE v.empresa_id=?", empresaId);
                int entrevistas = contar(con,
                    "SELECT COUNT(*) FROM entrevistas ent " +
                    "JOIN postulaciones p ON ent.postulacion_id = p.id " +
                    "JOIN vacantes v ON p.vacante_id = v.id " +
                    "WHERE v.empresa_id=? AND MONTH(ent.fecha_hora)=MONTH(CURDATE()) " +
                    "AND YEAR(ent.fecha_hora)=YEAR(CURDATE())", empresaId);
                int contratados = contar(con,
                    "SELECT COUNT(*) FROM postulaciones p JOIN vacantes v ON p.vacante_id=v.id " +
                    "WHERE v.empresa_id=? AND p.estado='aceptada'", empresaId);

                cargarDatos(
                    rs.getString("nombre_empresa"),
                    rs.getString("correo_contacto"),
                    rs.getString("telefono"),
                    rs.getString("sitio_web"),
                    rs.getString("direccion"),
                    rs.getString("nit"),
                    rs.getString("descripcion"),
                    rs.getString("linkedin_url"),
                    rs.getString("facebook_url"),
                    rs.getString("instagram_url"),
                    vacantesActivas, postulaciones, entrevistas, contratados,
                    rs.getInt("visitas_perfil")
                );
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar los datos de la empresa: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int contar(Connection con, String sql, String empresaId) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, empresaId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
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

        lblTituloEmpresa = new JLabel("Mi empresa");
        lblTituloEmpresa.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTituloEmpresa.setForeground(TEXT_DARK);

        JLabel lblFecha = new JLabel(getFechaActual());
        lblFecha.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblFecha.setForeground(TEXT_MID);

        titulo.add(lblTituloEmpresa);
        titulo.add(lblFecha);

        bar.add(titulo, BorderLayout.WEST);
        return bar;
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
        btnEditar.addActionListener(e -> abrirEditor());

        header.add(titulo,    BorderLayout.WEST);
        header.add(btnEditar, BorderLayout.EAST);
        card.add(header, BorderLayout.NORTH);

        JPanel campos = new JPanel();
        campos.setOpaque(false);
        campos.setLayout(new BoxLayout(campos, BoxLayout.Y_AXIS));

        lblCorreo    = crearValorTexto("—", false);
        lblTelefono  = crearValorTexto("—", true);
        lblSitioWeb  = crearValorTexto("—", false);
        lblDireccion = crearValorTexto("—", false);
        lblNIT       = crearValorTexto("—", true);

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

    private void abrirEditor() {
        if (empresaIdActual == null) {
            JOptionPane.showMessageDialog(this, "No hay una empresa cargada para editar.");
            return;
        }
        Window ventana = SwingUtilities.getWindowAncestor(this);
        Frame frame = (ventana instanceof Frame) ? (Frame) ventana : null;
        new EditarEmpresaDialog(frame, empresaIdActual, this).setVisible(true);
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
        String s = sdf.format(new Date());
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public void cargarDatos(
            String nombreEmpresa, String correo, String telefono,
            String sitioWeb, String direccion, String nit, String descripcion,
            String linkedin, String facebook, String instagram,
            int vacantesActivas, int postulaciones, int entrevistas,
            int contratados, int visitas) {

        SwingUtilities.invokeLater(() -> {
            lblTituloEmpresa.setText(nombreEmpresa != null ? nombreEmpresa : "Mi empresa");
            lblCorreo.setText(vacio(correo));
            lblTelefono.setText(vacio(telefono));
            lblSitioWeb.setText(vacio(sitioWeb));
            lblDireccion.setText(vacio(direccion));
            lblNIT.setText(vacio(nit));
            txtSobreEmpresa.setText(descripcion != null ? descripcion : "");
            lblLinkedIn.setText(vacio(linkedin));
            lblFacebook.setText(vacio(facebook));
            lblInstagram.setText(vacio(instagram));
            lblVacantesActivas.setText(String.valueOf(vacantesActivas));
            lblPostulaciones.setText(String.valueOf(postulaciones));
            lblEntrevistas.setText(String.valueOf(entrevistas));
            lblContratados.setText(String.valueOf(contratados));
            lblVisitas.setText(String.valueOf(visitas));
        });
    }

    private String vacio(String s) {
        return (s == null || s.isBlank()) ? "No registrado" : s;
    }
}