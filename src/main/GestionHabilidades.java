package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.UUID;

public class GestionHabilidades extends JPanel {

    private static final Color BEIGE      = new Color(0xD4, 0xCD, 0xC5);
    private static final Color BG         = new Color(245, 247, 250);
    private static final Color COLOR_MENU = Color.decode("#243A69");

    private DefaultTableModel modeloTabla;
    private JTextField tfNombre, tfCategoria;
    private final WorkBridgeApp app;

    public GestionHabilidades(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(BG);

        add(new SidebarAdmin(app, "habilidades"), BorderLayout.WEST);

        JPanel derecho = new JPanel(new BorderLayout());
        derecho.setBackground(BG);
        derecho.add(crearTopBar("Gestión de Habilidades"), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(BG);

        // Formulario
        JPanel panelForm = new JPanel(null);
        panelForm.setBackground(Color.WHITE);
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
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setBorderPainted(false);
        panelForm.add(btnAgregar);

        // Tabla
        String[] columnas = {"ID", "Nombre", "Categoría"};
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

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.add(panelForm, BorderLayout.NORTH);
        contenedor.add(scroll, BorderLayout.CENTER);

        contenido.add(contenedor, BorderLayout.CENTER);
        derecho.add(contenido, BorderLayout.CENTER);
        add(derecho, BorderLayout.CENTER);

        cargarHabilidades();

        btnAgregar.addActionListener(e -> {
            String nombre    = tfNombre.getText().trim();
            String categoria = tfCategoria.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Escribe el nombre de la habilidad.");
                return;
            }
            agregarHabilidad(nombre, categoria);
        });
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