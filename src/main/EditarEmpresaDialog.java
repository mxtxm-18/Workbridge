package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class EditarEmpresaDialog extends JDialog {

    private final String empresaId;
    private final Gestion_Empresa panelOrigen;

    private JTextField txtNombre, txtNit, txtSector, txtSitioWeb;
    private JTextField txtCorreo, txtTelefono, txtDireccion;
    private JTextField txtLinkedin, txtFacebook, txtInstagram;
    private JTextArea txtDescripcion;

    public EditarEmpresaDialog(Frame owner, String empresaId, Gestion_Empresa panelOrigen) {
        super(owner, "Editar información de la empresa", true);
        this.empresaId = empresaId;
        this.panelOrigen = panelOrigen;

        setSize(560, 640);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        add(crearFormulario(), BorderLayout.CENTER);
        add(crearBotones(), BorderLayout.SOUTH);

        cargarValoresActuales();
    }

    private JPanel crearFormulario() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 24, 10, 24));

        txtNombre     = agregarCampo(panel, "Nombre de la empresa");
        txtNit        = agregarCampo(panel, "NIT / Registro legal");
        txtSector     = agregarCampo(panel, "Sector");
        txtSitioWeb   = agregarCampo(panel, "Sitio web");
        txtCorreo     = agregarCampo(panel, "Correo de contacto");
        txtTelefono   = agregarCampo(panel, "Teléfono");
        txtDireccion  = agregarCampo(panel, "Dirección");
        txtLinkedin   = agregarCampo(panel, "LinkedIn");
        txtFacebook   = agregarCampo(panel, "Facebook");
        txtInstagram  = agregarCampo(panel, "Instagram");

        JLabel lblDesc = new JLabel("Sobre la empresa");
        lblDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(Box.createVerticalStrut(8));
        panel.add(lblDesc);

        txtDescripcion = new JTextArea(5, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        scrollDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(Box.createVerticalStrut(4));
        panel.add(scrollDesc);

        JScrollPane contenedor = new JScrollPane(panel);
        contenedor.setBorder(null);
        contenedor.getVerticalScrollBar().setUnitIncrement(16);

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.add(contenedor, BorderLayout.CENTER);
        return wrap;
    }

    private JTextField agregarCampo(JPanel panel, String etiqueta) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField campo = new JTextField();
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));

        panel.add(lbl);
        panel.add(Box.createVerticalStrut(2));
        panel.add(campo);
        panel.add(Box.createVerticalStrut(10));
        return campo;
    }

    private JPanel crearBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        JButton btnGuardar = new JButton("Guardar cambios");
        btnGuardar.addActionListener(e -> guardarCambios());

        panel.add(btnCancelar);
        panel.add(btnGuardar);
        return panel;
    }

    private void cargarValoresActuales() {
        String sql = "SELECT nombre_empresa, nit, sector, sitio_web, correo_contacto, " +
                     "telefono, direccion, linkedin_url, facebook_url, instagram_url, descripcion " +
                     "FROM empresas WHERE id = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, empresaId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    txtNombre.setText(rs.getString("nombre_empresa"));
                    txtNit.setText(rs.getString("nit"));
                    txtSector.setText(rs.getString("sector"));
                    txtSitioWeb.setText(rs.getString("sitio_web"));
                    txtCorreo.setText(rs.getString("correo_contacto"));
                    txtTelefono.setText(rs.getString("telefono"));
                    txtDireccion.setText(rs.getString("direccion"));
                    txtLinkedin.setText(rs.getString("linkedin_url"));
                    txtFacebook.setText(rs.getString("facebook_url"));
                    txtInstagram.setText(rs.getString("instagram_url"));
                    txtDescripcion.setText(rs.getString("descripcion"));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar los datos: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarCambios() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de la empresa es obligatorio.");
            return;
        }

        String sql = "UPDATE empresas SET nombre_empresa=?, nit=?, sector=?, sitio_web=?, " +
                     "correo_contacto=?, telefono=?, direccion=?, linkedin_url=?, facebook_url=?, " +
                     "instagram_url=?, descripcion=? WHERE id=?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, txtNombre.getText().trim());
            ps.setString(2, txtNit.getText().trim());
            ps.setString(3, txtSector.getText().trim());
            ps.setString(4, txtSitioWeb.getText().trim());
            ps.setString(5, txtCorreo.getText().trim());
            ps.setString(6, txtTelefono.getText().trim());
            ps.setString(7, txtDireccion.getText().trim());
            ps.setString(8, txtLinkedin.getText().trim());
            ps.setString(9, txtFacebook.getText().trim());
            ps.setString(10, txtInstagram.getText().trim());
            ps.setString(11, txtDescripcion.getText().trim());
            ps.setString(12, empresaId);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(this, "Datos actualizados correctamente.");
                panelOrigen.cargarEmpresaPorId(empresaId);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la empresa a actualizar.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Error al guardar los cambios: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}