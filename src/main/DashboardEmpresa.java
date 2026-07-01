import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * ============================================================================
 * WORKBRIDGE - DASHBOARD EMPRESARIAL
 * ============================================================================
 * Interfaz principal para empresas dentro de la plataforma WorkBridge.
 * Replica el panel de control mostrado en la imagen de referencia
 * "Dashboard Empresarial", utilizando estrictamente los colores del manual
 * de identidad y de la paleta de colores oficial del proyecto:
 *
 *      Beige institucional .... #D4CDC5
 *      Azul Acero (celeste) ... #5B88A5
 *      Azul Marino ............ #243A69
 *      Morado / Lavanda ....... #9B73A6
 *
 * Eslogan oficial: "Conectamos talento. Generamos oportunidades."
 *
 * Tecnologías: Java 11, Swing y AWT puro (sin librerías externas).
 * ============================================================================
 */
public class DashboardEmpresa extends JFrame {

    //========================================================
    // PALETA DE COLORES OFICIAL (extraída de PALETA_DE_COLORES.pdf)
    //========================================================
    private final Color COLOR_BEIGE        = new Color(0xD4, 0xCD, 0xC5); // Fondos suaves / tarjetas
    private final Color COLOR_AZUL_ACERO   = new Color(0x5B, 0x88, 0xA5); // Celeste corporativo
    private final Color COLOR_AZUL_MARINO  = new Color(0x24, 0x3A, 0x69); // Azul institucional (sidebar)
    private final Color COLOR_MORADO       = new Color(0x9B, 0x73, 0xA6); // Lavanda (hover / selección)
    private final Color COLOR_BLANCO       = Color.WHITE;
    private final Color COLOR_TEXTO        = new Color(0x2B, 0x2B, 0x2B);
    private final Color COLOR_TEXTO_CLARO  = new Color(0xF2, 0xF0, 0xED);
    private final Color COLOR_BORDE        = new Color(0xE2, 0xE2, 0xE2);
    private final Color COLOR_VERDE_GRAF   = new Color(0x4C, 0xAF, 0x7D); // Para tendencias positivas

    //========================================================
    // TIPOGRAFÍA
    //========================================================
    private final Font FUENTE_LOGO       = new Font("Segoe UI", Font.BOLD, 24);
    private final Font FUENTE_LOGO_SUB   = new Font("Segoe UI", Font.PLAIN, 9);
    private final Font FUENTE_MENU       = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font FUENTE_SALUDO     = new Font("Segoe UI", Font.BOLD, 17);
    private final Font FUENTE_FECHA      = new Font("Segoe UI", Font.PLAIN, 12);
    private final Font FUENTE_TITULO_DASH= new Font("Segoe UI", Font.PLAIN, 14);
    private final Font FUENTE_SECCION    = new Font("Segoe UI", Font.BOLD, 17);
    private final Font FUENTE_TARJ_TIT   = new Font("Segoe UI", Font.PLAIN, 13);
    private final Font FUENTE_TARJ_VAL   = new Font("Segoe UI", Font.BOLD, 30);
    private final Font FUENTE_TARJ_SUB   = new Font("Segoe UI", Font.PLAIN, 11);
    private final Font FUENTE_GRAF_TIT   = new Font("Segoe UI", Font.BOLD, 15);

    //========================================================
    // DATOS INSTITUCIONALES (Manual_de_marca - WorkBridge)
    //========================================================
    private final String NOMBRE_EMPRESA_USUARIO = "TechCorp S.A.";
    private final String ESLOGAN_WORKBRIDGE = "Conectamos talento, generamos oportunidades.";

    //========================================================
    // DECLARACIÓN DE COMPONENTES - ESTRUCTURA GENERAL
    //========================================================
    // Panel lateral izquierdo que contiene el logotipo y el menú de navegación
    private JPanel panelMenuLateral;
    // Panel superior que contiene el saludo, la fecha y los íconos de usuario
    private JPanel panelEncabezado;
    // Panel central donde se despliega todo el contenido del dashboard (con scroll)
    private JPanel panelContenidoDashboard;
    // Contenedor con scroll para que el contenido se pueda desplazar si la ventana es pequeña
    private JScrollPane scrollContenido;

