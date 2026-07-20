package main;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.UUID;
import org.mindrot.jbcrypt.BCrypt;

public class Registro extends JPanel {

    private final Color COLOR_MENU   = Color.decode("#243A69");
    private final Color COLOR_SEC    = Color.decode("#5B88A5");
    private final Color COLOR_ACENTO = Color.decode("#9B73A6");
    private final Color COLOR_FONDO  = Color.decode("#D4CDC5");
    private final Color COLOR_BLANCO = Color.WHITE;

    private JTextField    tfNombre, tfApellido, tfCorreo, tfTelefono;
    private JPasswordField pfPass, pfPass2;
    private JToggleButton  btnTrabajador, btnEmpresa;
    
    private final WorkBridgeApp app;

    public Registro(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        construirUI();
    }

    private void construirUI() {
        // ---- PANEL IZQUIERDO (branding) ----
        JPanel panelIzq = new JPanel(null);
        panelIzq.setPreferredSize(new Dimension(420, 800));
        panelIzq.setBackground(COLOR_MENU);

        // Logo desde Recursos/logo.png — si no existe muestra texto
        JLabel lblLogo = Recursos.crearLabelLogo(160, 70, "WorkBridge");
        lblLogo.setBounds(40, 40, 200, 80);
        panelIzq.add(lblLogo);

        JLabel lblSlogan = new JLabel(
            "<html><font color='white'><b>Conectamos talento.</b><br>Generamos Oportunidades.<br><br>"
          + "La plataforma profesional de Guatemala<br>donde empresas líderes y el mejor talento<br>"
          + "se encuentran, conectan y crecen juntos.</font></html>");
        lblSlogan.setBounds(40, 160, 340, 200);
        panelIzq.add(lblSlogan);

        JLabel lblStats = new JLabel(
            "<html><font color='white'><b>+8,400</b> Profesionales activos<br>"
          + "<b>+1,200</b> Empresas registradas<br>"
          + "<b>+3,500</b> Vacantes publicadas</font></html>");
        lblStats.setBounds(40, 600, 340, 100);
        panelIzq.add(lblStats);

        add(panelIzq, BorderLayout.WEST);

        // ---- PANEL DERECHO (formulario) ----
        JPanel panelDer = new JPanel(null);
        panelDer.setBackground(COLOR_BLANCO);

        JLabel lblTitulo = new JLabel("Crear cuenta nueva");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setBounds(60, 40, 400, 35);
        panelDer.add(lblTitulo);

        JLabel lblSub = new JLabel("Ingresa tus datos para registrarte");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(Color.GRAY);
        lblSub.setBounds(60, 75, 350, 20);
        panelDer.add(lblSub);

        // Tipo cuenta
        JLabel lblTipo = new JLabel("Tipo de cuenta");
        lblTipo.setBounds(60, 110, 200, 20);
        lblTipo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelDer.add(lblTipo);

        btnTrabajador = new JToggleButton("Trabajador");
        btnTrabajador.setBounds(60, 132, 150, 32);
        btnTrabajador.setBackground(COLOR_MENU);
        btnTrabajador.setForeground(COLOR_BLANCO);
        btnTrabajador.setFocusPainted(false);
        btnTrabajador.setSelected(true);
        panelDer.add(btnTrabajador);

        btnEmpresa = new JToggleButton("Empresa");
        btnEmpresa.setBounds(215, 132, 150, 32);
        btnEmpresa.setBackground(COLOR_FONDO);
        btnEmpresa.setForeground(Color.DARK_GRAY);
        btnEmpresa.setFocusPainted(false);
        panelDer.add(btnEmpresa);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(btnTrabajador);
        grupo.add(btnEmpresa);

        // Campos
        panelDer.add(label("Nombre", 60, 185));
        tfNombre = field(60, 205, panelDer);

        panelDer.add(label("Apellido", 60, 253));
        tfApellido = field(60, 273, panelDer);

        panelDer.add(label("Correo electrónico", 60, 321));
        tfCorreo = field(60, 341, panelDer);

        panelDer.add(label("Teléfono (opcional)", 60, 389));
        tfTelefono = field(60, 409, panelDer);

        panelDer.add(label("Contraseña", 60, 457));
        pfPass = new JPasswordField();
        pfPass.setBounds(60, 477, 480, 32);
        panelDer.add(pfPass);

        panelDer.add(label("Confirmar contraseña", 60, 525));
        pfPass2 = new JPasswordField();
        pfPass2.setBounds(60, 545, 480, 32);
        panelDer.add(pfPass2);

        JCheckBox chkTerminos = new JCheckBox(
            "Acepto los Términos de uso y Política de privacidad");
        chkTerminos.setBounds(60, 593, 420, 25);
        chkTerminos.setBackground(COLOR_BLANCO);
        panelDer.add(chkTerminos);

        JButton btnRegistrar = new JButton("Crear cuenta");
        btnRegistrar.setBounds(60, 633, 480, 38);
        btnRegistrar.setBackground(COLOR_MENU);
        btnRegistrar.setForeground(COLOR_BLANCO);
        btnRegistrar.setFocusPainted(false);
        panelDer.add(btnRegistrar);

        // Enlace a login (por si ya tiene cuenta)
        JLabel lblLogin = new JLabel(
            "<html><u>¿Ya tienes cuenta? Inicia sesión</u></html>");
        lblLogin.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblLogin.setForeground(COLOR_SEC);
        lblLogin.setBounds(60, 680, 300, 20);
        lblLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelDer.add(lblLogin);

        add(panelDer, BorderLayout.CENTER);

        // Acciones
        btnRegistrar.addActionListener(e -> registrarUsuario(chkTerminos));
        lblLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                mostrarLoginRapido();
            }
        });
    }

    // Helpers de UI
    private JLabel label(String texto, int x, int y) {
        JLabel l = new JLabel(texto);
        l.setBounds(x, y, 280, 18);
        return l;
    }

    private JTextField field(int x, int y, JPanel parent) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, 480, 32);
        parent.add(tf);
        return tf;
    }

    // ---- Registro ----
    private void registrarUsuario(JCheckBox chkTerminos) {
        String nombre   = tfNombre.getText().trim();
        String apellido = tfApellido.getText().trim();
        String correo   = tfCorreo.getText().trim();
        String telefono = tfTelefono.getText().trim();
        String pass     = new String(pfPass.getPassword());
        String pass2    = new String(pfPass2.getPassword());
        String rol      = btnEmpresa.isSelected() ? "reclutador" : "trabajador";

        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa los campos obligatorios."); return;
        }
        if (!pass.equals(pass2)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden."); return;
        }
        if (!chkTerminos.isSelected()) {
            JOptionPane.showMessageDialog(this, "Debes aceptar los términos."); return;
        }

        String sql = "INSERT INTO usuarios (id, nombre, apellido, email, password_hash, telefono, rol) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.getConexion()) {

            String nuevoId = UUID.randomUUID().toString();
            String passwordHash = BCrypt.hashpw(
                    pass,
                    BCrypt.gensalt()
            );
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, nuevoId);
                ps.setString(2, nombre);
                ps.setString(3, apellido);
                ps.setString(4, correo);
                ps.setString(5, passwordHash);     // ⚠ En producción usa hash (BCrypt)
                ps.setString(6, telefono.isEmpty() ? null : telefono);
                ps.setString(7, rol);
                ps.executeUpdate();
            }

            // Si se registró como "Empresa", crear automáticamente su fila en
            // la tabla empresas, vinculada a este usuario (usuario_id).
            // Así Gestion_Empresa siempre tiene una empresa que cargar/editar.
            if ("reclutador".equals(rol)) {
                String sqlEmpresa = "INSERT INTO empresas (id, usuario_id, nombre_empresa, correo_contacto, telefono) "
                                   + "VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement psEmp = con.prepareStatement(sqlEmpresa)) {
                    psEmp.setString(1, UUID.randomUUID().toString());
                    psEmp.setString(2, nuevoId);
                    psEmp.setString(3, nombre + " " + apellido); // nombre provisional, editable luego
                    psEmp.setString(4, correo);
                    psEmp.setString(5, telefono.isEmpty() ? null : telefono);
                    psEmp.executeUpdate();
                }
            }

            JOptionPane.showMessageDialog(this, "¡Cuenta creada exitosamente!");
            app.iniciarSesion(nuevoId, rol);

        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this, "Ese correo ya está registrado.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // ---- Login rápido (diálogo simple) ----
    private void mostrarLoginRapido() {
        JTextField tfEmail = new JTextField(20);
        JPasswordField pfContrasena = new JPasswordField(20);

        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.add(new JLabel("Correo electrónico:"));
        panel.add(tfEmail);
        panel.add(new JLabel("Contraseña:"));
        panel.add(pfContrasena);

        int ok = JOptionPane.showConfirmDialog(
            this, panel, "Iniciar sesión",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (ok != JOptionPane.OK_OPTION) return;

        String email = tfEmail.getText().trim();
        String pass  = new String(pfContrasena.getPassword());

        if (email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa correo y contraseña."); return;
        }

        String sql = "SELECT id, rol FROM usuarios WHERE email = ? AND password_hash = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                app.iniciarSesion(rs.getString("id"), rs.getString("rol"));
            } else {
                JOptionPane.showMessageDialog(this, "Correo o contraseña incorrectos.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
