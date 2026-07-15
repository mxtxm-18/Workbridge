package main;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

// Panel simple que dibuja un gráfico de barras vertical a partir de un
public class GraficaBarras extends JPanel {

    private Map<String, Integer> datos = new LinkedHashMap<>();
    private Color colorBarra = new Color(0x24, 0x3A, 0x69);
    private String titulo;

    public GraficaBarras(String titulo) {
        this.titulo = titulo;
        setBackground(Color.WHITE);
    }

    public void setColorBarra(Color color) {
        this.colorBarra = color;
    }

    /** Actualiza los datos a graficar y repinta el panel. */
    public void setDatos(Map<String, Integer> nuevosDatos) {
        this.datos = (nuevosDatos != null) ? nuevosDatos : new LinkedHashMap<>();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();

        // Título
        g2.setColor(Color.DARK_GRAY);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
        g2.drawString(titulo, 20, 25);

        if (datos.isEmpty()) {
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            g2.setColor(Color.GRAY);
            g2.drawString("Sin datos para mostrar", 20, 50);
            return;
        }

        int margenIzq = 50;
        int margenDer = 30;
        int margenSup = 50;
        int margenInf = 50;

        int areaAncho = ancho - margenIzq - margenDer;
        int areaAlto = alto - margenSup - margenInf;
        int baseY = margenSup + areaAlto;

        int max = 1;
        for (int v : datos.values()) {
            if (v > max) max = v;
        }

        // Eje base
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawLine(margenIzq, baseY, ancho - margenDer, baseY);

        int n = datos.size();
        int espacio = areaAncho / n;
        int barraAncho = (int) (espacio * 0.5);

        int i = 0;
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        for (Map.Entry<String, Integer> e : datos.entrySet()) {
            int valor = e.getValue();
            int barraAlto = (int) (((double) valor / max) * (areaAlto - 20));
            int x = margenIzq + i * espacio + (espacio - barraAncho) / 2;
            int y = baseY - barraAlto;

            g2.setColor(colorBarra);
            g2.fillRoundRect(x, y, barraAncho, barraAlto, 8, 8);

            // Valor encima de la barra
            g2.setColor(Color.DARK_GRAY);
            String texto = String.valueOf(valor);
            int textoAncho = g2.getFontMetrics().stringWidth(texto);
            g2.drawString(texto, x + (barraAncho - textoAncho) / 2, y - 6);

            // Etiqueta debajo del eje
            String etiqueta = e.getKey();
            int etiquetaAncho = g2.getFontMetrics().stringWidth(etiqueta);
            g2.drawString(etiqueta, x + (barraAncho - etiquetaAncho) / 2, baseY + 20);

            i++;
        }
    }
}