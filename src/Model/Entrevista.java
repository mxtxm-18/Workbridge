/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDateTime;

public class Entrevista {

    private String id;

    private Postulacion postulacion;

    private LocalDateTime fechaHora;

    private String modalidad;

    private String enlace;

    private String estado;

    public Entrevista() {
    }

    public Entrevista(String id,
                      Postulacion postulacion,
                      LocalDateTime fechaHora,
                      String modalidad,
                      String enlace,
                      String estado) {

        this.id = id;
        this.postulacion = postulacion;
        this.fechaHora = fechaHora;
        this.modalidad = modalidad;
        this.enlace = enlace;
        this.estado = estado;
    }

    public void cancelar() {
        estado = "CANCELADA";
    }

    public void completar() {
        estado = "COMPLETADA";
    }

    public void reprogramar(LocalDateTime nuevaFecha) {
        this.fechaHora = nuevaFecha;
    }

    public String getId() {
        return id;
    }

    public Postulacion getPostulacion() {
        return postulacion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getModalidad() {
        return modalidad;
    }

    public String getEnlace() {
        return enlace;
    }

    public String getEstado() {
        return estado;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPostulacion(Postulacion postulacion) {
        this.postulacion = postulacion;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Entrevista{" + "id=" + id + ", postulacion=" + postulacion + ", fechaHora=" + fechaHora + ", modalidad=" + modalidad + ", enlace=" + enlace + ", estado=" + estado + '}';
    }
    
}