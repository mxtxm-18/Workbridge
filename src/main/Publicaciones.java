package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.util.UUID;

public class Publicaciones extends JPanel {

    private static final Color BEIGE     = new Color(0xD4, 0xCD, 0xC5);
    private static final Color BG        = new Color(245, 247, 250);
    private static final Color COLOR_MENU = Color.decode("#243A69");

    private JPanel panelPosts;
    private final WorkBridgeApp app;

    public Publicaciones(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(BG);
        construirUI();
    }

    private void construirUI() {
        add(new SidebarAdmin(app, "publicaciones"), BorderLayout.WEST);

        JPanel derecho = new JPanel(new BorderLayout());
        derecho.setBackground(BG);
        derecho.add(crearTopBar("Publicaciones"), BorderLayout.NORTH);

        // El rol "trabajador" (usuario) solo puede ver publicaciones, no crearlas.
        boolean puedePublicar = app != null && !"trabajador".equals(app.getRolSesion());

        JPanel panelNuevo = new JPanel(null);
        panelNuevo.setBackground(Color.WHITE);
        panelNuevo.setPreferredSize(new Dimension(0, 100));
        panelNuevo.setVisible(puedePublicar);

        JTextArea taContenido = new JTextArea("¿Qué deseas compartir?");
        taContenido.setBounds(20, 10, 700, 50);
        taContenido.setLineWrap(true);
        taContenido.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panelNuevo.add(taContenido);

        JButton btnPublicar = new JButton("Publicar");
        btnPublicar.setBounds(730, 10, 100, 50);
        btnPublicar.setBackground(COLOR_MENU);
        btnPublicar.setForeground(Color.WHITE);
        btnPublicar.setBorderPainted(false);
        btnPublicar.setFocusPainted(false);
        panelNuevo.add(btnPublicar);

        panelPosts = new JPanel();
        panelPosts.setLayout(new BoxLayout(panelPosts, BoxLayout.Y_AXIS));
        panelPosts.setBackground(BG);

        JScrollPane scroll = new JScrollPane(panelPosts);
        scroll.setBorder(null);

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(panelNuevo, BorderLayout.NORTH);
        centro.add(scroll, BorderLayout.CENTER);

        derecho.add(centro, BorderLayout.CENTER);
        add(derecho, BorderLayout.CENTER);

        cargarPublicaciones();

        btnPublicar.addActionListener(e -> {
            String texto = taContenido.getText().trim();
            if (texto.isEmpty() || texto.equals("¿Qué deseas compartir?")) return;
            publicar(texto);
            taContenido.setText("");
            cargarPublicaciones();
        });
    }

    private JPanel crearTopBar(String titulo) {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BEIGE);
        bar.setBorder(new EmptyBorder(12, 30, 12, 30));
        bar.setPreferredSize(new Dimension(0, 70));

        JPanel izq = new JPanel(new GridLayout(2, 1));
        izq.setOpaque(false);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel lblFecha = new JLabel(new java.text.SimpleDateFormat(
                "EEEE, d 'de' MMMM 'de' yyyy", new java.util.Locale("es", "GT"))
                .format(new java.util.Date()));
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblFecha.setForeground(new Color(0x55, 0x55, 0x77));

        izq.add(lblTitulo);
        izq.add(lblFecha);

        JPanel der = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        der.setOpaque(false);

        JTextField buscador = new JTextField("Buscar...");
        buscador.setPreferredSize(new Dimension(200, 30));
        buscador.setForeground(Color.GRAY);
        buscador.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (buscador.getText().equals("Buscar...")) buscador.setText("");
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (buscador.getText().isEmpty()) buscador.setText("Buscar...");
            }
        });

        JLabel campana = new JLabel("🔔");
        campana.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        campana.setCursor(new Cursor(Cursor.HAND_CURSOR));
        campana.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                if (app != null) app.irANotificaciones();
            }
        });
        JLabel perfil = new JLabel("👤");
        perfil.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        perfil.setCursor(new Cursor(Cursor.HAND_CURSOR));
        perfil.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                if (app != null) app.irAPerfil();
            }
        });

        der.add(buscador);
        der.add(campana);
        der.add(perfil);

        bar.add(izq, BorderLayout.WEST);
        bar.add(der, BorderLayout.EAST);
        return bar;
    }

    private void cargarPublicaciones() {
        panelPosts.removeAll();
        String sql = "SELECT u.nombre, u.apellido, p.contenido, p.creado_en "
                   + "FROM publicaciones p JOIN usuarios u ON p.usuario_id = u.id "
                   + "ORDER BY p.creado_en DESC LIMIT 50";
        try (Connection con = ConexionDB.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                panelPosts.add(crearTarjetaPost(
                    rs.getString("nombre") + " " + rs.getString("apellido"),
                    rs.getString("contenido"),
                    rs.getTimestamp("creado_en").toString()));
            }
        } catch (SQLException ex) {
            panelPosts.add(new JLabel("  Error al cargar publicaciones: " + ex.getMessage()));
        }
        panelPosts.revalidate();
        panelPosts.repaint();
    }

    private void publicar(String texto) {
        String usuarioId = app.getUsuarioIdSesion();
        if (usuarioId == null) {
            JOptionPane.showMessageDialog(this, "Inicia sesión para publicar.");
            return;
        }
        String sql = "INSERT INTO publicaciones (id, usuario_id, contenido, tipo) VALUES (?, ?, ?, 'post')";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, usuarioId);
            ps.setString(3, texto);
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al publicar: " + ex.getMessage());
        }
    }

    private JPanel crearTarjetaPost(String autor, String texto, String fecha) {
        JPanel card = new JPanel(null);
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        card.setPreferredSize(new Dimension(0, 100));
        card.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        JLabel lblAutor = new JLabel(autor);
        lblAutor.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblAutor.setBounds(20, 10, 400, 20);
        card.add(lblAutor);

        JLabel lblFecha = new JLabel(fecha);
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFecha.setForeground(Color.GRAY);
        lblFecha.setBounds(20, 30, 400, 16);
        card.add(lblFecha);

        JLabel lblTexto = new JLabel("<html>" + texto + "</html>");
        lblTexto.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTexto.setBounds(20, 50, 800, 35);
        card.add(lblTexto);

        return card;
    }
}