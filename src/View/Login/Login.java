package View.Login;

import Controller.LoginController;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import main.WorkBridgeApp;

public class Login extends JFrame
        implements ActionListener {

    // ==========================
    // COLORES WORKBRIDGE
    // ==========================
    private final Color AZUL_OSCURO
            = new Color(36, 58, 105);

    private final Color AZUL_CLARO
            = new Color(212, 224, 236);

    private final Color CREMA
            = new Color(240, 240, 240);

    private final Color TEXTO_GRIS
            = new Color(115, 115, 115);

    private final Color LINK
            = new Color(78, 121, 255);

    private final Color BORDE
            = new Color(225, 225, 225);

    // ==========================
    // SCALING
    // ==========================
    private final Dimension SCREEN
            = Toolkit.getDefaultToolkit()
                    .getScreenSize();

    private final double SCALE_W
            = SCREEN.getWidth() / 1400.0;

    private final double SCALE_H
            = SCREEN.getHeight() / 900.0;

    private int sw(int x) {
        return (int) (x * SCALE_W);
    }

    private int sh(int y) {
        return (int) (y * SCALE_H);
    }

    // ==========================
    // COMPONENTES
    // ==========================
    private JTextField txtCorreo;
    private JPasswordField txtPassword;

    private JButton btnIngresar;
    private JButton btnCrearCuenta;

    private JLabel lblRecuperar;

    private JToggleButton btnTrabajador;
    private JToggleButton btnContratista;

    private LoginController controller;

    // ==========================
    // CONSTRUCTOR
    // ==========================
    public Login() {

        controller
                = new LoginController(this);

        configurarVentana();

        iniciarComponentes();
    }

    // ==========================
    // CONFIG VENTANA
    // ==========================
    private void configurarVentana() {

        setTitle("WorkBridge");

        setSize(
                sw(1400),
                sh(900)
        );

        setMinimumSize(
                new Dimension(
                        sw(1100),
                        sh(700)
                )
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLocationRelativeTo(null);

        setResizable(true);

        setLayout(
                new GridBagLayout()
        );

        getContentPane()
                .setBackground(
                        AZUL_CLARO
                );
    }

    // ==========================
    // CARGAR ICONOS
    // ==========================
    private ImageIcon cargarIcono(
            String ruta,
            int width,
            int height
    ) {

        try {

            ImageIcon icon
                    = new ImageIcon(
                            getClass()
                                    .getResource(ruta)
                    );

            Image img
                    = icon.getImage()
                            .getScaledInstance(
                                    width,
                                    height,
                                    Image.SCALE_SMOOTH
                            );

            return new ImageIcon(img);

        } catch (Exception e) {

            System.out.println(
                    "No se pudo cargar: "
                    + ruta
            );

            return null;
        }
    }

    // ==========================
    // INICIAR UI
    // ==========================
    private void iniciarComponentes() {

        // ==========================
        // CARD PRINCIPAL
        // ==========================
        RoundedPanel card
                = new RoundedPanel(
                        sw(35)
                );

        card.setBackground(
                Color.WHITE
        );

        card.setPreferredSize(
                new Dimension(
                        sw(1150),
                        sh(760)
                )
        );

        card.setLayout(
                new GridLayout(
                        1,
                        2
                )
        );

        add(card);

        // ==========================
        // PANEL IZQUIERDO
        // ==========================
        RoundedPanel panelLeft
                = new RoundedPanel(
                        sw(35)
                );

        panelLeft.setBackground(
                AZUL_OSCURO
        );

        panelLeft.setLayout(
                new BorderLayout()
        );

        panelLeft.setBorder(
                new EmptyBorder(
                        sh(35),
                        sw(55),
                        sh(35),
                        sw(55)
                )
        );

        // ==========================
        // PANEL DERECHO
        // ==========================
        JPanel panelRight
                = new JPanel();

        panelRight.setBackground(
                Color.WHITE
        );

        panelRight.setLayout(
                new GridBagLayout()
        );
        // ==========================
// CONTENEDOR IZQUIERDO
// ==========================
        JPanel leftContainer
                = new JPanel();

        leftContainer.setOpaque(false);

        leftContainer.setLayout(
                new BorderLayout()
        );

// ==========================
// TOP IZQUIERDO
// ==========================
        JPanel topLeft
                = new JPanel();

        topLeft.setOpaque(false);

        topLeft.setLayout(
                new BoxLayout(
                        topLeft,
                        BoxLayout.Y_AXIS
                )
        );

// ==========================
// LOGO ARRIBA IZQUIERDA
// ==========================
        JLabel lblLogo
                = new JLabel(
                        cargarIcono(
                                "/Imagen/logo_Workbridge.png",
                                sw(310),
                                sh(100)
                        )
                );

        lblLogo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

// ==========================
// TEXTO PRINCIPAL
// ==========================
        JLabel lblTitulo = new JLabel(
                "<html>"
                + "Conectamos "
                + "<font color='#6EA8FF'>"
                + "Talento."
                + "</font><br>"
                + "Generamos Oportunidades."
                + "</html>"
        );

        lblTitulo.setForeground(
                Color.WHITE
        );

        lblTitulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        sw(38)
                )
        );

        lblTitulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

// ==========================
// DESCRIPCIÓN
// ==========================
        JLabel lblDescripcion
                = new JLabel(
                        "<html>"
                        + "La plataforma profesional de Guatemala<br>"
                        + "donde empresas líderes y el mejor<br>"
                        + "talento del país se encuentran,<br>"
                        + "conectan y crecen juntos."
                        + "</html>"
                );

        lblDescripcion.setForeground(
                new Color(
                        220,
                        220,
                        220
                )
        );

        lblDescripcion.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        sw(14)
                )
        );

        lblDescripcion.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

