
package Model;
import java.time.LocalDateTime;

public class Notificacion {

    private String id;

    private Usuario usuario;

    private String titulo;

    private String mensaje;

    private boolean leida;

    private LocalDateTime fecha;

    public Notificacion() {
    }

    public Notificacion(String id,
                        Usuario usuario,
                        String titulo,
                        String mensaje,
                        boolean leida,
                        LocalDateTime fecha) {

        this.id = id;
        this.usuario = usuario;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.leida = leida;
        this.fecha = fecha;
    }

    public void marcarComoLeida() {
        this.leida = true;
    }

    public String getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public boolean isLeida() {
        return leida;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setLeida(boolean leida) {
        this.leida = leida;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Notificacion{" + "id=" + id + ", usuario=" + usuario + ", titulo=" + titulo + ", mensaje=" + mensaje + ", leida=" + leida + ", fecha=" + fecha + '}';
    }
    
    
}