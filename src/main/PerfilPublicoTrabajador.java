package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class PerfilPublicoTrabajador extends JPanel {

    private static final Color BEIGE = new Color(0xD4, 0xCD, 0xC5);
    private static final Color BG    = new Color(245, 247, 250);

    private final WorkBridgeApp app;

    public PerfilPublicoTrabajador(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(BG);

        add(new SidebarAdmin(app, "perfilPublicoTrabajador"), BorderLayout.WEST);

        JPanel derecho = new JPanel(new BorderLayout());
        derecho.setBackground(BG);
        derecho.add(crearTopBar("Perfil del Candidato"), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(BG);

        JPanel panelInfo = new JPanel(null);
        panelInfo.setBackground(Color.WHITE);
        contenido.add(panelInfo, BorderLayout.CENTER);

        derecho.add(contenido, BorderLayout.CENTER);
        add(derecho, BorderLayout.CENTER);

        cargarPerfil(panelInfo);
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

    private void cargarPerfil(JPanel panel) {
        String usuarioId = app.getUsuarioIdSesion();
        String sql = (usuarioId != null)
            ? "SELECT u.nombre, u.apellido, u.email, u.departamento, u.municipio, "
              + "p.resumen, p.nivel_experiencia, p.modalidad_preferida, p.disponible "
              + "FROM usuarios u JOIN perfiles_trabajador p ON u.id = p.usuario_id "
              + "WHERE u.id = '" + usuarioId + "' LIMIT 1"
            : "SELECT u.nombre, u.apellido, u.email, u.departamento, u.municipio, "
              + "p.resumen, p.nivel_experiencia, p.modalidad_preferida, p.disponible "
              + "FROM usuarios u JOIN perfiles_trabajador p ON u.id = p.usuario_id LIMIT 1";

        try (Connection con = ConexionDB.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                JLabel lNombre = new JLabel(rs.getString("nombre") + " " + rs.getString("apellido"));
                lNombre.setFont(new Font("Segoe UI", Font.BOLD, 20));
                lNombre.setBounds(40, 30, 500, 30);
                panel.add(lNombre);

                agregarCampo(panel, "Correo:",     rs.getString("email"),                  80);
                agregarCampo(panel, "Ubicación:",  rs.getString("departamento") + ", " + rs.getString("municipio"), 120);
                agregarCampo(panel, "Nivel:",      rs.getString("nivel_experiencia"),       160);
                agregarCampo(panel, "Modalidad:",  rs.getString("modalidad_preferida"),     200);
                agregarCampo(panel, "Disponible:", rs.getBoolean("disponible") ? "Sí" : "No", 240);

                String resumen = rs.getString("resumen");
                JLabel lResumen = new JLabel(
                    "<html><b>Resumen:</b><br>" + (resumen != null ? resumen : "Sin resumen") + "</html>");
                lResumen.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                lResumen.setBounds(40, 290, 700, 80);
                panel.add(lResumen);
            } else {
                JLabel l = new JLabel("No se encontró perfil de trabajador.");
                l.setBounds(40, 40, 400, 30);
                panel.add(l);
            }
        } catch (SQLException ex) {
            JLabel l = new JLabel("Error al cargar perfil: " + ex.getMessage());
            l.setBounds(40, 40, 600, 30);
            panel.add(l);
        }
    }

    private void agregarCampo(JPanel panel, String etiqueta, String valor, int y) {
        JLabel lbl = new JLabel(
            "<html><b>" + etiqueta + "</b> " + (valor != null ? valor : "—") + "</html>");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setBounds(40, y, 600, 25);
        panel.add(lbl);
    }
}