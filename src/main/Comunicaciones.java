package main;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Comunicaciones extends JPanel {

    private JPanel panelMensajes;
    private final WorkBridgeApp app;

    public Comunicaciones(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        add(new SidebarAdmin(app, "comunicaciones"), BorderLayout.WEST);

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(new Color(245, 247, 250));

        JLabel lblTitulo = new JLabel("Comunicaciones");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 30, 10, 0));
        contenido.add(lblTitulo, BorderLayout.NORTH);

        panelMensajes = new JPanel();
        panelMensajes.setLayout(new BoxLayout(panelMensajes, BoxLayout.Y_AXIS));
        panelMensajes.setBackground(new Color(245, 247, 250));

        JScrollPane scroll = new JScrollPane(panelMensajes);
        scroll.setBorder(null);
        contenido.add(scroll, BorderLayout.CENTER);

        add(contenido, BorderLayout.CENTER);

        cargarMensajes();
    }

    private void cargarMensajes() {
        panelMensajes.removeAll();

        String sql = "SELECT titulo, mensaje, creado_en FROM notificaciones "
                   + "WHERE tipo = 'mensaje' ORDER BY creado_en DESC LIMIT 50";

        try (Connection con = ConexionDB.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean hay = false;

            while (rs.next()) {
                hay = true;

                JPanel card = new JPanel(null);
                card.setBackground(Color.WHITE);
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
                card.setPreferredSize(new Dimension(0, 80));
                card.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

                JLabel lTitulo = new JLabel(rs.getString("titulo"));
                lTitulo.setFont(new Font("Segoe UI", Font.BOLD, 13));
                lTitulo.setBounds(20, 10, 700, 20);
                card.add(lTitulo);

                String msg = rs.getString("mensaje");
                JLabel lMsg = new JLabel(msg != null ? msg : "");
                lMsg.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                lMsg.setBounds(20, 32, 700, 18);
                card.add(lMsg);

                JLabel lFecha = new JLabel(rs.getTimestamp("creado_en").toString());
                lFecha.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                lFecha.setForeground(Color.GRAY);
                lFecha.setBounds(20, 54, 400, 16);
                card.add(lFecha);

                panelMensajes.add(card);
            }

            if (!hay) {
                JLabel l = new JLabel("  No hay mensajes.");
                l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                l.setForeground(Color.GRAY);
                panelMensajes.add(l);
            }

        } catch (SQLException ex) {
            panelMensajes.add(new JLabel("  Error: " + ex.getMessage()));
        }

        panelMensajes.revalidate();
        panelMensajes.repaint();
    }
}