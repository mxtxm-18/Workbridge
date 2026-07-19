package View.Trabajador;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Panel de perfil del trabajador
 *
 * Diseño: - Compatible con DashboardBase - Solo Swing - Sin lógica de negocio -
 * Diseño tipo perfil profesional
 */
public class MiPerfil extends JPanel {

    // =====================================================
    // COLORES DEL SISTEMA
    // =====================================================
    private final Color CREMA = new Color(212, 205, 197);
    private final Color MORADO = new Color(155, 115, 166);
    private final Color AZUL_OSCURO = new Color(36, 58, 105);
    private final Color AZUL_CLARO = new Color(91, 136, 165);

    private final Color FONDO = new Color(245, 245, 245);
    private final Color BLANCO = Color.WHITE;
    private final Color TEXTO_GRIS = new Color(100, 100, 100);

    // =====================================================
    // CONTENEDORES PRINCIPALES
    // =====================================================
    private JPanel contenido;

    private JScrollPane scroll;

    // =====================================================
    // FUENTES
    // =====================================================
    private final Font TITULO
            = new Font(
                    "Segoe UI",
                    Font.BOLD,
                    30
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

    // =====================================================
    // CONSTRUCTOR
    // =====================================================
    public MiPerfil() {

        inicializar();

    }

    // =====================================================
    // INICIALIZAR PANEL
    // =====================================================
    private void inicializar() {

        setLayout(new BorderLayout());

        setBackground(FONDO);

        crearContenedor();

        agregarContenido();

    }

    // =====================================================
    // CREAR SCROLL PRINCIPAL
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

        scroll = new JScrollPane(
                contenido
        );

        scroll.setBorder(null);

        scroll.getVerticalScrollBar()
                .setUnitIncrement(16);

        scroll.setBackground(FONDO);

        add(
                scroll,
                BorderLayout.CENTER
        );

    }

    // =====================================================
    // ESPACIO PARA CONSTRUIR EL PERFIL
    // =====================================================
    private void agregarContenido() {

        contenido.add(crearPerfilSuperior());
        contenido.add(Box.createVerticalStrut(20));

        contenido.add(crearResumenProfesional());
        contenido.add(Box.createVerticalStrut(20));

        contenido.add(crearInformacionYEstadisticas());
        contenido.add(Box.createVerticalStrut(20));

        contenido.add(crearHabilidades());
        contenido.add(Box.createVerticalStrut(20));

        contenido.add(crearExperiencia());
        contenido.add(Box.createVerticalStrut(20));

        contenido.add(crearEducacion());
        contenido.add(Box.createVerticalStrut(20));

    }

    private JPanel crearPerfilSuperior() {

        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(BLANCO);
        tarjeta.setBorder(new EmptyBorder(0, 0, 25, 0));
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));

        //=====================================================
        // BANNER
        //=====================================================
        JPanel banner = new JPanel(null);
        banner.setBackground(AZUL_OSCURO);
        banner.setPreferredSize(new Dimension(900, 120));

        tarjeta.add(banner, BorderLayout.NORTH);

        //=====================================================
        // CONTENIDO
        //=====================================================
        JPanel cuerpo = new JPanel(new BorderLayout());
        cuerpo.setBackground(BLANCO);
        cuerpo.setBorder(new EmptyBorder(55, 25, 25, 25));

        tarjeta.add(cuerpo, BorderLayout.CENTER);

//=====================================================
// FOTO DEL TRABAJADOR
//=====================================================
       java.net.URL url = getClass().getResource("/Imagen/foto_trabajador.png");

if (url == null) {
    JOptionPane.showMessageDialog(
            this,
            "No se encontró la imagen:\n/Imagen/foto_trabajador.png"
    );
}

ImageIcon icono = new ImageIcon(url);

Image imagen = icono.getImage().getScaledInstance(
        110,
        110,
        Image.SCALE_SMOOTH
);

JLabel foto = new JLabel(new ImageIcon(imagen));

        foto.setPreferredSize(
                new Dimension(
                        110,
                        110
                )
        );

    JPanel marcoFoto = new JPanel(new BorderLayout());

marcoFoto.setBackground(Color.WHITE);

marcoFoto.setBorder(
        BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(220,220,220)
                ),
                new EmptyBorder(5,5,5,5)
        )
);

