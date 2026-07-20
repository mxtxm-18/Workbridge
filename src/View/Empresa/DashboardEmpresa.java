package View.Empresa;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DashboardEmpresa extends JFrame {

    // ==========================================
    // COLORES
    // ==========================================
    private final Color AZUL_OSCURO
            = new Color(54, 73, 125);

    private final Color MORADO
            = new Color(171, 132, 187);

    private final Color FONDO
            = new Color(245, 245, 245);

    // ==========================================
    // COMPONENTES
    // ==========================================
    private JPanel panelContenido;

    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JLabel lblFecha;
    private JLabel lblIconoHeader;
    private final List<JButton> botonesMenu
            = new ArrayList<>();

    // ==========================================
    // CONSTRUCTOR
    // ==========================================
    public DashboardEmpresa() {

        setTitle("Dashboard Trabajador");

        setSize(1450, 850);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLayout(
                new BorderLayout()
        );

        iniciarComponentes();
    }

    // ==========================================
    // INICIAR COMPONENTES
    // ==========================================
    private void iniciarComponentes() {

        JPanel panelPrincipal
                = new JPanel(
                        new BorderLayout()
                );

        // ==========================================
        // SIDEBAR
        // ==========================================
        JPanel sidebar
                = crearSidebar();

        // ==========================================
        // PANEL DERECHO
        // ==========================================
        JPanel panelDerecho
                = new JPanel(
                        new BorderLayout()
                );

        panelDerecho.setBackground(
                FONDO
        );

        // HEADER
        panelDerecho.add(
                crearHeader(),
                BorderLayout.NORTH
        );

        // PANEL CONTENIDO
        panelContenido
                = new JPanel(
                        new BorderLayout()
                );

        panelContenido.setBackground(
                FONDO
        );

        panelDerecho.add(
                panelContenido,
                BorderLayout.CENTER
        );

        panelPrincipal.add(
                sidebar,
                BorderLayout.WEST
        );

        panelPrincipal.add(
                panelDerecho,
                BorderLayout.CENTER
        );

        add(panelPrincipal);

        // ==========================================
        // VISTA INICIAL
        // ==========================================
        mostrarVista(
                new Inicio()
        );
        cambiarEncabezado(
                "Inicio",
                "Bienvenido al panel del Empresarial",
                "/Imagen/icon_inicio.png"
        );
    }

    // ==========================================
    // SIDEBAR
    // ==========================================
    private JPanel crearSidebar() {

        JPanel sidebar
                = new JPanel();

        sidebar.setBackground(
                AZUL_OSCURO
        );

        sidebar.setPreferredSize(
                new Dimension(
                        285,
                        0
                )
        );

        sidebar.setLayout(
                new BoxLayout(
                        sidebar,
                        BoxLayout.Y_AXIS
                )
        );

        sidebar.setBorder(
                new EmptyBorder(
                        22,
                        22,
                        22,
                        22
                )
        );

        // ==========================================
        // LOGO
        // ==========================================
        ImageIcon logoIcon
                = new ImageIcon(
                        getClass().getResource(
                                "/Imagen/logo_Workbridge.png"
                        )
                );

        Image logoEscalado
                = logoIcon.getImage()
                        .getScaledInstance(
                                220,
                                75,
                                Image.SCALE_SMOOTH
                        );

        JLabel lblLogo
                = new JLabel(
                        new ImageIcon(
                                logoEscalado
                        )
                );

        lblLogo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // ==========================================
        // SLOGAN
        // ==========================================
        JLabel lblSlogan
                = new JLabel(
                        "<html>"
                        + "Conectamos Talento "
                        + "Generando Oportunidades"
                        + "</html>"
                );

        lblSlogan.setForeground(
                Color.WHITE
        );

        lblSlogan.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        10
                )
        );

        lblSlogan.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // ==========================================
        // AGREGAR LOGO
        // ==========================================
        sidebar.add(
                lblLogo
        );

        sidebar.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                6
                        )
                )
        );

        sidebar.add(
                lblSlogan
        );

        sidebar.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                35
                        )
                )
        );

        // ==========================================
        // MENÚ
        // ==========================================
        sidebar.add(
                crearMenuConIcono(
                        "Inicio",
                        "/Imagen/icon_inicio.png"
                )
        );

        sidebar.add(
                crearMenuConIcono(
                        "MiEmpresa",
                        "/Imagen/icon_edificio.png"
                )
        );

        sidebar.add(
                crearMenuConIcono(
                        "MisVacantes",
                        "/Imagen/icon_vacantes.png"
                )
        );

        sidebar.add(
                crearMenuConIcono(
                        "Entrevistas",
                        "/Imagen/icon_entrevistas.png"
                )
        );

        sidebar.add(
                crearMenuConIcono(
                        "Comunicaciones",
                        "/Imagen/icon_chat.png"
                )
        );

        sidebar.add(
                crearMenuConIcono(
                        "Notificaciones",
                        "/Imagen/icon_notificacion.png"
                )
        );
        sidebar.add(
                crearMenuConIcono(
                        "Configuración",
                        "/Imagen/icon_configuracion.png"
                )
        );

        // EMPUJA USER ABAJO
        sidebar.add(
                Box.createVerticalGlue()
        );

        // ==========================================
        // USER ABAJO
        // ==========================================
        JPanel panelUser
                = new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                8,
                                0
                        )
                );

        panelUser.setOpaque(false);

        panelUser.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        ImageIcon userIcon
                = new ImageIcon(
                        getClass().getResource(
                                "/Imagen/icon_techcorp.png"
                        )
                );

        Image imgUser
                = userIcon.getImage()
                        .getScaledInstance(
                                32,
                                32,
                                Image.SCALE_SMOOTH
                        );

        JLabel lblUserIcon
                = new JLabel(
                        new ImageIcon(
                                imgUser
                        )
                );

        JLabel lblEmpresa
                = new JLabel(
                        "TechCorp S.A."
                );

        lblEmpresa.setForeground(
                Color.WHITE
        );

        lblEmpresa.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );

        panelUser.add(
                lblUserIcon
        );

        panelUser.add(
                lblEmpresa
        );

        sidebar.add(
                panelUser
        );

        return sidebar;
    }
    // ==========================================
    // HEADER
    // ==========================================

    private JPanel crearHeader() {

        JPanel header
                = new JPanel(
                        new BorderLayout()
                );
        header.setBackground(
                new Color(
                        212,
                        205,
                        197
                )
        );
        header.setBorder(
                new EmptyBorder(
                        18,
                        28,
                        18,
                        28
                )
        );

        // ==========================================
        // IZQUIERDA
        // ==========================================
        JPanel izquierda
                = new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                18,
                                0
                        )
                );

        izquierda.setBackground(
                new Color(
                        212,
                        205,
                        197
                )
        );
        // ICONO
        lblIconoHeader
                = new JLabel();

        // PANEL TEXTO
        JPanel panelTexto
                = new JPanel();

        panelTexto.setBackground(
                new Color(
                        212,
                        205,
                        197
                )
        );
        panelTexto.setLayout(
                new BoxLayout(
                        panelTexto,
                        BoxLayout.Y_AXIS
                )
        );

        // TITULO
        lblTitulo
                = new JLabel();

        lblTitulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        31
                )
        );

        lblTitulo.setForeground(
                AZUL_OSCURO
        );

        // SUBTITULO
        lblSubtitulo
                = new JLabel();

        lblSubtitulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        lblSubtitulo.setForeground(
                AZUL_OSCURO
        );

        panelTexto.add(
                lblTitulo
        );

        panelTexto.add(
                lblSubtitulo
        );

        izquierda.add(
                lblIconoHeader
        );

        izquierda.add(
                panelTexto
        );

        // ==========================================
        // DERECHA
        // ==========================================
        JPanel derecha
                = new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                16,
                                0
                        )
                );
        derecha.setBackground(
                new Color(
                        212,
                        205,
                        197
                )
        );
        // ==========================================
        // PANEL BUSQUEDA 
        // ==========================================
        JPanel panelBusqueda
                = new JPanel(
                        new BorderLayout()
                );

        panelBusqueda.setPreferredSize(
                new Dimension(
                        260,
                        42
                )
        );

        panelBusqueda.setBackground(
                Color.WHITE
        );

        panelBusqueda.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        220,
                                        220,
                                        220
                                ),
                                1
                        ),
                        new EmptyBorder(
                                0,
                                14,
                                0,
                                14
                        )
                )
        );

