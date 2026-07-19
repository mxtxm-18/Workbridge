package View.Trabajador;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Mensajes extends JPanel {

    //=====================================================
    // COLORES
    //=====================================================

    private final Color FONDO = new Color(245,245,245);
    private final Color BLANCO = Color.WHITE;
    private final Color AZUL = new Color(36,58,105);
    private final Color MORADO = new Color(155,115,166);
    private final Color GRIS = new Color(110,110,110);

    //=====================================================
    // COMPONENTES
    //=====================================================

    private JPanel contenido;

    private JScrollPane scroll;

    //=====================================================
    // FUENTES
    //=====================================================

    private final Font TITULO =
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    28
            );

    private final Font SUBTITULO =
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    18
            );

    private final Font TEXTO =
            new Font(
                    "Segoe UI",
                    Font.PLAIN,
                    14
            );

    //=====================================================
    // CONSTRUCTOR
    //=====================================================

    public Mensajes() {

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

        contenido.add(crearBusqueda());
        contenido.add(Box.createVerticalStrut(20));

        contenido.add(crearListaMensajes());

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
                            new Color(225,225,225)
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

    //=========================================
    // IZQUIERDA
    //=========================================

    JPanel izquierda = new JPanel();

    izquierda.setOpaque(false);

    izquierda.setLayout(
            new BoxLayout(
                    izquierda,
                    BoxLayout.Y_AXIS
            )
    );

    JLabel titulo = new JLabel("💬 Mensajes");

    titulo.setFont(TITULO);

    titulo.setForeground(AZUL);

    JLabel descripcion = new JLabel(
            "<html>"
            + "Comunícate con empresas y reclutadores.<br>"
            + "Consulta el historial de conversaciones."
            + "</html>"
    );

    descripcion.setFont(TEXTO);

    descripcion.setForeground(GRIS);

    izquierda.add(titulo);
    izquierda.add(Box.createVerticalStrut(8));
    izquierda.add(descripcion);

    //=========================================
    // BOTÓN
    //=========================================

    JButton nuevo = new JButton("＋ Nuevo mensaje");

    nuevo.setBackground(MORADO);

    nuevo.setForeground(Color.WHITE);

    nuevo.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    14
            )
    );

    nuevo.setFocusPainted(false);

    nuevo.setCursor(
            new Cursor(
                    Cursor.HAND_CURSOR
            )
    );

    nuevo.setBorder(
            BorderFactory.createEmptyBorder(
                    10,
                    20,
                    10,
                    20
            )
    );

    JPanel derecha = new JPanel(
            new FlowLayout(
                    FlowLayout.RIGHT
            )
    );

    derecha.setOpaque(false);

    derecha.add(nuevo);

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
// BARRA DE BÚSQUEDA
//=====================================================

private JPanel crearBusqueda() {

    JPanel panel = new JPanel(new BorderLayout());

    panel.setOpaque(false);

    panel.setMaximumSize(
            new Dimension(
                    Integer.MAX_VALUE,
                    50
            )
    );

    JTextField buscar = new JTextField();

    buscar.setPreferredSize(
            new Dimension(
                    300,
                    45
            )
    );

    buscar.setFont(TEXTO);

    buscar.setText("Buscar conversación...");

    buscar.setBorder(
            BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(
                            new Color(220,220,220)
                    ),
                    new EmptyBorder(
                            0,
                            15,
                            0,
                            15
                    )
            )
    );

    JLabel icono = new JLabel("🔍");

    icono.setFont(
            new Font(
                    "Segoe UI Emoji",
                    Font.PLAIN,
                    18
            )
    );

    JPanel contenedor = new JPanel(new BorderLayout());

    contenedor.setBackground(BLANCO);

    contenedor.setBorder(
            BorderFactory.createLineBorder(
                    new Color(220,220,220)
            )
    );

    contenedor.add(
            buscar,
            BorderLayout.CENTER
    );

    contenedor.add(
            icono,
            BorderLayout.EAST
    );

    panel.add(
            contenedor,
            BorderLayout.CENTER
    );

    return panel;

}
//=====================================================
// LISTA DE MENSAJES
//=====================================================

