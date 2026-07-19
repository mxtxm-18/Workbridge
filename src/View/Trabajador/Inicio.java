package View.Trabajador;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Inicio extends JPanel {

    // =====================================================
    // COLORES
    // =====================================================
    private final Color FONDO = new Color(245, 245, 245);
    private final Color BLANCO = Color.WHITE;
    private final Color AZUL_OSCURO = new Color(36, 58, 105);
    private final Color MORADO = new Color(155, 115, 166);
    private final Color CREMA = new Color(212, 205, 197);

    // =====================================================
    // FUENTES
    // =====================================================
    private final Font TITULO
            = new Font(
                    "Segoe UI",
                    Font.BOLD,
                    28
            );

    private final Font SUBTITULO
            = new Font(
                    "Segoe UI",
                    Font.PLAIN,
                    15
            );

    // =====================================================
    // COMPONENTES
    // =====================================================
    private JPanel contenido;

    private JScrollPane scroll;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================
    public Inicio() {

        inicializar();

    }

    // =====================================================
    // INICIALIZAR
    // =====================================================
    private void inicializar() {

        setLayout(new BorderLayout());

        setBackground(FONDO);

        crearContenedor();

        construirVista();

    }

    // =====================================================
    // CONTENEDOR PRINCIPAL
    // =====================================================
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

        scroll.getViewport().setBackground(FONDO);

        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(
                scroll,
                BorderLayout.CENTER
        );

    }

    // =====================================================
    // AQUÍ SE IRÁ CONSTRUYENDO EL DASHBOARD
    // =====================================================
// =====================================================
// CONSTRUIR DASHBOARD
// =====================================================
    private void construirVista() {

        contenido.add(crearBienvenida());
        contenido.add(Box.createVerticalStrut(25));

        contenido.add(crearTarjetasResumen());
        contenido.add(Box.createVerticalStrut(25));

        contenido.add(crearPanelInferior());
        contenido.add(Box.createVerticalStrut(25));

        contenido.add(crearVacantes());
        contenido.add(Box.createVerticalStrut(20));

    }
    // =====================================================
// BIENVENIDA
// =====================================================

    private JPanel crearBienvenida() {

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
                        180
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

        JLabel bienvenida = new JLabel("¡Bienvenido, Brandon!");

        bienvenida.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        bienvenida.setForeground(AZUL_OSCURO);

        JLabel mensaje = new JLabel(
                "<html>"
                + "Nos alegra tenerte nuevamente en la plataforma.<br>"
                + "Desde aquí podrás consultar tus vacantes, entrevistas,<br>"
                + "postulaciones y mantener actualizado tu perfil."
                + "</html>"
        );

        mensaje.setFont(SUBTITULO);

        mensaje.setForeground(new Color(90, 90, 90));

        izquierda.add(bienvenida);
        izquierda.add(Box.createVerticalStrut(12));
        izquierda.add(mensaje);

        //=========================================
        // DERECHA
        //=========================================
        JLabel icono = new JLabel("👋");

        icono.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        70
                )
        );

        JPanel derecha = new JPanel(
                new BorderLayout()
        );

        derecha.setOpaque(false);

        derecha.add(
                icono,
                BorderLayout.CENTER
        );

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
// =====================================================
// TARJETAS RESUMEN
// =====================================================

    private JPanel crearTarjetasResumen() {

        JPanel panel = new JPanel(
                new GridLayout(
                        1,
                        4,
                        20,
                        0
                )
        );

        panel.setOpaque(false);

        panel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        150
                )
        );

        panel.add(
                crearTarjetaResumen(
                        "💼",
                        "Vacantes",
                        "12"
                )
        );

        panel.add(
                crearTarjetaResumen(
                        "📄",
                        "Postulaciones",
                        "5"
                )
        );

        panel.add(
                crearTarjetaResumen(
                        "📅",
                        "Entrevistas",
                        "2"
                )
        );

        panel.add(
                crearTarjetaResumen(
                        "💬",
                        "Mensajes",
                        "3"
                )
        );

        return panel;

    }
