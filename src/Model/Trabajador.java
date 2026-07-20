
package Model;

public class Trabajador extends Usuario {

    private String profesion;
    private String descripcionPerfil;
    private String cvRuta;
    private Integer aniosExperiencia;

    // Constructor vacío
    public Trabajador() {
        super();
        setRol("TRABAJADOR");
    }

    // Constructor registro
    public Trabajador(
            String nombre,
            String apellido,
            String correo,
            String passwordHash
    ) {

        super(
                nombre,
                apellido,
                correo,
                passwordHash
        );

        setRol("TRABAJADOR");
    }

    // Constructor completo
    public Trabajador(
            String idUsuario,
            String nombre,
            String apellido,
            String correo,
            String passwordHash,
            String telefono,
            String fotoPerfil,
            String profesion,
            String descripcionPerfil,
            String cvRuta,
            Integer aniosExperiencia
    ) {

        super();

        setIdUsuario(idUsuario);
        setNombre(nombre);
        setApellido(apellido);
        setCorreo(correo);
        setPasswordHash(passwordHash);
        setTelefono(telefono);
        setFotoPerfil(fotoPerfil);
        setRol("trabajador");

        this.profesion = profesion;
        this.descripcionPerfil =
                descripcionPerfil;

        this.cvRuta = cvRuta;

        this.aniosExperiencia =
                aniosExperiencia;
    }

    // Getters y Setters

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(
            String profesion
    ) {
        this.profesion = profesion;
    }

    public String getDescripcionPerfil() {
        return descripcionPerfil;
    }

    public void setDescripcionPerfil(
            String descripcionPerfil
    ) {
        this.descripcionPerfil =
                descripcionPerfil;
    }

    public String getCvRuta() {
        return cvRuta;
    }

    public void setCvRuta(
            String cvRuta
    ) {
        this.cvRuta = cvRuta;
    }

    public Integer getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(
            Integer aniosExperiencia
    ) {
        this.aniosExperiencia =
                aniosExperiencia;
    }

    @Override
    public String toString() {

        return "Trabajador{"
                + "nombre="
                + getNombreCompleto()
                + ", correo="
                + getCorreo()
                + ", profesion="
                + profesion
                + ", experiencia="
                + aniosExperiencia
                + '}';
    }
}