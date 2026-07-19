
package View.Trabajador;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Notificaciones extends JPanel {

    //=====================================================
    // COLORES
    //=====================================================

    private final Color FONDO = new Color(245,245,245);
    private final Color BLANCO = Color.WHITE;
    private final Color AZUL = new Color(36,58,105);
    private final Color MORADO = new Color(155,115,166);

    //=====================================================

    private JPanel contenido;
    private JScrollPane scroll;

    //=====================================================

    public Notificaciones(){

        inicializar();

    }

    //=====================================================

    private void inicializar(){

        setLayout(new BorderLayout());

        setBackground(FONDO);

        crearContenedor();

        construirVista();

    }

    //=====================================================

    private void crearContenedor(){

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

    contenido.add(crearFiltros());
    contenido.add(Box.createVerticalStrut(20));

    contenido.add(crearListaNotificaciones());

}
//=====================================================
// CABECERA
//=====================================================
private JPanel crearCabecera(){

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
                    150
            )
    );

    //==========================================
    // LADO IZQUIERDO
    //==========================================
    JPanel izquierda = new JPanel();

    izquierda.setOpaque(false);

    izquierda.setLayout(
            new BoxLayout(
                    izquierda,
                    BoxLayout.Y_AXIS
            )
    );

    JLabel titulo = new JLabel("🔔 Centro de Notificaciones");

    titulo.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    28
            )
    );

    titulo.setForeground(AZUL);

    JLabel descripcion = new JLabel(
            "<html>"
            + "Consulta las últimas novedades de tu cuenta,<br>"
            + "vacantes, entrevistas y postulaciones."
            + "</html>"
    );

    descripcion.setFont(
            new Font(
                    "Segoe UI",
                    Font.PLAIN,
                    15
            )
    );

    descripcion.setForeground(new Color(90,90,90));

    izquierda.add(titulo);
    izquierda.add(Box.createVerticalStrut(10));
    izquierda.add(descripcion);

    //==========================================
    // BOTÓN
    //==========================================
    JButton marcar = new JButton("✓ Marcar todas como leídas");

    marcar.setBackground(MORADO);

    marcar.setForeground(Color.WHITE);

    marcar.setFocusPainted(false);

    marcar.setCursor(
            new Cursor(
                    Cursor.HAND_CURSOR
            )
    );

    marcar.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    14
            )
    );

    marcar.setBorder(
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

    derecha.add(marcar);

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
// LISTA DE NOTIFICACIONES
//=====================================================
private JPanel crearListaNotificaciones(){

    JPanel panel = new JPanel();

    panel.setOpaque(false);

    panel.setLayout(
            new BoxLayout(
                    panel,
                    BoxLayout.Y_AXIS
            )
    );

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
            crearNotificacion(
                    "🔵",
                    "Nueva entrevista programada",
                    "TechCorp S.A. te invitó a una entrevista.",
                    "Hace 10 minutos"
            )
    );

    panel.add(Box.createVerticalStrut(12));

    panel.add(
            crearNotificacion(
                    "🟣",
                    "Perfil actualizado",
                    "Tus datos fueron actualizados correctamente.",
                    "Hace 1 hora"
            )
    );

    panel.add(Box.createVerticalStrut(25));

    JLabel ayer = new JLabel("AYER");

    ayer.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    18
            )
    );

    ayer.setForeground(AZUL);

    panel.add(ayer);
    panel.add(Box.createVerticalStrut(15));

    panel.add(
            crearNotificacion(
                    "🟢",
                    "Nueva vacante disponible",
                    "Hay una vacante que coincide con tu perfil.",
                    "Hace 5 horas"
            )
    );

    panel.add(Box.createVerticalStrut(12));

    panel.add(
            crearNotificacion(
                    "🟡",
                    "Estado de postulación",
                    "Tu postulación pasó a revisión.",
                    "Hace 1 día"
            )
    );

    return panel;

}
//=====================================================
// TARJETA DE NOTIFICACIÓN
//=====================================================
private JPanel crearNotificacion(
        String icono,
        String titulo,
        String descripcion,
        String tiempo){

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

    JLabel lblIcono = new JLabel(icono);

    lblIcono.setFont(
            new Font(
                    "Segoe UI Emoji",
                    Font.PLAIN,
                    24
            )
    );

    tarjeta.add(
            lblIcono,
            BorderLayout.WEST
    );

    JPanel centro = new JPanel();

    centro.setOpaque(false);

    centro.setLayout(
            new BoxLayout(
                    centro,
                    BoxLayout.Y_AXIS
            )
    );

    JLabel lblTitulo = new JLabel(titulo);

    lblTitulo.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    15
            )
    );

    lblTitulo.setForeground(AZUL);

    JLabel lblDescripcion = new JLabel(descripcion);

    lblDescripcion.setFont(
            new Font(
                    "Segoe UI",
                    Font.PLAIN,
                    14
            )
    );

    lblDescripcion.setForeground(Color.GRAY);

    centro.add(lblTitulo);
    centro.add(Box.createVerticalStrut(5));
    centro.add(lblDescripcion);

    tarjeta.add(
            centro,
            BorderLayout.CENTER
    );

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
    botones.add(crearBotonFiltro("No leídas", false));
    botones.add(crearBotonFiltro("Entrevistas", false));
    botones.add(crearBotonFiltro("Postulaciones", false));

    //=========================================
    // CONTADOR
    //=========================================

    JLabel contador = new JLabel("4 notificaciones recientes");

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
                        new Color(220,220,220)
                )
        );

    }

    return boton;

}
}