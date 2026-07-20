
package Model;
import java.time.LocalDateTime;

public class Mensaje {

    private String id;

    private Usuario remitente;

    private Usuario destinatario;

    private String asunto;

    private String contenido;

    private LocalDateTime fechaEnvio;

    private boolean leido;

    public Mensaje() {
    }

    public Mensaje(String id,
                   Usuario remitente,
                   Usuario destinatario,
                   String asunto,
                   String contenido,
                   LocalDateTime fechaEnvio,
                   boolean leido) {

        this.id = id;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.contenido = contenido;
        this.fechaEnvio = fechaEnvio;
        this.leido = leido;
    }

    public void marcarLeido() {
        this.leido = true;
    }

    public String getId() {
        return id;
    }

    public Usuario getRemitente() {
        return remitente;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public String getContenido() {
        return contenido;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRemitente(Usuario remitente) {
        this.remitente = remitente;
    }

    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    @Override
    public String toString() {
        return "Mensaje{" + "id=" + id + ", remitente=" + remitente + ", destinatario=" + destinatario + ", asunto=" + asunto + ", contenido=" + contenido + ", fechaEnvio=" + fechaEnvio + ", leido=" + leido + '}';
    }
    
}
