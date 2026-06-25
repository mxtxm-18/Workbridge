package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VerificacionEmpresas extends JPanel {

    private final Color COLOR_MENU   = Color.decode("#243A69");
    private final Color COLOR_BLANCO = Color.WHITE;

    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private final WorkBridgeApp app;

    public VerificacionEmpresas(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JLabel lblTitulo = new JLabel("Verificación de Empresas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 30, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        String[] columnas = {"ID", "Empresa", "NIT", "Sector", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(COLOR_MENU);
        tabla.getTableHeader().setForeground(COLOR_BLANCO);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panelBotones.setBackground(new Color(245, 247, 250));

        JButton btnVerificar = new JButton("✔ Verificar");
        btnVerificar.setBackground(new Color(34, 139, 34));
        btnVerificar.setForeground(COLOR_BLANCO);
        btnVerificar.setBorderPainted(false);

        JButton btnRechazar = new JButton("✘ Rechazar");
        btnRechazar.setBackground(new Color(180, 40, 40));
        btnRechazar.setForeground(COLOR_BLANCO);
        btnRechazar.setBorderPainted(false);

        JButton btnRefrescar = new JButton("↺ Refrescar");
        btnRefrescar.setBorderPainted(false);

        panelBotones.add(btnVerificar);
        panelBotones.add(btnRechazar);
        panelBotones.add(btnRefrescar);

        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarEmpresas();

        btnVerificar.addActionListener(e -> cambiarEstado("verificada"));
        btnRechazar.addActionListener(e  -> cambiarEstado("rechazada"));
        btnRefrescar.addActionListener(e -> cargarEmpresas());
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