// ==========================
// SPACING
// ==========================
        topLeft.add(lblLogo);

        topLeft.add(
                Box.createVerticalStrut(
                        sh(40)
                )
        );

        topLeft.add(lblTitulo);

        topLeft.add(
                Box.createVerticalStrut(
                        sh(25)
                )
        );

        topLeft.add(lblDescripcion);

// ==========================
// STATS PANEL
// ==========================
        JPanel panelStats
                = new JPanel(
                        new GridLayout(
                                1,
                                3,
                                sw(20),
                                0
                        )
                );

        panelStats.setOpaque(false);

// ==========================
// PROFESIONALES
// ==========================
        JPanel stat1
                = crearStat(
                        "/Imagen/icon_profesionales.png",
                        "+8,400",
                        "Profesionales"
                );

// ==========================
// EMPRESAS
// ==========================
        JPanel stat2
                = crearStat(
                        "/Imagen/icon_edificio.png",
                        "+1,200",
                        "Empresas"
                );

// ==========================
// VACANTES
// ==========================
        JPanel stat3
                = crearStat(
                        "/Imagen/icon_maletin.png",
                        "+3,500",
                        "Vacantes"
                );

        panelStats.add(stat1);
        panelStats.add(stat2);
        panelStats.add(stat3);

// ==========================
// AGREGAR AL LEFT PANEL
// ==========================
        leftContainer.add(
                topLeft,
                BorderLayout.NORTH
        );

        leftContainer.add(
                panelStats,
                BorderLayout.SOUTH
        );

        panelLeft.add(
                leftContainer,
                BorderLayout.CENTER
        );
// ==========================
// CONTENEDOR FORM CENTRADO
// ==========================
        JPanel formContainer
                = new JPanel();

        formContainer.setOpaque(false);

        formContainer.setLayout(
                new BoxLayout(
                        formContainer,
                        BoxLayout.Y_AXIS
                )
        );

        formContainer.setBorder(
                new EmptyBorder(
                        sh(40),
                        sw(65),
                        sh(40),
                        sw(65)
                )
        );

// ==========================
// BIENVENIDA
// ==========================
        JLabel lblBienvenido
                = new JLabel(
                        "Bienvenido de vuelta"
                );

        lblBienvenido.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        lblBienvenido.setForeground(
                AZUL_OSCURO
        );

        lblBienvenido.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        sw(30)
                )
        );

// ==========================
// SUBTITULO
// ==========================
        JLabel lblSubtitulo
                = new JLabel(
                        "Ingresa a tu cuenta para continuar"
                );

        lblSubtitulo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        lblSubtitulo.setForeground(
                TEXTO_GRIS
        );

        lblSubtitulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        sw(13)
                )
        );

