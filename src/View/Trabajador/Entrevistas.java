package View.Trabajador;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Entrevistas extends JPanel {

    //=====================================================
    // COLORES
    //=====================================================
    private final Color FONDO = new Color(245, 245, 245);
    private final Color BLANCO = Color.WHITE;
    private final Color AZUL = new Color(36, 58, 105);
    private final Color MORADO = new Color(155, 115, 166);
    private final Color GRIS = new Color(110, 110, 110);

    //=====================================================
    // COMPONENTES
    //=====================================================
    private JPanel contenido;

    private JScrollPane scroll;

    //=====================================================
    // FUENTES
    //=====================================================
    private final Font TITULO
            = new Font(
                    "Segoe UI",
                    Font.BOLD,
                    28
            );

    private final Font SUBTITULO
            = new Font(
                    "Segoe UI",
                    Font.BOLD,
                    18
            );

    private final Font TEXTO
            = new Font(
                    "Segoe UI",
                    Font.PLAIN,
                    14
            );

    //=====================================================
    // CONSTRUCTOR
    //=====================================================
    public Entrevistas() {

        inicializar();

    }

    //=====================================================
    // INICIALIZAR
    //=====================================================
    private void inicializar() {

        setLayout(new BorderLayout());

        setBackground(FONDO);

        crearContenedor();

        construirVista();

    }

    //=====================================================
    // CONTENEDOR PRINCIPAL
    //=====================================================
    private void crearContenedor() {

        contenido = new JPanel();

        contenido.setLayout(
                new BoxLayout(
                        contenido,
                        BoxLayout.Y_AXIS
                )
        );

        contenido.setBackground(FONDO);

        contenido.setBorder(
                new EmptyBorder(
                        25,
                        35,
                        30,
                        35
                )
        );

        scroll = new JScrollPane(contenido);

        scroll.setBorder(null);

        scroll.setBackground(FONDO);

        scroll.getVerticalScrollBar()
                .setUnitIncrement(16);

        add(
                scroll,
                BorderLayout.CENTER
        );

    }

    //=====================================================
    // CONSTRUIR VISTA
    //=====================================================
    private void construirVista() {

        contenido.add(crearCabecera());
        contenido.add(Box.createVerticalStrut(20));

        contenido.add(crearProximaEntrevista());
        contenido.add(Box.createVerticalStrut(20));

        contenido.add(crearFiltros());
        contenido.add(Box.createVerticalStrut(20));

        contenido.add(crearListaEntrevistas());

    }
    //=====================================================
// CABECERA
//=====================================================

    private JPanel crearCabecera() {

        JPanel tarjeta = new JPanel(new BorderLayout());

        tarjeta.setBackground(BLANCO);

        tarjeta.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 225, 225)
                        ),
                        new EmptyBorder(
                                25,
                                30,
                                25,
                                30
                        )
                )
        );

        tarjeta.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        140
                )
        );

        //--------------------------------------------------
        // LADO IZQUIERDO
        //--------------------------------------------------
        JPanel izquierda = new JPanel();

        izquierda.setOpaque(false);

        izquierda.setLayout(
                new BoxLayout(
                        izquierda,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titulo = new JLabel("📅 Gestión de Entrevistas");

        titulo.setFont(TITULO);

        titulo.setForeground(AZUL);

        JLabel descripcion = new JLabel(
                "<html>"
                + "Consulta tus entrevistas programadas,<br>"
                + "confirma asistencia y revisa tu historial."
                + "</html>"
        );

        descripcion.setFont(TEXTO);

        descripcion.setForeground(GRIS);

        izquierda.add(titulo);
        izquierda.add(Box.createVerticalStrut(8));
        izquierda.add(descripcion);

        //--------------------------------------------------
        // BOTÓN
        //--------------------------------------------------
        JButton boton = new JButton("＋ Agendar");

        boton.setBackground(MORADO);

        boton.setForeground(Color.WHITE);

        boton.setFocusPainted(false);

        boton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        boton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        boton.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        22,
                        10,
                        22
                )
        );

        JPanel derecha = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT
                )
        );

        derecha.setOpaque(false);

        derecha.add(boton);

        tarjeta.add(
                izquierda,
                BorderLayout.CENTER
        );

        tarjeta.add(
                derecha,
                BorderLayout.EAST
        );

        return tarjeta;

    }

