package main;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.UUID;

public class Publicaciones extends JPanel {

    private final Color COLOR_MENU   = Color.decode("#243A69");
    private final Color COLOR_BLANCO = Color.WHITE;

    private JPanel panelPosts;
    private final WorkBridgeApp app;

    public Publicaciones(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));
        construirUI();
    }

    private void construirUI() {
        add(new SidebarAdmin(app, "publicaciones"), BorderLayout.WEST);

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(new Color(245, 247, 250));

        JPanel norte = new JPanel(new BorderLayout());
        norte.setBackground(new Color(245, 247, 250));

        JLabel lblTitulo = new JLabel("Inicio");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 30, 5, 0));
        norte.add(lblTitulo, BorderLayout.WEST);

        JButton btnSalir = new JButton("Cerrar sesión");
        btnSalir.setBackground(Color.decode("#243A69"));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setBorderPainted(false);
        btnSalir.setFocusPainted(false);
        btnSalir.setMargin(new Insets(5, 15, 5, 15));
        btnSalir.addActionListener(e -> app.cerrarSesion());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        btnPanel.setOpaque(false);
        btnPanel.add(btnSalir);
        norte.add(btnPanel, BorderLayout.EAST);

        contenido.add(norte, BorderLayout.NORTH);

        JPanel panelNuevo = new JPanel(null);
        panelNuevo.setBackground(COLOR_BLANCO);
        panelNuevo.setPreferredSize(new Dimension(0, 100));

        JTextArea taContenido = new JTextArea("¿Qué deseas compartir?");
        taContenido.setBounds(20, 10, 700, 50);
        taContenido.setLineWrap(true);
        taContenido.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panelNuevo.add(taContenido);

        JButton btnPublicar = new JButton("Publicar");
        btnPublicar.setBounds(730, 10, 100, 50);
        btnPublicar.setBackground(COLOR_MENU);
        btnPublicar.setForeground(COLOR_BLANCO);
        btnPublicar.setBorderPainted(false);
        btnPublicar.setFocusPainted(false);
        panelNuevo.add(btnPublicar);

        panelPosts = new JPanel();
        panelPosts.setLayout(new BoxLayout(panelPosts, BoxLayout.Y_AXIS));
        panelPosts.setBackground(new Color(245, 247, 250));

        JScrollPane scroll = new JScrollPane(panelPosts);
        scroll.setBorder(null);

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(panelNuevo, BorderLayout.NORTH);
        centro.add(scroll, BorderLayout.CENTER);

        contenido.add(centro, BorderLayout.CENTER);
        add(contenido, BorderLayout.CENTER);

        cargarPublicaciones();

        btnPublicar.addActionListener(e -> {
            String texto = taContenido.getText().trim();
            if (texto.isEmpty() || texto.equals("¿Qué deseas compartir?")) return;
            publicar(texto);
            taContenido.setText("");
            cargarPublicaciones();
        });
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
                String autor = rs.getString("nombre") + " " + rs.getString("apellido");
                panelPosts.add(crearTarjetaPost(
                    autor,
                    rs.getString("contenido"),
                    rs.getTimestamp("creado_en").toString()
                ));
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
        card.setBackground(COLOR_BLANCO);
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