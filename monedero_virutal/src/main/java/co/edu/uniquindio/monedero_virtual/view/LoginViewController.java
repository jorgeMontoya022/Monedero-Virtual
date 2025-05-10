package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class LoginViewController extends CoreViewController {

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
        mostrarMensaje("Error", "Error al iniciar sesión", 
        "Actualmente estamos realizando mejoras significativas en la experiencia de usuario. Esto puede provocar ciertos inconvenientes temporales en la funcionalidad de la plataforma. Le pedimos disculpas por las molestias ocasionadas y le agradecemos su comprensión y paciencia mientras resolvemos este inconveniente.", Alert.AlertType.ERROR);

    }

    @FXML
    void onIrRegistro(ActionEvent event) {
        browseWindow("/co/edu/uniquindio/monedero_virtual/registro-view.fxml", "Solvi - Registro de usuario", event);

    }

    @FXML
    void initialize() {
       

    }

}
