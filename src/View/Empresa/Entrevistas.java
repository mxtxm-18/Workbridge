/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Empresa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Entrevistas extends JPanel{
    
    public Entrevistas() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Entrevistas");

        titulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        add(titulo, BorderLayout.NORTH);

        JPanel contenido = new JPanel();
        contenido.setBackground(Color.WHITE);

        add(contenido, BorderLayout.CENTER);
    }
    
}