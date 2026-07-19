package View.Trabajador;

import java.awt.*;
import javax.swing.*;

public class Vacantes extends JPanel {

    //=========================
    // COLORES
    //=========================

    private final Color AZUL = new Color(36,58,105);
    private final Color MORADO = new Color(155,115,166);
    private final Color CREMA = new Color(245,245,245);
    private final Color BORDE = new Color(228,230,235);

    //=========================
    // PANEL DE TARJETAS
    //=========================

    private JPanel panelVacantes;

    //=========================
    // CONSTRUCTOR
    //=========================

    public Vacantes() {

        setLayout(new BorderLayout());
        setBackground(CREMA);

        construirInterfaz();

    }

    //=========================
    // INTERFAZ
    //=========================

    private void construirInterfaz() {

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(CREMA);

        principal.add(crearHeader(), BorderLayout.NORTH);

        panelVacantes = new JPanel();
        panelVacantes.setOpaque(false);
        panelVacantes.setLayout(new BoxLayout(panelVacantes, BoxLayout.Y_AXIS));
        panelVacantes.setBorder(BorderFactory.createEmptyBorder(20,25,20,25));

        JScrollPane scroll = new JScrollPane(panelVacantes);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(CREMA);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        principal.add(scroll, BorderLayout.CENTER);

     principal.add(scroll, BorderLayout.CENTER);

cargarVacantes();

add(principal);
    }

    //=========================
    // HEADER
    //=========================

    private JPanel crearHeader() {

        JPanel header = new JPanel();
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0,0,1,0,BORDE));
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        //---------------------------------------------------------
        // TITULO
        //---------------------------------------------------------

        JLabel titulo = new JLabel("Mis Vacantes");
        titulo.setFont(new Font("Segoe UI",Font.BOLD,28));
        titulo.setForeground(AZUL);

        JLabel subtitulo = new JLabel("Buscar vacantes");
        subtitulo.setFont(new Font("Segoe UI",Font.PLAIN,14));
        subtitulo.setForeground(Color.GRAY);

        JPanel arriba = new JPanel(new BorderLayout());
        arriba.setOpaque(false);
        arriba.setBorder(BorderFactory.createEmptyBorder(20,25,10,25));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos,BoxLayout.Y_AXIS));

        textos.add(titulo);
        textos.add(Box.createVerticalStrut(4));
        textos.add(subtitulo);

        arriba.add(textos,BorderLayout.WEST);

        header.add(arriba);

        //---------------------------------------------------------
        // BUSCADOR
        //---------------------------------------------------------

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT,12,12));
        filtros.setOpaque(false);
        filtros.setBorder(BorderFactory.createEmptyBorder(0,20,10,20));

        JTextField txtBuscar = new JTextField(" Buscar vacantes, cargos o empresas");
        txtBuscar.setPreferredSize(new Dimension(320,36));

        JComboBox<String> ubicacion = new JComboBox<>(
                new String[]{
                        "Ubicación",
                        "Guatemala",
                        "Remoto",
                        "Ciudad de Guatemala"
                });

        ubicacion.setPreferredSize(new Dimension(160,36));

        JComboBox<String> modalidad = new JComboBox<>(
                new String[]{
                        "Modalidad",
                        "Remoto",
                        "Híbrido",
                        "Presencial"
                });

        modalidad.setPreferredSize(new Dimension(140,36));

        JButton buscar = new JButton("Buscar");
        buscar.setBackground(MORADO);
        buscar.setForeground(Color.WHITE);
        buscar.setFocusPainted(false);
        buscar.setPreferredSize(new Dimension(110,36));

        filtros.add(txtBuscar);
        filtros.add(ubicacion);
        filtros.add(modalidad);
        filtros.add(buscar);

        header.add(filtros);

        //---------------------------------------------------------
        // CONTADOR
        //---------------------------------------------------------

        JPanel barra = new JPanel(new BorderLayout());
        barra.setOpaque(false);
        barra.setBorder(BorderFactory.createEmptyBorder(0,25,15,25));

        JLabel cantidad = new JLabel("123 Vacantes encontradas");
        cantidad.setFont(new Font("Segoe UI",Font.BOLD,15));

        JButton recientes = new JButton("Más recientes");
        recientes.setFocusPainted(false);

        barra.add(cantidad,BorderLayout.WEST);
        barra.add(recientes,BorderLayout.EAST);

        header.add(barra);

        return header;

    }
    //=====================================================
