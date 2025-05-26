package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.monedero_virtual.controller.GestionRetirosController;
import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.utils.Sesion;
import co.edu.uniquindio.monedero_virtual.view.obeserver.ObserverManagement;
import co.edu.uniquindio.monedero_virtual.view.obeserver.ObserverView;
import co.edu.uniquindio.monedero_virtual.view.obeserver.TipoEvento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GestionRetirosViewController extends CoreViewController implements ObserverView {

    GestionRetirosController gestionRetirosController;

    Cliente clienteLogueado;

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
    private TableColumn<?, ?> descripcionColumn;

    @FXML
    private TextField descripcionField;

    @FXML
    private TableColumn<?, ?> fechaTransaccionColumn;

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
    private Button retiroButton;

    @FXML
    private TableView<?> retirosTable;

    @FXML
    private Label userNameLabel;

    @FXML
    void onAbrirNotificaciones(ActionEvent event) {

    }

    @FXML
    void onClearFields(ActionEvent event) {

    }

    @FXML
    void onRetiro(ActionEvent event) {

    }

    @FXML
    void initialize() {
        gestionRetirosController = new GestionRetirosController();
        clienteLogueado = (Cliente) Sesion.getInstance().getCliente();
        ObserverManagement.getInstance().addObserver(TipoEvento.CLIENTE, this);
        ObserverManagement.getInstance().addObserver(TipoEvento.CUENTA, this);
        ObserverManagement.getInstance().addObserver(TipoEvento.MONEDERO, this);
        initView();
        

    }

     private void initView() {
        mostrarInformacion();
    }

     private void mostrarInformacion() {
        String nombreCompleto = clienteLogueado.getNombreCompleto();
        String primerNombre = nombreCompleto.split(" ")[0]; // divide por espacios y toma el primero
        userNameLabel.setText("Tus depósitos, " + primerNombre);
    }

    @Override
    public void updateView(TipoEvento event) {
        switch (event) {
            case CLIENTE:
            mostrarInformacion();
                break;
        
            default:
                break;
        }
    
    }

}
