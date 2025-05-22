package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GestionDepositoViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button clearButton;

    @FXML
    private TableColumn<?, ?> cuentaColumn;

    @FXML
    private ComboBox<?> cuentaComboBox;

    @FXML
    private Button depositButton;

    @FXML
    private TableView<?> depositosTable;

    @FXML
    private TableColumn<?, ?> descripcionColumn;

    @FXML
    private TextField descripcionField;

    @FXML
    private TableColumn<?, ?> fechaColumn;

    @FXML
    private TableColumn<?, ?> idTransaccionColumn;

    @FXML
    private TableColumn<?, ?> monederoColumn;

    @FXML
    private ComboBox<?> monederoComboBox;

    @FXML
    private TableColumn<?, ?> montoColumn;

    @FXML
    private TextField montoField;

    @FXML
    private Button notificationButton;

    @FXML
    private Label userNameLabel;

    @FXML
    void onAbrirNotificaciones(ActionEvent event) {

    }

    @FXML
    void onClearFields(ActionEvent event) {

    }

    @FXML
    void onDeposit(ActionEvent event) {

    }

    @FXML
    void initialize() {
       

    }

}