private JPanel crearListaMensajes() {

    JPanel panel = new JPanel();

    panel.setOpaque(false);

    panel.setLayout(
            new BoxLayout(
                    panel,
                    BoxLayout.Y_AXIS
            )
    );

    //--------------------------------------------------
    // TÍTULO
    //--------------------------------------------------

    JLabel conversaciones = new JLabel("CONVERSACIONES");

    conversaciones.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    18
            )
    );

    conversaciones.setForeground(AZUL);

    panel.add(conversaciones);
    panel.add(Box.createVerticalStrut(18));

    //--------------------------------------------------
    // MENSAJES
    //--------------------------------------------------

    panel.add(
            crearMensaje(
                    "👤",
                    "TechCorp S.A.",
                    "Hola Brandon, queremos confirmar tu entrevista.",
                    "Hace 5 min"
            )
    );

    panel.add(Box.createVerticalStrut(12));

    panel.add(
            crearMensaje(
                    "👤",
                    "SoftSolutions",
                    "Hemos recibido correctamente tu postulación.",
                    "Hace 2 horas"
            )
    );

    panel.add(Box.createVerticalStrut(12));

    panel.add(
            crearMensaje(
                    "👤",
                    "Innovatech",
                    "Gracias por participar en nuestro proceso.",
                    "Ayer"
            )
    );

    panel.add(Box.createVerticalStrut(12));

    panel.add(
            crearMensaje(
                    "👤",
                    "GlobalTech",
                    "Tu perfil fue revisado por nuestro equipo.",
                    "Hace 3 días"
            )
    );

    panel.add(Box.createVerticalStrut(12));

    panel.add(
            crearMensaje(
                    "👤",
                    "Digital Company",
                    "Existe una nueva oportunidad para ti.",
                    "Hace 1 semana"
            )
    );

    return panel;

}
//=====================================================
// TARJETA DE MENSAJE
//=====================================================

private JPanel crearMensaje(
        String icono,
        String empresa,
        String mensaje,
        String tiempo
) {

    JPanel tarjeta = new JPanel(new BorderLayout());

    tarjeta.setBackground(BLANCO);

    tarjeta.setBorder(
            BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(
                            new Color(225,225,225)
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

    tarjeta.setCursor(
            new Cursor(
                    Cursor.HAND_CURSOR
            )
    );

    //=========================================
    // ICONO
    //=========================================

    JLabel lblIcono = new JLabel(icono);

    lblIcono.setFont(
            new Font(
                    "Segoe UI Emoji",
                    Font.PLAIN,
                    28
            )
    );

    tarjeta.add(
            lblIcono,
            BorderLayout.WEST
    );

    //=========================================
    // INFORMACIÓN
    //=========================================

    JPanel centro = new JPanel();

    centro.setOpaque(false);

    centro.setLayout(
            new BoxLayout(
                    centro,
                    BoxLayout.Y_AXIS
            )
    );

    JLabel lblEmpresa = new JLabel(empresa);

    lblEmpresa.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    15
            )
    );

    lblEmpresa.setForeground(AZUL);

    JLabel lblMensaje = new JLabel(mensaje);

    lblMensaje.setFont(TEXTO);

    lblMensaje.setForeground(GRIS);

    centro.add(lblEmpresa);
    centro.add(Box.createVerticalStrut(5));
    centro.add(lblMensaje);

    tarjeta.add(
            centro,
            BorderLayout.CENTER
    );

    //=========================================
    // HORA
    //=========================================

    JLabel lblTiempo = new JLabel(tiempo);

    lblTiempo.setFont(
            new Font(
                    "Segoe UI",
                    Font.PLAIN,
                    13
            )
    );

    lblTiempo.setForeground(MORADO);

    tarjeta.add(
            lblTiempo,
            BorderLayout.EAST
    );

    return tarjeta;

}
}