    //========================================================
    // DECLARACIÓN DE COMPONENTES - MENÚ LATERAL
    //========================================================
    // Etiqueta que muestra el nombre "WorkBridge" estilizado como logotipo
    private JLabel lblNombreLogo;
    // Etiqueta que muestra el eslogan institucional debajo del logotipo
    private JLabel lblEslogan;
    // Botones de navegación del menú lateral
    private JButton btnInicio;
    private JButton btnMiPerfil;
    private JButton btnBuscarVacantes;
    private JButton btnMisPostulaciones;
    private JButton btnEntrevistas;
    private JButton btnNotificacionesMenu;
    private JButton btnDocumentos;
    private JButton btnConfiguracion;
    // Botón encargado de cerrar la sesión activa del usuario empresarial
    private JButton btnCerrarSesion;
    // Panel inferior del menú lateral que muestra la empresa logueada
    private JPanel panelUsuarioFooter;
    // Etiqueta que muestra el nombre de la empresa logueada en el pie del menú
    private JLabel lblNombreEmpresaFooter;
    // Arreglo que agrupa todos los botones del menú para manejar la selección visual
    private JButton[] botonesMenu;
    // Índice del botón actualmente seleccionado dentro del menú lateral
    private int indiceBotonSeleccionado = 7; // Configuración aparece seleccionada en la referencia

    //========================================================
    // DECLARACIÓN DE COMPONENTES - ENCABEZADO SUPERIOR
    //========================================================
    // Etiqueta que saluda a la empresa según la hora del día
    private JLabel lblSaludoEmpresa;
    // Etiqueta que muestra la fecha actual completa en español
    private JLabel lblFechaActual;
    // Etiqueta que indica el nombre de la sección actual ("Dashboard Empresarial")
    private JLabel lblTituloSeccion;
    // Botón con ícono de campana para notificaciones rápidas
    private JButton btnIconoNotificaciones;
    // Botón con ícono de usuario para acceso rápido al perfil
    private JButton btnIconoPerfil;

    //========================================================
    // DECLARACIÓN DE COMPONENTES - TARJETAS ESTADÍSTICAS
    //========================================================
    // Panel que agrupa las cuatro tarjetas de indicadores principales
    private JPanel panelTarjetasPrincipales;
    // Tarjeta: Postulantes totales recibidos por la empresa
    private JPanel panelPostulantesTotales;
    // Tarjeta: Ofertas de empleo actualmente activas
    private JPanel panelOfertasActivas;
    // Tarjeta: Visitas totales al perfil de la empresa
    private JPanel panelVisitasPerfil;
    // Tarjeta: Nuevos postulantes registrados recientemente
    private JPanel panelNuevosPostulantes;

    //========================================================
    // DECLARACIÓN DE COMPONENTES - GRÁFICAS PRINCIPALES
    //========================================================
    // Panel que dibuja la gráfica de visitas a las vacantes (línea)
    private JPanel panelGraficaVisitasVacantes;
    // Panel que dibuja la gráfica de ofertas de empleo (área doble)
    private JPanel panelGraficaOfertasEmpleo;

    //========================================================
    // DECLARACIÓN DE COMPONENTES - TARJETAS INFERIORES
    //========================================================
    // Tarjeta: Tasa de contratación general de la empresa
    private JPanel panelTasaContratacion;
    // Tarjeta: Cantidad de entrevistas programadas
    private JPanel panelEntrevistasProgramadas;
    // Tarjeta: Tiempo promedio de contratación en días
    private JPanel panelTiempoPromedioContratacion;
    // Tarjeta: Estado de afiliación / planes contratados por la empresa
    private JPanel panelEstadoAfiliacion;

    // Datos simulados para las mini-gráficas tipo "sparkline" de cada tarjeta
    private final int[] DATOS_POSTULANTES   = {10, 14, 18, 22, 30, 45, 60, 70, 78, 86};
    private final int[] DATOS_OFERTAS       = {5, 6, 8, 9, 12, 15, 18, 20, 23, 26};
    private final int[] DATOS_VISITAS       = {20, 18, 19, 21, 23, 24, 25, 25, 24, 25};
    private final int[] DATOS_NUEVOS_POST   = {8, 9, 10, 12, 15, 17, 19, 20, 21, 23};
    private final int[] DATOS_TASA          = {15, 16, 18, 19, 20, 21, 22, 23, 24, 25};
    private final int[] DATOS_ENTREVISTAS   = {10, 12, 14, 16, 18, 19, 21, 22, 24, 25};
    private final int[] DATOS_TIEMPO        = {35, 33, 31, 30, 29, 28, 27, 26, 25, 25};

    //========================================================
    // CONSTRUCTOR
    //========================================================
    public DashboardEmpresa() {
        configurarVentana();
        construirMenuLateral();
        construirEncabezado();
        construirContenidoPrincipal();
        ensamblarVentana();
        seleccionarBotonMenu(indiceBotonSeleccionado);
    }