// ICONO
        JLabel lblBuscar
                = new JLabel("🔍");

        lblBuscar.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        16
                )
        );

        JTextField txtBuscar
                = new JTextField();

        txtBuscar.setBorder(null);

        txtBuscar.setOpaque(false);

        txtBuscar.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        txtBuscar.setText(
                "Buscar usuario, enpresa vacante "
        );

        panelBusqueda.add(
                lblBuscar,
                BorderLayout.WEST
        );

        panelBusqueda.add(
                txtBuscar,
                BorderLayout.CENTER
        );
        // ==========================================
        // CAMPANA
        // ==========================================
        JLabel lblCampana
                = new JLabel();

        ImageIcon iconCampana
                = new ImageIcon(
                        getClass().getResource(
                                "/Imagen/icon_notificacionceleste.png"
                        )
                );

        Image imgCampana
                = iconCampana.getImage()
                        .getScaledInstance(
                                28,
                                28,
                                Image.SCALE_SMOOTH
                        );

        lblCampana.setIcon(
                new ImageIcon(
                        imgCampana
                )
        );

        lblCampana.setBorder(
                BorderFactory.createEmptyBorder(
                        7, 0, 0, 0
                )
        );

        // ==========================================
        // PERFIL
        // ==========================================
        JLabel lblPerfil
                = new JLabel();

        ImageIcon iconPerfil
                = new ImageIcon(
                        getClass().getResource(
                                "/Imagen/icon_perfilceleste.png"
                        )
                );

        Image imgPerfil
                = iconPerfil.getImage()
                        .getScaledInstance(
                                28,
                                28,
                                Image.SCALE_SMOOTH
                        );

        lblPerfil.setIcon(
                new ImageIcon(
                        imgPerfil
                )
        );

        // ==========================================
        // AGREGAR
        // ==========================================
        header.add(
                izquierda,
                BorderLayout.WEST
        );

        header.add(
                derecha,
                BorderLayout.EAST
        );
