package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ResourceBundle;

import co.edu.uniquindio.monedero_virtual.controller.GestionDepositosController;
import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.model.Cuenta;
import co.edu.uniquindio.monedero_virtual.model.Deposito;
import co.edu.uniquindio.monedero_virtual.model.Monedero;
import co.edu.uniquindio.monedero_virtual.model.Transaccion;
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

public class GestionDepositoViewController extends CoreViewController {

    GestionDepositosController depositosController;
    Cliente clienteLogueado;
    ObservableList<Transaccion>  listaDepositos = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button clearButton;

    @FXML
    private TableColumn<Deposito, String> cuentaColumn;

    @FXML
    private ComboBox<Cuenta> cuentaComboBox;

    @FXML
    private Button depositButton;

    @FXML
    private TableView<Transaccion> depositosTable;

    @FXML
    private TableColumn<Deposito, String> descripcionColumn;

    @FXML
    private TextField descripcionField;

    @FXML
    private TableColumn<Deposito, String> fechaColumn;

    @FXML
    private TableColumn<Deposito, String> idTransaccionColumn;

    @FXML
    private TableColumn<Deposito, String> monederoColumn;

    @FXML
    private ComboBox<Monedero> monederoComboBox;

    @FXML
    private TableColumn<Deposito, String> montoColumn;

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
        limpiarCampos();
    }

    @FXML
    void initialize() {
        depositosController = new GestionDepositosController();
        clienteLogueado = (Cliente) Sesion.getInstance().getCliente();
        initView();
    }

    @FXML
    private void onDeposit(ActionEvent event) {
        Deposito deposito = depositoBuilder();
        if(depositosController.realizarDeposito(deposito)){
            mostrarMensaje("", "Depósito exitoso!",
                        "Se ha realizado un depósito de " + deposito.getMonto(), Alert.AlertType.CONFIRMATION);
        }else{
            mostrarMensaje("Error", "Se ha producido un error",
                        "No se realizó el depósito", Alert.AlertType.ERROR);
        }
    }

    private Deposito depositoBuilder() {
        String idTransaccion;
        SecureRandom random = new SecureRandom();
        idTransaccion = String.format("%09d", random.nextInt(100_000_000));

        String montoText = montoField.getText();
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

        if(descripcionField.getText() == null){
            mostrarMensaje("Advertencia", "Debe tener una descripción",
                    "Por favor, ingrese una descripción", Alert.AlertType.WARNING);
            return null;
        }

        if(cuentaComboBox.getValue() == null){
            mostrarMensaje("Advertencia", "Debe seleccionar una cuenta",
                    "Por favor, seleccione una cuenta", Alert.AlertType.WARNING);
            return null;
        }

        if(monederoComboBox.getValue() == null){
            mostrarMensaje("Advertencia", "Debe seleccionar un monedero",
                    "Por favor, seleccione un monedero", Alert.AlertType.WARNING);
            return null;
        }
    

        Deposito deposito = new Deposito(
                idTransaccion,
                LocalDate.now(),
                monto,
                descripcionField.getText(),
                cuentaComboBox.getValue(),
                monederoComboBox.getValue()
        );

        return deposito;
    }

    private void limpiarCampos() {
        montoField.clear();
        cuentaComboBox.getSelectionModel().clearSelection();
        monederoComboBox.getSelectionModel().clearSelection();
        descripcionField.clear();
    }

    private void initView() {
        initDataBinding();
        getDepositos();
        initializeDataCombobox();
        depositosTable.getItems().clear();
        depositosTable.setItems(listaDepositos);
        mostrarInformacion();
    }

    private void initDataBinding() {
        idTransaccionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdTransaccion()));
        fechaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFechaTransaccion())));
        montoColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonto())));
        cuentaColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCuenta())));
        monederoColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonedero())));
        descripcionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
    }

    private void getDepositos() {
        int idCliente = clienteLogueado.getId();
        listaDepositos.clear();
        listaDepositos.addAll(depositosController.getDepositosCliente(idCliente));
    }

    private void initializeDataCombobox() {
        int idCliente = clienteLogueado.getId();
        cuentaComboBox.getItems().addAll(depositosController.getCuentasUsuario(idCliente));
        monederoComboBox.getItems().addAll(depositosController.getMonederosCliente(idCliente));
    }

    private void mostrarInformacion() {
        String nombreCompleto = clienteLogueado.getNombreCompleto();
        String primerNombre = nombreCompleto.split(" ")[0]; // divide por espacios y toma el primero
        userNameLabel.setText("Tus depósitos, " + primerNombre);
    }

}