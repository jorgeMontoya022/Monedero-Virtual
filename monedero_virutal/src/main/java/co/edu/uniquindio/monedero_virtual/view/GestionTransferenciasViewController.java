package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ResourceBundle;

import co.edu.uniquindio.monedero_virtual.controller.GestionTransferenciasController;
import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.model.Cuenta;
import co.edu.uniquindio.monedero_virtual.model.Monedero;
import co.edu.uniquindio.monedero_virtual.model.Transaccion;
import co.edu.uniquindio.monedero_virtual.model.Transferencia;
import co.edu.uniquindio.monedero_virtual.utils.Sesion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GestionTransferenciasViewController extends CoreViewController{

    GestionTransferenciasController transferenciasController;
    Cliente clienteLogueado;
    ObservableList<Transaccion>  listaTransferencias = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Transferencia, String> amountColumn;

    @FXML
    private TextField amountField;

    @FXML
    private Button clearButton;

    @FXML
    private TableColumn<Transferencia, String> dateColumn;

    @FXML
    private TableColumn<Transferencia, String> descriptionColumn;

    @FXML
    private TextField descriptionField;

    @FXML
    private TableColumn<Transferencia, String> destinationAccountColumn;

    @FXML
    private TextField destinationAccountField;

    @FXML
    private Button notificationButton;

    @FXML
    private TableColumn<Transferencia, String> originAccountColumn;

    @FXML
    private ComboBox<Cuenta> originAccountComboBox;

    @FXML
    private Button transferButton;

    @FXML
    private TableColumn<Transferencia, String> transferIdColumn;

    @FXML
    private TableView<Transaccion> transfersTable;

    @FXML
    private Label userNameLabel;

    @FXML
    private TableColumn<Transferencia, String> walletColumn;

    @FXML
    private ComboBox<Monedero> walletComboBox;

    @FXML
    void onAbrirNotificaciones(ActionEvent event) {

    }

    @FXML
    void onClearFields(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    void initialize() {
        transferenciasController = new GestionTransferenciasController();
        clienteLogueado = (Cliente) Sesion.getInstance().getCliente();
        initView();
    }

    @FXML
    void onTransfer(ActionEvent event) {
        Transferencia transferencia = transferenciaBuilder();
        if(transferenciasController.realizarTransferencia(transferencia)){
            mostrarMensaje("", "Transferencia exitosa!",
                        "Se ha realizado una transferencia de " + transferencia.getMonto(), Alert.AlertType.CONFIRMATION);
        }else{
            mostrarMensaje("Error", "Se ha producido un error",
                        "No se realizó la transferencia", Alert.AlertType.ERROR);
        }
    }

    private Transferencia transferenciaBuilder() {
        String idTransaccion;
        SecureRandom random = new SecureRandom();
        idTransaccion = String.format("%09d", random.nextInt(100_000_000));

        String montoText = amountField.getText();
        double monto;
        try {
            monto = Double.parseDouble(montoText);
            if (monto <= 0) {
                mostrarMensaje("Error", "El monto debe ser mayor a cero",
                        "Por favor, ingrese un monto válido", Alert.AlertType.ERROR);
                return null;
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Error", "El monto debe ser un número",
                    "Por favor, ingrese un monto válido", Alert.AlertType.ERROR);
            return null;
        }

        String idCuentaDestinoText = destinationAccountField.getText();
        int idCuentaDestino;
        try {
            idCuentaDestino = Integer.parseInt(idCuentaDestinoText);
        } catch (NumberFormatException e) {
            mostrarMensaje("Advertencia", "Debe enviarse a una cuenta",
                    "Por favor, ingrese el número de la cuenta destinataria", Alert.AlertType.WARNING);
            return null;
        }

        if(descriptionField.getText() == null){
            mostrarMensaje("Advertencia", "Debe tener una descripción",
                    "Por favor, ingrese una descripción", Alert.AlertType.WARNING);
            return null;
        }

        if(originAccountComboBox.getValue() == null){
            mostrarMensaje("Advertencia", "Debe seleccionar una cuenta",
                    "Por favor, seleccione una cuenta", Alert.AlertType.WARNING);
            return null;
        }

        if(walletComboBox.getValue() == null){
            mostrarMensaje("Advertencia", "Debe seleccionar un monedero",
                    "Por favor, seleccione un monedero", Alert.AlertType.WARNING);
            return null;
        }

        Cuenta cuentaDestino;
        cuentaDestino = transferenciasController.buscarCuenta(idCuentaDestino);
        if(cuentaDestino == null){
            mostrarMensaje("Error", "No se encontró la cuenta destinataria",
                    "Por favor, ingrese el número de una cuenta existente", Alert.AlertType.ERROR);
            return null;
        }

        Transferencia transferencia = new Transferencia(
                idTransaccion,
                LocalDate.now(),
                monto,
                descriptionField.getText(),
                originAccountComboBox.getValue(),
                cuentaDestino,
                walletComboBox.getValue()
        );

        return transferencia;
    }

    private void initView() {
        initDataBinding();
        getTransferencias();
        initializeDataCombobox();
        transfersTable.getItems().clear();
        transfersTable.setItems(listaTransferencias);
        mostrarInformacion();
    }

    private void initDataBinding() {
        originAccountColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCuenta())));
        destinationAccountColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCuentaRecibe())));
        amountColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonto())));
        transferIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdTransaccion()));
        walletColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonedero())));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFechaTransaccion())));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
    }

    private void getTransferencias() {
        int idCliente = clienteLogueado.getId();
        listaTransferencias.clear();
        listaTransferencias.addAll(transferenciasController.getTransferenciasCliente(idCliente));
    }

    private void initializeDataCombobox() {
        int idCliente = clienteLogueado.getId();
        originAccountComboBox.getItems().addAll(transferenciasController.getCuentasUsuario(idCliente));
        walletComboBox.getItems().addAll(transferenciasController.getMonederosCliente(idCliente));
    }

    private void limpiarCampos() {
        originAccountComboBox.getSelectionModel().clearSelection();
        walletComboBox.getSelectionModel().clearSelection();
        amountField.clear();
        destinationAccountField.clear();
        descriptionField.clear();
    }

    private void mostrarInformacion() {
        String nombreCompleto = clienteLogueado.getNombreCompleto();
        String primerNombre = nombreCompleto.split(" ")[0]; // divide por espacios y toma el primero
        userNameLabel.setText("Tus transferencias, " + primerNombre);
    }


}
