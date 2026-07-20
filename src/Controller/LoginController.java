package Controller;

import Model.Usuario;
import Service.AuthService;
import View.Admin.DashboardAdmin;
import View.Empresa.DashboardEmpresa;
import View.Trabajador.DashboardTrabajador;
import View.Login.Login;
import javax.swing.JOptionPane;

public class LoginController {

    private Login view;
    private AuthService authService;

    public LoginController(
            Login view
    ) {

        this.view = view;
        authService
                = new AuthService();
    }

    public void iniciarSesion() {

    try {

        String correo =
                view.getCorreo();

        String password =
                view.getPassword();

        Usuario usuario =
                authService.login(
                        correo,
                        password
                );

        // ======================
        // VALIDAR ROL
        // ======================
        String rolSeleccionado;

        if (view.esTrabajador()) {

            rolSeleccionado =
                    "trabajador";

        } else {

            rolSeleccionado =
                    "reclutador";
        }

        // ADMIN puede entrar siempre
        if (!usuario.getRol()
                .equalsIgnoreCase(
                        "admin"
                )) {

            if (!usuario.getRol()
                    .equalsIgnoreCase(
                            rolSeleccionado
                    )) {

                JOptionPane.showMessageDialog(
                        view,
                        "Este usuario no pertenece al rol seleccionado",
                        "Error de acceso",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }
        }

        // ======================
        // ABRIR DASHBOARD
        // ======================
        abrirDashboard(usuario);

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                view,
                e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
  private void abrirDashboard(
        Usuario usuario
) {

    switch (
            usuario.getRol()
    ) {

        case "trabajador":

            new DashboardTrabajador()
                    .setVisible(true);

            break;

        case "reclutador":

            new DashboardEmpresa()
                    .setVisible(true);

            break;

        case "admin":

            new DashboardAdmin()
                    .setVisible(true);

            break;
    }

    view.dispose();
}
}