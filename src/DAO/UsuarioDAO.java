package DAO;


import Model.Administrador;
import Model.Empresa;
import Model.Trabajador;
import Model.Usuario;
import View.Trabajador.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario buscarPorCorreo(String correo) {

        String sql =
                "SELECT * FROM usuarios "
                + "WHERE email = ?";

        try {

            Connection con =
                    ConexionBD
                            .getInstancia()
                            .getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(
                    1,
                    correo
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                String rol =
                        rs.getString("rol");

                Usuario usuario;

                // FACTORY METHOD
                switch (rol) {

                    case "trabajador":

                        usuario =
                                new Trabajador();
                        break;

                    case "reclutador":

                        usuario =
                                new Empresa();
                        break;

                    case "admin":

                        usuario =
                                new Administrador();
                        break;

                    default:
                        return null;
                }

                // =========================
                // MAPEAR DATOS
                // =========================
                usuario.setIdUsuario(
                        rs.getString("id")
                );

                usuario.setNombre(
                        rs.getString("nombre")
                );

                usuario.setApellido(
                        rs.getString("apellido")
                );

                usuario.setCorreo(
                        rs.getString("email")
                );

                usuario.setPasswordHash(
                        rs.getString("password_hash")
                );

                usuario.setTelefono(
                        rs.getString("telefono")
                );

                usuario.setRol(
                        rs.getString("rol")
                );

                return usuario;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al buscar usuario: "
                    + e.getMessage()
            );
        }

        return null;
    }
}