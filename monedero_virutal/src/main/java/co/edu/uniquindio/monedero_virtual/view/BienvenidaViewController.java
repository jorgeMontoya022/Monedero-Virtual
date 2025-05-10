package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class BienvenidaViewController extends CoreViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    void OnIrInicioSesion(ActionEvent event) {
        browseWindow("/co/edu/uniquindio/monedero_virtual/login-view.fxml", "Solvi - Inicio de Sesión", event);

    }

    @FXML
    void onIrRegistro(ActionEvent event) {
        browseWindow("/co/edu/uniquindio/monedero_virtual/registro-view.fxml", "Solvi - Registro de usuario", event);

    }

    @FXML
    void initialize() {
       

    }

}
