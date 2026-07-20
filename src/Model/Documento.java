/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.time.LocalDateTime;

public class Documento {

    private String id;

    private Trabajador trabajador;

    private String nombreArchivo;

    private String rutaArchivo;

    private String tipo;

    private LocalDateTime fechaSubida;

    public Documento() {
    }

    public Documento(String id,
                     Trabajador trabajador,
                     String nombreArchivo,
                     String rutaArchivo,
                     String tipo,
                     LocalDateTime fechaSubida) {

        this.id = id;
        this.trabajador = trabajador;
        this.nombreArchivo = nombreArchivo;
        this.rutaArchivo = rutaArchivo;
        this.tipo = tipo;
        this.fechaSubida = fechaSubida;
    }

    public String getId() {
        return id;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDateTime getFechaSubida() {
        return fechaSubida;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setFechaSubida(LocalDateTime fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    @Override
    public String toString() {
        return "Documento{" + "id=" + id + ", trabajador=" + trabajador + ", nombreArchivo=" + nombreArchivo + ", rutaArchivo=" + rutaArchivo + ", tipo=" + tipo + ", fechaSubida=" + fechaSubida + '}';
    }
    
}