// ==========================
// SELECTOR ROL
// ==========================
        JPanel panelRol
                = new JPanel(
                        new GridLayout(
                                1,
                                2,
                                sw(12),
                                0
                        )
                );

        panelRol.setOpaque(false);

        panelRol.setMaximumSize(
                new Dimension(
                        sw(440),
                        sh(55)
                )
        );

        panelRol.setPreferredSize(
                new Dimension(
                        sw(440),
                        sh(55)
                )
        );

        btnTrabajador
                = new JToggleButton(
                        " Trabajador",
                        cargarIcono(
                                "/Imagen/icon_trabajador.png",
                                sw(20),
                                sh(20)
                        )
                );

        btnContratista
                = new JToggleButton(
                        " Contratista",
                        cargarIcono(
                                "/Imagen/icon_contratista.png",
                                sw(20),
                                sh(20)
                        )
                );

        ButtonGroup grupo
                = new ButtonGroup();

        grupo.add(btnTrabajador);
        grupo.add(btnContratista);

        btnTrabajador.setSelected(true);

        estilizarRol(
                btnTrabajador,
                true
        );

        estilizarRol(
                btnContratista,
                false
        );

        btnTrabajador.addActionListener(e -> {

            estilizarRol(
                    btnTrabajador,
                    true
            );

            estilizarRol(
                    btnContratista,
                    false
            );
        });

        btnContratista.addActionListener(e -> {

            estilizarRol(
                    btnTrabajador,
                    false
            );

            estilizarRol(
                    btnContratista,
                    true
            );
        });

        panelRol.add(btnTrabajador);
        panelRol.add(btnContratista);

// ==========================
// LABEL CORREO
// ==========================
        JLabel lblCorreo
                = new JLabel(
                        "Correo electrónico"
                );

        lblCorreo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        lblCorreo.setMaximumSize(
                new Dimension(
                        sw(440),
                        sh(20)
                )
        );

        lblCorreo.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        lblCorreo.setForeground(
                AZUL_OSCURO
        );

        lblCorreo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        sw(13)
                )
        );

// ==========================
// INPUT CORREO
// ==========================
        RoundedPanel panelCorreo
                = new RoundedPanel(
                        sw(20)
                );

        panelCorreo.setBackground(
                Color.WHITE
        );

        panelCorreo.setBorder(
                BorderFactory.createLineBorder(
                        BORDE,
                        1
                )
        );

        panelCorreo.setMaximumSize(
                new Dimension(
                        sw(440),
                        sh(58)
                )
        );

        panelCorreo.setPreferredSize(
                new Dimension(
                        sw(440),
                        sh(58)
                )
        );

        panelCorreo.setLayout(
                new BorderLayout(
                        sw(15),
                        0
                )
        );

        JLabel iconCorreo
                = new JLabel(
                        cargarIcono(
                                "/Imagen/icon_correo.png",
                                sw(22),
                                sh(22)
                        )
                );

        iconCorreo.setBorder(
                new EmptyBorder(
                        0,
                        sw(15),
                        0,
                        0
                )
        );

        txtCorreo
                = new JTextField();

        txtCorreo.setBorder(null);

        txtCorreo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        sw(14)
                )
        );

        panelCorreo.add(
                iconCorreo,
                BorderLayout.WEST
        );

        panelCorreo.add(
                txtCorreo,
                BorderLayout.CENTER
        );

// ==========================
// LABEL PASSWORD
// ==========================
        JLabel lblPassword
                = new JLabel(
                        "Contraseña"
                );

        lblPassword.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        lblPassword.setMaximumSize(
                new Dimension(
                        sw(440),
                        sh(20)
                )
        );

        lblPassword.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        lblPassword.setForeground(
                AZUL_OSCURO
        );

        lblPassword.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        sw(13)
                )
        );

// ==========================
// INPUT PASSWORD
// ==========================
        RoundedPanel panelPassword
                = new RoundedPanel(
                        sw(20)
                );

        panelPassword.setBackground(
                Color.WHITE
        );

        panelPassword.setBorder(
                BorderFactory.createLineBorder(
                        BORDE,
                        1
                )
        );

        panelPassword.setMaximumSize(
                new Dimension(
                        sw(440),
                        sh(58)
                )
        );

        panelPassword.setPreferredSize(
                new Dimension(
                        sw(440),
                        sh(58)
                )
        );

        panelPassword.setLayout(
                new BorderLayout(
                        sw(15),
                        0
                )
        );

        JLabel iconPassword
                = new JLabel(
                        cargarIcono(
                                "/Imagen/icon_candado.png",
                                sw(22),
                                sh(22)
                        )
                );

        iconPassword.setBorder(
                new EmptyBorder(
                        0,
                        sw(15),
                        0,
                        0
                )
        );

        txtPassword
                = new JPasswordField();
        JLabel iconVista
                = new JLabel(
                        cargarIcono(
                                "/Imagen/icon_vista.png",
                                sw(22),
                                sh(22)
                        )
                );

        iconVista.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        0,
                        sw(15)
                )
        );

        iconVista.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
        iconVista.addMouseListener(
                new MouseAdapter() {

            private boolean visible
                    = false;

            @Override
            public void mouseClicked(
                    MouseEvent e
            ) {

                visible = !visible;

                txtPassword.setEchoChar(
                        visible
                                ? (char) 0
                                : '●'
                );
            }
        });
        txtPassword.setBorder(null);

        txtPassword.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        sw(14)
                )
        );

        panelPassword.add(
                iconPassword,
                BorderLayout.WEST
        );

        panelPassword.add(
                txtPassword,
                BorderLayout.CENTER
        );

        panelPassword.add(
                iconVista,
                BorderLayout.EAST
        );
