package Model;

import Model.enums.EstadoCuenta;//importacion de enum estado cuenta
import java.time.LocalDateTime;//impotacion de tipo de dato de fecha exacta

public abstract class Usuario {

    //Atriburos
    private String idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String passwordHash;
    private String telefono;
    private String fotoPerfil;
    private EstadoCuenta estadoCuenta;
    private String rol;
    private boolean correoVerificado;
    private LocalDateTime fechaRegistro;
    private LocalDateTime ultimoLogin;

    //contructores
    public Usuario(String nombre, String apellido, String correo, String passwordHash) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.estadoCuenta
                = EstadoCuenta.ACTIVO;
        this.fechaRegistro
                = LocalDateTime.now();
        this.correoVerificado
                = false;
    }

    //vacio
    public Usuario() {
    }

    //completo
    public Usuario(String idUsuario, String nombre, String apellido, String correo, String passwordHash, String telefono, String fotoPerfil, EstadoCuenta estadoCuenta, String rol, boolean correoVerificado, LocalDateTime fechaRegistro, LocalDateTime ultimoLogin) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.telefono = telefono;
        this.fotoPerfil = fotoPerfil;
        this.estadoCuenta = estadoCuenta;
        this.rol = rol;
        this.correoVerificado = correoVerificado;
        this.fechaRegistro = fechaRegistro;
        this.ultimoLogin = ultimoLogin;
    }

    //Getters
    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public boolean isCorreoVerificado() {
        return correoVerificado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public LocalDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    public EstadoCuenta getEstadoCuenta() {
        return estadoCuenta;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRol() {
        return rol;
    }

    public String getNombreCompleto() {

        return nombre
                + " "
                + apellido;
    }

    public void cambiarPassword(
            String nuevaPasswordHash
    ) {

        this.passwordHash
                = nuevaPasswordHash;
    }

    public void desactivarCuenta() {

        this.estadoCuenta
                = EstadoCuenta.INACTIVO;
    }

    public void activarCuenta() {

        this.estadoCuenta
                = EstadoCuenta.ACTIVO;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    //Setters
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public void setCorreoVerificado(boolean correoVerificado) {
        this.correoVerificado = correoVerificado;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setUltimoLogin(LocalDateTime ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public void setEstadoCuenta(EstadoCuenta estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public boolean estaActivo() {

        return estadoCuenta
                == EstadoCuenta.ACTIVO;
    }

    public void verificarCorreo() {

        this.correoVerificado
                = true;
    }

    public void actualizarUltimoLogin() {

        this.ultimoLogin
                = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Usuario{"
                + "idUsuario=" + idUsuario
                + ", nombre=" + nombre
                + ", apellido=" + apellido
                + ", correo=" + correo
                + ", telefono=" + telefono
                + ", rol=" + rol
                + ", fotoPerfil=" + fotoPerfil
                + ", estadoCuenta=" + estadoCuenta
                + ", correoVerificado="
                + correoVerificado
                + ", fechaRegistro="
                + fechaRegistro
                + ", ultimoLogin="
                + ultimoLogin
                + '}';
    }

}