//=====================================================
// PRÓXIMA ENTREVISTA
//=====================================================
    private JPanel crearProximaEntrevista() {

        JPanel tarjeta = new JPanel(new BorderLayout());

        tarjeta.setBackground(BLANCO);

        tarjeta.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 225, 225)
                        ),
                        new EmptyBorder(
                                25,
                                25,
                                25,
                                25
                        )
                )
        );

        tarjeta.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        180
                )
        );

        //--------------------------------------------------
        // DATOS
        //--------------------------------------------------
        JPanel datos = new JPanel();

        datos.setOpaque(false);

        datos.setLayout(
                new BoxLayout(
                        datos,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titulo = new JLabel("Próxima Entrevista");

        titulo.setFont(SUBTITULO);

        titulo.setForeground(AZUL);

        JLabel empresa = new JLabel("Empresa: TechCorp S.A.");
        empresa.setFont(TEXTO);

        JLabel fecha = new JLabel("Fecha: 25 Julio 2026");
        fecha.setFont(TEXTO);

        JLabel hora = new JLabel("Hora: 10:30 AM");
        hora.setFont(TEXTO);

        JLabel modalidad = new JLabel("Modalidad: Virtual");
        modalidad.setFont(TEXTO);

        JLabel estado = new JLabel("Estado: Confirmada");

        estado.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        estado.setForeground(
                new Color(
                        0,
                        150,
                        80
                )
        );

        datos.add(titulo);
        datos.add(Box.createVerticalStrut(12));
        datos.add(empresa);
        datos.add(Box.createVerticalStrut(5));
        datos.add(fecha);
        datos.add(Box.createVerticalStrut(5));
        datos.add(hora);
        datos.add(Box.createVerticalStrut(5));
        datos.add(modalidad);
        datos.add(Box.createVerticalStrut(5));
        datos.add(estado);

        //--------------------------------------------------
        // BOTÓN
        //--------------------------------------------------
        JButton detalles = new JButton("Ver detalles");

        detalles.setBackground(AZUL);

        detalles.setForeground(Color.WHITE);

        detalles.setFocusPainted(false);

        detalles.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        detalles.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        detalles.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );

        JPanel derecha = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        0,
                        45
                )
        );

        derecha.setOpaque(false);

        derecha.add(detalles);

        tarjeta.add(
                datos,
                BorderLayout.CENTER
        );

        tarjeta.add(
                derecha,
                BorderLayout.EAST
        );

        return tarjeta;

    }
//=====================================================
// FILTROS
//=====================================================

    private JPanel crearFiltros() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setOpaque(false);

        panel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        //=========================================
        // BOTONES
        //=========================================
        JPanel botones = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        10,
                        0
                )
        );

        botones.setOpaque(false);

        botones.add(crearBotonFiltro("Todas", true));
        botones.add(crearBotonFiltro("Pendientes", false));
        botones.add(crearBotonFiltro("Hoy", false));
        botones.add(crearBotonFiltro("Finalizadas", false));

        //=========================================
        // CONTADOR
        //=========================================
        JLabel contador = new JLabel("4 entrevistas registradas");

        contador.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        contador.setForeground(Color.GRAY);

        panel.add(
                botones,
                BorderLayout.WEST
        );

        panel.add(
                contador,
                BorderLayout.EAST
        );

        return panel;

    }