marcoFoto.add(foto);
cuerpo.add(marcoFoto, BorderLayout.WEST);
        //=====================================================
        // DATOS
        //=====================================================
        JPanel datos = new JPanel();
        datos.setOpaque(false);
        datos.setLayout(new BoxLayout(datos, BoxLayout.Y_AXIS));

        JLabel nombre = new JLabel("     Brandon Subuyuj");
        nombre.setFont(new Font("Segoe UI", Font.BOLD, 28));
        nombre.setForeground(AZUL_OSCURO);

        JLabel cargo = new JLabel("         Trabajador");
        cargo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        cargo.setForeground(MORADO);

        JLabel ubicacion = new JLabel("            📍 Guatemala");
        ubicacion.setFont(TEXTO);
        ubicacion.setForeground(TEXTO_GRIS);

        JLabel estado = new JLabel("            🟢 Disponible para trabajar");
        estado.setFont(TEXTO);
        estado.setForeground(new Color(0, 140, 70));

        datos.add(nombre);
        datos.add(Box.createVerticalStrut(6));
        datos.add(cargo);
        datos.add(Box.createVerticalStrut(8));
        datos.add(ubicacion);
        datos.add(Box.createVerticalStrut(6));
        datos.add(estado);

        cuerpo.add(datos, BorderLayout.CENTER);

        //=====================================================
        // BOTÓN
        //=====================================================
        JButton editar = new JButton("Editar perfil");

        editar.setBackground(MORADO);
        editar.setForeground(Color.WHITE);
        editar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        editar.setFocusPainted(false);
        editar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        editar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setOpaque(false);
        panelBoton.add(editar);

        cuerpo.add(panelBoton, BorderLayout.EAST);

        return tarjeta;
    }

    private JPanel crearResumenProfesional() {

        JPanel tarjeta = new JPanel(new BorderLayout());

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
                        180
                )
        );

        JLabel titulo = new JLabel("Resumen Profesional");

        titulo.setFont(SUBTITULO);

        titulo.setForeground(AZUL_OSCURO);

        JTextArea texto = new JTextArea(
                "Trabajador responsable con experiencia en atención al cliente, "
                + "gestión de pedidos y trabajo en equipo. "
                + "Me adapto rápidamente a nuevos entornos laborales y "
                + "busco ofrecer un excelente servicio en cada actividad."
        );

        texto.setLineWrap(true);

        texto.setWrapStyleWord(true);

        texto.setEditable(false);

        texto.setOpaque(false);

        texto.setFont(TEXTO);

        texto.setForeground(TEXTO_GRIS);

        tarjeta.add(
                titulo,
                BorderLayout.NORTH
        );

        tarjeta.add(
                texto,
                BorderLayout.CENTER
        );

        return tarjeta;

    }

    private JPanel crearInformacionYEstadisticas() {

        JPanel contenedor = new JPanel(
                new GridLayout(
                        1,
                        2,
                        20,
                        0
                )
        );

        contenedor.setOpaque(false);

        contenedor.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        260
                )
        );

        //===========================================
        // INFORMACIÓN PERSONAL
        //===========================================
        JPanel informacion = new JPanel();

        informacion.setLayout(
                new BoxLayout(
                        informacion,
                        BoxLayout.Y_AXIS
                )
        );

        informacion.setBackground(BLANCO);

        informacion.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 225, 225)
                        ),
                        new EmptyBorder(20, 20, 20, 20)
                )
        );

        JLabel t1 = new JLabel("Información Personal");

        t1.setFont(SUBTITULO);

        t1.setForeground(AZUL_OSCURO);

        informacion.add(t1);

        informacion.add(Box.createVerticalStrut(18));

        informacion.add(crearDato("Nombre", "Brandon Subuyuj"));
        informacion.add(Box.createVerticalStrut(10));

        informacion.add(crearDato("Correo", "Brandon@gmail.com"));
        informacion.add(Box.createVerticalStrut(10));

        informacion.add(crearDato("Teléfono", "4354-6787"));
        informacion.add(Box.createVerticalStrut(10));

        informacion.add(crearDato("Dirección", "Guatemala"));
        informacion.add(Box.createVerticalStrut(10));

        informacion.add(crearDato("Edad", "24 años"));

        //===========================================
        // ESTADÍSTICAS
        //===========================================
        JPanel estadisticas = new JPanel();

        estadisticas.setLayout(
                new GridLayout(
                        2,
                        2,
                        15,
                        15
                )
        );

        estadisticas.setBackground(BLANCO);

        estadisticas.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 225, 225)
                        ),
                        new EmptyBorder(20, 20, 20, 20)
                )
        );

        estadisticas.add(crearEstadistica("25", "Pedidos"));
        estadisticas.add(crearEstadistica("4.9", "Calificación"));
        estadisticas.add(crearEstadistica("3", "Años"));
        estadisticas.add(crearEstadistica("98%", "Puntualidad"));

        contenedor.add(informacion);
        contenedor.add(estadisticas);

        return contenedor;

    }

    private JPanel crearDato(String titulo, String valor) {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setOpaque(false);

        JLabel l1 = new JLabel(titulo);

        l1.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        l1.setForeground(AZUL_OSCURO);

        JLabel l2 = new JLabel(valor);

        l2.setFont(TEXTO);

        l2.setForeground(TEXTO_GRIS);

        panel.add(l1, BorderLayout.NORTH);
        panel.add(l2, BorderLayout.CENTER);

        return panel;

    }

    private JPanel crearEstadistica(String numero, String titulo) {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(CREMA);

        panel.setBorder(
                new EmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        JLabel valor = new JLabel(
                numero,
                SwingConstants.CENTER
        );

        valor.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        valor.setForeground(MORADO);

        JLabel texto = new JLabel(
                titulo,
                SwingConstants.CENTER
        );

        texto.setFont(TEXTO);

        texto.setForeground(AZUL_OSCURO);

        panel.add(
                valor,
                BorderLayout.CENTER
        );

        panel.add(
                texto,
                BorderLayout.SOUTH
        );

        return panel;

    }

    private JPanel crearHabilidades() {

        JPanel tarjeta = new JPanel(new BorderLayout());

        tarjeta.setBackground(BLANCO);

        tarjeta.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(225, 225, 225)),
                        new EmptyBorder(20, 20, 20, 20)
                )
        );

        tarjeta.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        170
                )
        );

        JLabel titulo = new JLabel("Habilidades e Idiomas");
        titulo.setFont(SUBTITULO);
        titulo.setForeground(AZUL_OSCURO);
