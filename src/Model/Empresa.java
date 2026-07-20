package Model;

public class Empresa
        extends Usuario {

    private String empresa;
    private String cargo;

    // Constructor vacío
    public Empresa() {

        super();
        setRol("RECLUTADOR");
    }

    // Constructor registro
    public Empresa(
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

        setRol("RECLUTADOR");
    }

    // Constructor completo
    public Empresa(
            String idUsuario,
            String nombre,
            String apellido,
            String correo,
            String passwordHash,
            String telefono,
            String fotoPerfil,
            String empresa,
            String cargo
    ) {

        super();

        setIdUsuario(idUsuario);
        setNombre(nombre);
        setApellido(apellido);
        setCorreo(correo);
        setPasswordHash(passwordHash);
        setTelefono(telefono);
        setFotoPerfil(fotoPerfil);
        setRol("reclutador");

        this.empresa = empresa;
        this.cargo = cargo;
    }

    // Getters y Setters

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(
            String empresa
    ) {
        this.empresa = empresa;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(
            String cargo
    ) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {

        return "Reclutador{"
                + "nombre="
                + getNombreCompleto()
                + ", empresa="
                + empresa
                + ", cargo="
                + cargo
                + '}';
    }
}