// ==========================
// FORGOT PASSWORD DERECHA
// ==========================
        JPanel panelForgot
                = new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                0,
                                0
                        )
                );

        panelForgot.setOpaque(false);

        panelForgot.setMaximumSize(
                new Dimension(
                        sw(440),
                        sh(25)
                )
        );

        lblRecuperar
                = new JLabel(
                        "¿Olvidaste tu contraseña?"
                );

        lblRecuperar.setForeground(
                LINK
        );

        lblRecuperar.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        lblRecuperar.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        sw(12)
                )
        );

        panelForgot.add(
                lblRecuperar
        );
// ==========================
// BOTÓN INGRESAR
// ==========================
        btnIngresar
                = new JButton(
                        " Ingresar",
                        cargarIcono(
                                "/Imagen/icon_ingresar.png",
                                sw(22),
                                sh(22)
                        )
                );

        btnIngresar.setMaximumSize(
                new Dimension(
                        sw(440),
                        sh(58)
                )
        );

        btnIngresar.setPreferredSize(
                new Dimension(
                        sw(440),
                        sh(58)
                )
        );

        btnIngresar.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        btnIngresar.setBackground(
                AZUL_OSCURO
        );

        btnIngresar.setForeground(
                Color.WHITE
        );

        btnIngresar.setFocusPainted(false);

        btnIngresar.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        btnIngresar.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        sw(15)
                )
        );

        btnIngresar.setBorder(
                BorderFactory.createEmptyBorder()
        );

        btnIngresar.setHorizontalTextPosition(
                SwingConstants.RIGHT
        );

        btnIngresar.setIconTextGap(
                sw(8)
        );

        btnIngresar.addActionListener(this);
        btnIngresar.setOpaque(true);
        btnIngresar.setContentAreaFilled(true);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setFocusPainted(false);

// ==========================
// TEXTO O CENTRADA
// ==========================
        JLabel lblO
                = new JLabel("O");

        lblO.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        lblO.setForeground(
                Color.GRAY
        );

        lblO.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        sw(15)
                )
        );

// ==========================
// BOTÓN CREAR CUENTA
// ==========================
        btnCrearCuenta
                = new JButton(
                        " Crear cuenta nueva",
                        cargarIcono(
                                "/Imagen/icon_usuario+.png",
                                sw(22),
                                sh(22)
                        )
                );
        btnCrearCuenta.addActionListener(e -> {

            WorkBridgeApp app = new WorkBridgeApp();

            app.setVisible(true);

            app.mostrarPantalla("registro");

            dispose();

        });
        btnCrearCuenta.setMaximumSize(
                new Dimension(
                        sw(440),
                        sh(58)
                )
        );

        btnCrearCuenta.setPreferredSize(
                new Dimension(
                        sw(440),
                        sh(58)
                )
        );

        btnCrearCuenta.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        btnCrearCuenta.setBackground(
                Color.WHITE
        );

        btnCrearCuenta.setForeground(
                AZUL_OSCURO
        );

        btnCrearCuenta.setFocusPainted(false);

        btnCrearCuenta.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        btnCrearCuenta.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        sw(15)
                )
        );

        btnCrearCuenta.setBorder(
                BorderFactory.createLineBorder(
                        AZUL_OSCURO,
                        2
                )
        );

        btnCrearCuenta.setIconTextGap(
                sw(8)
        );

        btnCrearCuenta.addActionListener(this);

// ==========================
// TEXTO LEGAL
// ==========================
        JLabel lblLegal
                = new JLabel(
                        "<html>"
                        + "<div style='text-align:center;"
                        + "width:360px;'>"
                        + "Al ingresar aceptas nuestros "
                        + "<font color='#4E79FF'>"
                        + "Términos de uso"
                        + "</font>"
                        + " y "
                        + "<font color='#4E79FF'>"
                        + "Política de privacidad"
                        + "</font>."
                        + "</div>"
                        + "</html>"
                );

        lblLegal.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        lblLegal.setForeground(
                TEXTO_GRIS
        );

        lblLegal.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        sw(11)
                )
        );

        lblLegal.setMaximumSize(
                new Dimension(
                        sw(440),
                        sh(45)
                )
        );