// =====================================================
// TARJETA INDIVIDUAL
// =====================================================

    private JPanel crearTarjetaResumen(
            String icono,
            String titulo,
            String valor
    ) {

        JPanel tarjeta = new JPanel();

        tarjeta.setLayout(
                new BoxLayout(
                        tarjeta,
                        BoxLayout.Y_AXIS
                )
        );

        tarjeta.setBackground(BLANCO);

        tarjeta.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 225, 225)
                        ),
                        new EmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );

        JLabel lblIcono = new JLabel(icono);

        lblIcono.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        28
                )
        );

        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblValor = new JLabel(valor);

        lblValor.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        34
                )
        );

        lblValor.setForeground(AZUL_OSCURO);

        lblValor.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitulo = new JLabel(titulo);

        lblTitulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        lblTitulo.setForeground(MORADO);

        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        tarjeta.add(Box.createVerticalGlue());
        tarjeta.add(lblIcono);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(lblValor);
        tarjeta.add(Box.createVerticalStrut(6));
        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createVerticalGlue());

        return tarjeta;

    }
    //=====================================================
// PANEL INFERIOR
//=====================================================

    private JPanel crearPanelInferior() {

        JPanel panel = new JPanel(
                new GridLayout(
                        1,
                        2,
                        20,
                        0
                )
        );

        panel.setOpaque(false);

        panel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        250
                )
        );

        panel.add(crearEntrevistas());

        panel.add(crearActividad());

        return panel;

    }
//=====================================================
// PRÓXIMAS ENTREVISTAS
//=====================================================

    private JPanel crearEntrevistas() {

        JPanel tarjeta = new JPanel();

        tarjeta.setLayout(
                new BoxLayout(
                        tarjeta,
                        BoxLayout.Y_AXIS
                )
        );

        tarjeta.setBackground(BLANCO);

        tarjeta.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 225, 225)
                        ),
                        new EmptyBorder(20, 20, 20, 20)
                )
        );

        tarjeta.add(crearTitulo("📅 Próximas Entrevistas"));
        tarjeta.add(Box.createVerticalStrut(15));

        tarjeta.add(new JLabel("• TechCorp S.A."));
        tarjeta.add(new JLabel("  22 Julio - 10:00 AM"));

        tarjeta.add(Box.createVerticalStrut(15));

        tarjeta.add(new JLabel("• Soft Solutions"));
        tarjeta.add(new JLabel("  25 Julio - 02:00 PM"));

        return tarjeta;

    }
//=====================================================
// ACTIVIDAD RECIENTE
//=====================================================

    private JPanel crearActividad() {

        JPanel tarjeta = new JPanel();

        tarjeta.setLayout(
                new BoxLayout(
                        tarjeta,
                        BoxLayout.Y_AXIS
                )
        );

        tarjeta.setBackground(BLANCO);

        tarjeta.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 225, 225)
                        ),
                        new EmptyBorder(20, 20, 20, 20)
                )
        );

        tarjeta.add(crearTitulo("🔔 Actividad Reciente"));
        tarjeta.add(Box.createVerticalStrut(15));

        tarjeta.add(new JLabel("✓ Postulación enviada."));
        tarjeta.add(Box.createVerticalStrut(10));

        tarjeta.add(new JLabel("✓ Perfil actualizado."));
        tarjeta.add(Box.createVerticalStrut(10));

        tarjeta.add(new JLabel("✓ Nueva vacante disponible."));

        return tarjeta;

    }
//=====================================================
// VACANTES
//=====================================================

    private JPanel crearVacantes() {

        JPanel tarjeta = new JPanel();

        tarjeta.setLayout(
                new BoxLayout(
                        tarjeta,
                        BoxLayout.Y_AXIS
                )
        );

        tarjeta.setBackground(BLANCO);

        tarjeta.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 225, 225)
                        ),
                        new EmptyBorder(20, 20, 20, 20)
                )
        );

        tarjeta.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        230
                )
        );

        tarjeta.add(crearTitulo("💼 Vacantes Recomendadas"));
        tarjeta.add(Box.createVerticalStrut(15));

        tarjeta.add(new JLabel("• Cajero - Restaurante Freddy Quick Bite"));
        tarjeta.add(Box.createVerticalStrut(8));

        tarjeta.add(new JLabel("• Atención al Cliente - TechCorp"));
        tarjeta.add(Box.createVerticalStrut(8));

        tarjeta.add(new JLabel("• Auxiliar Administrativo - Sistemas GT"));
        tarjeta.add(Box.createVerticalStrut(8));

        tarjeta.add(new JLabel("• Recepcionista - Hotel Vista Real"));

        return tarjeta;

    }
//=====================================================
// TITULO DE TARJETAS
//=====================================================

    private JLabel crearTitulo(String texto) {

        JLabel titulo = new JLabel(texto);

        titulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        titulo.setForeground(AZUL_OSCURO);

        return titulo;

    }
}
