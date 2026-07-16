package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class Comunicaciones extends JPanel {

    private static final Color BEIGE = new Color(0xD4, 0xCD, 0xC5);
    private static final Color BG    = new Color(245, 247, 250);

    private JPanel panelMensajes;
    private final WorkBridgeApp app;

    public Comunicaciones(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(BG);

        add(new SidebarAdmin(app, "comunicaciones"), BorderLayout.WEST);

        JPanel derecho = new JPanel(new BorderLayout());
        derecho.setBackground(BG);
        derecho.add(crearTopBar("Comunicaciones"), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(BG);

        panelMensajes = new JPanel();
        panelMensajes.setLayout(new BoxLayout(panelMensajes, BoxLayout.Y_AXIS));
        panelMensajes.setBackground(BG);

        JScrollPane scroll = new JScrollPane(panelMensajes);
        scroll.setBorder(null);
        contenido.add(scroll, BorderLayout.CENTER);

        derecho.add(contenido, BorderLayout.CENTER);
        add(derecho, BorderLayout.CENTER);

        cargarMensajes();
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