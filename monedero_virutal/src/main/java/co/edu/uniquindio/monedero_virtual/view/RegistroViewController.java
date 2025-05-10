package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistroViewController extends CoreViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dpFechaNacimiento;

    @FXML
    private Button registerButton;

    @FXML
    private TextField txtCelular;

    @FXML
    private PasswordField txtConfirmarContraseña;

    @FXML
    private PasswordField txtContraseña;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDirección;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombreCompleto;

    @FXML
    void onIniciarSesion(ActionEvent event) {
        browseWindow("/co/edu/uniquindio/monedero_virtual/login-view.fxml", "Solvi - Inicio de Sesión", event);

    }

    @FXML
    void onRegistrarUsuario(ActionEvent event) {
          mostrarMensaje("Error", "Error al registrarse", 
        "Actualmente estamos realizando mejoras significativas en la experiencia de usuario. Esto puede provocar ciertos inconvenientes temporales en la funcionalidad de la plataforma. Le pedimos disculpas por las molestias ocasionadas y le agradecemos su comprensión y paciencia mientras resolvemos este inconveniente.", Alert.AlertType.ERROR);

    }

    @FXML
    void initialize() {
        

    }

}
