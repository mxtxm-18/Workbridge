package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class GestionDocumentos extends JPanel {

    private static final Color BEIGE      = new Color(0xD4, 0xCD, 0xC5);
    private static final Color BG         = new Color(245, 247, 250);
    private static final Color COLOR_MENU = Color.decode("#243A69");

    private DefaultTableModel modeloTabla;
    private final WorkBridgeApp app;

    public GestionDocumentos(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(BG);

        add(new SidebarAdmin(app, "documentos"), BorderLayout.WEST);

        JPanel derecho = new JPanel(new BorderLayout());
        derecho.setBackground(BG);
        derecho.add(crearTopBar("Mis Documentos"), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(BG);

        String[] columnas = {"Tipo", "Nombre de archivo", "Fecha de subida", "URL"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(COLOR_MENU);
        tabla.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        contenido.add(scroll, BorderLayout.CENTER);

        derecho.add(contenido, BorderLayout.CENTER);
        add(derecho, BorderLayout.CENTER);

        cargarDocumentos();
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

    private void cargarDocumentos() {
        modeloTabla.setRowCount(0);
        String usuarioId = app.getUsuarioIdSesion();
        String sql = (usuarioId != null)
            ? "SELECT tipo, nombre_archivo, subido_en, url FROM documentos "
              + "WHERE usuario_id = '" + usuarioId + "' ORDER BY subido_en DESC"
            : "SELECT tipo, nombre_archivo, subido_en, url FROM documentos ORDER BY subido_en DESC";
        try (Connection con = ConexionDB.getConexion();
             Statement st  = con.createStatement();
             ResultSet rs  = st.executeQuery(sql)) {
            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getString("tipo"),
                    rs.getString("nombre_archivo"),
                    rs.getTimestamp("subido_en"),
                    rs.getString("url")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar documentos: " + ex.getMessage());
        }
    }
}