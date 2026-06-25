package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.UUID;

public class GestionHabilidades extends JPanel {

    private final Color COLOR_MENU   = Color.decode("#243A69");
    private final Color COLOR_BLANCO = Color.WHITE;

    private DefaultTableModel modeloTabla;
    private JTextField tfNombre, tfCategoria;
    private final WorkBridgeApp app;

    public GestionHabilidades(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        add(new SidebarAdmin(app, "habilidades"), BorderLayout.WEST);

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(new Color(245, 247, 250));

        JLabel lbl = new JLabel("Gestión de Habilidades");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lbl.setBorder(BorderFactory.createEmptyBorder(15, 30, 5, 0));
        contenido.add(lbl, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(null);
        panelForm.setBackground(COLOR_BLANCO);
        panelForm.setPreferredSize(new Dimension(0, 80));

        JLabel lblN = new JLabel("Habilidad:");
        lblN.setBounds(20, 25, 80, 25);
        panelForm.add(lblN);

        tfNombre = new JTextField();
        tfNombre.setBounds(105, 25, 200, 30);
        panelForm.add(tfNombre);

        JLabel lblC = new JLabel("Categoría:");
        lblC.setBounds(320, 25, 80, 25);
        panelForm.add(lblC);

        tfCategoria = new JTextField();
        tfCategoria.setBounds(405, 25, 200, 30);
        panelForm.add(tfCategoria);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(620, 25, 110, 30);
        btnAgregar.setBackground(COLOR_MENU);
        btnAgregar.setForeground(COLOR_BLANCO);
        btnAgregar.setBorderPainted(false);
        panelForm.add(btnAgregar);

        String[] columnas = {"ID", "Nombre", "Categoría"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(COLOR_MENU);
        tabla.getTableHeader().setForeground(COLOR_BLANCO);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.add(panelForm, BorderLayout.NORTH);
        contenedor.add(scroll, BorderLayout.CENTER);

        contenido.add(contenedor, BorderLayout.CENTER);
        add(contenido, BorderLayout.CENTER);

        cargarHabilidades();

        btnAgregar.addActionListener(e -> {
            String nombre = tfNombre.getText().trim();
            String categoria = tfCategoria.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Escribe el nombre de la habilidad.");
                return;
            }

            agregarHabilidad(nombre, categoria);
        });
    }

    private void cargarHabilidades() {
        modeloTabla.setRowCount(0);
        String sql = "SELECT id, nombre, categoria FROM habilidades ORDER BY categoria, nombre";

        try (Connection con = ConexionDB.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria")
                });
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar habilidades: " + ex.getMessage());
        }
    }

    private void agregarHabilidad(String nombre, String categoria) {
        String sql = "INSERT INTO habilidades (id, nombre, categoria) VALUES (?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, nombre);
            ps.setString(3, categoria.isEmpty() ? null : categoria);
            ps.executeUpdate();

            tfNombre.setText("");
            tfCategoria.setText("");
            cargarHabilidades();

        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this, "Esa habilidad ya existe.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}