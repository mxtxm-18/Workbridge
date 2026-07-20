
package Model;

public class PerfilTrabajador {

    private String id;
    private String profesion;
    private String descripcion;
    private String cvUrl;
    private String experiencia;

    public PerfilTrabajador() {
    }

    public PerfilTrabajador(String id,
                            String profesion,
                            String descripcion,
                            String cvUrl,
                            String experiencia) {

        this.id = id;
        this.profesion = profesion;
        this.descripcion = descripcion;
        this.cvUrl = cvUrl;
        this.experiencia = experiencia;
    }

    public String getId() {
        return id;
    }

    public String getProfesion() {
        return profesion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    @Override
    public String toString() {
        return "PerfilTrabajador{" + "id=" + id + ", profesion=" + profesion + ", descripcion=" + descripcion + ", cvUrl=" + cvUrl + ", experiencia=" + experiencia + '}';
    }


}
