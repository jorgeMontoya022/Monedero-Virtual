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

public class GestionTransferenciasViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> amountColumn;

    @FXML
    private TextField amountField;

    @FXML
    private Button clearButton;

    @FXML
    private TableColumn<?, ?> dateColumn;

    @FXML
    private TableColumn<?, ?> descriptionColumn;

    @FXML
    private TextField descriptionField;

    @FXML
    private TableColumn<?, ?> destinationAccountColumn;

    @FXML
    private TextField destinationAccountField;

    @FXML
    private Button notificationButton;

    @FXML
    private TableColumn<?, ?> originAccountColumn;

    @FXML
    private ComboBox<?> originAccountComboBox;

    @FXML
    private Button transferButton;

    @FXML
    private TableColumn<?, ?> transferIdColumn;

    @FXML
    private TableView<?> transfersTable;

    @FXML
    private Label userNameLabel;

    @FXML
    private TableColumn<?, ?> walletColumn;

    @FXML
    private ComboBox<?> walletComboBox;

    @FXML
    void onAbrirNotificaciones(ActionEvent event) {

    }

    @FXML
    void onClearFields(ActionEvent event) {

    }

    @FXML
    void onTransfer(ActionEvent event) {

    }

    @FXML
    void initialize() {
       

    }

}
