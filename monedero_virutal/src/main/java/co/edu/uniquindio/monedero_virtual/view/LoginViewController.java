package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.uniquindio.monedero_virtual.controller.LoginController;
import co.edu.uniquindio.monedero_virtual.model.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginViewController extends CoreViewController {
    LoginController loginController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField emailField;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberCheckbox;

    @FXML
    void onIniciarSesión(ActionEvent event) {
        if (!validarCampos()) {
            return;

        }
        try{
            String correo = emailField.getText().trim();
            int contrasenia = parsearContrasenia(passwordField.getText());

            if(!validarCorreo(correo)) {
                 mostrarMensaje("Formato inválido", "Correo incorrecto", "El correo ingresado no es válido.", Alert.AlertType.WARNING);
                 emailField.requestFocus();
                 return;
            }

            Cliente clienteValidado = loginController.validarAcceso(correo, contrasenia);

            if (clienteValidado == null) {
                mostrarMensaje("Acceso denegado", "Credenciales inválidas", "Correo o contraseña incorrectos.", Alert.AlertType.ERROR);
                return;
            }

            loginController.guardarSesion(clienteValidado);
            abrirDashBoard(clienteValidado, event);
        }catch(Exception e) {
             mostrarMensaje("Error inesperado", "No se pudo iniciar sesión", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

   private int parsearContrasenia(String contrasenia) {
        if (contrasenia == null || contrasenia.trim().isEmpty()) {
            return -1;

        }
        try {
            return Integer.parseInt(contrasenia);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private boolean validarCampos() {
         if (emailField.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty()) {
            mostrarMensaje("Campos vacíos", "Faltan datos", "Por favor llene todos los datos", Alert.AlertType.WARNING);
            return false;

        }
        return true;
    
    }

     private boolean validarCorreo(String correo) {
         Pattern patron = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = patron.matcher(correo);
        return matcher.find();
    }

    private void abrirDashBoard(Cliente clienteValidado, ActionEvent event) {
        try{
            if (clienteValidado != null) {
                browseWindow("/co/edu/uniquindio/monedero_virtual/container-view.fxml", "Dashboard", event);
            }

        } catch (Exception e) {
            mostrarMensaje(
                    "Error de redirección",
                    "No se pudo cargar la siguiente ventana",
                    "Ocurrió un error al intentar cargar la ventana de usuario. Por favor intente nuevamente.",
                    Alert.AlertType.ERROR
            );
        }
    }

    @FXML
    void onIrRegistro(ActionEvent event) {
        if(mostrarMensajeConfirmacion("¿Deseas ir a la ventana de registro)")) {
            browseWindow("/co/edu/uniquindio/monedero_virtual/registro-view.fxml", "Solvi - Registro de usuario", event);


        }
        
    }

    @FXML
    void initialize() {
        loginController = new LoginController();
        emailField.setText("jorgetoro708@gmail.com");
        passwordField.setText(String.valueOf(1234));

    }

}
