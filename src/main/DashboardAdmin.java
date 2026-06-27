package main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class DashboardAdmin extends JPanel {

    private final Color HEADER = new Color(219, 214, 207);
    private final Color CARD1  = new Color(181, 208, 230);
    private final Color CARD2  = new Color(193, 199, 225);
    private final Color CARD3  = new Color(221, 216, 210);

    private final WorkBridgeApp app;

    public DashboardAdmin(WorkBridgeApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Sidebar
        add(new SidebarAdmin(app, "dashboardAdmin"), BorderLayout.WEST);

        // Panel derecho (todo el contenido original)
        JPanel derecho = new JPanel(new BorderLayout());
        derecho.setBackground(Color.WHITE);

        // Barra superior
        derecho.add(crearTopBar(), BorderLayout.NORTH);

        // Contenido principal
        derecho.add(crearContenido(), BorderLayout.CENTER);

        add(derecho, BorderLayout.CENTER);
    }

    // ─── Top bar ────────────────────────────────────────────────────────────

    private JPanel crearTopBar() {
        JPanel top = new JPanel(null);
        top.setPreferredSize(new Dimension(0, 45));
        top.setBackground(HEADER);

        JLabel titulo = new JLabel("Dashboard administrativo");
        titulo.setBounds(10, 4, 300, 20);
        titulo.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
        top.add(titulo);

        JLabel fecha = new JLabel("Domingo, 7 de junio de 2026");
        fecha.setBounds(10, 22, 250, 15);
        fecha.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        top.add(fecha);

        JLabel usuario = new JLabel("Administrador");
        usuario.setBounds(880, 10, 180, 20);
        usuario.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
        top.add(usuario);

        return top;
    }

    // ─── Contenido principal ─────────────────────────────────────────────────

    private JPanel crearContenido() {
        JPanel contenido = new JPanel(null);
        contenido.setBackground(Color.WHITE);

        // Tarjetas superiores
        contenido.add(crearTarjeta("Resumen de Usuario", "127", "Usuarios",         CARD1, 100, 70));
        contenido.add(crearTarjeta("Ofertas De Empleo",  "36",  "Activas",          CARD2, 420, 70));
        contenido.add(crearTarjeta("Comunicaciones",     "25",  "Enviadas",         CARD3, 740, 70));

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

    private JPanel crearTarjeta(String titulo, String valor, String subtitulo,
                                 Color color, int x, int y) {
        JPanel panel = new JPanel(null);
        panel.setBounds(x, y, 240, 130);
        panel.setBackground(color);
        panel.setBorder(new LineBorder(Color.GRAY));

        JLabel t = new JLabel(titulo);
        t.setBounds(10, 10, 200, 20);
        t.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
        panel.add(t);

        JLabel v = new JLabel(valor);
        v.setBounds(10, 30, 140, 60);
        v.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 52));
        panel.add(v);

        JLabel s = new JLabel(subtitulo);
        s.setBounds(12, 100, 100, 20);
        s.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(s);

        return panel;
    }
}