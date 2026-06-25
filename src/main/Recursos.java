package main;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Clase utilitaria para cargar recursos (imágenes, íconos) desde el paquete Recursos.
 * Uso: ImageIcon icon = Recursos.getLogo(ancho, alto);
 */
public class Recursos {

    /**
     * Carga el logo y lo escala al tamaño indicado.
     * Devuelve null sin lanzar excepción si la imagen no existe.
     */
    public static ImageIcon getLogo(int ancho, int alto) {
        URL url = Recursos.class.getResource("/Recursos/logo.png");
        if (url == null) return null;
        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    /**
     * Crea un JLabel con el logo escalado, o con texto fallback si no existe la imagen.
     */
    public static JLabel crearLabelLogo(int ancho, int alto, String textoFallback) {
        ImageIcon icon = getLogo(ancho, alto);
        if (icon != null) {
            return new JLabel(icon);
        } else {
            JLabel lbl = new JLabel(textoFallback);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
            lbl.setForeground(Color.WHITE);
            return lbl;
        }
    }
}
