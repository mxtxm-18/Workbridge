package main;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class DashboardModerador extends JPanel {

    static final Color AZULOSCURO = new Color(0x24, 0x3A, 0x69);
    static final Color CELESTE = new Color(0x5B, 0x88, 0xA5);
    static final Color NARANJACLARO = new Color(0xD4, 0xCD, 0xC5);
    static final Color LAVANDA = new Color(0x9B, 0x73, 0xA6);
    static final Color SECAZUL = new Color(0x20, 0x33, 0x5B);
    static final Color SECCELESTE = new Color(0x53, 0x7B, 0x94);
    static final Color SECLAVANDA = new Color(0x84, 0x64, 0x8D);
    static final Color MUSTAR = new Color(0xFD, 0xBD, 0x2D);
    static final Color VERDEMENTA = new Color(0x5C, 0xE1, 0xE6);
    static final Color TURQUOISE = new Color(0x00, 0xA4, 0xBD);
    static final Color FONDOPAGINA = new Color(0xF0, 0xEE, 0xE9);
    static final Color FONDOCARD = Color.WHITE;
    static final Color BORDE = new Color(0xE0, 0xDB, 0xD4);
    static final Color TEXTOOSCURO = new Color(0x1A, 0x20, 0x35);
    static final Color TEXTOMEDIO = new Color(0x3D, 0x4A, 0x5F);
    static final Color TEXTOSUAVE = new Color(0x7A, 0x86, 0x99);
    static final Color EXITO = new Color(0x2E, 0x9E, 0x6E);
    static final Color ALERTA = new Color(0xD4, 0x92, 0x0A);
    static final Color PELIGRO = new Color(0xC0, 0x39, 0x2B);

    static final Font FONTTITULO = new Font("SansSerif", Font.BOLD, 14);
    static final Font FONTSUBTIT = new Font("SansSerif", Font.BOLD, 12);
    static final Font FONTBODY = new Font("SansSerif", Font.PLAIN, 11);
    static final Font FONTSMALL = new Font("SansSerif", Font.PLAIN, 9);
    static final Font FONTKPI = new Font("SansSerif", Font.BOLD, 26);
    static final Font FONTLABEL = new Font("SansSerif", Font.BOLD, 9);
    static final Font FONTNAV = new Font("SansSerif", Font.PLAIN, 12);
    static final Font FONTNAVACT = new Font("SansSerif", Font.BOLD, 12);
    static final Font FONTBTN = new Font("SansSerif", Font.BOLD, 10);
    static final Font FONTBRAND = new Font("SansSerif", Font.BOLD, 16);

    private final WorkBridgeApp app;
    private JScrollPane scrollContenido;
    private JPanel panelReportes;
    private JPanel panelGrafico;
    private DefaultTableModel modeloUsuarios;
    private DefaultTableModel modeloVacantes;

    public DashboardModerador(WorkBridgeApp app) {
        this.app = app;
        setBackground(FONDOPAGINA);
        setLayout(new BorderLayout());
        add(new SidebarAdmin(app, "dashboardModerador"), BorderLayout.WEST);
        add(buildMain(), BorderLayout.CENTER);
    }

    private void navegarA(String destino) {
        if (destino == null || destino.isEmpty() || destino.equals("dashboardModerador")) return;
        if (app != null) app.mostrarPantalla(destino);
    }

    private void ejecutarBusqueda(String texto) {
        if (texto == null || texto.isBlank() || texto.equals("Buscar...")) return;
        JOptionPane.showMessageDialog(this, "Búsqueda ejecutada: " + texto);
    }

    private JPanel buildMain() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(FONDOPAGINA);
        main.add(buildTopbar(), BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setBackground(FONDOPAGINA);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));

        content.add(buildKpiRow());
        content.add(Box.createVerticalStrut(14));
        content.add(buildFila1());
        content.add(Box.createVerticalStrut(14));
        content.add(buildFila2());
        content.add(Box.createVerticalStrut(14));
        content.add(buildFila3());

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(FONDOPAGINA);
        this.scrollContenido = scroll;

        main.add(scroll, BorderLayout.CENTER);
        return main;
    }

    private JPanel buildTopbar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(Color.WHITE);
        bar.setBorder(BorderFactory.createEmptyBorder(0, 22, 0, 22));
        bar.setPreferredSize(new Dimension(0, 56));
        return bar;
    }

    private JPanel buildKpiRow() { return new JPanel(); }
    private JPanel buildFila1() { return new JPanel(); }
    private JPanel buildFila2() { return new JPanel(); }
    private JPanel buildFila3() { return new JPanel(); }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("WorkBridge Dashboard Moderador");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1280, 800);
            frame.setLocationRelativeTo(null);
            frame.add(new DashboardModerador(null));
            frame.setVisible(true);
        });
    }
}