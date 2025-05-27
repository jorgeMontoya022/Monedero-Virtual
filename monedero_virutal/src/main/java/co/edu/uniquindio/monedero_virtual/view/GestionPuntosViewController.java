package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import co.edu.uniquindio.monedero_virtual.controller.GestionPuntosController;
import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.model.enums.Beneficio;
import co.edu.uniquindio.monedero_virtual.utils.Sesion;
import co.edu.uniquindio.monedero_virtual.view.obeserver.ObserverManagement;
import co.edu.uniquindio.monedero_virtual.view.obeserver.ObserverView;
import co.edu.uniquindio.monedero_virtual.view.obeserver.TipoEvento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class GestionPuntosViewController extends CoreViewController implements ObserverView {

    GestionPuntosController gestionPuntosController;
    Cliente clienteLogueado;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Beneficio> beneficiosComboBox;

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
        Stage ownerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        openWindow("/co/edu/uniquindio/monedero_virtual/gestion-notificaciones-view.fxml", "Mis notificaciones",
                ownerStage);

    }

    @FXML
    void onCanjearBeneficio(ActionEvent event) {
        canjearBeneficio();
    }

    @FXML
    void initialize() {
        gestionPuntosController = new GestionPuntosController();
        clienteLogueado = (Cliente) Sesion.getInstance().getCliente();
        mostrarInformacion(clienteLogueado);
        initializeDataCombobox();
        ObserverManagement.getInstance().addObserver(TipoEvento.CLIENTE, this);
        ObserverManagement.getInstance().addObserver(TipoEvento.DEPOSITO, this);
        ObserverManagement.getInstance().addObserver(TipoEvento.TRANSFERENCIA, this);
        ObserverManagement.getInstance().addObserver(TipoEvento.RETIRO, this);
    }

    private void mostrarInformacion(Cliente clienteLogueado) {
        String nombreCompleto = clienteLogueado.getNombreCompleto();
        String primerNombre = nombreCompleto.split(" ")[0]; // divide por espacios y toma el primero
        userNameLabel.setText("Tus Puntos, " + primerNombre);
        mostrarPuntosInformacion(clienteLogueado);
    }

    private void mostrarPuntosInformacion(Cliente cliente){
        puntosClienteLabel.setText(String.valueOf(cliente.getPuntos().getPuntosAcumulados()));
        tipoRangoLabel.setText(cliente.getTipoRango().toString());
        if(cliente.getPuntos().getFechaActivacion() != null){
            fechaActivacionLabel.setText(cliente.getPuntos().getFechaActivacion().toString());
        }else{
            fechaActivacionLabel.setText("No se ha activado algún beneficio");
        }
        if(cliente.getPuntos().getBeneficioActivo() != null){
            beneficioLabel.setText(cliente.getPuntos().getBeneficioActivo().toString());
        }else{
            beneficioLabel.setText("Ninguno");
        }
    }

    private void initializeDataCombobox() {
        beneficiosComboBox.getItems().clear();
        beneficiosComboBox.getItems().addAll(gestionPuntosController.getBeneficios());
    }

    private void canjearBeneficio(){
        Beneficio beneficio = beneficiosComboBox.getValue();
        if(beneficio == null){
            mostrarMensaje("Advertencia", "No seleccionó beneficio", "Debe seleccionar un beneficio", AlertType.WARNING);
            return;
        }else{
            clienteLogueado.getPuntos().setBeneficioActivo(beneficio);
            clienteLogueado.getPuntos().setFechaDeActivacion(LocalDate.now());
            ObserverManagement.getInstance().notifyObservers(TipoEvento.CLIENTE);
        }
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