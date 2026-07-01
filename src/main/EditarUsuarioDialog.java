package main;

import javax.swing.*;
import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 * Ventana modal para editar la información de un usuario existente.
 * Se abre desde GestionUsuarios cuando el admin selecciona una fila
 * y presiona "Editar".
 *
 * Ajustado al esquema real de la tabla `usuarios`: no existe columna
 * `estado`. El campo de contraseña es opcional: si se deja vacío,
 * la contraseña actual no se modifica.
 */
public class EditarUsuarioDialog extends JDialog {

    private final Color COLOR_MENU = new Color(0x24, 0x3A, 0x69);
    private final Color MORADO     = new Color(196, 167, 206);

    private final JTextField txtNombre   = new JTextField();
    private final JTextField txtApellido = new JTextField();
    private final JTextField txtCorreo   = new JTextField();
    private final JPasswordField txtPassword = new JPasswordField();
    private final JComboBox<String> comboRol =
            new JComboBox<>(new String[]{"trabajador", "reclutador", "admin"});

    private final String idUsuario;
    private boolean guardadoExitoso = false;

    public EditarUsuarioDialog(Frame owner, String idUsuario, String nombre,
                                String apellido, String correo, String rol) {
        super(owner, "Editar Usuario", true);
        this.idUsuario = idUsuario;

        setSize(420, 470);
        setLocationRelativeTo(owner);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel titulo = new JLabel("Editar Usuario");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(MORADO.darker());
        titulo.setBounds(20, 15, 300, 30);
        add(titulo);

        int y = 60;
        y = agregarCampo("Nombre", txtNombre, y);
        y = agregarCampo("Apellido", txtApellido, y);
        y = agregarCampo("Correo", txtCorreo, y);
        y = agregarCampoPassword("Nueva contraseña (dejar vacío para no cambiar)", txtPassword, y);
        y = agregarComboField("Rol", comboRol, y);

        txtNombre.setText(nombre);
        txtApellido.setText(apellido);
        txtCorreo.setText(correo);
        comboRol.setSelectedItem(rol);

        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setBounds(60, y + 20, 160, 35);
        btnGuardar.setBackground(COLOR_MENU);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(230, y + 20, 120, 35);
        btnCancelar.setBackground(Color.LIGHT_GRAY);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(false);
        add(btnCancelar);

        btnGuardar.addActionListener(e -> guardarCambios());
        btnCancelar.addActionListener(e -> dispose());
    }

    private int agregarCampo(String etiqueta, JTextField campo, int y) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setBounds(20, y, 370, 22);
        add(lbl);

        campo.setBounds(20, y + 22, 370, 28);
        add(campo);
        return y + 58;
    }

    private int agregarCampoPassword(String etiqueta, JPasswordField campo, int y) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setBounds(20, y, 370, 18);
        add(lbl);

        campo.setBounds(20, y + 20, 370, 28);
        add(campo);
        return y + 56;
    }

    private int agregarComboField(String etiqueta, JComboBox<String> combo, int y) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setBounds(20, y, 150, 22);
        add(lbl);

        combo.setBounds(20, y + 22, 370, 28);
        add(combo);
        return y + 58;
    }

    // ─── Validación y guardado ─────────────────────────────────────────────

    private void guardarCambios() {
        String nombre   = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String correo   = txtCorreo.getText().trim();
        String nuevaPassword = new String(txtPassword.getPassword()).trim();

        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe completar todos los campos.",
                    "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!correo.contains("@") || !correo.contains(".")) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese un correo electrónico válido.",
                    "Correo inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!nuevaPassword.isEmpty() && nuevaPassword.length() < 6) {
            JOptionPane.showMessageDialog(this,
                    "La contraseña debe tener al menos 6 caracteres.",
                    "Contraseña insegura", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean cambiaPassword = !nuevaPassword.isEmpty();

        String sql = cambiaPassword
                ? "UPDATE usuarios SET nombre = ?, apellido = ?, email = ?, rol = ?, password_hash = ? WHERE id = ?"
                : "UPDATE usuarios SET nombre = ?, apellido = ?, email = ?, rol = ? WHERE id = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int idx = 1;
            ps.setString(idx++, nombre);
            ps.setString(idx++, apellido);
            ps.setString(idx++, correo);
            ps.setString(idx++, (String) comboRol.getSelectedItem());
            if (cambiaPassword) {
                ps.setString(idx++, hashPassword(nuevaPassword));
            }
            ps.setString(idx, idUsuario);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                guardadoExitoso = true;
                JOptionPane.showMessageDialog(this,
                        "Los cambios se guardaron correctamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se encontró el usuario a actualizar.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar la información: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchAlgorithmException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al procesar la contraseña: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /** Indica si el diálogo cerró tras guardar exitosamente, para refrescar la tabla. */
    public boolean isGuardadoExitoso() {
        return guardadoExitoso;
    }
}