// ==========================
// AGREGAR COMPONENTES FORM
// ==========================
        formContainer.add(
                lblBienvenido
        );

        formContainer.add(
                Box.createVerticalStrut(
                        sh(6)
                )
        );

        formContainer.add(
                lblSubtitulo
        );

        formContainer.add(
                Box.createVerticalStrut(
                        sh(28)
                )
        );

        formContainer.add(
                panelRol
        );

        formContainer.add(
                Box.createVerticalStrut(
                        sh(26)
                )
        );

        formContainer.add(
                lblCorreo
        );

        formContainer.add(
                Box.createVerticalStrut(
                        sh(7)
                )
        );

        formContainer.add(
                panelCorreo
        );

        formContainer.add(
                Box.createVerticalStrut(
                        sh(18)
                )
        );

        formContainer.add(
                lblPassword
        );

        formContainer.add(
                Box.createVerticalStrut(
                        sh(7)
                )
        );

        formContainer.add(
                panelPassword
        );

        formContainer.add(
                Box.createVerticalStrut(
                        sh(8)
                )
        );

        formContainer.add(
                panelForgot
        );

        formContainer.add(
                Box.createVerticalStrut(
                        sh(24)
                )
        );

        formContainer.add(
                btnIngresar
        );

        formContainer.add(
                Box.createVerticalStrut(
                        sh(14)
                )
        );

        formContainer.add(
                lblO
        );

        formContainer.add(
                Box.createVerticalStrut(
                        sh(14)
                )
        );

        formContainer.add(
                btnCrearCuenta
        );

        formContainer.add(
                Box.createVerticalStrut(
                        sh(22)
                )
        );

        formContainer.add(
                lblLegal
        );

// ==========================
// CENTRAR FORM
// ==========================
        panelRight.add(formContainer);

// ==========================
// AGREGAR PANELES
// ==========================
        card.add(panelLeft);
        card.add(panelRight);

    } // FIN iniciarComponentes()

// ==========================
// ESTILO BOTONES ROL
// ==========================
    private void estilizarRol(
            JToggleButton boton,
            boolean activo
    ) {

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
                        sw(14)
                )
        );
        boton.setIconTextGap(
                sw(8)
        );

        boton.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        boton.setHorizontalTextPosition(
                SwingConstants.RIGHT
        );
        boton.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        if (activo) {

            boton.setBackground(
                    new Color(
                            225,
                            235,
                            248
                    )
            );

            boton.setForeground(
                    AZUL_OSCURO
            );

        } else {

            boton.setBackground(
                    Color.WHITE
            );

            boton.setForeground(
                    Color.GRAY
            );
        }
    }

// ==========================
// CREAR STATS
// ==========================
    private JPanel crearStat(
            String rutaIcono,
            String numero,
            String texto
    ) {

        JPanel panel
                = new JPanel();

        panel.setOpaque(false);

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel icon
                = new JLabel(
                        cargarIcono(
                                rutaIcono,
                                sw(28),
                                sh(28)
                        )
                );

        JLabel lblNumero
                = new JLabel(numero);

        lblNumero.setForeground(
                Color.WHITE
        );

        lblNumero.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        sw(18)
                )
        );

        JLabel lblTexto
                = new JLabel(texto);

        lblTexto.setForeground(
                new Color(
                        220,
                        220,
                        220
                )
        );

        lblTexto.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        sw(11)
                )
        );

        panel.add(icon);
        panel.add(Box.createVerticalStrut(6));
        panel.add(lblNumero);
        panel.add(lblTexto);

        return panel;
    }

// ==========================
// LOGIN FUNCIONAL
// ==========================
    @Override
    public void actionPerformed(
            ActionEvent e
    ) {

        if (e.getSource()
                == btnIngresar) {

            controller.iniciarSesion();
        }

        if (e.getSource()
                == btnCrearCuenta) {

            JOptionPane.showMessageDialog(
                    this,
                    "Abrir formulario de registro"
            );
        }
    }

// ==========================
// GETTERS
// ==========================
    public String getCorreo() {

        return txtCorreo
                .getText()
                .trim();
    }

    public String getPassword() {

        return String.valueOf(
                txtPassword
                        .getPassword()
        );
    }

    public boolean esTrabajador() {

        return btnTrabajador
                .isSelected();
    }

// ==========================
// PANEL REDONDEADO REAL
// ==========================
    class RoundedPanel
            extends JPanel {

        private final int radius;

        public RoundedPanel(
                int radius
        ) {

            this.radius = radius;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2
                    = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(
                    getBackground()
            );

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    radius,
                    radius
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

// ==========================
// MAIN
// ==========================
    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(() -> {

            new Login()
                    .setVisible(true);
        });
    }
}
