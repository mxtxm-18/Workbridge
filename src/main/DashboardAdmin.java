package main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.*;

public class DashboardAdmin extends JPanel {

    private final Color BEIGE     = new Color(219, 213, 205);
    private final Color CARD1     = new Color(181, 208, 230);
    private final Color CARD2     = new Color(193, 199, 225);
    private final Color CARD3     = new Color(221, 216, 210);
    private final Color COLOR_MENU = new Color(0x24, 0x3A, 0x69);

    private final WorkBridgeApp app;

    // Referencias a las tarjetas para poder actualizar sus valores tras cargar la BD
    private JLabel valorUsuarios;
    private JLabel valorOfertas;
    private JLabel valorComunicaciones;

    public DashboardAdmin(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ── Diseño común: Sidebar ────────────────────────────────────────────
        add(new SidebarAdmin(app, "dashboardAdmin"), BorderLayout.WEST);

        JPanel derecho = new JPanel(new BorderLayout());
        derecho.setBackground(Color.WHITE);
        derecho.add(crearTopBar(), BorderLayout.NORTH);
        derecho.add(crearContenido(), BorderLayout.CENTER);

        add(derecho, BorderLayout.CENTER);

        cargarDatosDashboard();
    }

    // ─── Barra superior beige (misma apariencia que el resto de la interfaz) ─

    private JPanel crearTopBar() {
        JPanel top = new JPanel(null);
        top.setPreferredSize(new Dimension(0, 70));
        top.setBackground(BEIGE);

        JLabel titulo = new JLabel("Dashboard Administrativo");
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

        JLabel perfil = new JLabel("👤 Administrador");
        perfil.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        perfil.setBounds(790, 24, 150, 22);
        top.add(perfil);

        return top;
    }

    // ─── Contenido principal ─────────────────────────────────────────────────

    private JPanel crearContenido() {
        JPanel contenido = new JPanel(null);
        contenido.setBackground(Color.WHITE);

        JPanel tarjetaUsuarios = crearTarjeta("Resumen de Usuario", "...", "Usuarios", CARD1, 100, 70);
        valorUsuarios = (JLabel) tarjetaUsuarios.getClientProperty("valor");
        contenido.add(tarjetaUsuarios);

        JPanel tarjetaOfertas = crearTarjeta("Ofertas De Empleo", "...", "Activas", CARD2, 420, 70);
        valorOfertas = (JLabel) tarjetaOfertas.getClientProperty("valor");
        contenido.add(tarjetaOfertas);

        JPanel tarjetaComunicaciones = crearTarjeta("Comunicaciones", "...", "Enviadas", CARD3, 740, 70);
        valorComunicaciones = (JLabel) tarjetaComunicaciones.getClientProperty("valor");
        contenido.add(tarjetaComunicaciones);

        // Botón de recarga manual (útil si otro admin agrega datos mientras la ventana está abierta)
        JButton btnActualizar = new JButton("Actualizar Datos");
        btnActualizar.setBounds(100, 175, 160, 30);
        btnActualizar.setBackground(COLOR_MENU);
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setBorderPainted(false);
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(e -> cargarDatosDashboard());
        contenido.add(btnActualizar);

        // Gráfica usuarios
        JPanel grafica1 = new JPanel();
        grafica1.setBounds(100, 220, 290, 200);
        grafica1.setBorder(new LineBorder(Color.GRAY));
        grafica1.setBackground(Color.WHITE);
        grafica1.add(new JLabel("Usuarios"));
        contenido.add(grafica1);

        // Gráfica empleos
        JPanel grafica2 = new JPanel();
        grafica2.setBounds(470, 220, 290, 200);
        grafica2.setBorder(new LineBorder(Color.GRAY));
        grafica2.setBackground(Color.WHITE);
        grafica2.add(new JLabel("Ofertas de Empleo"));
        contenido.add(grafica2);

        return contenido;
    }

    // ─── Tarjeta ─────────────────────────────────────────────────────────────

    private JPanel crearTarjeta(String titulo, String valorInicial, String subtitulo,
                                 Color color, int x, int y) {
        JPanel panel = new JPanel(null);
        panel.setBounds(x, y, 240, 130);
        panel.setBackground(color);
        panel.setBorder(new LineBorder(Color.GRAY));

        JLabel t = new JLabel(titulo);
        t.setBounds(10, 10, 200, 20);
        t.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(t);

        JLabel v = new JLabel(valorInicial);
        v.setBounds(10, 30, 140, 60);
        v.setFont(new Font("Segoe UI", Font.BOLD, 52));
        panel.add(v);

        JLabel s = new JLabel(subtitulo);
        s.setBounds(12, 100, 100, 20);
        s.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(s);

        // Guardamos referencia al JLabel del valor para poder actualizarlo luego
        panel.putClientProperty("valor", v);

        return panel;
    }

    // ─── Carga de datos reales desde la BD ──────────────────────────────────

    private void cargarDatosDashboard() {
        try (Connection con = ConexionDB.getConexion()) {

            int totalUsuarios = obtenerConteo(con, "SELECT COUNT(*) FROM usuarios");
            int ofertasActivas = obtenerConteo(con,
                    "SELECT COUNT(*) FROM ofertas WHERE estado = 'activa'");
            int comunicacionesEnviadas = obtenerConteo(con,
                    "SELECT COUNT(*) FROM comunicaciones WHERE estado = 'enviado'");

            valorUsuarios.setText(String.valueOf(totalUsuarios));
            valorOfertas.setText(String.valueOf(ofertasActivas));
            valorComunicaciones.setText(String.valueOf(comunicacionesEnviadas));

            if (totalUsuarios == 0 && ofertasActivas == 0 && comunicacionesEnviadas == 0) {
                JOptionPane.showMessageDialog(this, "No se encontraron datos.",
                        "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            valorUsuarios.setText("--");
            valorOfertas.setText("--");
            valorComunicaciones.setText("--");
            JOptionPane.showMessageDialog(this,
                    "Error al cargar la información del dashboard: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int obtenerConteo(Connection con, String sql) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }
}