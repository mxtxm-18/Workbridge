package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class Notificaciones extends JPanel {

    private static final Color BEIGE = new Color(0xD4, 0xCD, 0xC5);
    private static final Color BG    = new Color(245, 247, 250);

    private JPanel panelLista;
    private final WorkBridgeApp app;

    public Notificaciones(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(BG);

        add(new SidebarAdmin(app, "notificaciones"), BorderLayout.WEST);

        JPanel derecho = new JPanel(new BorderLayout());
        derecho.setBackground(BG);
        derecho.add(crearTopBar("Notificaciones"), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(BG);

        panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setBackground(BG);

        JScrollPane scroll = new JScrollPane(panelLista);
        scroll.setBorder(null);
        contenido.add(scroll, BorderLayout.CENTER);

        derecho.add(contenido, BorderLayout.CENTER);
        add(derecho, BorderLayout.CENTER);

        cargarNotificaciones();
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
        JLabel perfil = new JLabel("👤");
        perfil.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));

        der.add(buscador);
        der.add(campana);
        der.add(perfil);

        bar.add(izq, BorderLayout.WEST);
        bar.add(der, BorderLayout.EAST);
        return bar;
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
        card.setBackground(leida ? Color.WHITE : new Color(232, 240, 254));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        card.setPreferredSize(new Dimension(0, 80));
        card.setBorder(BorderFactory.createMatteBorder(
            0, leida ? 0 : 4, 1, 0,
            leida ? Color.LIGHT_GRAY : Color.decode("#243A69")));

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