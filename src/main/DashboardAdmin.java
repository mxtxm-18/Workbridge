package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class DashboardAdmin extends JPanel {

    private final Color COLOR_MENU       = Color.decode("#243A69");
    private final Color COLOR_SECUNDARIO = Color.decode("#5B88A5");
    private final Color COLOR_ACENTO     = Color.decode("#9B73A6");

    private final WorkBridgeApp app;

    public DashboardAdmin(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        construirUI();
    }

    private void construirUI() {
        JPanel sidebar = new SidebarAdmin(app, "dashboardAdmin");
        add(sidebar, BorderLayout.WEST);

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(Color.WHITE);

        JPanel norte = new JPanel(new BorderLayout());
        norte.setBackground(Color.WHITE);
        norte.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel("Dashboard Administrativo");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JButton btnSalir = new JButton("Cerrar sesión");
        btnSalir.addActionListener(e -> app.cerrarSesion());

        norte.add(titulo, BorderLayout.WEST);
        norte.add(btnSalir, BorderLayout.EAST);

        contenido.add(norte, BorderLayout.NORTH);

        JPanel panelTarjetas = new JPanel(new GridLayout(2, 3, 20, 20));
        panelTarjetas.setBackground(Color.WHITE);
        panelTarjetas.setBorder(new EmptyBorder(20, 40, 40, 40));

        try (Connection con = ConexionDB.getConexion()) {
            panelTarjetas.add(crearTarjeta(
                    "Usuarios totales",
                    contar(con, "SELECT COUNT(*) FROM usuarios"),
                    COLOR_SECUNDARIO));

            panelTarjetas.add(crearTarjeta(
                    "Trabajadores",
                    contar(con, "SELECT COUNT(*) FROM usuarios WHERE rol='trabajador'"),
                    Color.decode("#4A90D9")));

            panelTarjetas.add(crearTarjeta(
                    "Empresas registradas",
                    contar(con, "SELECT COUNT(*) FROM empresas"),
                    COLOR_MENU));

            panelTarjetas.add(crearTarjeta(
                    "Empresas pendientes",
                    contar(con, "SELECT COUNT(*) FROM empresas WHERE estado_verificacion='pendiente'"),
                    new Color(200, 130, 30)));

            panelTarjetas.add(crearTarjeta(
                    "Vacantes activas",
                    contar(con, "SELECT COUNT(*) FROM vacantes WHERE estado='activa'"),
                    new Color(34, 139, 34)));

            panelTarjetas.add(crearTarjeta(
                    "Postulaciones totales",
                    contar(con, "SELECT COUNT(*) FROM postulaciones"),
                    COLOR_ACENTO));

        } catch (SQLException ex) {
            JLabel err = new JLabel("Error al conectar con la base de datos: " + ex.getMessage());
            err.setForeground(Color.RED);
            panelTarjetas.add(err);
        }

        contenido.add(panelTarjetas, BorderLayout.CENTER);
        add(contenido, BorderLayout.CENTER);
    }

    private String contar(Connection con, String sql) {
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return String.valueOf(rs.getInt(1));
        } catch (SQLException ex) {
            return "?";
        }
        return "0";
    }

    private JPanel crearTarjeta(String titulo, String valor, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(color);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblValor.setForeground(Color.WHITE);

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(lblValor, BorderLayout.CENTER);
        return panel;
    }
}