// ==========================================
// FECHA
// ==========================================
        lblFecha
                = new JLabel(
                        obtenerFecha()
                );

        lblFecha.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        lblFecha.setForeground(
                AZUL_OSCURO
        );

        derecha.add(
                lblFecha
        );
        derecha.add(
                panelBusqueda
        );

        derecha.add(
                lblCampana
        );

        derecha.add(
                lblPerfil
        );
        return header;
    }
    // ==========================================
    // BOTON MENU
    // ==========================================

    private JButton crearMenuConIcono(
            String texto,
            String rutaIcono
    ) {

        JButton btn
                = new JButton(texto);

        btn.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        52
                )
        );

        btn.setPreferredSize(
                new Dimension(
                        230,
                        52
                )
        );
        btn.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        52
                )
        );
        btn.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        btn.setIconTextGap(14);

        btn.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        btn.setForeground(
                Color.WHITE
        );

        btn.setBackground(
                AZUL_OSCURO
        );

        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        btn.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // ==========================================
        // ICONO
        // ==========================================
        java.net.URL location
                = getClass().getResource(
                        rutaIcono
                );

        System.out.println(
                "Buscando icono: "
                + rutaIcono
                + " -> "
                + location
        );

        if (location == null) {

            System.out.println(
                    "ERROR: No existe -> "
                    + rutaIcono
            );

            return btn;
        }

        botonesMenu.add(btn);

        // ==========================================
        // EVENTO MENU
        // ==========================================
        btn.addActionListener(e -> {

            seleccionarMenu(btn);

            switch (texto) {

                case "Inicio":

                    mostrarVista(
                            new Inicio()
                    );

                    cambiarEncabezado(
                            "Inicio",
                            "Bienvenido a la Empresa",
                            "/Imagen/icon_inicio.png"
                    );
                    break;

                case "MiEmpresa":

                    mostrarVista(
                            new MiEmpresa()
                    );

                    cambiarEncabezado(
                            "Entrevistas",
                            "Administra la información y presentación de tu organización.",
                            "/Imagen/icon_edificio.png"
                    );
                    break;
                case "MisVacantes":

                    mostrarVista(
                            new MisVacantes()
                    );

                    cambiarEncabezado(
                            "MisVacantes",
                            "Crea y gestiona las ofertas laborales de tu empresa.",
                            "/Imagen/icon_vacantes.png"
                    );
                    break;
                case "Entrevistas":

                    mostrarVista(
                            new Entrevistas()
                    );

                    cambiarEncabezado(
                            "Entrevistas",
                            "Organiza y da seguimiento a los procesos de selección",
                            "/Imagen/icon_entrevistas.png"
                    );
                    break;

                case "Comunicaciones":

                    mostrarVista(
                            new Comunicaciones()
                    );

                    cambiarEncabezado(
                            "Comunicaciones",
                            "Mantén contacto directo con los candidatos y administradores.",
                            "/Imagen/icon_comunicaciones.png"
                    );

                case "Notificaciones":

                    mostrarVista(
                            new Notificaciones()
                    );

                    cambiarEncabezado(
                            "Notificaciones",
                            "Recibe alertas sobre postulaciones, entrevistas y novedades.",
                            "/Imagen/icon_notificacion.png"
                    );
                    
                
                    break;
                case "Configuración":

                    mostrarVista(
                            new Configuracion()
                    );

                    cambiarEncabezado(
                            "Configuración",
                            "Personaliza las preferencias y la seguridad de tu cuenta empresarial.",
                            "/Imagen/icon_configuracion.png"
                    );
                    break;

            }
        });
        ImageIcon icon
                = new ImageIcon(
                        location
                );

        Image img
                = icon.getImage()
                        .getScaledInstance(
                                22,
                                22,
                                Image.SCALE_SMOOTH
                        );

        btn.setIcon(
                new ImageIcon(img)
        );

        System.out.println(
                "Icono cargado para: "
                + texto
        );
        return btn;
    }

    // ==========================================
    // MOSTRAR VISTA
    // ==========================================
    private void mostrarVista(JPanel vista) {

        panelContenido.removeAll();

        panelContenido.add(
                vista,
                BorderLayout.CENTER
        );

        panelContenido.revalidate();
        panelContenido.repaint();
    }

    // ==========================================
    // MENU MORADO
    // ==========================================
    private void seleccionarMenu(
            JButton seleccionado
    ) {

        for (JButton btn : botonesMenu) {

            btn.setBackground(
                    AZUL_OSCURO
            );
        }

        seleccionado.setBackground(
                MORADO
        );
    }

    // ==========================================
    // CAMBIAR HEADER
    // ==========================================
    private void cambiarEncabezado(
            String titulo,
            String subtitulo,
            String rutaIcono
    ) {

        lblTitulo.setText(
                titulo
        );

        lblSubtitulo.setText(
                subtitulo
        );

        lblFecha.setText(
                obtenerFecha()
        );

        // ICONO
        ImageIcon icon
                = new ImageIcon(
                        getClass().getResource(
                                rutaIcono
                        )
                );

        Image img
                = icon.getImage()
                        .getScaledInstance(
                                42,
                                42,
                                Image.SCALE_SMOOTH
                        );

        lblIconoHeader.setIcon(
                new ImageIcon(img)
        );
    }

    // ==========================================
    // FECHA
    // ==========================================
    private String obtenerFecha() {

        LocalDate hoy
                = LocalDate.now();

        DateTimeFormatter formato
                = DateTimeFormatter.ofPattern(
                        "EEEE, dd 'de' MMMM 'de' yyyy",
                        new Locale("es")
                );

        return hoy.format(formato);
    }

    // ==========================================
    // MAIN
    // ==========================================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new DashboardEmpresa()
                    .setVisible(true);
        });
    }
}
