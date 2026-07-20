
package Model;
public class Postulacion {

    private String id;

    private Trabajador trabajador;

    private Vacante vacante;

    private String estado;

    public Postulacion() {
    }

    public Postulacion(String id,
                       Trabajador trabajador,
                       Vacante vacante,
                       String estado) {

        this.id = id;
        this.trabajador = trabajador;
        this.vacante = vacante;
        this.estado = estado;
    }

    public void aceptar() {
        estado = "ACEPTADA";
    }

    public void rechazar() {
        estado = "RECHAZADA";
    }

    public void enRevision() {
        estado = "EN_REVISION";
    }

    public String getId() {
        return id;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public Vacante getVacante() {
        return vacante;
    }

    public String getEstado() {
        return estado;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public void setVacante(Vacante vacante) {
        this.vacante = vacante;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Postulacion{" + "id=" + id + ", trabajador=" + trabajador + ", vacante=" + vacante + ", estado=" + estado + '}';
    }
    
}