    //========================================================
    // CONFIGURACIÓN GENERAL DE LA VENTANA
    //========================================================
    private void configurarVentana() {
        setTitle("WorkBridge - Dashboard Empresarial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 650));
        setSize(1200, 720);
        setLocationRelativeTo(null);
        // Layout principal de tipo BorderLayout para distribuir sidebar, header y contenido
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_BLANCO);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Si falla, se conserva el Look and Feel por defecto sin interrumpir la app
        }
    }

    //========================================================
    // CREACIÓN DEL MENÚ LATERAL (SIDEBAR)
    //========================================================
    private void construirMenuLateral() {
        panelMenuLateral = new JPanel();
        panelMenuLateral.setLayout(new BoxLayout(panelMenuLateral, BoxLayout.Y_AXIS));
        panelMenuLateral.setBackground(COLOR_AZUL_MARINO);
        panelMenuLateral.setPreferredSize(new Dimension(235, 0));
        panelMenuLateral.setBorder(new EmptyBorder(15, 0, 15, 0));

        // ---- Bloque del logotipo ----
        JPanel panelLogo = crearPanelLogotipo();
        panelLogo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelMenuLateral.add(panelLogo);
        panelMenuLateral.add(Box.createRigidArea(new Dimension(0, 20)));

        // ---- Botones de navegación ----
        btnInicio              = crearBotonMenu("Inicio", '\u2302');
        btnMiPerfil            = crearBotonMenu("Mi perfil", '\u263A');
        btnBuscarVacantes      = crearBotonMenu("Buscar Vacantes", '\u2315');
        btnMisPostulaciones    = crearBotonMenu("Mis postulaciones", '\u2709');
        btnEntrevistas         = crearBotonMenu("Entrevistas", '\u2696');
        btnNotificacionesMenu  = crearBotonMenu("Notificaciones", '\u266B');
        btnDocumentos          = crearBotonMenu("Documentos", '\u2637');
        btnConfiguracion       = crearBotonMenu("Configuracion", '\u2699');

        botonesMenu = new JButton[]{
            btnInicio, btnMiPerfil, btnBuscarVacantes, btnMisPostulaciones,
            btnEntrevistas, btnNotificacionesMenu, btnDocumentos, btnConfiguracion
        };

        for (int i = 0; i < botonesMenu.length; i++) {
            final int indice = i;
            botonesMenu[i].addActionListener(e -> seleccionarBotonMenu(indice));
            botonesMenu[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            panelMenuLateral.add(botonesMenu[i]);
        }

        panelMenuLateral.add(Box.createVerticalGlue());

        // ---- Botón de cerrar sesión ----
        btnCerrarSesion = crearBotonMenu("Cerrar sesion", '\u23FB');
        btnCerrarSesion.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnCerrarSesion.addActionListener(e -> manejarCerrarSesion());
        panelMenuLateral.add(btnCerrarSesion);
        panelMenuLateral.add(Box.createRigidArea(new Dimension(0, 12)));

        // ---- Panel inferior con la empresa logueada ----
        panelUsuarioFooter = construirPanelUsuarioFooter();
        panelUsuarioFooter.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelMenuLateral.add(panelUsuarioFooter);
    }

    // Construye el bloque visual del logotipo "WorkBridge" con su eslogan
    private JPanel crearPanelLogotipo() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_AZUL_MARINO);
        panel.setBorder(new EmptyBorder(10, 20, 10, 10));
        panel.setMaximumSize(new Dimension(235, 90));

        // Ícono del maletín + puente dibujado vectorialmente (sin imágenes externas)
        JPanel iconoMaletin = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_MORADO);
                g2.fillRoundRect(4, 12, 30, 20, 6, 6);
                g2.setColor(COLOR_AZUL_ACERO);
                g2.fillRoundRect(12, 6, 14, 10, 4, 4);
                g2.setColor(COLOR_AZUL_MARINO);
                g2.fillRect(4, 20, 30, 3);
                g2.dispose();
            }
        };
        iconoMaletin.setOpaque(false);
        iconoMaletin.setMaximumSize(new Dimension(38, 32));
        iconoMaletin.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblNombreLogo = new JLabel("Work Bridge");
        lblNombreLogo.setFont(FUENTE_LOGO);
        lblNombreLogo.setForeground(COLOR_BLANCO);
        lblNombreLogo.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblEslogan = new JLabel("<html>CONECTAMOS TALENTO,<br>GENERAMOS OPORTUNIDADES</html>");
        lblEslogan.setFont(FUENTE_LOGO_SUB);
        lblEslogan.setForeground(COLOR_BEIGE);
        lblEslogan.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel filaSuperior = new JPanel();
        filaSuperior.setLayout(new BoxLayout(filaSuperior, BoxLayout.X_AXIS));
        filaSuperior.setOpaque(false);
        filaSuperior.add(iconoMaletin);
        filaSuperior.add(Box.createRigidArea(new Dimension(6, 0)));
        filaSuperior.add(lblNombreLogo);
        filaSuperior.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(filaSuperior);
        panel.add(Box.createRigidArea(new Dimension(0, 4)));
        panel.add(lblEslogan);
        return panel;
    }

    // Crea un botón de menú lateral estilizado, con ícono textual y efecto hover
    private JButton crearBotonMenu(String texto, char icono) {
        JButton boton = new JButton("  " + icono + "    " + texto);
        boton.setFont(FUENTE_MENU);
        boton.setForeground(COLOR_TEXTO_CLARO);
        boton.setBackground(COLOR_AZUL_MARINO);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(true);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setMaximumSize(new Dimension(235, 38));
        boton.setPreferredSize(new Dimension(235, 38));
        boton.setBorder(new EmptyBorder(8, 18, 8, 8));

        // Efecto hover: cambia de color cuando el cursor pasa sobre el botón
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!boton.equals(botonesMenuActualSeleccionado())) {
                    boton.setBackground(COLOR_AZUL_ACERO);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!boton.equals(botonesMenuActualSeleccionado())) {
                    boton.setBackground(COLOR_AZUL_MARINO);
                }
            }
        });
        return boton;
    }

    // Devuelve el botón actualmente marcado como seleccionado (o null si aún no existe el arreglo)
    private JButton botonesMenuActualSeleccionado() {
        if (botonesMenu == null) return null;
        if (indiceBotonSeleccionado < 0 || indiceBotonSeleccionado >= botonesMenu.length) return null;
        return botonesMenu[indiceBotonSeleccionado];
    }

    // Marca visualmente el botón seleccionado del menú (color morado/lavanda) y actualiza el título
    private void seleccionarBotonMenu(int indice) {
        indiceBotonSeleccionado = indice;
        for (int i = 0; i < botonesMenu.length; i++) {
            botonesMenu[i].setBackground(i == indice ? COLOR_MORADO : COLOR_AZUL_MARINO);
        }
        if (lblTituloSeccion != null) {
            String[] nombresSecciones = {
                "Inicio", "Mi Perfil", "Buscar Vacantes", "Mis Postulaciones",
                "Entrevistas", "Notificaciones", "Documentos", "Dashboard Empresarial"
            };
            lblTituloSeccion.setText(nombresSecciones[indice]);
        }
    }

    // Construye el panel inferior del menú con el nombre de la empresa logueada
    private JPanel construirPanelUsuarioFooter() {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(new Color(0x1C, 0x2E, 0x54));
        panel.setMaximumSize(new Dimension(235, 55));
        panel.setBorder(new EmptyBorder(8, 14, 8, 14));

        // Ícono circular simulando avatar de la empresa
        JPanel avatar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_BEIGE);
                g2.fillOval(0, 0, 32, 32);
                g2.setColor(COLOR_AZUL_MARINO);
                g2.fillOval(10, 6, 12, 12);
                g2.fillOval(6, 20, 20, 14);
                g2.dispose();
            }
        };
        avatar.setOpaque(false);
        avatar.setPreferredSize(new Dimension(32, 32));

        lblNombreEmpresaFooter = new JLabel(NOMBRE_EMPRESA_USUARIO);
        lblNombreEmpresaFooter.setFont(FUENTE_MENU);
        lblNombreEmpresaFooter.setForeground(COLOR_BLANCO);

        panel.add(avatar, BorderLayout.WEST);
        panel.add(lblNombreEmpresaFooter, BorderLayout.CENTER);
        return panel;
    }

    //========================================================
    // CREACIÓN DEL ENCABEZADO SUPERIOR
    //========================================================
    private void construirEncabezado() {
        panelEncabezado = new JPanel(new BorderLayout());
        panelEncabezado.setBackground(COLOR_BEIGE);
        panelEncabezado.setPreferredSize(new Dimension(0, 70));
        panelEncabezado.setBorder(new EmptyBorder(10, 25, 10, 25));

        // ---- Bloque izquierdo: saludo y fecha ----
        JPanel panelSaludo = new JPanel();
        panelSaludo.setOpaque(false);
        panelSaludo.setLayout(new BoxLayout(panelSaludo, BoxLayout.Y_AXIS));

        lblSaludoEmpresa = new JLabel(generarSaludoSegunHora() + ", " + NOMBRE_EMPRESA_USUARIO + " !");
        lblSaludoEmpresa.setFont(FUENTE_SALUDO);
        lblSaludoEmpresa.setForeground(COLOR_AZUL_MARINO);

        lblFechaActual = new JLabel(generarFechaActualEnEspanol());
        lblFechaActual.setFont(FUENTE_FECHA);
        lblFechaActual.setForeground(COLOR_TEXTO);

        panelSaludo.add(lblSaludoEmpresa);
        panelSaludo.add(lblFechaActual);

        // ---- Bloque derecho: título de sección + íconos ----
        JPanel panelDerecho = new JPanel();
        panelDerecho.setOpaque(false);
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.X_AXIS));

        lblTituloSeccion = new JLabel("Dashboard Empresarial");
        lblTituloSeccion.setFont(FUENTE_TITULO_DASH);
        lblTituloSeccion.setForeground(COLOR_TEXTO);

        btnIconoNotificaciones = crearBotonIcono("\uD83D\uDD14", "Ver notificaciones");
        btnIconoPerfil = crearBotonIcono("\uD83D\uDC64", "Ir a mi perfil");
        btnIconoNotificaciones.addActionListener(e -> mostrarMensajeInformativo("Notificaciones",
            "Tienes notificaciones nuevas sobre tus vacantes y postulantes."));
        btnIconoPerfil.addActionListener(e -> mostrarMensajeInformativo("Perfil",
            "Accediendo al perfil de la empresa " + NOMBRE_EMPRESA_USUARIO + "."));

        panelDerecho.add(lblTituloSeccion);
        panelDerecho.add(Box.createRigidArea(new Dimension(20, 0)));
        panelDerecho.add(btnIconoNotificaciones);
        panelDerecho.add(Box.createRigidArea(new Dimension(8, 0)));
        panelDerecho.add(btnIconoPerfil);

        panelEncabezado.add(panelSaludo, BorderLayout.WEST);
        panelEncabezado.add(panelDerecho, BorderLayout.EAST);
    }

    // Crea un botón circular simple utilizado para los íconos de la barra superior
    private JButton crearBotonIcono(String simbolo, String tooltip) {
        JButton boton = new JButton(simbolo);
        boton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        boton.setFocusPainted(false);
        boton.setBackground(COLOR_BLANCO);
        boton.setForeground(COLOR_AZUL_MARINO);
        boton.setBorder(new LineBorder(COLOR_BORDE, 1, true));
        boton.setPreferredSize(new Dimension(34, 34));
        boton.setMaximumSize(new Dimension(34, 34));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setToolTipText(tooltip);
        return boton;
    }

    // Genera el saludo según la hora del sistema (Buenos días / tardes / noches)
    private String generarSaludoSegunHora() {
        int hora = new java.util.GregorianCalendar().get(java.util.Calendar.HOUR_OF_DAY);
        if (hora < 12) return "¡Buenos días";
        if (hora < 19) return "¡Buenas tardes";
        return "¡Buenas noches";
    }

    // Genera la fecha actual formateada en español, ej: "Domingo, 7 de junio de 2026"
    private String generarFechaActualEnEspanol() {
        SimpleDateFormat formato = new SimpleDateFormat("EEEE, d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String fecha = formato.format(new Date());
        return fecha.substring(0, 1).toUpperCase() + fecha.substring(1);
    }

    //========================================================
    // CREACIÓN DEL CONTENIDO PRINCIPAL DEL DASHBOARD
    //========================================================
    private void construirContenidoPrincipal() {
        panelContenidoDashboard = new JPanel();
        panelContenidoDashboard.setLayout(new BoxLayout(panelContenidoDashboard, BoxLayout.Y_AXIS));
        panelContenidoDashboard.setBackground(COLOR_BLANCO);
        panelContenidoDashboard.setBorder(new EmptyBorder(20, 25, 25, 25));

        JLabel lblResumen = new JLabel("Resumen de rendimiento (Performance Overview)");
        lblResumen.setFont(FUENTE_SECCION);
        lblResumen.setForeground(COLOR_AZUL_MARINO);
        lblResumen.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelContenidoDashboard.add(lblResumen);
        panelContenidoDashboard.add(Box.createRigidArea(new Dimension(0, 15)));

        // ---- Fila de tarjetas estadísticas principales ----
        panelTarjetasPrincipales = new JPanel(new GridLayout(1, 4, 18, 0));
        panelTarjetasPrincipales.setOpaque(false);
        panelTarjetasPrincipales.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelTarjetasPrincipales.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

        panelPostulantesTotales = crearTarjetaEstadistica("Postulantes Totales:", "86", "Usuarios", DATOS_POSTULANTES, COLOR_AZUL_ACERO);
        panelOfertasActivas     = crearTarjetaEstadistica("Ofertas De Empleo Activas", "26", "", DATOS_OFERTAS, COLOR_MORADO);
        panelVisitasPerfil      = crearTarjetaEstadistica("Visitas al Perfil: (Empresa)", "25", "", DATOS_VISITAS, new Color(0x9A, 0x9A, 0x9A));
        panelNuevosPostulantes  = crearTarjetaEstadistica("Nuevos Postulantes:", "23", "+15%", DATOS_NUEVOS_POST, COLOR_VERDE_GRAF);

        panelTarjetasPrincipales.add(panelPostulantesTotales);
        panelTarjetasPrincipales.add(panelOfertasActivas);
        panelTarjetasPrincipales.add(panelVisitasPerfil);
        panelTarjetasPrincipales.add(panelNuevosPostulantes);

        panelContenidoDashboard.add(panelTarjetasPrincipales);
        panelContenidoDashboard.add(Box.createRigidArea(new Dimension(0, 18)));

        // ---- Fila de gráficas grandes ----
        JPanel panelGraficasGrandes = new JPanel(new GridLayout(1, 2, 18, 0));
        panelGraficasGrandes.setOpaque(false);
        panelGraficasGrandes.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelGraficasGrandes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 230));

        panelGraficaVisitasVacantes = crearPanelGraficaLinea("Visitas a tus Vacantes",
            new int[]{5, 12, 18, 30, 45, 55, 70, 80, 95, 110, 118, 112},
            COLOR_VERDE_GRAF, false);
        panelGraficaOfertasEmpleo = crearPanelGraficaLinea("Ofertas de Empleo",
            new int[]{50, 70, 60, 90, 80, 100, 75, 95, 120, 110, 140, 150},
            COLOR_AZUL_MARINO, true);

        panelGraficasGrandes.add(panelGraficaVisitasVacantes);
        panelGraficasGrandes.add(panelGraficaOfertasEmpleo);

        panelContenidoDashboard.add(panelGraficasGrandes);
        panelContenidoDashboard.add(Box.createRigidArea(new Dimension(0, 18)));

        // ---- Fila inferior de tarjetas e indicador de afiliación ----
        JPanel panelFilaInferior = new JPanel(new GridLayout(1, 4, 18, 0));
        panelFilaInferior.setOpaque(false);
        panelFilaInferior.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelFilaInferior.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        panelTasaContratacion = crearTarjetaEstadistica("Tasa de Contratación", "25", "", DATOS_TASA, COLOR_VERDE_GRAF);
        panelEntrevistasProgramadas = crearTarjetaEstadistica("Entrevistas Programadas", "25", "", DATOS_ENTREVISTAS, COLOR_VERDE_GRAF);
        panelTiempoPromedioContratacion = crearTarjetaEstadistica("Tiempo Promedio de Contratación", "25", "", DATOS_TIEMPO, new Color(0xC0, 0x6B, 0x5A));
        panelEstadoAfiliacion = crearTarjetaEstadoAfiliacion();

        panelFilaInferior.add(panelTasaContratacion);
        panelFilaInferior.add(panelEntrevistasProgramadas);
        panelFilaInferior.add(panelTiempoPromedioContratacion);
        panelFilaInferior.add(panelEstadoAfiliacion);

        panelContenidoDashboard.add(panelFilaInferior);

        scrollContenido = new JScrollPane(panelContenidoDashboard);
        scrollContenido.setBorder(null);
        scrollContenido.getVerticalScrollBar().setUnitIncrement(16);
        scrollContenido.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    // Crea una tarjeta estadística estándar con título, valor grande y mini-gráfica
    private JPanel crearTarjetaEstadistica(String titulo, String valor, String subtexto, int[] datos, Color colorGrafica) {
        JPanel tarjeta = new JPanel(new BorderLayout(0, 6));
        tarjeta.setBackground(COLOR_BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 1, true),
            new EmptyBorder(12, 14, 10, 14)
        ));

        JLabel lblTitulo = new JLabel("<html>" + titulo + "</html>");
        lblTitulo.setFont(FUENTE_TARJ_TIT);
        lblTitulo.setForeground(COLOR_TEXTO);

        JPanel panelValor = new JPanel(new BorderLayout());
        panelValor.setOpaque(false);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(FUENTE_TARJ_VAL);
        lblValor.setForeground(COLOR_AZUL_MARINO);

        JPanel miniGrafica = crearMiniGrafica(datos, colorGrafica);

        JPanel filaInferior = new JPanel(new BorderLayout());
        filaInferior.setOpaque(false);
        JLabel lblSub = new JLabel(subtexto);
        lblSub.setFont(FUENTE_TARJ_SUB);
        lblSub.setForeground(colorGrafica);
        filaInferior.add(lblSub, BorderLayout.WEST);

        panelValor.add(lblValor, BorderLayout.WEST);
        panelValor.add(miniGrafica, BorderLayout.EAST);

        tarjeta.add(lblTitulo, BorderLayout.NORTH);
        tarjeta.add(panelValor, BorderLayout.CENTER);
        tarjeta.add(filaInferior, BorderLayout.SOUTH);

        return tarjeta;
    }

    // Crea una pequeña gráfica de línea tipo "sparkline" usada dentro de las tarjetas
    private JPanel crearMiniGrafica(int[] datos, Color color) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setStroke(new BasicStroke(2f));
                g2.setColor(color);

                int ancho = getWidth();
                int alto = getHeight();
                int max = Integer.MIN_VALUE;
                int min = Integer.MAX_VALUE;
                for (int valor : datos) {
                    max = Math.max(max, valor);
                    min = Math.min(min, valor);
                }
                if (max == min) max = min + 1;

                int n = datos.length;
                int[] xs = new int[n];
                int[] ys = new int[n];
                for (int i = 0; i < n; i++) {
                    xs[i] = (int) (i * (ancho - 4) / (double) (n - 1)) + 2;
                    ys[i] = alto - 2 - (int) ((datos[i] - min) * (alto - 6) / (double) (max - min));
                }
                for (int i = 0; i < n - 1; i++) {
                    g2.drawLine(xs[i], ys[i], xs[i + 1], ys[i + 1]);
                }
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(70, 30));
        return panel;
    }

    // Crea un panel de gráfica grande (línea o área doble) para la sección central del dashboard
    private JPanel crearPanelGraficaLinea(String titulo, int[] datos, Color colorPrincipal, boolean conSegundaSerie) {
        JPanel contenedor = new JPanel(new BorderLayout(0, 8));
        contenedor.setBackground(COLOR_BLANCO);
        contenedor.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 1, true),
            new EmptyBorder(14, 16, 14, 16)
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(FUENTE_GRAF_TIT);
        lblTitulo.setForeground(COLOR_AZUL_MARINO);
        contenedor.add(lblTitulo, BorderLayout.NORTH);

        // Segunda serie simulada (morado) sólo para la gráfica de "Ofertas de Empleo"
        int[] segundaSerie = null;
        if (conSegundaSerie) {
            segundaSerie = new int[]{80, 60, 70, 50, 65, 90, 75, 60, 85, 70, 95, 80};
        }
        final int[] serieFinal = segundaSerie;

        String[] meses = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};

        JPanel panelGrafica = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarGraficaArea(g, datos, serieFinal, colorPrincipal, getWidth(), getHeight(), meses, datos.length == 6 ? 6 : 6);
            }
        };
        panelGrafica.setOpaque(false);
        contenedor.add(panelGrafica, BorderLayout.CENTER);
        return contenedor;
    }

    // Dibuja una gráfica de área/línea con ejes simples y etiquetas de mes (Jan - Jun)
    private void dibujarGraficaArea(Graphics g, int[] datos, int[] datosSecundarios, Color color,
                                      int ancho, int alto, String[] etiquetasMeses, int cantidadMeses) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int margenIzq = 35;
        int margenInf = 22;
        int margenSup = 8;
        int margenDer = 10;
        int areaAncho = ancho - margenIzq - margenDer;
        int areaAlto = alto - margenInf - margenSup;

        int max = Integer.MIN_VALUE;
        for (int v : datos) max = Math.max(max, v);
        if (datosSecundarios != null) for (int v : datosSecundarios) max = Math.max(max, v);
        max = (int) Math.ceil(max / 50.0) * 50;
        if (max == 0) max = 50;

        // Ejes y líneas guía horizontales
        g2.setColor(new Color(0xE5, 0xE5, 0xE5));
        for (int i = 0; i <= 4; i++) {
            int y = margenSup + areaAlto - (i * areaAlto / 4);
            g2.drawLine(margenIzq, y, ancho - margenDer, y);
            String etiqueta = String.valueOf((max * i) / 4);
            g2.setColor(COLOR_TEXTO);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 9));
            g2.drawString(etiqueta, 2, y + 4);
            g2.setColor(new Color(0xE5, 0xE5, 0xE5));
        }

        int n = Math.min(datos.length, etiquetasMeses.length);

        // Serie secundaria (si existe), color morado, dibujada primero como fondo
        if (datosSecundarios != null) {
            dibujarSerieRellena(g2, datosSecundarios, n, margenIzq, margenSup, areaAncho, areaAlto, max, COLOR_MORADO, 60);
        }
        // Serie principal
        dibujarSerieRellena(g2, datos, n, margenIzq, margenSup, areaAncho, areaAlto, max, color, 90);

        // Etiquetas de los meses en el eje X
        g2.setColor(COLOR_TEXTO);
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        for (int i = 0; i < n; i++) {
            int x = margenIzq + (int) (i * areaAncho / (double) (n - 1));
            FontMetrics fm = g2.getFontMetrics();
            int textW = fm.stringWidth(etiquetasMeses[i]);
            g2.drawString(etiquetasMeses[i], x - textW / 2, alto - 5);
        }

        g2.dispose();
    }

    // Dibuja una serie de datos como área semitransparente con línea superior marcada
    private void dibujarSerieRellena(Graphics2D g2, int[] datos, int n, int margenIzq, int margenSup,
                                       int areaAncho, int areaAlto, int max, Color color, int alphaRelleno) {
        int[] xs = new int[n];
        int[] ys = new int[n];
        for (int i = 0; i < n; i++) {
            xs[i] = margenIzq + (int) (i * areaAncho / (double) (n - 1));
            ys[i] = margenSup + areaAlto - (int) (datos[i] * areaAlto / (double) max);
        }

        Polygon poligonoRelleno = new Polygon();
        poligonoRelleno.addPoint(xs[0], margenSup + areaAlto);
        for (int i = 0; i < n; i++) poligonoRelleno.addPoint(xs[i], ys[i]);
        poligonoRelleno.addPoint(xs[n - 1], margenSup + areaAlto);

        g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 35));
        g2.fillPolygon(poligonoRelleno);

        g2.setColor(color);
        g2.setStroke(new BasicStroke(2.2f));
        for (int i = 0; i < n - 1; i++) {
            g2.drawLine(xs[i], ys[i], xs[i + 1], ys[i + 1]);
        }
    }

    // Crea la tarjeta especial de "Estado de Afiliación" con la lista de planes contratados
    private JPanel crearTarjetaEstadoAfiliacion() {
        JPanel tarjeta = new JPanel(new BorderLayout(0, 6));
        tarjeta.setBackground(COLOR_BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 1, true),
            new EmptyBorder(10, 12, 10, 12)
        ));

        JLabel lblTitulo = new JLabel("<html>Estado de Afiliación</html>");
        lblTitulo.setFont(FUENTE_TARJ_TIT);
        lblTitulo.setForeground(COLOR_TEXTO);
        tarjeta.add(lblTitulo, BorderLayout.NORTH);

        JPanel listaPlanes = new JPanel();
        listaPlanes.setLayout(new BoxLayout(listaPlanes, BoxLayout.Y_AXIS));
        listaPlanes.setOpaque(false);

        String[] planes = {
            "Plan 24m + IVA / 200 Mbps: Q319,483.5",
            "Plan 24m + IVA / 200 Mbps: Q319,483.5",
            "Plan 24m + IVA / 200 Mbps: Q319,462.5",
            "Plan 26m + IVA / 200 Mbps: Q338,462.5",
            "Plan 26m + IVA / 200 Mbps: Q338,462.5"
        };
        for (String plan : planes) {
            JLabel lblPlan = new JLabel(plan);
            lblPlan.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            lblPlan.setForeground(COLOR_TEXTO);
            listaPlanes.add(lblPlan);
        }

        tarjeta.add(listaPlanes, BorderLayout.CENTER);
        return tarjeta;
    }

    //========================================================
    // ENSAMBLAJE FINAL DE LA VENTANA
    //========================================================
    private void ensamblarVentana() {
        add(panelMenuLateral, BorderLayout.WEST);

        JPanel panelDerechoCompleto = new JPanel(new BorderLayout());
        panelDerechoCompleto.add(panelEncabezado, BorderLayout.NORTH);
        panelDerechoCompleto.add(scrollContenido, BorderLayout.CENTER);

        add(panelDerechoCompleto, BorderLayout.CENTER);
    }

    //========================================================
    // EVENTOS Y FUNCIONES DE APOYO
    //========================================================

    // Maneja el evento de cerrar sesión, solicitando confirmación previa al usuario
    private void manejarCerrarSesion() {
        try {
            int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea cerrar la sesión de " + NOMBRE_EMPRESA_USUARIO + "?",
                "Confirmar cierre de sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            if (opcion == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Sesión cerrada correctamente.",
                    "WorkBridge", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al cerrar la sesión: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Punto de extensión para liberar recursos adicionales si fuese necesario
        }
    }

    // Muestra un mensaje informativo estándar reutilizable en distintas partes de la interfaz
    private void mostrarMensajeInformativo(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    //========================================================
    // MÉTODO PRINCIPAL
    //========================================================
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            DashboardEmpresa ventana = new DashboardEmpresa();
            ventana.setVisible(true);
        });
    }
}