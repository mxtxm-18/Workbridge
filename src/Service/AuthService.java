package Service;

import DAO.UsuarioDAO;
import Model.Usuario;
import Util.Encriptador;

public class AuthService {

    private UsuarioDAO usuarioDAO;

    public AuthService() {

        usuarioDAO =
                new UsuarioDAO();
    }

    public Usuario login(
            String correo,
            String password
    ) throws Exception {

        // Validar campos vacíos
        if (correo.isEmpty()
                || password.isEmpty()) {

            throw new Exception(
                    "Debe completar todos los campos"
            );
        }

        // Buscar usuario
        Usuario usuario =
                usuarioDAO.buscarPorCorreo(
                        correo
                );

        if (usuario == null) {

            throw new Exception(
                    "Correo no registrado"
            );
        }

        // Verificar contraseña
        boolean passwordCorrecta =
                Encriptador.verificarPassword(
                        password,
                        usuario.getPasswordHash()
                );

        if (!passwordCorrecta) {

            throw new Exception(
                    "Contraseña incorrecta"
            );
        }

        return usuario;
    }
}