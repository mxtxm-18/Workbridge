package main;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Notificaciones extends JPanel {

    private final Color COLOR_BLANCO = Color.WHITE;

    private JPanel panelLista;
    private final WorkBridgeApp app;

    public Notificaciones(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        add(new SidebarAdmin(app, "notificaciones"), BorderLayout.WEST);

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(new Color(245, 247, 250));

        JLabel lblTitulo = new JLabel("Notificaciones");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 30, 10, 0));
        contenido.add(lblTitulo, BorderLayout.NORTH);

        panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setBackground(new Color(245, 247, 250));

        JScrollPane scroll = new JScrollPane(panelLista);
        scroll.setBorder(null);
        contenido.add(scroll, BorderLayout.CENTER);

        add(contenido, BorderLayout.CENTER);

        cargarNotificaciones();
    }

    private void cargarNotificaciones() {
        panelLista.removeAll();

        String usuarioId = app.getUsuarioIdSesion();
        String sql = (usuarioId != null)
            ? "SELECT titulo, mensaje, leida, creado_en FROM notificaciones "
              + "WHERE usuario_id = '" + usuarioId + "' ORDER BY creado_en DESC LIMIT 50"
            : "SELECT titulo, mensaje, leida, creado_en FROM notificaciones "
              + "ORDER BY creado_en DESC LIMIT 50";

        try (Connection con = ConexionDB.getConexion();
             Statement st  = con.createStatement();
             ResultSet rs  = st.executeQuery(sql)) {

            boolean hay = false;

            while (rs.next()) {
                hay = true;
                panelLista.add(crearTarjeta(
                    rs.getString("titulo"),
                    rs.getString("mensaje"),
                    rs.getTimestamp("creado_en").toString(),
                    rs.getBoolean("leida")));
            }

            if (!hay) {
                JLabel lbl = new JLabel("  No hay notificaciones.");
                lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                lbl.setForeground(Color.GRAY);
                panelLista.add(lbl);
            }

        } catch (SQLException ex) {
            panelLista.add(new JLabel("  Error: " + ex.getMessage()));
        }

        panelLista.revalidate();
        panelLista.repaint();
    }

    private JPanel crearTarjeta(String titulo, String mensaje, String fecha, boolean leida) {
        JPanel card = new JPanel(null);
        card.setBackground(leida ? COLOR_BLANCO : new Color(232, 240, 254));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        card.setPreferredSize(new Dimension(0, 80));
        card.setBorder(BorderFactory.createMatteBorder(
            0, leida ? 0 : 4, 1, 0,
            leida ? Color.LIGHT_GRAY : Color.decode("#243A69")
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitulo.setBounds(20, 10, 700, 20);
        card.add(lblTitulo);

        JLabel lblMsg = new JLabel(mensaje != null ? mensaje : "");
        lblMsg.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblMsg.setForeground(Color.DARK_GRAY);
        lblMsg.setBounds(20, 30, 700, 18);
        card.add(lblMsg);

        JLabel lblFecha = new JLabel(fecha);
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFecha.setForeground(Color.GRAY);
        lblFecha.setBounds(20, 52, 400, 16);
        card.add(lblFecha);

        return card;
    }
}