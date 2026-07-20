
package Model;

public class RegistroModel{

    private Usuario usuario;

    private Trabajador trabajador;

    private Empresa empresa;

    private String rol;

    public RegistroModel(Usuario usuario, Trabajador trabajador, Empresa empresa, String rol) {
        this.usuario = usuario;
        this.trabajador = trabajador;
        this.empresa = empresa;
        this.rol = rol;
    }

    public RegistroModel() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public String getRol() {
        return rol;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    
    @Override
    public String toString() {
        return "Registro{" + "usuario=" + usuario + ", trabajador=" + trabajador + ", empresa=" + empresa + '}';
    }

}