//=====================================================
// LISTA DE ENTREVISTAS
//=====================================================
    private JPanel crearListaEntrevistas() {

        JPanel panel = new JPanel();

        panel.setOpaque(false);

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        //--------------------------------------------------
        // HOY
        //--------------------------------------------------
        JLabel hoy = new JLabel("HOY");

        hoy.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        hoy.setForeground(AZUL);

        panel.add(hoy);
        panel.add(Box.createVerticalStrut(15));

        panel.add(
                crearTarjetaEntrevista(
                        "💻",
                        "Desarrollador Java",
                        "TechCorp S.A.",
                        "10:30 AM · Virtual",
                        "Confirmada"
                )
        );

        panel.add(Box.createVerticalStrut(15));

        //--------------------------------------------------
        // PRÓXIMAS
        //--------------------------------------------------
        JLabel proximas = new JLabel("PRÓXIMAS");

        proximas.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        proximas.setForeground(AZUL);

        panel.add(proximas);
        panel.add(Box.createVerticalStrut(15));

        panel.add(
                crearTarjetaEntrevista(
                        "👨‍💼",
                        "Analista de Sistemas",
                        "SoftSolutions",
                        "27 Julio · Presencial",
                        "Pendiente"
                )
        );

        panel.add(Box.createVerticalStrut(12));

        panel.add(
                crearTarjetaEntrevista(
                        "🏢",
                        "Soporte Técnico",
                        "GlobalTech",
                        "30 Julio · Virtual",
                        "Pendiente"
                )
        );

        panel.add(Box.createVerticalStrut(20));

        //--------------------------------------------------
        // FINALIZADAS
        //--------------------------------------------------
        JLabel finalizadas = new JLabel("FINALIZADAS");

        finalizadas.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        finalizadas.setForeground(AZUL);

        panel.add(finalizadas);
        panel.add(Box.createVerticalStrut(15));

        panel.add(
                crearTarjetaEntrevista(
                        "✔",
                        "Auxiliar Administrativo",
                        "Innovatech",
                        "12 Julio · Presencial",
                        "Completada"
                )
        );

        return panel;

    }
//=====================================================
// TARJETA DE ENTREVISTA
//=====================================================

    private JPanel crearTarjetaEntrevista(
            String icono,
            String puesto,
            String empresa,
            String fecha,
            String estado) {

        JPanel tarjeta = new JPanel(new BorderLayout());

        tarjeta.setBackground(BLANCO);

        tarjeta.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 225, 225)
                        ),
                        new EmptyBorder(
                                18,
                                20,
                                18,
                                20
                        )
                )
        );

        tarjeta.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        95
                )
        );

        //--------------------------------------------------
        // ICONO
        //--------------------------------------------------
        JLabel lblIcono = new JLabel(icono);

        lblIcono.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        26
                )
        );

        tarjeta.add(
                lblIcono,
                BorderLayout.WEST
        );

        //--------------------------------------------------
        // INFORMACIÓN
        //--------------------------------------------------
        JPanel centro = new JPanel();

        centro.setOpaque(false);

        centro.setLayout(
                new BoxLayout(
                        centro,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel lblPuesto = new JLabel(puesto);

        lblPuesto.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        lblPuesto.setForeground(AZUL);

        JLabel lblEmpresa = new JLabel(empresa);

        lblEmpresa.setFont(TEXTO);

        lblEmpresa.setForeground(Color.GRAY);

        JLabel lblFecha = new JLabel(fecha);

        lblFecha.setFont(TEXTO);

        lblFecha.setForeground(MORADO);

        centro.add(lblPuesto);
        centro.add(Box.createVerticalStrut(5));
        centro.add(lblEmpresa);
        centro.add(Box.createVerticalStrut(5));
        centro.add(lblFecha);

        tarjeta.add(
                centro,
                BorderLayout.CENTER
        );

        //--------------------------------------------------
        // ESTADO
        //--------------------------------------------------
        JLabel lblEstado = new JLabel(estado);

        lblEstado.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        if (estado.equals("Confirmada")) {

            lblEstado.setForeground(
                    new Color(0, 150, 80)
            );

        } else if (estado.equals("Pendiente")) {

            lblEstado.setForeground(
                    new Color(220, 140, 0)
            );

        } else {

            lblEstado.setForeground(
                    MORADO
            );

        }

        tarjeta.add(
                lblEstado,
                BorderLayout.EAST
        );

        return tarjeta;

    }

//=====================================================
// BOTÓN FILTRO
//=====================================================
    private JButton crearBotonFiltro(
            String texto,
            boolean seleccionado
    ) {

        JButton boton = new JButton(texto);

        boton.setFocusPainted(false);

        boton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        boton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        boton.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        18,
                        8,
                        18
                )
        );

        if (seleccionado) {

            boton.setBackground(MORADO);

            boton.setForeground(Color.WHITE);

        } else {

            boton.setBackground(BLANCO);

            boton.setForeground(AZUL);

            boton.setBorder(
                    BorderFactory.createLineBorder(
                            new Color(220, 220, 220)
                    )
            );

        }

        return boton;

    }
}
