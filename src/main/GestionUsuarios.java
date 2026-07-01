package main;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class GestionUsuarios extends JPanel {

    // Paleta oficial del proyecto
    private final Color BEIGE      = new Color(0xD4, 0xCD, 0xC5); // #D4CDC5
    private final Color AZUL_CLARO = new Color(0x5B, 0x88, 0xA5); // #5B88A5
    private final Color COLOR_MENU = new Color(0x24, 0x3A, 0x69); // #243A69
    private final Color MORADO     = new Color(0x9B, 0x73, 0xA6); // #9B73A6
    private final Color GRIS_FILAS = new Color(245, 245, 245);

    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private final WorkBridgeApp app;

    public GestionUsuarios(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ── Diseño común: Sidebar y Topbar iguales al resto de la interfaz ──
        add(new SidebarAdmin(app, "gestionUsuarios"), BorderLayout.WEST);

        JPanel derecho = new JPanel(new BorderLayout());
        derecho.setBackground(Color.WHITE);
        derecho.add(crearTopBar(), BorderLayout.NORTH);
        derecho.add(crearContenido(), BorderLayout.CENTER);

        add(derecho, BorderLayout.CENTER);

        cargarUsuarios();
    }

    // ─── Control de acceso por rol ───────────────────────────────────────────
    // NOTA: reemplaza esta línea por la forma real en que WorkBridgeApp guarda
    // el rol del usuario logueado (por ejemplo: app.getUsuarioActual().getRol()).
    // Se deja así para no romper la compilación si ese método aún no existe.
    private boolean tieneAccesoAdmin() {
        String rolActual = "admin"; // TODO: reemplazar por el rol real de la sesión
        if (!"admin".equalsIgnoreCase(rolActual)) {
            JOptionPane.showMessageDialog(this,
                    "No tiene permisos para acceder a esta sección.",
                    "Acceso denegado", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    // ─── Barra superior beige (Topbar común) ────────────────────────────────

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
                new String[]{"Todos", "trabajador", "reclutador", "admin"});
        comboRol.setBounds(135, 20, 120, 30);
        principal.add(comboRol);

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBounds(270, 20, 90, 30);
        btnFiltrar.setBackground(COLOR_MENU);
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.setBorderPainted(false);
        btnFiltrar.setFocusPainted(false);
        principal.add(btnFiltrar);

        // Botones de edición / eliminación (subclases de edición pedidas)
        JButton btnEditar = new JButton("Editar Usuario");
        btnEditar.setBounds(380, 20, 150, 30);
        btnEditar.setBackground(MORADO);
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setBorderPainted(false);
        btnEditar.setFocusPainted(false);
        principal.add(btnEditar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(545, 20, 100, 30);
        btnEliminar.setBackground(AZUL_CLARO);
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setFocusPainted(false);
        principal.add(btnEliminar);

        // Botón de creación (Create del CRUD) — segunda fila de acciones
        JButton btnNuevo = new JButton("+ Nuevo Usuario");
        btnNuevo.setBounds(20, 60, 170, 30);
        btnNuevo.setBackground(BEIGE);
        btnNuevo.setForeground(COLOR_MENU);
        btnNuevo.setBorderPainted(false);
        btnNuevo.setFocusPainted(false);
        principal.add(btnNuevo);

        // Panel tabla (desplazado hacia abajo para dar espacio al botón "Nuevo Usuario")
        JPanel panelTabla = new JPanel(null);
        panelTabla.setBounds(15, 100, 970, 525);

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

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(new Color(220, 220, 220));
        tabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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
                (String) comboRol.getSelectedItem()));

        btnFiltrar.addActionListener(e -> filtrarTabla(
                buscar.getText(),
                (String) comboRol.getSelectedItem()));

        btnNuevo.addActionListener(e -> abrirCreacionUsuario());
        btnEditar.addActionListener(e -> abrirEdicionUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuarioSeleccionado());

        // Doble clic también abre edición
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    abrirEdicionUsuario();
                }
            }
        });

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
            boolean hayDatos = false;
            while (rs.next()) {
                hayDatos = true;
                modeloTabla.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email"),
                    rs.getString("rol"),
                    rs.getTimestamp("creado_en")
                });
            }
            if (!hayDatos) {
                JOptionPane.showMessageDialog(this, "No se encontraron datos.",
                        "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ─── Filtrado (con PreparedStatement, sin inyección SQL) ────────────────

    private void filtrarTabla(String texto, String rol) {
        modeloTabla.setRowCount(0);

        StringBuilder sql = new StringBuilder(
            "SELECT id, nombre, apellido, email, rol, creado_en FROM usuarios WHERE 1=1");

        if (rol != null && !rol.equals("Todos"))
            sql.append(" AND rol = ?");
        if (texto != null && !texto.isBlank())
            sql.append(" AND (nombre LIKE ? OR apellido LIKE ? OR email LIKE ?)");

        sql.append(" ORDER BY creado_en DESC");

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int idx = 1;
            if (rol != null && !rol.equals("Todos"))
                ps.setString(idx++, rol);
            if (texto != null && !texto.isBlank()) {
                String like = "%" + texto + "%";
                ps.setString(idx++, like);
                ps.setString(idx++, like);
                ps.setString(idx++, like);
            }

            try (ResultSet rs = ps.executeQuery()) {
                boolean hayDatos = false;
                while (rs.next()) {
                    hayDatos = true;
                    modeloTabla.addRow(new Object[]{
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("rol"),
                        rs.getTimestamp("creado_en")
                    });
                }
                if (!hayDatos) {
                    JOptionPane.showMessageDialog(this, "No se encontraron datos.",
                            "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al filtrar: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ─── Creación de usuario (abre la subclase CrearUsuarioDialog) ──────────

    private void abrirCreacionUsuario() {
        if (!tieneAccesoAdmin()) return;

        Window ventana = SwingUtilities.getWindowAncestor(this);
        Frame owner = (ventana instanceof Frame) ? (Frame) ventana : null;

        CrearUsuarioDialog dialogo = new CrearUsuarioDialog(owner);
        dialogo.setVisible(true);

        if (dialogo.isGuardadoExitoso()) {
            cargarUsuarios();
        }
    }

    // ─── Edición de usuario (abre la subclase EditarUsuarioDialog) ──────────

    private void abrirEdicionUsuario() {
        if (!tieneAccesoAdmin()) return;

        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un elemento antes de editar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id       = (String) modeloTabla.getValueAt(fila, 0);
        String nombre   = (String) modeloTabla.getValueAt(fila, 1);
        String apellido = (String) modeloTabla.getValueAt(fila, 2);
        String correo   = (String) modeloTabla.getValueAt(fila, 3);
        String rol      = (String) modeloTabla.getValueAt(fila, 4);

        Window ventana = SwingUtilities.getWindowAncestor(this);
        Frame owner = (ventana instanceof Frame) ? (Frame) ventana : null;

        EditarUsuarioDialog dialogo = new EditarUsuarioDialog(
                owner, id, nombre, apellido, correo, rol);
        dialogo.setVisible(true);

        if (dialogo.isGuardadoExitoso()) {
            cargarUsuarios();
        }
    }

    // ─── Eliminación con confirmación ───────────────────────────────────────

    private void eliminarUsuarioSeleccionado() {
        if (!tieneAccesoAdmin()) return;

        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un elemento antes de eliminar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = (String) modeloTabla.getValueAt(fila, 0);
        String nombre = (String) modeloTabla.getValueAt(fila, 1);

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este registro? (" + nombre + ")",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Los cambios se guardaron correctamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron datos.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar la información: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}