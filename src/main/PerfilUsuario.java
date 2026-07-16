package main;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

/**
 * ============================================================================
 * WORKBRIDGE - PERFIL DE USUARIO
 * ============================================================================
 * Interfaz de perfil de candidato dentro de la plataforma WorkBridge.
 * Replica la pantalla "Mi Perfil" de la imagen de referencia, permitiendo
 * editar foto de perfil, portada, información personal, experiencia,
 * educación, habilidades, idiomas y certificaciones.
 *
 * Colores oficiales (PALETA_DE_COLORES.pdf):
 *      Beige institucional .... #D4CDC5
 *      Azul Acero (celeste) ... #5B88A5
 *      Azul Marino ............ #243A69
 *      Morado / Lavanda ....... #9B73A6
 *
 * Tecnologías: Java 11, Swing y AWT puro (sin librerías externas).
 * ============================================================================
 */
public class PerfilUsuario extends JPanel {

    //========================================================
    // PALETA DE COLORES OFICIAL
    //========================================================
    private final Color COLOR_BEIGE        = new Color(0xD4, 0xCD, 0xC5);
    private final Color COLOR_AZUL_ACERO   = new Color(0x5B, 0x88, 0xA5);
    private final Color COLOR_AZUL_MARINO  = new Color(0x24, 0x3A, 0x69);
    private final Color COLOR_MORADO       = new Color(0x9B, 0x73, 0xA6);
    private final Color COLOR_BLANCO       = Color.WHITE;
    private final Color COLOR_TEXTO        = new Color(0x2B, 0x2B, 0x2B);
    private final Color COLOR_TEXTO_CLARO  = new Color(0xF2, 0xF0, 0xED);
    private final Color COLOR_BORDE        = new Color(0xE2, 0xE2, 0xE2);
    private final Color COLOR_VERDE_EXITO  = new Color(0x4C, 0xAF, 0x7D);

    //========================================================
    // TIPOGRAFÍA
    //========================================================
    private final Font FUENTE_LOGO       = new Font("Segoe UI", Font.BOLD, 24);
    private final Font FUENTE_MENU       = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font FUENTE_TITULO_SUP = new Font("Segoe UI", Font.BOLD, 16);
    private final Font FUENTE_FECHA      = new Font("Segoe UI", Font.PLAIN, 12);
    private final Font FUENTE_NOMBRE     = new Font("Segoe UI", Font.BOLD, 22);
    private final Font FUENTE_SUBTITULO  = new Font("Segoe UI", Font.PLAIN, 13);
    private final Font FUENTE_SECCION    = new Font("Segoe UI", Font.BOLD, 15);
    private final Font FUENTE_TEXTO      = new Font("Segoe UI", Font.PLAIN, 13);
    private final Font FUENTE_PEQUENA    = new Font("Segoe UI", Font.PLAIN, 11);
    private final Font FUENTE_BOTON      = new Font("Segoe UI", Font.BOLD, 12);

    //========================================================
    // DATOS INSTITUCIONALES (Manual_de_marca - WorkBridge)
    //========================================================
    private final String NOMBRE_EMPRESA_SESION = "TechCorp S.A.";

    //========================================================
    // DECLARACIÓN DE COMPONENTES - ESTRUCTURA GENERAL
    //========================================================
    // Panel lateral izquierdo con el logotipo y el menú de navegación
    private JPanel panelMenuLateral;
    // Caché de la imagen del logotipo cargada desde logo.png (evita releerla en cada repintado)
    private Image imagenLogoCache;
    // Indica si ya se intentó cargar la imagen del logo (exista o no) para no reintentarlo cada vez
    private boolean intentoCargaLogoRealizado = false;
    // Panel superior con el título de la sección, búsqueda e íconos
    private JPanel panelEncabezado;
    // Contenedor con scroll para todo el contenido central del perfil
    private JScrollPane scrollContenido;
    // Panel central que agrupa la portada, la información y las secciones del perfil
    private JPanel panelContenidoCentral;
    // Panel derecho con información rápida, vacantes y empresas sugeridas
    private JPanel panelLateralDerecho;

    //========================================================
    // DECLARACIÓN DE COMPONENTES - MENÚ LATERAL
    //========================================================
    private JButton btnInicio;
    private JButton btnMiEmpresa;
    private JButton btnMisVacantes;
    private JButton btnMisPostulaciones;
    private JButton btnMisEntrevistas;
    private JButton btnComunicaciones;
    private JButton btnNotificacionesMenu;
    private JButton btnSistema;
    private JButton btnConfiguracion;
    private JButton[] botonesMenu;
    private int indiceBotonSeleccionado = 0;
    // Panel inferior del menú que muestra la empresa/usuario logueado
    private JPanel panelUsuarioFooter;

    //========================================================
    // DECLARACIÓN DE COMPONENTES - ENCABEZADO SUPERIOR
    //========================================================
    private JLabel lblTituloMiPerfil;
    private JLabel lblFechaActual;
    private JTextField campoBusquedaTrabajos;
    private JButton btnIconoNotificaciones;
    private JButton btnIconoPerfil;

    //========================================================
    // DECLARACIÓN DE COMPONENTES - PORTADA Y FOTO DE PERFIL
    //========================================================
    // Panel que dibuja la imagen (o degradado) de portada del perfil
    private JPanel panelPortada;
    // Imagen de portada seleccionada por el usuario (null si se usa el degradado por defecto)
    private Image imagenPortada;
    // Botón para editar/cambiar la imagen de portada
    private JButton btnEditarPortada;
    // Panel circular que muestra la foto de perfil del usuario
    private JPanel panelFotoPerfil;
    // Imagen de perfil seleccionada por el usuario (null si se usa el ícono por defecto)
    private Image imagenPerfil;
    // Botón pequeño tipo cámara para cambiar la foto de perfil
    private JButton btnCambiarFoto;

    //========================================================
    // DECLARACIÓN DE COMPONENTES - INFORMACIÓN PERSONAL
    //========================================================
    // Etiqueta con el nombre completo del usuario
    private JLabel lblNombreUsuario;
    // Etiqueta con el título profesional / carrera del usuario
    private JLabel lblTituloProfesional;
    // Etiqueta con la ubicación del usuario
    private JLabel lblUbicacion;
    // Etiqueta (enlace) con el correo electrónico del usuario
    private JLabel lblCorreo;
    // Etiqueta con el número de teléfono del usuario
    private JLabel lblTelefono;
    // Botón que activa el modo de edición del perfil
    private JButton btnEditarPerfil;
    // Botón que descarga el currículum del usuario (simulado)
    private JButton btnDescargarCV;
    // Botón para contactar al usuario
    private JButton btnContactar;

    // Variables de datos editables del perfil (modelo simple en memoria)
    private String datoNombre = "Nombre Usuario";
    private String datoTitulo = "Estudiante de Bachillerato Industrial con Especialidad en Perito en Informática";
    private String datoUbicacion = "Guatemala, Guatemala";
    private String datoCorreo = "usuario12@gmail.com";
    private String datoTelefono = "+502 XXXX-XXXX";
    private String datoAcercaDe = "Estudiante con interés en programación, bases de datos y desarrollo de "
            + "software. Responsable, creativo y con capacidad de trabajo en equipo.";

    //========================================================
    // DECLARACIÓN DE COMPONENTES - SECCIÓN "ACERCA DE MÍ"
    //========================================================
    private JPanel panelAcercaDeMi;
    private JLabel lblAcercaDeMiTexto;
    private JButton btnEditarAcercaDeMi;

