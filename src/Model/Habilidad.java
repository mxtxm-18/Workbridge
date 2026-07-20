
package Model;

public class Habilidad {

    private String id;
    private String nombre;

    public Habilidad() {
    }

    public Habilidad(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Habilidad{" + "id=" + id + ", nombre=" + nombre + '}';
    }
    
}