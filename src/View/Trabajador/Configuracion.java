package View.Trabajador;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Configuracion extends JPanel {


    // ============================
    // COLORES
    // ============================

    private final Color CREMA = new Color(212,205,197);
    private final Color MORADO = new Color(155,115,166);
    private final Color AZUL_OSCURO = new Color(36,58,105);
    private final Color AZUL_CLARO = new Color(91,136,165);

    private final Color FONDO = new Color(245,245,245);
    private final Color TEXTO_GRIS = new Color(100,100,100);


    public Configuracion() {


        setLayout(new BorderLayout());
        setBackground(FONDO);


        // ============================
        // TITULO
        // ============================

        JLabel titulo = new JLabel("Configuración");

        titulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        titulo.setForeground(AZUL_OSCURO);

        titulo.setBorder(
                new EmptyBorder(20,25,20,20)
        );


        add(titulo,BorderLayout.NORTH);



        // ============================
        // CONTENIDO
        // ============================


        JPanel contenido = new JPanel();

        contenido.setLayout(
                new BoxLayout(
                        contenido,
                        BoxLayout.Y_AXIS
                )
        );

        contenido.setBackground(FONDO);

        contenido.setBorder(
                new EmptyBorder(10,25,30,25)
        );



        contenido.add(
                crearTarjetaCuenta()
        );

        contenido.add(
                Box.createVerticalStrut(20)
        );


        contenido.add(
                crearTarjetaPreferencias()
        );


        contenido.add(
                Box.createVerticalStrut(20)
        );


        contenido.add(
                crearTarjetaSeguridad()
        );


        contenido.add(
                Box.createVerticalStrut(20)
        );


        contenido.add(
                crearTarjetaApariencia()
        );




        JScrollPane scroll = new JScrollPane(contenido);

        scroll.setBorder(null);

        scroll.getVerticalScrollBar()
                .setUnitIncrement(16);


        add(scroll,BorderLayout.CENTER);

    }




    // =====================================================
    // TARJETA CUENTA
    // =====================================================


    private JPanel crearTarjetaCuenta(){


        JPanel panel = crearTarjeta();


        JLabel titulo =
                crearTitulo("Cuenta");


        panel.add(titulo);


        panel.add(
                crearTexto(
                        "Nombre: Brandon Subuyuj"
                )
        );


        panel.add(
                crearTexto(
                        "Correo: brandon@email.com"
                )
        );


        panel.add(
                crearTexto(
                        "Teléfono: +502 5555-5555"
                )
        );


        JButton boton =
                crearBoton(
                        "Editar información"
                );


        panel.add(
                boton
        );


        return panel;

    }





    // =====================================================
    // TARJETA PREFERENCIAS
    // =====================================================


    private JPanel crearTarjetaPreferencias(){


        JPanel panel =
                crearTarjeta();



        panel.add(
                crearTitulo(
                        "Preferencias"
                )
        );


        JCheckBox notificaciones =
                new JCheckBox(
                        "Recibir notificaciones"
                );


        JCheckBox perfil =
                new JCheckBox(
                        "Mostrar perfil público"
                );


        JCheckBox vacantes =
                new JCheckBox(
                        "Alertas de nuevas vacantes"
                );


        configurarCheck(notificaciones);
        configurarCheck(perfil);
        configurarCheck(vacantes);



        panel.add(notificaciones);
        panel.add(perfil);
        panel.add(vacantes);



        return panel;

    }






    // =====================================================
    // TARJETA SEGURIDAD
    // =====================================================


    private JPanel crearTarjetaSeguridad(){


        JPanel panel =
                crearTarjeta();


        panel.add(
                crearTitulo(
                        "Seguridad"
                )
        );


        panel.add(
                crearTexto(
                        "Último acceso: Hoy 5:30 PM"
                )
        );


        JButton boton =
                crearBoton(
                        "Actualizar contraseña"
                );


        panel.add(boton);


        return panel;

    }






    // =====================================================
    // TARJETA APARIENCIA
    // =====================================================


    private JPanel crearTarjetaApariencia(){


        JPanel panel =
                crearTarjeta();



        panel.add(
                crearTitulo(
                        "Apariencia"
                )
        );



        JComboBox<String> tema =
                new JComboBox<>();


        tema.addItem(
                "Tema claro"
        );


        tema.addItem(
                "Tema oscuro"
        );


        JComboBox<String> idioma =
                new JComboBox<>();


        idioma.addItem(
                "Español"
        );


        idioma.addItem(
                "English"
        );



        panel.add(
                crearTexto(
                        "Tema:"
                )
        );

        panel.add(
                tema
        );


        panel.add(
                crearTexto(
                        "Idioma:"
                )
        );


        panel.add(
                idioma
        );


        return panel;

    }







    // =====================================================
    // COMPONENTES REUTILIZABLES
    // =====================================================



    private JPanel crearTarjeta(){


        JPanel panel =
                new JPanel();


        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );


        panel.setBackground(Color.WHITE);


        panel.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(
                                new Color(225,225,225)
                        ),

                        new EmptyBorder(
                                20,
                                25,
                                20,
                                25
                        )
                )
        );



        panel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        panel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        250
                )
        );


        return panel;

    }







    private JLabel crearTitulo(String texto){


        JLabel label =
                new JLabel(texto);


        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );


        label.setForeground(
                AZUL_OSCURO
        );


        label.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        15,
                        0
                )
        );


        return label;

    }







    private JLabel crearTexto(String texto){


        JLabel label =
                new JLabel(texto);


        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );


        label.setForeground(
                TEXTO_GRIS
        );


        label.setBorder(
                new EmptyBorder(
                        5,
                        0,
                        5,
                        0
                )
        );


        return label;

    }







    private JButton crearBoton(String texto){


        JButton boton =
                new JButton(texto);


        boton.setBackground(
                MORADO
        );


        boton.setForeground(
                Color.WHITE
        );


        boton.setFocusPainted(false);


        boton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );


        return boton;

    }






    private void configurarCheck(JCheckBox check){


        check.setBackground(
                Color.WHITE
        );


        check.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );


        check.setForeground(
                TEXTO_GRIS
        );


    }


}