    //========================================================
    // DECLARACIÓN DE COMPONENTES - EXPERIENCIA, EDUCACIÓN, HABILIDADES
    //========================================================
    // Panel que contiene la lista de experiencia laboral
    private JPanel panelExperienciaLaboral;
    // Panel que contiene la información de educación
    private JPanel panelEducacion;
    // Panel que contiene las "chips" de habilidades
    private JPanel panelHabilidades;
    // Panel que contiene las certificaciones obtenidas
    private JPanel panelCertificaciones;
    // Panel que contiene los idiomas y su nivel
    private JPanel panelIdiomas;

    // Listas en memoria que representan los datos editables de cada sección
    private List<String> listaExperiencia = new ArrayList<>(List.of(
        "Auxiliar de Soporte Técnico", "Mantenimiento de equipos", "Instalación de software", "Atención a usuarios"));
    private List<String> listaEducacion = new ArrayList<>(List.of(
        "Educación", "Instituto Emiliani Somascos", "Bachillerato en Ciencias y Letras con Orientación en Computación", "2024 - 2026"));
    private List<String> listaHabilidades = new ArrayList<>(List.of(
        "Java", "Python", "MySQL", "Microsoft Office", "Trabajo en equipo", "Comunicación Efectiva"));
    private List<String> listaCertificaciones = new ArrayList<>(List.of(
        "Introducción a Programación", "Bases de Datos", "Ofimática"));
    private List<String> listaIdiomas = new ArrayList<>(List.of(
        "Español - Nativo", "Inglés - Intermedio"));

    //========================================================
    // DECLARACIÓN DE COMPONENTES - PANEL DERECHO
    //========================================================
    private JLabel lblPorcentajePerfil;
    private JProgressBar barraCompletado;
    private JLabel lblUltimaActualizacion;

    // Bandera que indica si el perfil se encuentra actualmente en modo edición
    private boolean modoEdicionActivo = false;

    //========================================================
    // CONSTRUCTOR
    //========================================================
    public PerfilUsuario() {
        configurarVentana();
        construirMenuLateral();
        construirEncabezado();
        construirContenidoCentral();
        construirPanelLateralDerecho();
        ensamblarVentana();
        seleccionarBotonMenu(0);
    }

    //========================================================
    // CONFIGURACIÓN GENERAL DE LA VENTANA
    //========================================================
    private void configurarVentana() {
        setLayout(new BorderLayout());
        setBackground(COLOR_BLANCO);
        setOpaque(true);
        setPreferredSize(new Dimension(1150, 680));
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

        JPanel panelLogo = crearPanelLogotipo();
        panelLogo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelMenuLateral.add(panelLogo);
        panelMenuLateral.add(Box.createRigidArea(new Dimension(0, 20)));

        btnInicio              = crearBotonMenu("Inicio", '\u2302');
        btnMiEmpresa           = crearBotonMenu("Mi empresa", '\u2302');
        btnMisVacantes         = crearBotonMenu("Mis Vacantes", '\u2315');
        btnMisPostulaciones    = crearBotonMenu("Mis Postulaciones", '\u263A');
        btnMisEntrevistas      = crearBotonMenu("Mis Entrevistas", '\u2696');
        btnComunicaciones      = crearBotonMenu("Comunicaciones", '\u2709');
        btnNotificacionesMenu  = crearBotonMenu("Notificaciones", '\u266B');
        btnSistema             = crearBotonMenu("Sistema", '\u2637');
        btnConfiguracion       = crearBotonMenu("Cofiguracion", '\u2699');

        botonesMenu = new JButton[]{
            btnInicio, btnMiEmpresa, btnMisVacantes, btnMisPostulaciones,
            btnMisEntrevistas, btnComunicaciones, btnNotificacionesMenu, btnSistema, btnConfiguracion
        };

        for (int i = 0; i < botonesMenu.length; i++) {
            final int indice = i;
            botonesMenu[i].addActionListener(e -> seleccionarBotonMenu(indice));
            botonesMenu[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            panelMenuLateral.add(botonesMenu[i]);
        }

        panelMenuLateral.add(Box.createVerticalGlue());

        panelUsuarioFooter = construirPanelUsuarioFooter();
        panelUsuarioFooter.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelMenuLateral.add(panelUsuarioFooter);
    }

    // Carga la imagen logo.png ubicada junto a las clases del proyecto (classpath raíz).
    // Si no existe, devuelve null y la interfaz usa el ícono vectorial de respaldo.
    private Image cargarImagenLogo() {
        if (intentoCargaLogoRealizado) {
            return imagenLogoCache;
        }
        intentoCargaLogoRealizado = true;
        try {
            java.net.URL recurso = getClass().getResource("/logo.png");
            if (recurso != null) {
                imagenLogoCache = new ImageIcon(recurso).getImage();
            }
        } catch (Exception ex) {
            imagenLogoCache = null;
        }
        return imagenLogoCache;
    }

    // Construye el bloque visual del logotipo "WorkBridge"
    private JPanel crearPanelLogotipo() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_AZUL_MARINO);
        panel.setBorder(new EmptyBorder(10, 20, 10, 10));
        panel.setMaximumSize(new Dimension(235, 60));

