package main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class DashboardAdmin extends JPanel {

    private final Color BEIGE      = new Color(219, 213, 205);
    private final Color CARD1      = new Color(181, 208, 230);
    private final Color CARD2      = new Color(193, 199, 225);
    private final Color CARD3      = new Color(221, 216, 210);
    private final Color COLOR_MENU = new Color(0x24, 0x3A, 0x69);

    // Ancho del sidebar (debe coincidir con el usado por SidebarAdmin en el resto de las interfaces)
    private final int SIDEBAR_WIDTH = 220;
    // Resolución objetivo
    private final int SCREEN_WIDTH  = 1920;
    private final int SCREEN_HEIGHT = 1080;
    private final int CONTENT_WIDTH = SCREEN_WIDTH - SIDEBAR_WIDTH; // 1690

    private final WorkBridgeApp app;

    // Referencias a las tarjetas para poder actualizar sus valores tras cargar la BD
    private JLabel valorUsuarios;
    private JLabel valorOfertas;
    private JLabel valorComunicaciones;

    // Referencias a las gráficas para poder actualizarlas tras cargar la BD
    private GraficaBarras grafica1; // Usuarios
    private GraficaBarras grafica2; // Ofertas de Empleo

    public DashboardAdmin(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        // ── Diseño común: Sidebar (el mismo componente que usan las demás pantallas,
        //    por eso los botones de la izquierda quedan unificados en toda la app) ──
        add(new SidebarAdmin(app, "dashboardAdmin"), BorderLayout.WEST);

        JPanel derecho = new JPanel(new BorderLayout());
        derecho.setBackground(Color.WHITE);
        derecho.add(crearTopBar(), BorderLayout.NORTH);

        // El contenido puede necesitar scroll si la ventana se hace más chica que 1920x1080
        JScrollPane scroll = new JScrollPane(crearContenido());
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        derecho.add(scroll, BorderLayout.CENTER);

        add(derecho, BorderLayout.CENTER);

        cargarDatosDashboard();
    }

    // ─── Barra superior beige (misma apariencia que el resto de la interfaz) ─

    private JPanel crearTopBar() {
        JPanel top = new JPanel(null);
        top.setPreferredSize(new Dimension(CONTENT_WIDTH, 90));
        top.setBackground(BEIGE);

        JLabel titulo = new JLabel("Dashboard Administrativo");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setBounds(30, 12, 500, 36);
        top.add(titulo);

        JLabel fecha = new JLabel("Domingo, 7 de junio de 2026");
        fecha.setBounds(30, 52, 320, 22);
        fecha.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        top.add(fecha);

        JLabel campana = new JLabel("🔔");
        campana.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        campana.setBounds(CONTENT_WIDTH - 260, 28, 36, 36);
        campana.setCursor(new Cursor(Cursor.HAND_CURSOR));
        campana.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                if (app != null) app.irANotificaciones();
            }
        });
        top.add(campana);

        JLabel perfil = new JLabel("👤 Administrador");
        perfil.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        perfil.setBounds(CONTENT_WIDTH - 210, 30, 180, 26);
        perfil.setCursor(new Cursor(Cursor.HAND_CURSOR));
        perfil.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                if (app != null) app.irAPerfil();
            }
        });
        top.add(perfil);

        return top;
    }

    // ─── Contenido principal ─────────────────────────────────────────────────

    private JPanel crearContenido() {
        JPanel contenido = new JPanel(null);
        contenido.setBackground(Color.WHITE);
        contenido.setPreferredSize(new Dimension(CONTENT_WIDTH, SCREEN_HEIGHT - 90));

        int cardW = 340, cardH = 160;
        int y1 = 40;
        int x1 = 60;
        int x2 = (CONTENT_WIDTH - cardW) / 2;
        int x3 = CONTENT_WIDTH - cardW - 60;

        JPanel tarjetaUsuarios = crearTarjeta("Resumen de Usuario", "...", "Usuarios", CARD1, x1, y1, cardW, cardH);
        valorUsuarios = (JLabel) tarjetaUsuarios.getClientProperty("valor");
        contenido.add(tarjetaUsuarios);

        JPanel tarjetaOfertas = crearTarjeta("Ofertas De Empleo", "...", "Activas", CARD2, x2, y1, cardW, cardH);
        valorOfertas = (JLabel) tarjetaOfertas.getClientProperty("valor");
        contenido.add(tarjetaOfertas);

        JPanel tarjetaComunicaciones = crearTarjeta("Comunicaciones", "...", "Enviadas", CARD3, x3, y1, cardW, cardH);
        valorComunicaciones = (JLabel) tarjetaComunicaciones.getClientProperty("valor");
        contenido.add(tarjetaComunicaciones);

        // Botón de recarga manual (útil si otro admin agrega datos mientras la ventana está abierta)
        JButton btnActualizar = new JButton("Actualizar Datos");
        btnActualizar.setBounds(x1, y1 + cardH + 25, 200, 40);
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnActualizar.setBackground(COLOR_MENU);
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setBorderPainted(false);
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(e -> cargarDatosDashboard());
        contenido.add(btnActualizar);

        // Graficas ocupan el ancho disponible dividido en dos, con mas alto para aprovechar 1080px
        int graficaY = y1 + cardH + 90;
        int graficaH = 480;
        int graficaW = (CONTENT_WIDTH - 60 - 60 - 30) / 2; // margen izq/der + separación entre las dos

        grafica1 = new GraficaBarras("Usuarios por Rol");
        grafica1.setBounds(x1, graficaY, graficaW, graficaH);
        grafica1.setBorder(new LineBorder(Color.GRAY));
        grafica1.setColorBarra(new Color(70, 130, 180));
        contenido.add(grafica1);

        grafica2 = new GraficaBarras("Ofertas de Empleo por Estado");
        grafica2.setBounds(x1 + graficaW + 30, graficaY, graficaW, graficaH);
        grafica2.setBorder(new LineBorder(Color.GRAY));
        grafica2.setColorBarra(new Color(0x24, 0x3A, 0x69));
        contenido.add(grafica2);

        return contenido;
    }

    // ─── Tarjeta ─────────────────────────────────────────────────────────────

    private JPanel crearTarjeta(String titulo, String valorInicial, String subtitulo,
                                 Color color, int x, int y, int w, int h) {
        JPanel panel = new JPanel(null);
        panel.setBounds(x, y, w, h);
        panel.setBackground(color);
        panel.setBorder(new LineBorder(Color.GRAY));

        JLabel t = new JLabel(titulo);
        t.setBounds(15, 12, w - 30, 24);
        t.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panel.add(t);

        JLabel v = new JLabel(valorInicial);
        v.setBounds(15, 40, w - 30, 70);
        v.setFont(new Font("Segoe UI", Font.BOLD, 60));
        panel.add(v);

        JLabel s = new JLabel(subtitulo);
        s.setBounds(17, h - 30, 120, 22);
        s.setFont(new Font("Segoe UI", Font.PLAIN, 15));
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
                    "SELECT COUNT(*) FROM vacantes WHERE estado = 'activa'");
            int comunicacionesEnviadas = obtenerConteo(con,
                    "SELECT COUNT(*) FROM notificaciones");

            valorUsuarios.setText(String.valueOf(totalUsuarios));
            valorOfertas.setText(String.valueOf(ofertasActivas));
            valorComunicaciones.setText(String.valueOf(comunicacionesEnviadas));

            // Gráfica de vacantes agrupadas por estado (activa / pausada / cerrada)
            Map<String, Integer> datosVacantes = obtenerConteoAgrupado(con,
                    "SELECT estado, COUNT(*) FROM vacantes GROUP BY estado");
            grafica2.setDatos(datosVacantes);

            // Gráfica de usuarios agrupados por rol
            // NOTA: ajusta "rol" al nombre real de la columna en tu tabla "usuarios"
            Map<String, Integer> datosUsuarios = obtenerConteoAgrupado(con,
                    "SELECT rol, COUNT(*) FROM usuarios GROUP BY rol");
            grafica1.setDatos(datosUsuarios);

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

    /** Ejecuta un SQL tipo "SELECT columna, COUNT(*) ... GROUP BY columna" y arma un mapa etiqueta→valor. */
    private Map<String, Integer> obtenerConteoAgrupado(Connection con, String sql) throws SQLException {
        Map<String, Integer> resultado = new LinkedHashMap<>();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String etiqueta = rs.getString(1);
                int valor = rs.getInt(2);
                resultado.put(etiqueta != null ? etiqueta : "N/A", valor);
            }
        }
        return resultado;
    }
}