package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.monedero_virtual.controller.GestionMonederosController;
import co.edu.uniquindio.monedero_virtual.controller.GestionPuntosController;
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

public class GestionMonederosViewController extends CoreViewController implements ObserverView {

    GestionMonederosController gestionMonederosController;
    Cliente clienteLogueado;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button clearButton;

    @FXML
    private Button crearMonederoButton;

    @FXML
    private TableColumn<?, ?> cuentaAsociadaColumn;

    @FXML
    private ComboBox<?> cuentaComboBox;

    @FXML
    private TableColumn<?, ?> idMonederoColumn;

    @FXML
    private TextField idMonederoField;

    @FXML
    private TableView<?> monederosTable;

    @FXML
    private TableColumn<?, ?> montoActualColumn;

    @FXML
    private TableColumn<?, ?> nombreColumn;

    @FXML
    private TextField nombreMonederoField;

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
    void onCrearMonedero(ActionEvent event) {

    }

    @FXML
    void initialize() {
        gestionMonederosController = new GestionMonederosController();
        clienteLogueado = (Cliente) Sesion.getInstance().getCliente();
        mostrarInformacion(clienteLogueado);
        ObserverManagement.getInstance().addObserver(TipoEvento.CLIENTE, this);
        ObserverManagement.getInstance().addObserver(TipoEvento.DEPOSITO, this);
        ObserverManagement.getInstance().addObserver(TipoEvento.TRANSFERENCIA, this);
        ObserverManagement.getInstance().addObserver(TipoEvento.RETIRO, this);

    }

    private void mostrarInformacion(Cliente clienteLogueado) {
        String nombreCompleto = clienteLogueado.getNombreCompleto();
        String primerNombre = nombreCompleto.split(" ")[0]; // divide por espacios y toma el primero
        userNameLabel.setText("Tus Monederos, " + primerNombre);

    }

    @Override
    public void updateView(TipoEvento event) {
        switch (event) {
            case CLIENTE:
                mostrarInformacion(clienteLogueado);
                break;

            default:
                break;
        }
    }

}