        // Ícono del logotipo: intenta cargar logo.png real; si no existe, dibuja el ícono vectorial de respaldo
        JPanel iconoMaletin = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Image logo = cargarImagenLogo();
                if (logo != null) {
                    g2.drawImage(logo, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g2.setColor(COLOR_MORADO);
                    g2.fillRoundRect(4, 12, 30, 20, 6, 6);
                    g2.setColor(COLOR_AZUL_ACERO);
                    g2.fillRoundRect(12, 6, 14, 10, 4, 4);
                    g2.setColor(COLOR_AZUL_MARINO);
                    g2.fillRect(4, 20, 30, 3);
                }
                g2.dispose();
            }
        };
        iconoMaletin.setOpaque(false);
        iconoMaletin.setMaximumSize(new Dimension(38, 32));

        JLabel lblNombreLogo = new JLabel("Work Bridge");
        lblNombreLogo.setFont(FUENTE_LOGO);
        lblNombreLogo.setForeground(COLOR_BLANCO);

        JPanel fila = new JPanel();
        fila.setLayout(new BoxLayout(fila, BoxLayout.X_AXIS));
        fila.setOpaque(false);
        fila.add(iconoMaletin);
        fila.add(Box.createRigidArea(new Dimension(6, 0)));
        fila.add(lblNombreLogo);
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(fila);
        return panel;
    }

    // Crea un botón de menú lateral estilizado con efecto hover y selección
    private JButton crearBotonMenu(String texto, char icono) {
        JButton boton = new JButton("  " + icono + "    " + texto);
        boton.setFont(FUENTE_MENU);
        boton.setForeground(COLOR_TEXTO_CLARO);
        boton.setBackground(COLOR_AZUL_MARINO);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setContentAreaFilled(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setMaximumSize(new Dimension(235, 36));
        boton.setPreferredSize(new Dimension(235, 36));
        boton.setBorder(new EmptyBorder(7, 18, 7, 8));

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!boton.equals(obtenerBotonSeleccionado())) {
                    boton.setBackground(COLOR_AZUL_ACERO);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!boton.equals(obtenerBotonSeleccionado())) {
                    boton.setBackground(COLOR_AZUL_MARINO);
                }
            }
        });
        return boton;
    }

    private JButton obtenerBotonSeleccionado() {
        if (botonesMenu == null || indiceBotonSeleccionado < 0 || indiceBotonSeleccionado >= botonesMenu.length) {
            return null;
        }
        return botonesMenu[indiceBotonSeleccionado];
    }

    // Marca visualmente el botón seleccionado del menú lateral
    private void seleccionarBotonMenu(int indice) {
        indiceBotonSeleccionado = indice;
        for (int i = 0; i < botonesMenu.length; i++) {
            botonesMenu[i].setBackground(i == indice ? COLOR_MORADO : COLOR_AZUL_MARINO);
        }
    }

    // Construye el panel inferior del menú con la empresa/usuario logueado
    private JPanel construirPanelUsuarioFooter() {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(new Color(0x1C, 0x2E, 0x54));
        panel.setMaximumSize(new Dimension(235, 55));
        panel.setBorder(new EmptyBorder(8, 14, 8, 14));

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

        JLabel lblNombreEmpresaFooter = new JLabel(NOMBRE_EMPRESA_SESION);
        lblNombreEmpresaFooter.setFont(FUENTE_MENU);
        lblNombreEmpresaFooter.setForeground(COLOR_BLANCO);

        // Botón de salida rápida (ícono) al final del panel inferior
        JLabel lblSalida = new JLabel("\u21AA");
        lblSalida.setForeground(COLOR_BLANCO);
        lblSalida.setFont(new Font("Segoe UI", Font.BOLD, 16));

        panel.add(avatar, BorderLayout.WEST);
        panel.add(lblNombreEmpresaFooter, BorderLayout.CENTER);
        panel.add(lblSalida, BorderLayout.EAST);
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

        JPanel panelTituloFecha = new JPanel();
        panelTituloFecha.setOpaque(false);
        panelTituloFecha.setLayout(new BoxLayout(panelTituloFecha, BoxLayout.Y_AXIS));

        lblTituloMiPerfil = new JLabel("Mi Perfil");
        lblTituloMiPerfil.setFont(FUENTE_TITULO_SUP);
        lblTituloMiPerfil.setForeground(COLOR_AZUL_MARINO);

        lblFechaActual = new JLabel(generarFechaActualEnEspanol());
        lblFechaActual.setFont(FUENTE_FECHA);
        lblFechaActual.setForeground(COLOR_TEXTO);

        panelTituloFecha.add(lblTituloMiPerfil);
        panelTituloFecha.add(lblFechaActual);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setOpaque(false);
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.X_AXIS));

        campoBusquedaTrabajos = new JTextField(18);
        campoBusquedaTrabajos.setFont(FUENTE_TEXTO);
        campoBusquedaTrabajos.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 1, true), new EmptyBorder(5, 10, 5, 10)));
        campoBusquedaTrabajos.setToolTipText("Buscar Trabajos");
        campoBusquedaTrabajos.addActionListener(e -> manejarBusquedaTrabajos());

        JButton btnBuscar = new JButton("\uD83D\uDD0D");
        btnBuscar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(e -> manejarBusquedaTrabajos());

        btnIconoNotificaciones = crearBotonIcono("\uD83D\uDD14", "Ver notificaciones");
        btnIconoPerfil = crearBotonIcono("\uD83D\uDC64", "Mi perfil");
        btnIconoNotificaciones.addActionListener(e ->
            mostrarMensajeInformativo("Notificaciones", "No tienes notificaciones nuevas por el momento."));

        panelDerecho.add(btnBuscar);
        panelDerecho.add(campoBusquedaTrabajos);
        panelDerecho.add(Box.createRigidArea(new Dimension(15, 0)));
        panelDerecho.add(btnIconoNotificaciones);
        panelDerecho.add(Box.createRigidArea(new Dimension(8, 0)));
        panelDerecho.add(btnIconoPerfil);

        panelEncabezado.add(panelTituloFecha, BorderLayout.WEST);
        panelEncabezado.add(panelDerecho, BorderLayout.EAST);
    }

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

    private String generarFechaActualEnEspanol() {
        SimpleDateFormat formato = new SimpleDateFormat("EEEE, d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String fecha = formato.format(new Date());
        return fecha.substring(0, 1).toUpperCase() + fecha.substring(1);
    }

    // Maneja la búsqueda de trabajos desde el campo de búsqueda del encabezado
    private void manejarBusquedaTrabajos() {
        String texto = campoBusquedaTrabajos.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe una palabra clave para buscar vacantes.",
                "Búsqueda vacía", JOptionPane.WARNING_MESSAGE);
            return;
        }
        mostrarMensajeInformativo("Buscar Trabajos", "Buscando vacantes relacionadas con: \"" + texto + "\"");
    }

    //========================================================
    // CREACIÓN DEL CONTENIDO CENTRAL DEL PERFIL
    //========================================================
    private void construirContenidoCentral() {
        panelContenidoCentral = new JPanel();
        panelContenidoCentral.setLayout(new BoxLayout(panelContenidoCentral, BoxLayout.Y_AXIS));
        panelContenidoCentral.setBackground(COLOR_BLANCO);
        panelContenidoCentral.setBorder(new EmptyBorder(20, 25, 25, 15));

        panelContenidoCentral.add(construirTarjetaPortadaYDatos());
        panelContenidoCentral.add(Box.createRigidArea(new Dimension(0, 16)));
        panelContenidoCentral.add(construirSeccionAcercaDeMi());
        panelContenidoCentral.add(Box.createRigidArea(new Dimension(0, 16)));
        panelContenidoCentral.add(construirSeccionExperienciaYEducacion());
        panelContenidoCentral.add(Box.createRigidArea(new Dimension(0, 16)));
        panelContenidoCentral.add(construirSeccionHabilidadesYCertificaciones());
        panelContenidoCentral.add(Box.createRigidArea(new Dimension(0, 16)));
        panelContenidoCentral.add(construirSeccionIdiomas());
    }

    // ---- Tarjeta superior con portada, foto de perfil, nombre y botones de acción ----
    private JPanel construirTarjetaPortadaYDatos() {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(COLOR_BLANCO);
        tarjeta.setBorder(new LineBorder(COLOR_BORDE, 1, true));
        tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 330));

        // ---- Portada ----
        JLayeredPane capaPortada = new JLayeredPane();
        capaPortada.setPreferredSize(new Dimension(100, 150));
        capaPortada.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        panelPortada = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (imagenPortada != null) {
                    g2.drawImage(imagenPortada, 0, 0, getWidth(), getHeight(), this);
                } else {
                    GradientPaint degradado = new GradientPaint(
                        0, 0, COLOR_AZUL_MARINO, getWidth(), getHeight(), COLOR_AZUL_ACERO);
                    g2.setPaint(degradado);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
                g2.dispose();
            }
        };
        panelPortada.setBounds(0, 0, 1200, 150);

        btnEditarPortada = new JButton("\uD83D\uDCF7 Editar Portada");
        btnEditarPortada.setFont(FUENTE_BOTON);
        btnEditarPortada.setBackground(COLOR_BLANCO);
        btnEditarPortada.setForeground(COLOR_AZUL_MARINO);
        btnEditarPortada.setFocusPainted(false);
        btnEditarPortada.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEditarPortada.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 1, true), new EmptyBorder(6, 12, 6, 12)));
        btnEditarPortada.setBounds(990, 12, 170, 32);
        btnEditarPortada.addActionListener(e -> manejarSeleccionImagen(true));

        capaPortada.add(panelPortada, Integer.valueOf(0));
        capaPortada.add(btnEditarPortada, Integer.valueOf(1));

        // Hace que portada y botón se redimensionen junto con la ventana
        capaPortada.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int ancho = capaPortada.getWidth();
                panelPortada.setBounds(0, 0, ancho, 150);
                btnEditarPortada.setBounds(Math.max(10, ancho - 190), 12, 170, 32);
            }
        });

        // ---- Foto de perfil circular superpuesta a la portada ----
        panelFotoPerfil = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Ellipse2D.Double circulo = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
                g2.setClip(circulo);
                if (imagenPerfil != null) {
                    g2.drawImage(imagenPerfil, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g2.setColor(COLOR_BLANCO);
                    g2.fillOval(0, 0, getWidth(), getHeight());
                    g2.setColor(COLOR_AZUL_MARINO);
                    g2.fillOval(getWidth() / 3, getHeight() / 5, getWidth() / 3, getWidth() / 3);
                    g2.fillOval(getWidth() / 6, (int) (getHeight() * 0.55), (int) (getWidth() * 0.66), (int) (getHeight() * 0.5));
                }
                g2.setClip(null);
                g2.setColor(COLOR_BLANCO);
                g2.setStroke(new BasicStroke(4f));
                g2.drawOval(2, 2, getWidth() - 4, getHeight() - 4);
                g2.dispose();
            }
        };
        panelFotoPerfil.setOpaque(false);
        panelFotoPerfil.setBounds(30, 105, 90, 90);

        btnCambiarFoto = new JButton("\uD83D\uDCF7");
        btnCambiarFoto.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        btnCambiarFoto.setBackground(COLOR_BLANCO);
        btnCambiarFoto.setBorder(new LineBorder(COLOR_BORDE, 1, true));
        btnCambiarFoto.setFocusPainted(false);
        btnCambiarFoto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCambiarFoto.setBounds(95, 175, 26, 26);
        btnCambiarFoto.setToolTipText("Cambiar foto de perfil");
        btnCambiarFoto.addActionListener(e -> manejarSeleccionImagen(false));

        capaPortada.add(panelFotoPerfil, Integer.valueOf(2));
        capaPortada.add(btnCambiarFoto, Integer.valueOf(3));

        tarjeta.add(capaPortada);

        // ---- Bloque de información personal debajo de la portada ----
        JPanel panelInfoPersonal = new JPanel();
        panelInfoPersonal.setLayout(new BoxLayout(panelInfoPersonal, BoxLayout.Y_AXIS));
        panelInfoPersonal.setOpaque(false);
        panelInfoPersonal.setBorder(new EmptyBorder(45, 30, 16, 30));

        lblNombreUsuario = new JLabel(datoNombre);
        lblNombreUsuario.setFont(FUENTE_NOMBRE);
        lblNombreUsuario.setForeground(COLOR_TEXTO);

        lblTituloProfesional = new JLabel(datoTitulo);
        lblTituloProfesional.setFont(FUENTE_SUBTITULO);
        lblTituloProfesional.setForeground(COLOR_AZUL_ACERO);

        JPanel filaContacto = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 4));
        filaContacto.setOpaque(false);

        lblUbicacion = new JLabel("\uD83D\uDCCD " + datoUbicacion);
        lblUbicacion.setFont(FUENTE_PEQUENA);

        lblCorreo = new JLabel("\u2709 " + datoCorreo);
        lblCorreo.setFont(FUENTE_PEQUENA);
        lblCorreo.setForeground(COLOR_AZUL_ACERO);

        lblTelefono = new JLabel("\u260E " + datoTelefono);
        lblTelefono.setFont(FUENTE_PEQUENA);

        filaContacto.add(lblUbicacion);
        filaContacto.add(lblCorreo);
        filaContacto.add(lblTelefono);

        JPanel filaBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filaBotones.setOpaque(false);

        btnEditarPerfil = crearBotonPrimario("Editar Perfil");
        btnDescargarCV = crearBotonPrimario("Descargar CV");
        btnContactar = crearBotonPrimario("Contactar");

        btnEditarPerfil.addActionListener(e -> manejarEditarPerfil());
        btnDescargarCV.addActionListener(e -> manejarDescargarCV());
        btnContactar.addActionListener(e -> manejarContactar());

        filaBotones.add(btnEditarPerfil);
        filaBotones.add(btnDescargarCV);
        filaBotones.add(btnContactar);

        panelInfoPersonal.add(lblNombreUsuario);
        panelInfoPersonal.add(Box.createRigidArea(new Dimension(0, 4)));
        panelInfoPersonal.add(lblTituloProfesional);
        panelInfoPersonal.add(Box.createRigidArea(new Dimension(0, 8)));
        panelInfoPersonal.add(filaContacto);
        panelInfoPersonal.add(filaBotones);

        tarjeta.add(panelInfoPersonal);
        return tarjeta;
    }

    // Crea un botón estilizado con el color institucional (azul marino) para acciones primarias
    private JButton crearBotonPrimario(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(COLOR_AZUL_MARINO);
        boton.setForeground(COLOR_BLANCO);
        boton.setFocusPainted(false);
        boton.setOpaque(true);              // Obliga al botón a pintar su propio fondo
        boton.setContentAreaFilled(true);   // Necesario en Windows L&F para respetar setBackground
        boton.setBorderPainted(false);      // Oculta el borde nativo gris del sistema
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(new EmptyBorder(8, 16, 8, 16));
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(COLOR_AZUL_ACERO);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(COLOR_AZUL_MARINO);
            }
        });
        return boton;
    }

    // ---- Sección "Acerca de mí" ----
    private JPanel construirSeccionAcercaDeMi() {
        panelAcercaDeMi = crearTarjetaSeccionBase("Acerca de mí");
        panelAcercaDeMi.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        lblAcercaDeMiTexto = new JLabel("<html><div style='width:850px;'>" + datoAcercaDe + "</div></html>");
        lblAcercaDeMiTexto.setFont(FUENTE_TEXTO);
        lblAcercaDeMiTexto.setForeground(COLOR_TEXTO);
        lblAcercaDeMiTexto.setBorder(new EmptyBorder(8, 0, 0, 0));

        btnEditarAcercaDeMi = crearBotonEditarPequeno();
        btnEditarAcercaDeMi.addActionListener(e -> manejarEditarAcercaDeMi());

        JPanel encabezadoSeccion = (JPanel) panelAcercaDeMi.getComponent(0);
        encabezadoSeccion.add(btnEditarAcercaDeMi, BorderLayout.EAST);

        panelAcercaDeMi.add(lblAcercaDeMiTexto, BorderLayout.CENTER);
        return panelAcercaDeMi;
    }

    // Crea la base reutilizable de una tarjeta de sección (título + borde estándar)
    private JPanel crearTarjetaSeccionBase(String titulo) {
        JPanel tarjeta = new JPanel(new BorderLayout(0, 4));
        tarjeta.setBackground(COLOR_BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 1, true), new EmptyBorder(14, 18, 14, 18)));
        tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setOpaque(false);
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(FUENTE_SECCION);
        lblTitulo.setForeground(COLOR_AZUL_MARINO);
        encabezado.add(lblTitulo, BorderLayout.WEST);

        tarjeta.add(encabezado, BorderLayout.NORTH);
        return tarjeta;
    }

    // Crea un botón pequeño de "lápiz" reutilizado para editar secciones
    private JButton crearBotonEditarPequeno() {
        JButton boton = new JButton("\u270E Editar");
        boton.setFont(FUENTE_PEQUENA);
        boton.setForeground(COLOR_AZUL_ACERO);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    // ---- Sección combinada: Experiencia Laboral + Educación (dos columnas) ----
    private JPanel construirSeccionExperienciaYEducacion() {
        JPanel contenedor = new JPanel(new GridLayout(1, 2, 16, 0));
        contenedor.setOpaque(false);
        contenedor.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenedor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 230));

        panelExperienciaLaboral = crearTarjetaSeccionBase("Experiencia Laboral");
        JPanel listaExp = new JPanel();
        listaExp.setLayout(new BoxLayout(listaExp, BoxLayout.Y_AXIS));
        listaExp.setOpaque(false);
        actualizarListaTextoEnPanel(listaExp, listaExperiencia);
        panelExperienciaLaboral.add(listaExp, BorderLayout.CENTER);

        JPanel panelBotonesExp = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        panelBotonesExp.setOpaque(false);
        JButton btnAgregarExp = crearBotonEditarPequeno();
        btnAgregarExp.setText("\u2795 Agregar");
        JButton btnEliminarExp = crearBotonEditarPequeno();
        btnEliminarExp.setText("\u2796 Eliminar última");
        btnAgregarExp.addActionListener(e -> manejarAgregarElemento(listaExperiencia, listaExp, "Nueva experiencia laboral"));
        btnEliminarExp.addActionListener(e -> manejarEliminarUltimoElemento(listaExperiencia, listaExp));
        panelBotonesExp.add(btnAgregarExp);
        panelBotonesExp.add(btnEliminarExp);
        panelExperienciaLaboral.add(panelBotonesExp, BorderLayout.SOUTH);

        panelEducacion = crearTarjetaSeccionBase("Educación");
        JPanel listaEdu = new JPanel();
        listaEdu.setLayout(new BoxLayout(listaEdu, BoxLayout.Y_AXIS));
        listaEdu.setOpaque(false);
        actualizarListaTextoEnPanel(listaEdu, listaEducacion);
        panelEducacion.add(listaEdu, BorderLayout.CENTER);

        JButton btnEditarEducacion = crearBotonEditarPequeno();
        ((JPanel) panelEducacion.getComponent(0)).add(btnEditarEducacion, BorderLayout.EAST);
        btnEditarEducacion.addActionListener(e -> manejarEditarListaCompleta("Educación", listaEducacion, listaEdu));

        contenedor.add(panelExperienciaLaboral);
        contenedor.add(panelEducacion);
        return contenedor;
    }

    // ---- Sección combinada: Habilidades + Certificaciones (dos columnas) ----
    private JPanel construirSeccionHabilidadesYCertificaciones() {
        JPanel contenedor = new JPanel(new GridLayout(1, 2, 16, 0));
        contenedor.setOpaque(false);
        contenedor.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenedor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));

        panelHabilidades = crearTarjetaSeccionBase("Habilidades");
        JPanel chipsHabilidades = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
        chipsHabilidades.setOpaque(false);
        actualizarChipsEnPanel(chipsHabilidades, listaHabilidades, COLOR_AZUL_ACERO);
        panelHabilidades.add(chipsHabilidades, BorderLayout.CENTER);

        JButton btnEditarHabilidades = crearBotonEditarPequeno();
        ((JPanel) panelHabilidades.getComponent(0)).add(btnEditarHabilidades, BorderLayout.EAST);
        btnEditarHabilidades.addActionListener(e ->
            manejarEditarListaComoChips("Habilidades", listaHabilidades, chipsHabilidades, COLOR_AZUL_ACERO));

        panelCertificaciones = crearTarjetaSeccionBase("Certificaciones");
        JPanel listaCert = new JPanel();
        listaCert.setLayout(new BoxLayout(listaCert, BoxLayout.Y_AXIS));
        listaCert.setOpaque(false);
        actualizarListaTextoEnPanel(listaCert, listaCertificaciones);
        panelCertificaciones.add(listaCert, BorderLayout.CENTER);

        JButton btnEditarCert = crearBotonEditarPequeno();
        ((JPanel) panelCertificaciones.getComponent(0)).add(btnEditarCert, BorderLayout.EAST);
        btnEditarCert.addActionListener(e -> manejarEditarListaCompleta("Certificaciones", listaCertificaciones, listaCert));

        contenedor.add(panelHabilidades);
        contenedor.add(panelCertificaciones);
        return contenedor;
    }

    // ---- Sección de Idiomas ----
    private JPanel construirSeccionIdiomas() {
        panelIdiomas = crearTarjetaSeccionBase("Idiomas");
        panelIdiomas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        JPanel filaIdiomas = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 8));
        filaIdiomas.setOpaque(false);
        actualizarListaHorizontalEnPanel(filaIdiomas, listaIdiomas);
        panelIdiomas.add(filaIdiomas, BorderLayout.CENTER);

        JButton btnEditarIdiomas = crearBotonEditarPequeno();
        ((JPanel) panelIdiomas.getComponent(0)).add(btnEditarIdiomas, BorderLayout.EAST);
        btnEditarIdiomas.addActionListener(e -> manejarEditarListaHorizontal("Idiomas", listaIdiomas, filaIdiomas));

        return panelIdiomas;
    }

    // Vuelca una lista de cadenas como etiquetas verticales (con viñeta) dentro de un panel
    private void actualizarListaTextoEnPanel(JPanel panelLista, List<String> datos) {
        panelLista.removeAll();
        for (String item : datos) {
            JLabel lbl = new JLabel("• " + item);
            lbl.setFont(FUENTE_TEXTO);
            lbl.setForeground(COLOR_TEXTO);
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelLista.add(lbl);
        }
        panelLista.revalidate();
        panelLista.repaint();
    }

    // Vuelca una lista de cadenas en forma horizontal separada por espacios (usado en Idiomas)
    private void actualizarListaHorizontalEnPanel(JPanel panel, List<String> datos) {
        panel.removeAll();
        for (String item : datos) {
            JLabel lbl = new JLabel(item);
            lbl.setFont(FUENTE_TEXTO);
            lbl.setForeground(COLOR_TEXTO);
            panel.add(lbl);
        }
        panel.revalidate();
        panel.repaint();
    }

    // Vuelca una lista de cadenas como "chips" redondeadas de colores (usado en Habilidades)
    private void actualizarChipsEnPanel(JPanel panel, List<String> datos, Color colorChip) {
        panel.removeAll();
        for (String item : datos) {
            JLabel chip = new JLabel(item);
            chip.setOpaque(true);
            chip.setBackground(new Color(colorChip.getRed(), colorChip.getGreen(), colorChip.getBlue(), 40));
            chip.setForeground(COLOR_AZUL_MARINO);
            chip.setFont(FUENTE_PEQUENA);
            chip.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(colorChip, 1, true), new EmptyBorder(4, 10, 4, 10)));
            panel.add(chip);
        }
        panel.revalidate();
        panel.repaint();
    }

    //========================================================
    // CREACIÓN DEL PANEL LATERAL DERECHO
    //========================================================
    private void construirPanelLateralDerecho() {
        panelLateralDerecho = new JPanel();
        panelLateralDerecho.setLayout(new BoxLayout(panelLateralDerecho, BoxLayout.Y_AXIS));
        panelLateralDerecho.setBackground(COLOR_BLANCO);
        panelLateralDerecho.setPreferredSize(new Dimension(260, 0));
        panelLateralDerecho.setBorder(new EmptyBorder(20, 10, 25, 25));

        panelLateralDerecho.add(construirTarjetaInformacionRapida());
        panelLateralDerecho.add(Box.createRigidArea(new Dimension(0, 16)));
        panelLateralDerecho.add(construirTarjetaVacantesRecomendadas());
        panelLateralDerecho.add(Box.createRigidArea(new Dimension(0, 16)));
        panelLateralDerecho.add(construirTarjetaEmpresasSugeridas());
    }

    // ---- Tarjeta: Información rápida (porcentaje de perfil, CV, última actualización) ----
    private JPanel construirTarjetaInformacionRapida() {
        JPanel tarjeta = crearTarjetaSeccionBase("Información Rápida");
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);
        contenido.setBorder(new EmptyBorder(6, 0, 0, 0));

        lblPorcentajePerfil = new JLabel("Perfil completado 85%");
        lblPorcentajePerfil.setFont(FUENTE_PEQUENA);
        lblPorcentajePerfil.setAlignmentX(Component.LEFT_ALIGNMENT);

        barraCompletado = new JProgressBar(0, 100);
        barraCompletado.setValue(85);
        barraCompletado.setForeground(COLOR_AZUL_ACERO);
        barraCompletado.setBackground(COLOR_BEIGE);
        barraCompletado.setBorderPainted(false);
        barraCompletado.setPreferredSize(new Dimension(100, 10));
        barraCompletado.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));
        barraCompletado.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblCvCargado = new JLabel("CV cargado: sí");
        lblCvCargado.setFont(FUENTE_PEQUENA);
        lblCvCargado.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblUltimaActualizacion = new JLabel("Última actualización: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        lblUltimaActualizacion.setFont(FUENTE_PEQUENA);
        lblUltimaActualizacion.setForeground(COLOR_AZUL_ACERO);
        lblUltimaActualizacion.setAlignmentX(Component.LEFT_ALIGNMENT);

        contenido.add(lblPorcentajePerfil);
        contenido.add(Box.createRigidArea(new Dimension(0, 6)));
        contenido.add(barraCompletado);
        contenido.add(Box.createRigidArea(new Dimension(0, 10)));
        contenido.add(lblCvCargado);
        contenido.add(Box.createRigidArea(new Dimension(0, 6)));
        contenido.add(lblUltimaActualizacion);

        tarjeta.add(contenido, BorderLayout.CENTER);
        return tarjeta;
    }

    // ---- Tarjeta: Vacantes recomendadas ----
    private JPanel construirTarjetaVacantesRecomendadas() {
        JPanel tarjeta = crearTarjetaSeccionBase("Vacante recomendada");
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);
        contenido.setBorder(new EmptyBorder(6, 0, 0, 0));

        String[][] vacantes = {
            {"Desarrollador Junior", "TechCorp SA. - Tiempo completo"},
            {"Soporte Tecnico", "InnovaTech - Medio tiempo"},
            {"Auxiliar de Sistemas", "Soluciones Digitales GT. - Tiempo completo"}
        };

        for (String[] vacante : vacantes) {
            JLabel lblPuesto = new JLabel(vacante[0]);
            lblPuesto.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lblPuesto.setForeground(COLOR_AZUL_ACERO);
            lblPuesto.setAlignmentX(Component.LEFT_ALIGNMENT);
            lblPuesto.setCursor(new Cursor(Cursor.HAND_CURSOR));
            lblPuesto.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mostrarMensajeInformativo("Vacante", "Abriendo detalles de: " + vacante[0]);
                }
            });
            JLabel lblEmpresa = new JLabel(vacante[1]);
            lblEmpresa.setFont(FUENTE_PEQUENA);
            lblEmpresa.setAlignmentX(Component.LEFT_ALIGNMENT);

            contenido.add(lblPuesto);
            contenido.add(lblEmpresa);
            contenido.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        JLabel lblVerTodas = new JLabel("Ver todas las vacantes");
        lblVerTodas.setFont(FUENTE_PEQUENA);
        lblVerTodas.setForeground(COLOR_AZUL_MARINO);
        lblVerTodas.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblVerTodas.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblVerTodas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarMensajeInformativo("Vacantes", "Mostrando todas las vacantes disponibles.");
            }
        });
        contenido.add(lblVerTodas);

        tarjeta.add(contenido, BorderLayout.CENTER);
        return tarjeta;
    }

    // ---- Tarjeta: Empresas sugeridas ----
    private JPanel construirTarjetaEmpresasSugeridas() {
        JPanel tarjeta = crearTarjetaSeccionBase("Empresas Sugeridas");
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);
        contenido.setBorder(new EmptyBorder(6, 0, 0, 0));

        String[][] empresas = {
            {"TechCorp S.A.", "Tecnología, Información y Servicios"},
            {"InnovaTech", "Desarrollo de Software"},
            {"Soluciones Digitales GT", "Servicios de TI y Consultoría"}
        };

        for (String[] empresa : empresas) {
            JLabel lblEmpresa = new JLabel(empresa[0]);
            lblEmpresa.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lblEmpresa.setForeground(COLOR_AZUL_ACERO);
            lblEmpresa.setAlignmentX(Component.LEFT_ALIGNMENT);
            lblEmpresa.setCursor(new Cursor(Cursor.HAND_CURSOR));
            JLabel lblRubro = new JLabel("<html><div style='width:200px;'>" + empresa[1] + "</div></html>");
            lblRubro.setFont(FUENTE_PEQUENA);
            lblRubro.setAlignmentX(Component.LEFT_ALIGNMENT);

            contenido.add(lblEmpresa);
            contenido.add(lblRubro);
            contenido.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        JLabel lblVerMas = new JLabel("Ver más empresas");
        lblVerMas.setFont(FUENTE_PEQUENA);
        lblVerMas.setForeground(COLOR_AZUL_MARINO);
        lblVerMas.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblVerMas.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenido.add(lblVerMas);

        tarjeta.add(contenido, BorderLayout.CENTER);
        return tarjeta;
    }

    //========================================================
    // ENSAMBLAJE FINAL DE LA VENTANA
    //========================================================
    private void ensamblarVentana() {
        add(panelMenuLateral, BorderLayout.WEST);

        scrollContenido = new JScrollPane(panelContenidoCentral);
        scrollContenido.setBorder(null);
        scrollContenido.getVerticalScrollBar().setUnitIncrement(16);
        scrollContenido.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel panelCentroCompleto = new JPanel(new BorderLayout());
        panelCentroCompleto.add(panelEncabezado, BorderLayout.NORTH);
        panelCentroCompleto.add(scrollContenido, BorderLayout.CENTER);

        JScrollPane scrollDerecho = new JScrollPane(panelLateralDerecho);
        scrollDerecho.setBorder(null);
        scrollDerecho.getVerticalScrollBar().setUnitIncrement(16);
        scrollDerecho.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JSplitPane splitCentral = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelCentroCompleto, scrollDerecho);
        splitCentral.setResizeWeight(0.78);
        splitCentral.setBorder(null);
        splitCentral.setDividerSize(4);

        add(splitCentral, BorderLayout.CENTER);
    }

    //========================================================
    // EVENTOS - EDICIÓN DE PERFIL PRINCIPAL
    //========================================================

    // Abre un formulario de edición para los datos principales del perfil (nombre, título, contacto)
    private void manejarEditarPerfil() {
        JTextField campoNombre = new JTextField(datoNombre);
        JTextField campoTitulo = new JTextField(datoTitulo);
        JTextField campoUbicacion = new JTextField(datoUbicacion);
        JTextField campoCorreo = new JTextField(datoCorreo);
        JTextField campoTelefono = new JTextField(datoTelefono);

        JPanel formulario = new JPanel(new GridLayout(0, 1, 4, 6));
        formulario.add(crearEtiquetaFormulario("Nombre completo:"));
        formulario.add(campoNombre);
        formulario.add(crearEtiquetaFormulario("Título profesional:"));
        formulario.add(campoTitulo);
        formulario.add(crearEtiquetaFormulario("Ubicación:"));
        formulario.add(campoUbicacion);
        formulario.add(crearEtiquetaFormulario("Correo electrónico:"));
        formulario.add(campoCorreo);
        formulario.add(crearEtiquetaFormulario("Teléfono:"));
        formulario.add(campoTelefono);

        int opcion = JOptionPane.showConfirmDialog(this, formulario, "Editar Perfil",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opcion != JOptionPane.OK_OPTION) {
            mostrarMensajeInformativo("Edición cancelada", "No se realizaron cambios en tu perfil.");
            return;
        }

        try {
            String nuevoNombre = campoNombre.getText().trim();
            String nuevoTitulo = campoTitulo.getText().trim();
            String nuevaUbicacion = campoUbicacion.getText().trim();
            String nuevoCorreo = campoCorreo.getText().trim();
            String nuevoTelefono = campoTelefono.getText().trim();

            validarCampoNoVacio(nuevoNombre, "El nombre");
            validarCampoNoVacio(nuevoTitulo, "El título profesional");
            validarCampoNoVacio(nuevaUbicacion, "La ubicación");
            validarLongitudMaxima(nuevoNombre, 60, "El nombre");
            validarLongitudMaxima(nuevoTitulo, 120, "El título profesional");
            validarCorreoElectronico(nuevoCorreo);
            validarTelefono(nuevoTelefono);

            datoNombre = nuevoNombre;
            datoTitulo = nuevoTitulo;
            datoUbicacion = nuevaUbicacion;
            datoCorreo = nuevoCorreo;
            datoTelefono = nuevoTelefono;

            lblNombreUsuario.setText(datoNombre);
            lblTituloProfesional.setText(datoTitulo);
            lblUbicacion.setText("\uD83D\uDCCD " + datoUbicacion);
            lblCorreo.setText("\u2709 " + datoCorreo);
            lblTelefono.setText("\u260E " + datoTelefono);
            lblUltimaActualizacion.setText("Última actualización: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

            mostrarMensajeExito("Tu perfil se actualizó correctamente.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos inválidos", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado al guardar: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel crearEtiquetaFormulario(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FUENTE_PEQUENA);
        return lbl;
    }

    // Edita el texto de la sección "Acerca de mí" mediante un área de texto editable
    private void manejarEditarAcercaDeMi() {
        JTextArea area = new JTextArea(datoAcercaDe, 6, 40);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(area);

        int opcion = JOptionPane.showConfirmDialog(this, scroll, "Editar 'Acerca de mí'",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opcion != JOptionPane.OK_OPTION) return;

        try {
            String nuevoTexto = area.getText().trim();
            validarCampoNoVacio(nuevoTexto, "La descripción");
            validarLongitudMaxima(nuevoTexto, 500, "La descripción");
            datoAcercaDe = nuevoTexto;
            lblAcercaDeMiTexto.setText("<html><div style='width:850px;'>" + datoAcercaDe + "</div></html>");
            mostrarMensajeExito("Descripción actualizada correctamente.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dato inválido", JOptionPane.ERROR_MESSAGE);
        }
    }

    //========================================================
    // EVENTOS - LISTAS EDITABLES (EXPERIENCIA, EDUCACIÓN, ETC.)
    //========================================================

    // Agrega un nuevo elemento de texto a una lista de datos y refresca el panel visual asociado
    private void manejarAgregarElemento(List<String> listaDatos, JPanel panelVisual, String etiquetaCampo) {
        String entrada = JOptionPane.showInputDialog(this, etiquetaCampo + ":", "Agregar elemento", JOptionPane.PLAIN_MESSAGE);
        if (entrada == null) return; // El usuario canceló
        try {
            entrada = entrada.trim();
            validarCampoNoVacio(entrada, "El campo");
            validarLongitudMaxima(entrada, 150, "El campo");
            listaDatos.add(entrada);
            actualizarListaTextoEnPanel(panelVisual, listaDatos);
            mostrarMensajeExito("Elemento agregado correctamente.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dato inválido", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Elimina el último elemento de una lista de datos, solicitando confirmación previa
    private void manejarEliminarUltimoElemento(List<String> listaDatos, JPanel panelVisual) {
        if (listaDatos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay elementos para eliminar.", "Lista vacía", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Deseas eliminar el último elemento: \"" + listaDatos.get(listaDatos.size() - 1) + "\"?",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            listaDatos.remove(listaDatos.size() - 1);
            actualizarListaTextoEnPanel(panelVisual, listaDatos);
            mostrarMensajeExito("Elemento eliminado correctamente.");
        }
    }

    // Permite editar una lista completa de texto (una línea por elemento) mediante un área de texto
    private void manejarEditarListaCompleta(String tituloSeccion, List<String> listaDatos, JPanel panelVisual) {
        StringBuilder texto = new StringBuilder();
        for (String item : listaDatos) texto.append(item).append("\n");

        JTextArea area = new JTextArea(texto.toString(), 8, 40);
        area.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(area);

        int opcion = JOptionPane.showConfirmDialog(this, scroll, "Editar " + tituloSeccion,
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opcion != JOptionPane.OK_OPTION) return;

        try {
            String[] lineas = area.getText().split("\n");
            List<String> nuevaLista = new ArrayList<>();
            for (String linea : lineas) {
                String limpia = linea.trim();
                if (!limpia.isEmpty()) {
                    validarLongitudMaxima(limpia, 150, "Cada línea de " + tituloSeccion);
                    nuevaLista.add(limpia);
                }
            }
            if (nuevaLista.isEmpty()) {
                throw new IllegalArgumentException(tituloSeccion + " no puede quedar completamente vacío.");
            }
            listaDatos.clear();
            listaDatos.addAll(nuevaLista);
            actualizarListaTextoEnPanel(panelVisual, listaDatos);
            mostrarMensajeExito(tituloSeccion + " actualizado correctamente.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dato inválido", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Permite editar una lista que se muestra como "chips" (ej. Habilidades), separada por comas
    private void manejarEditarListaComoChips(String tituloSeccion, List<String> listaDatos, JPanel panelVisual, Color colorChip) {
        String actual = String.join(", ", listaDatos);
        String entrada = JOptionPane.showInputDialog(this,
            "Edita tus " + tituloSeccion.toLowerCase() + " separándolas por comas:", actual);
        if (entrada == null) return;

        try {
            String[] partes = entrada.split(",");
            List<String> nuevaLista = new ArrayList<>();
            for (String parte : partes) {
                String limpia = parte.trim();
                if (!limpia.isEmpty()) {
                    validarLongitudMaxima(limpia, 40, "Cada habilidad");
                    nuevaLista.add(limpia);
                }
            }
            if (nuevaLista.isEmpty()) {
                throw new IllegalArgumentException(tituloSeccion + " no puede quedar vacío.");
            }
            listaDatos.clear();
            listaDatos.addAll(nuevaLista);
            actualizarChipsEnPanel(panelVisual, listaDatos, colorChip);
            mostrarMensajeExito(tituloSeccion + " actualizadas correctamente.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dato inválido", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Permite editar una lista horizontal simple (ej. Idiomas), separada por comas
    private void manejarEditarListaHorizontal(String tituloSeccion, List<String> listaDatos, JPanel panelVisual) {
        String actual = String.join(", ", listaDatos);
        String entrada = JOptionPane.showInputDialog(this,
            "Edita tus " + tituloSeccion.toLowerCase() + " separados por comas (ej. Español - Nativo):", actual);
        if (entrada == null) return;

        try {
            String[] partes = entrada.split(",");
            List<String> nuevaLista = new ArrayList<>();
            for (String parte : partes) {
                String limpia = parte.trim();
                if (!limpia.isEmpty()) {
                    validarLongitudMaxima(limpia, 60, "Cada idioma");
                    nuevaLista.add(limpia);
                }
            }
            if (nuevaLista.isEmpty()) {
                throw new IllegalArgumentException(tituloSeccion + " no puede quedar vacío.");
            }
            listaDatos.clear();
            listaDatos.addAll(nuevaLista);
            actualizarListaHorizontalEnPanel(panelVisual, listaDatos);
            mostrarMensajeExito(tituloSeccion + " actualizados correctamente.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dato inválido", JOptionPane.ERROR_MESSAGE);
        }
    }

    //========================================================
    // EVENTOS - SELECCIÓN Y CARGA DE IMÁGENES (JFileChooser)
    //========================================================

    // Abre un selector de archivos para elegir una imagen de portada o de perfil, valida y la aplica
    private void manejarSeleccionImagen(boolean esPortada) {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle(esPortada ? "Seleccionar imagen de portada" : "Seleccionar foto de perfil");
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imágenes (PNG, JPG, JPEG)", "png", "jpg", "jpeg");
        selector.setFileFilter(filtro);

        int resultado = selector.showOpenDialog(this);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return; // El usuario canceló la selección
        }

        File archivoSeleccionado = selector.getSelectedFile();

        try {
            validarExtensionImagen(archivoSeleccionado.getName());

            Image imagenActual = esPortada ? imagenPortada : imagenPerfil;
            if (imagenActual != null) {
                int confirmacion = JOptionPane.showConfirmDialog(this,
                    "Ya existe una imagen cargada. ¿Deseas reemplazarla?",
                    "Confirmar reemplazo", JOptionPane.YES_NO_OPTION);
                if (confirmacion != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            BufferedImage imagenLeida = ImageIO.read(archivoSeleccionado);
            if (imagenLeida == null) {
                throw new IOException("El archivo seleccionado no es una imagen válida.");
            }

            // Vista previa antes de confirmar el guardado definitivo
            ImageIcon vistaPrevia = new ImageIcon(
                imagenLeida.getScaledInstance(220, esPortada ? 90 : 220, Image.SCALE_SMOOTH));
            JLabel lblVistaPrevia = new JLabel(vistaPrevia);
            int confirmacionFinal = JOptionPane.showConfirmDialog(this, lblVistaPrevia,
                "Vista previa - ¿Guardar esta imagen?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (confirmacionFinal != JOptionPane.OK_OPTION) {
                mostrarMensajeInformativo("Cambio cancelado", "No se modificó la imagen actual.");
                return;
            }

            // Escalado proporcional automático sin deformar la imagen original
            Image imagenEscalada = escalarImagenProporcional(imagenLeida, esPortada ? 1200 : 220, esPortada ? 150 : 220);

            if (esPortada) {
                imagenPortada = imagenEscalada;
                panelPortada.repaint();
            } else {
                imagenPerfil = imagenEscalada;
                panelFotoPerfil.repaint();
            }

            mostrarMensajeExito((esPortada ? "Portada" : "Foto de perfil") + " actualizada correctamente.");

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Formato no válido", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen: " + ex.getMessage(),
                "Error de lectura", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Escala una imagen de forma proporcional para que encaje dentro de un ancho/alto máximo sin deformarla
    private Image escalarImagenProporcional(BufferedImage original, int anchoMaximo, int altoMaximo) {
        double proporcionOriginal = (double) original.getWidth() / original.getHeight();
        double proporcionDestino = (double) anchoMaximo / altoMaximo;

        int anchoFinal;
        int altoFinal;
        if (proporcionOriginal > proporcionDestino) {
            altoFinal = altoMaximo;
            anchoFinal = (int) (altoMaximo * proporcionOriginal);
        } else {
            anchoFinal = anchoMaximo;
            altoFinal = (int) (anchoMaximo / proporcionOriginal);
        }
        return original.getScaledInstance(anchoFinal, altoFinal, Image.SCALE_SMOOTH);
    }

    // Valida que la extensión del archivo seleccionado sea PNG, JPG o JPEG
    private void validarExtensionImagen(String nombreArchivo) {
        String nombreMin = nombreArchivo.toLowerCase();
        if (!(nombreMin.endsWith(".png") || nombreMin.endsWith(".jpg") || nombreMin.endsWith(".jpeg"))) {
            throw new IllegalArgumentException("Solo se permiten imágenes en formato PNG, JPG o JPEG.");
        }
    }

    //========================================================
    // EVENTOS - ACCIONES ADICIONALES (CV, CONTACTO, BÚSQUEDA)
    //========================================================

    private void manejarDescargarCV() {
        try {
            mostrarMensajeExito("Tu currículum se está descargando...");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo descargar el CV: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void manejarContactar() {
        mostrarMensajeInformativo("Contactar", "Se ha enviado tu solicitud de contacto a " + datoNombre + ".");
    }

    //========================================================
    // VALIDACIONES GENERALES DE FORMULARIOS
    //========================================================

    private void validarCampoNoVacio(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " no puede estar vacío.");
        }
    }

    private void validarLongitudMaxima(String valor, int maximo, String nombreCampo) {
        if (valor.length() > maximo) {
            throw new IllegalArgumentException(nombreCampo + " no puede superar los " + maximo + " caracteres.");
        }
    }

    private void validarCorreoElectronico(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico no puede estar vacío.");
        }
        String patron = "^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$";
        if (!Pattern.matches(patron, correo.trim())) {
            throw new IllegalArgumentException("El formato del correo electrónico no es válido.");
        }
    }

    private void validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío.");
        }
        String patron = "^[+0-9\\-\\s]{7,20}$";
        if (!Pattern.matches(patron, telefono.trim())) {
            throw new IllegalArgumentException("El teléfono contiene caracteres no válidos. Use solo números, '+', '-' y espacios.");
        }
    }

    //========================================================
    // MENSAJES Y CIERRE DE VENTANA
    //========================================================

    private void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarMensajeInformativo(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    //========================================================
    // MÉTODO PRINCIPAL (solo para pruebas aisladas de esta pantalla)
    //========================================================
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame ventana = new JFrame("WorkBridge - Mi Perfil");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setContentPane(new PerfilUsuario());
            ventana.setMinimumSize(new Dimension(1150, 680));
            ventana.setSize(1220, 760);
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });
    }
}
