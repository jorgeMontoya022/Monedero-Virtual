package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GestionCuentasViewController extends CoreViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> accountIdColumn;

    @FXML
    private TextField accountIdField;

    @FXML
    private TableView<?> accountsTable;

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<?, ?> amountColumn;

    @FXML
    private TableColumn<?, ?> bankNameColumn;

    @FXML
    private TextField bankNameField;

    @FXML
    private Button clearButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button notificationButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private Label userNameLabel;

    @FXML
    void onAddAccount(ActionEvent event) {

    }

    @FXML
    void onClearFields(ActionEvent event) {

    }

    @FXML
    void onDeleteAccount(ActionEvent event) {

    }

    @FXML
    void onSearchAccount(ActionEvent event) {

    }

     @FXML
    void onAbrirNotificaciones(ActionEvent event) {
        Stage ownerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        openWindow("/co/edu/uniquindio/monedero_virtual/gestion-notificaciones-view.fxml", "Mis notificaciones", ownerStage);
    }


    @FXML
    void initialize() {
       

    }

}