// TARJETA DE VACANTE
//=====================================================
private JPanel crearTarjetaVacante(
        String iniciales,
        Color colorIcono,
        String puesto,
        String empresa,
        String ubicacion,
        String modalidad,
        String tipo,
        String salario,
        String publicada) {

    JPanel card = new JPanel(new BorderLayout());
    card.setBackground(Color.WHITE);
    card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(228,230,235),1,true),
            BorderFactory.createEmptyBorder(18,18,18,18)
    ));
    card.setMaximumSize(new Dimension(Integer.MAX_VALUE,125));

    //--------------------------------------------------
    // IZQUIERDA
    //--------------------------------------------------

    JPanel izquierda = new JPanel(new BorderLayout(15,0));
    izquierda.setOpaque(false);

    JPanel icono = new JPanel(new GridBagLayout());
    icono.setPreferredSize(new Dimension(52,52));
    icono.setBackground(colorIcono);

    JLabel lblIniciales = new JLabel(iniciales);
    lblIniciales.setForeground(Color.WHITE);
    lblIniciales.setFont(new Font("Segoe UI",Font.BOLD,18));

    icono.add(lblIniciales);

    izquierda.add(icono,BorderLayout.WEST);

    //--------------------------------------------------
    // INFORMACIÓN
    //--------------------------------------------------

    JPanel info = new JPanel();
    info.setOpaque(false);
    info.setLayout(new BoxLayout(info,BoxLayout.Y_AXIS));

    JLabel lblPuesto = new JLabel(puesto);
    lblPuesto.setFont(new Font("Segoe UI",Font.BOLD,17));
    lblPuesto.setForeground(new Color(30,30,30));

    JLabel lblEmpresa = new JLabel(empresa);
    lblEmpresa.setFont(new Font("Segoe UI",Font.PLAIN,14));
    lblEmpresa.setForeground(new Color(90,90,90));

    JPanel datos = new JPanel(new FlowLayout(FlowLayout.LEFT,8,0));
    datos.setOpaque(false);

    JLabel l1 = new JLabel("📍 "+ubicacion);
    l1.setFont(new Font("Segoe UI",Font.PLAIN,13));
    l1.setForeground(Color.GRAY);

    JLabel l2 = new JLabel("•");

    JLabel l3 = new JLabel(modalidad);
    l3.setForeground(new Color(80,80,80));
    l3.setFont(new Font("Segoe UI",Font.PLAIN,13));

    datos.add(l1);
    datos.add(l2);
    datos.add(l3);

    JPanel etiquetas = new JPanel(new FlowLayout(FlowLayout.LEFT,6,0));
    etiquetas.setOpaque(false);

    etiquetas.add(crearEtiqueta(modalidad,new Color(232,244,255),new Color(36,58,105)));
    etiquetas.add(crearEtiqueta(tipo,new Color(238,232,250),new Color(128,74,167)));

    info.add(lblPuesto);
    info.add(Box.createVerticalStrut(3));
    info.add(lblEmpresa);
    info.add(Box.createVerticalStrut(6));
    info.add(datos);
    info.add(Box.createVerticalStrut(8));
    info.add(etiquetas);

    izquierda.add(info,BorderLayout.CENTER);

    //--------------------------------------------------
    // DERECHA
    //--------------------------------------------------

    JPanel derecha = new JPanel();
    derecha.setOpaque(false);
    derecha.setLayout(new BoxLayout(derecha,BoxLayout.Y_AXIS));

    JLabel lblSalario = new JLabel(salario);
    lblSalario.setFont(new Font("Segoe UI",Font.BOLD,15));
    lblSalario.setForeground(new Color(36,58,105));
    lblSalario.setAlignmentX(Component.RIGHT_ALIGNMENT);

    JLabel lblFecha = new JLabel(publicada);
    lblFecha.setFont(new Font("Segoe UI",Font.PLAIN,12));
    lblFecha.setForeground(Color.GRAY);
    lblFecha.setAlignmentX(Component.RIGHT_ALIGNMENT);

    JButton guardar = new JButton("☆");
    guardar.setFocusPainted(false);
    guardar.setContentAreaFilled(false);
    guardar.setBorderPainted(false);
    guardar.setFont(new Font("Segoe UI Emoji",Font.PLAIN,20));
    guardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    guardar.setAlignmentX(Component.RIGHT_ALIGNMENT);

    derecha.add(lblSalario);
    derecha.add(Box.createVerticalStrut(8));
    derecha.add(lblFecha);
    derecha.add(Box.createVerticalGlue());
    derecha.add(guardar);

    //--------------------------------------------------

    card.add(izquierda,BorderLayout.CENTER);
    card.add(derecha,BorderLayout.EAST);

    return card;
}
//=====================================================
// CARGAR VACANTES
//=====================================================
private void cargarVacantes() {

    panelVacantes.removeAll();

    panelVacantes.add(crearTarjetaVacante(
            "GO",
            new Color(66,133,244),
            "Desarrollador Java Junior",
            "Google",
            "Ciudad de Guatemala",
            "Remoto",
            "Tiempo completo",
            "Q 8,500 - Q 11,000",
            "Hace 2 días"));

    panelVacantes.add(Box.createVerticalStrut(15));

    panelVacantes.add(crearTarjetaVacante(
            "MS",
            new Color(0,120,215),
            "Backend Developer",
            "Microsoft",
            "Guatemala",
            "Híbrido",
            "Tiempo completo",
            "Q 12,000 - Q 15,000",
            "Hace 1 día"));

    panelVacantes.add(Box.createVerticalStrut(15));

    panelVacantes.add(crearTarjetaVacante(
            "AM",
            new Color(255,153,0),
            "Full Stack Developer",
            "Amazon",
            "Remoto",
            "Remoto",
            "Tiempo completo",
            "Q 14,000 - Q 18,000",
            "Hace 3 días"));

    panelVacantes.add(Box.createVerticalStrut(15));

    panelVacantes.add(crearTarjetaVacante(
            "ME",
            new Color(66,103,178),
            "Frontend React",
            "Meta",
            "Ciudad de Guatemala",
            "Híbrido",
            "Tiempo completo",
            "Q 11,000 - Q 14,000",
            "Hace 5 horas"));

    panelVacantes.add(Box.createVerticalStrut(15));

    panelVacantes.add(crearTarjetaVacante(
            "AP",
            new Color(30,30,30),
            "Ingeniero de Software",
            "Apple",
            "Remoto",
            "Remoto",
            "Tiempo completo",
            "Q 15,000 - Q 20,000",
            "Hace 6 horas"));

    panelVacantes.add(Box.createVerticalStrut(15));

    panelVacantes.add(crearTarjetaVacante(
            "NV",
            new Color(118,185,0),
            "Desarrollador IA",
            "NVIDIA",
            "Guatemala",
            "Presencial",
            "Tiempo completo",
            "Q 18,000 - Q 24,000",
            "Hoy"));

    panelVacantes.add(Box.createVerticalStrut(15));

    panelVacantes.add(crearTarjetaVacante(
            "OR",
            new Color(244,81,30),
            "Java Spring Boot",
            "Oracle",
            "Ciudad de Guatemala",
            "Híbrido",
            "Tiempo completo",
            "Q 10,000 - Q 13,500",
            "Hoy"));

    panelVacantes.add(Box.createVerticalStrut(15));

    panelVacantes.add(crearTarjetaVacante(
            "IB",
            new Color(5,97,181),
            "Analista de Sistemas",
            "IBM",
            "Guatemala",
            "Presencial",
            "Tiempo completo",
            "Q 9,000 - Q 12,000",
            "Hace 4 días"));

    panelVacantes.add(Box.createVerticalGlue());

    revalidate();
    repaint();

}

private JLabel crearEtiqueta(String texto, Color fondo, Color colorTexto) {

    JLabel etiqueta = new JLabel(texto);
    etiqueta.setOpaque(true);
    etiqueta.setBackground(fondo);
    etiqueta.setForeground(colorTexto);
    etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 12));
    etiqueta.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));

    return etiqueta;
}
}