
package Model;

import java.util.Date;

public class Reporte {

    private String id;
    private String nombre;
    private String tipo;
    private String descripcion;
    private Date fechaGeneracion;
    private Usuario generadoPor;

    public Reporte() {
    }

    public Reporte(String id,
                   String nombre,
                   String tipo,
                   String descripcion,
                   Date fechaGeneracion,
                   Usuario generadoPor) {

        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fechaGeneracion = fechaGeneracion;
        this.generadoPor = generadoPor;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public Usuario getGeneradoPor() {
        return generadoPor;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public void setGeneradoPor(Usuario generadoPor) {
        this.generadoPor = generadoPor;
    }

    @Override
    public String toString() {
        return "Reporte{" + "id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + ", descripcion=" + descripcion + ", fechaGeneracion=" + fechaGeneracion + ", generadoPor=" + generadoPor + '}';
    }
    
}