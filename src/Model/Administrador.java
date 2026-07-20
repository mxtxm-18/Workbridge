package Model;

public class Administrador
        extends Usuario {

    private String nivelAcceso;

    // Constructor vacío
    public Administrador() {

        super();
        setRol("admin");
    }

    // Constructor registro
    public Administrador(
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

        setRol("ADMIN");
    }

    // Constructor completo
    public Administrador(
            String idUsuario,
            String nombre,
            String apellido,
            String correo,
            String passwordHash,
            String telefono,
            String fotoPerfil,
            String nivelAcceso
    ) {

        super();

        setIdUsuario(idUsuario);
        setNombre(nombre);
        setApellido(apellido);
        setCorreo(correo);
        setPasswordHash(passwordHash);
        setTelefono(telefono);
        setFotoPerfil(fotoPerfil);
        setRol("ADMIN");

        this.nivelAcceso =
                nivelAcceso;
    }

    // Getter y Setter

    public String getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(
            String nivelAcceso
    ) {
        this.nivelAcceso =
                nivelAcceso;
    }

    @Override
    public String toString() {

        return "Administrador{"
                + "nombre="
                + getNombreCompleto()
                + ", acceso="
                + nivelAcceso
                + '}';
    }
}