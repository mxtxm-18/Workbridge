package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VerificacionEmpresas extends JPanel {

    private static final Color BEIGE      = new Color(0xD4, 0xCD, 0xC5);
    private static final Color BG         = new Color(245, 247, 250);
    private static final Color COLOR_MENU = Color.decode("#243A69");

    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private final WorkBridgeApp app;

    public VerificacionEmpresas(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(BG);

        add(new SidebarAdmin(app, "empresas"), BorderLayout.WEST);

        JPanel derecho = new JPanel(new BorderLayout());
        derecho.setBackground(BG);
        derecho.add(crearTopBar("Verificación de Empresas"), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(BG);

        String[] columnas = {"ID", "Empresa", "NIT", "Sector", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(COLOR_MENU);
        tabla.getTableHeader().setForeground(Color.WHITE);

        // --- Bloquear modificación de tamaño de columnas y filas ---
        tabla.getTableHeader().setResizingAllowed(false);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla.setFillsViewportHeight(true);
        // ---

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Fijar tamaño del scroll y evitar cambios por layout
        // En este caso, el tamaño se adapta al contenedor, pero no se puede arrastrar
        // porque la tabla no permite resize y el layout es BorderLayout.

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panelBotones.setBackground(BG);

        JButton btnVerificar = new JButton("✔ Verificar");
        btnVerificar.setBackground(new Color(34, 139, 34));
        btnVerificar.setForeground(Color.WHITE);
        btnVerificar.setBorderPainted(false);

        JButton btnRechazar = new JButton("✘ Rechazar");
        btnRechazar.setBackground(new Color(180, 40, 40));
        btnRechazar.setForeground(Color.WHITE);
        btnRechazar.setBorderPainted(false);

        JButton btnRefrescar = new JButton("↺ Refrescar");
        btnRefrescar.setBorderPainted(false);

        panelBotones.add(btnVerificar);
        panelBotones.add(btnRechazar);
        panelBotones.add(btnRefrescar);

        contenido.add(scroll, BorderLayout.CENTER);
        contenido.add(panelBotones, BorderLayout.SOUTH);

        derecho.add(contenido, BorderLayout.CENTER);
        add(derecho, BorderLayout.CENTER);

        cargarEmpresas();
        btnVerificar.addActionListener(e -> cambiarEstado("verificada"));
        btnRechazar.addActionListener(e  -> cambiarEstado("rechazada"));
        btnRefrescar.addActionListener(e -> cargarEmpresas());
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

    private void cargarEmpresas() {
        modeloTabla.setRowCount(0);
        String sql = "SELECT id, nombre_empresa, nit, sector, estado_verificacion "
                   + "FROM empresas ORDER BY creado_en DESC";
        try (Connection con = ConexionDB.getConexion();
             Statement st  = con.createStatement();
             ResultSet rs  = st.executeQuery(sql)) {
            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("nombre_empresa"),
                    rs.getString("nit"),
                    rs.getString("sector"),
                    rs.getString("estado_verificacion")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void cambiarEstado(String nuevoEstado) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una empresa de la lista.");
            return;
        }
        String id  = (String) modeloTabla.getValueAt(fila, 0);
        String sql = "UPDATE empresas SET estado_verificacion = ?, verificado_en = NOW() WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setString(2, id);
            ps.executeUpdate();
            cargarEmpresas();
            JOptionPane.showMessageDialog(this, "Empresa marcada como: " + nuevoEstado);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}