JPanel etiquetas = new JPanel(
        new FlowLayout(
                FlowLayout.LEFT,
                12,
                12
        )
);
        etiquetas.setOpaque(false);

        etiquetas.add(crearChip("Atención al Cliente"));
        etiquetas.add(crearChip("Trabajo en Equipo"));
        etiquetas.add(crearChip("Gestión de Pedidos"));
        etiquetas.add(crearChip("Responsabilidad"));
        etiquetas.add(crearChip("Comunicación"));
        etiquetas.add(crearChip("Español"));
        etiquetas.add(crearChip("Inglés Básico"));

        tarjeta.add(titulo, BorderLayout.NORTH);
        tarjeta.add(etiquetas, BorderLayout.CENTER);

        return tarjeta;

    }

    private JPanel crearExperiencia() {

        JPanel tarjeta = new JPanel();

        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));

        tarjeta.setBackground(BLANCO);

        tarjeta.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(225, 225, 225)),
                        new EmptyBorder(20, 20, 20, 20)
                )
        );

        tarjeta.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        230
                )
        );

        JLabel titulo = new JLabel("Experiencia Laboral");
        titulo.setFont(SUBTITULO);
        titulo.setForeground(AZUL_OSCURO);

        tarjeta.add(titulo);
        tarjeta.add(Box.createVerticalStrut(18));

        tarjeta.add(crearExperienciaItem(
                "Mesero",
                "Restaurante Freddy Quick Bite",
                "2024 - Actualidad"
        ));

        tarjeta.add(Box.createVerticalStrut(15));

        tarjeta.add(crearExperienciaItem(
                "Atención al Cliente",
                "Restaurante El Sabor",
                "2022 - 2024"
        ));

        return tarjeta;

    }

    private JPanel crearEducacion() {

        JPanel tarjeta = new JPanel();

        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));

        tarjeta.setBackground(BLANCO);

        tarjeta.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(225, 225, 225)),
                        new EmptyBorder(20, 20, 20, 20)
                )
        );

        tarjeta.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        210
                )
        );

        JLabel titulo = new JLabel("Educación y Certificaciones");

        titulo.setFont(SUBTITULO);

        titulo.setForeground(AZUL_OSCURO);

        tarjeta.add(titulo);

        tarjeta.add(Box.createVerticalStrut(18));

        tarjeta.add(crearExperienciaItem(
                "Bachiller en Computación",
                "Instituto Nacional",
                "2018 - 2020"
        ));

        tarjeta.add(Box.createVerticalStrut(15));

        tarjeta.add(crearExperienciaItem(
                "Curso de Atención al Cliente",
                "Intecap",
                "2023"
        ));

        return tarjeta;

    }

    private JLabel crearChip(String texto) {

        JLabel chip = new JLabel("  " + texto + "  ");

        chip.setOpaque(true);

        chip.setBackground(CREMA);

        chip.setForeground(AZUL_OSCURO);

        chip.setFont(new Font("Segoe UI", Font.BOLD, 13));

        chip.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        14,
                        8,
                        14
                )
        );

        return chip;

    }

    private JPanel crearExperienciaItem(String puesto,
            String empresa,
            String fecha) {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setOpaque(false);

        JLabel l1 = new JLabel(puesto);

        l1.setFont(new Font("Segoe UI", Font.BOLD, 15));

        l1.setForeground(AZUL_OSCURO);

        JLabel l2 = new JLabel(empresa);

        l2.setFont(TEXTO);

        l2.setForeground(TEXTO_GRIS);

        JLabel l3 = new JLabel(fecha);

        l3.setFont(TEXTO);

        l3.setForeground(MORADO);

        JPanel centro = new JPanel();

        centro.setOpaque(false);

        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));

        centro.add(l1);
        centro.add(l2);

        panel.add(centro, BorderLayout.CENTER);

        panel.add(l3, BorderLayout.EAST);

        return panel;

    }
}
