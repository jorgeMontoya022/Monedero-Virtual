package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.monedero_virtual.controller.GestionPuntosController;
import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.model.enums.TipoRango;
import co.edu.uniquindio.monedero_virtual.utils.Sesion;
import co.edu.uniquindio.monedero_virtual.view.obeserver.ObserverManagement;
import co.edu.uniquindio.monedero_virtual.view.obeserver.ObserverView;
import co.edu.uniquindio.monedero_virtual.view.obeserver.TipoEvento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GestionPuntosViewController implements ObserverView {

    GestionPuntosController gestionPuntosController;
    Cliente clienteLogueado;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button canjearBeneficioButton;

    @FXML
    private Label beneficioLabel;

    @FXML
    private Label fechaActivacionLabel;

    @FXML
    private Button notificationButton;

    @FXML
    private Label puntosClienteLabel;

    @FXML
    private Label tipoRangoLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    void onAbrirNotificaciones(ActionEvent event) {

    }

    @FXML
    void onCanjearBeneficio(ActionEvent event) {

    }

    @FXML
    void initialize() {
        gestionPuntosController = new GestionPuntosController();
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
        userNameLabel.setText("Tus Puntos, " + primerNombre);

        

    }

    @Override
    public void updateView(TipoEvento event) {
        switch (event) {
            case CLIENTE:
                mostrarInformacion(clienteLogueado);
                break;
            case DEPOSITO:
                mostrarInformacion(clienteLogueado);
                break;
            case TRANSFERENCIA:
                mostrarInformacion(clienteLogueado);
                break;
            case RETIRO:
                mostrarInformacion(clienteLogueado);
                break;
            default:
                break;
        }
    }

}