
package Model;

import java.util.List;

public class Vacante {

    private String id;
    private String titulo;
    private String descripcion;
    private String modalidad;
    private double salarioMin;
    private double salarioMax;
    private String ubicacion;
    private String estado;

    private Empresa empresa;

    private List<Habilidad> habilidades;

    public Vacante() {
    }

    public Vacante(String id,
                   String titulo,
                   String descripcion,
                   String modalidad,
                   double salarioMin,
                   double salarioMax,
                   String ubicacion,
                   String estado,
                   Empresa empresa) {

        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.modalidad = modalidad;
        this.salarioMin = salarioMin;
        this.salarioMax = salarioMax;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.empresa = empresa;
    }

    public void cerrar() {
        this.estado = "CERRADA";
    }

    public void pausar() {
        this.estado = "PAUSADA";
    }

    public void activar() {
        this.estado = "ACTIVA";
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getModalidad() {
        return modalidad;
    }

    public double getSalarioMin() {
        return salarioMin;
    }

    public double getSalarioMax() {
        return salarioMax;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public List<Habilidad> getHabilidades() {
        return habilidades;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public void setSalarioMin(double salarioMin) {
        this.salarioMin = salarioMin;
    }

    public void setSalarioMax(double salarioMax) {
        this.salarioMax = salarioMax;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setHabilidades(List<Habilidad> habilidades) {
        this.habilidades = habilidades;
    }

    @Override
    public String toString() {
        return "Vacante{" + "id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", modalidad=" + modalidad + ", salarioMin=" + salarioMin + ", salarioMax=" + salarioMax + ", ubicacion=" + ubicacion + ", estado=" + estado + ", empresa=" + empresa + ", habilidades=" + habilidades + '}';
    }
    
}