package main;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class GestionUsuarios extends JPanel {

    private final Color BEIGE     = new Color(219, 213, 205);
    private final Color MORADO    = new Color(196, 167, 206);
    private final Color COLOR_MENU = new Color(0x24, 0x3A, 0x69);
    private final Color GRIS_FILAS = new Color(245, 245, 245);

    private DefaultTableModel modeloTabla;
    private final WorkBridgeApp app;

    public GestionUsuarios(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(new SidebarAdmin(app, "gestionUsuarios"), BorderLayout.WEST);

        JPanel derecho = new JPanel(new BorderLayout());
        derecho.setBackground(Color.WHITE);
        derecho.add(crearTopBar(), BorderLayout.NORTH);
        derecho.add(crearContenido(), BorderLayout.CENTER);

        add(derecho, BorderLayout.CENTER);

        cargarUsuarios();
    }

    // ─── Barra superior beige ───────────────────────────────────────────────

    private JPanel crearTopBar() {
        JPanel top = new JPanel(null);
        top.setPreferredSize(new Dimension(0, 70));
        top.setBackground(BEIGE);

        JLabel titulo = new JLabel("Gestión de Usuarios");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setBounds(15, 8, 350, 28);
        top.add(titulo);

        JLabel fecha = new JLabel("Domingo, 7 de junio de 2026");
        fecha.setBounds(15, 38, 250, 18);
        fecha.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        top.add(fecha);

        JLabel campana = new JLabel("🔔");
        campana.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        campana.setBounds(910, 20, 30, 30);
        top.add(campana);

        JLabel perfil = new JLabel("👤");
        perfil.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        perfil.setBounds(945, 20, 30, 30);
        top.add(perfil);

        return top;
    }

    // ─── Contenido principal ────────────────────────────────────────────────

    private JPanel crearContenido() {
        JPanel principal = new JPanel(null);
        principal.setBackground(Color.WHITE);

        // Filtros
        JLabel lblRol = new JLabel("Filtrar por Rol");
        lblRol.setBounds(20, 20, 110, 30);
        lblRol.setFont(new Font("Segoe UI", Font.BOLD, 14));
        principal.add(lblRol);

        JComboBox<String> comboRol = new JComboBox<>(
                new String[]{"Todos", "admin", "reclutador", "trabajador"});
        comboRol.setBounds(135, 20, 120, 30);
        principal.add(comboRol);

        JLabel lblEstado = new JLabel("Filtrar por Estado");
        lblEstado.setBounds(270, 20, 130, 30);
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 14));
        principal.add(lblEstado);

        JComboBox<String> comboEstado = new JComboBox<>(
                new String[]{"Todos", "activo", "inactivo"});
        comboEstado.setBounds(405, 20, 100, 30);
        principal.add(comboEstado);

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBounds(520, 20, 90, 30);
        btnFiltrar.setBackground(COLOR_MENU);
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.setBorderPainted(false);
        btnFiltrar.setFocusPainted(false);
        principal.add(btnFiltrar);

        // Panel tabla
        JPanel panelTabla = new JPanel(null);
        panelTabla.setBounds(15, 65, 970, 560);

        // Encabezado morado
        JPanel encabezado = new JPanel(null);
        encabezado.setBounds(0, 0, 970, 38);
        encabezado.setBackground(MORADO);

        JLabel lista = new JLabel("Lista de Usuarios");
        lista.setForeground(Color.WHITE);
        lista.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lista.setBounds(15, 7, 220, 24);
        encabezado.add(lista);

        JTextField buscar = new JTextField();
        buscar.setBounds(750, 7, 210, 24);
        buscar.setToolTipText("Buscar...");
        encabezado.add(buscar);

        panelTabla.add(encabezado);

        // Tabla
        String[] columnas = {"ID", "Nombre", "Apellido", "Correo", "Rol", "Registrado en"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(new Color(220, 220, 220));
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                if (!sel) c.setBackground(row % 2 == 0 ? Color.WHITE : GRIS_FILAS);
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(0, 38, 970, 522);
        panelTabla.add(scroll);

        principal.add(panelTabla);

        // Filtro en tiempo real por búsqueda
        buscar.addActionListener(e -> filtrarTabla(
                buscar.getText(),
                (String) comboRol.getSelectedItem(),
                (String) comboEstado.getSelectedItem()));

        btnFiltrar.addActionListener(e -> filtrarTabla(
                buscar.getText(),
                (String) comboRol.getSelectedItem(),
                (String) comboEstado.getSelectedItem()));

        return principal;
    }

    // ─── Carga desde BD ─────────────────────────────────────────────────────

    private void cargarUsuarios() {
        modeloTabla.setRowCount(0);
        String sql = "SELECT id, nombre, apellido, email, rol, creado_en "
                   + "FROM usuarios ORDER BY creado_en DESC";
        try (Connection con = ConexionDB.getConexion();
             Statement st  = con.createStatement();
             ResultSet rs  = st.executeQuery(sql)) {
            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email"),
                    rs.getString("rol"),
                    rs.getTimestamp("creado_en")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + ex.getMessage());
        }
    }

    // ─── Filtrado ────────────────────────────────────────────────────────────

    private void filtrarTabla(String texto, String rol, String estado) {
        modeloTabla.setRowCount(0);

        StringBuilder sql = new StringBuilder(
            "SELECT id, nombre, apellido, email, rol, creado_en FROM usuarios WHERE 1=1");

        if (rol != null && !rol.equals("Todos"))
            sql.append(" AND rol = '").append(rol).append("'");

        if (estado != null && !estado.equals("Todos"))
            sql.append(" AND estado = '").append(estado).append("'");

        if (texto != null && !texto.isBlank())
            sql.append(" AND (nombre LIKE '%").append(texto)
               .append("%' OR apellido LIKE '%").append(texto)
               .append("%' OR email LIKE '%").append(texto).append("%')");

        sql.append(" ORDER BY creado_en DESC");

        try (Connection con = ConexionDB.getConexion();
             Statement st  = con.createStatement();
             ResultSet rs  = st.executeQuery(sql.toString())) {
            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email"),
                    rs.getString("rol"),
                    rs.getTimestamp("creado_en")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al filtrar: " + ex.getMessage());
        }
    }
}