package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GestionMovimientosViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> accountColumn;

    @FXML
    private Button clearButton;

    @FXML
    private TableColumn<?, ?> dateColumn;

    @FXML
    private TableColumn<?, ?> descriptionColumn;

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private Button notificationButton;

    @FXML
    private Button reportButton;

    @FXML
    private Button searchButton;

    @FXML
    private Label totalTransactionsLabel;

    @FXML
    private TableView<?> transactionsTable;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    private Label userNameLabel;

    @FXML
    private TableColumn<?, ?> walletColumn;

    @FXML
    void onAbrirNotificaciones(ActionEvent event) {

    }

    @FXML
    void onClearFilters(ActionEvent event) {

    }

    @FXML
    void onGenerateReport(ActionEvent event) {

    }

    @FXML
    void onSearchTransactions(ActionEvent event) {

    }

    @FXML
    void initialize() {
    

    }

}