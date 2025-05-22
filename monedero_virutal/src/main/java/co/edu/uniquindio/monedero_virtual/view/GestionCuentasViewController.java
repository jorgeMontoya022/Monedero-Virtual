package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.monedero_virtual.controller.GestionCuentasController;
import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.model.Cuenta;
import co.edu.uniquindio.monedero_virtual.model.CuentaAhorrro;
import co.edu.uniquindio.monedero_virtual.model.CuentaCorriente;
import co.edu.uniquindio.monedero_virtual.utils.Sesion;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GestionCuentasViewController extends CoreViewController {

    GestionCuentasController gestionCuentasController;

    ObservableList<Cuenta> listaCuentas = FXCollections.observableArrayList();

    Cliente clienteLogueado;

    Cuenta cuentaSeleccionada;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Cuenta, String> accountIdColumn;

    @FXML
    private TextField accountIdField;

    @FXML
    private TableView<Cuenta> accountsTable;

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<Cuenta, String> amountColumn;

    @FXML
    private TableColumn<Cuenta, String> bankNameColumn;

    @FXML
    private TableColumn<Cuenta, String> accountTypeColumn;

    @FXML
    private ComboBox<String> accountTypeComboBox;

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
    private Label labelTotalCuentas;

    @FXML
    void onAddAccount(ActionEvent event) {
        agregarCuenta();

    }

    @FXML
    void onClearFields(ActionEvent event) {
        limpiarCampos();

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
        openWindow("/co/edu/uniquindio/monedero_virtual/gestion-notificaciones-view.fxml", "Mis notificaciones",
                ownerStage);
    }

    @FXML
    void initialize() {
        gestionCuentasController = new GestionCuentasController();
        clienteLogueado = (Cliente) Sesion.getInstance().getCliente();
        initView();

    }

    private void initView() {
        initDataBinding();
        getCuentas();
        initializeDataCombobox();
        accountsTable.getItems().clear();
        accountsTable.setItems(listaCuentas);
        // listenerSelection();
        mostrarInformacion();
        mostrarCantidadCuentas();
    }

    
    private void initDataBinding() {
        bankNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBanco()));
        accountIdColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNumeroCuenta())));
        accountTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getTipoCuenta(cellData.getValue())));
        amountColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonto())));

    }

    private void initializeDataCombobox() {
        accountTypeComboBox.getItems().addAll("Cuenta Ahorro", "Cuenta Corriente");
    }

    private String getTipoCuenta(Cuenta cuenta) {
        if (cuenta instanceof CuentaAhorrro) {
            return "Cuenta Ahorro";
        } else {
            return "Cuenta Corriente";
        }
    }

    private void getCuentas() {
        int idCliente = clienteLogueado.getId();
        listaCuentas.clear();
        listaCuentas.addAll(gestionCuentasController.getCuentasUsuario(idCliente));
    }

    private void mostrarInformacion() {
        String nombreCompleto = clienteLogueado.getNombreCompleto();
        String primerNombre = nombreCompleto.split(" ")[0]; // divide por espacios y toma el primero
        userNameLabel.setText("Tus cuentas, " + primerNombre);
        
    }

    private void mostrarCantidadCuentas() {
        labelTotalCuentas.setText("Total " + String.valueOf(listaCuentas.size()));
    
    }


    private void agregarCuenta() {
        Cuenta cuenta = buildDataCuenta();
        if (cuenta == null) {
            mostrarMensaje("Error", "Datos no validos", "El tipo de cuenta seleccionada no es valido",
                    Alert.AlertType.ERROR);
            return;
        }
        if (validarDatos(cuenta)) {
            if (gestionCuentasController.agregarCuenta(cuenta)) {
                listaCuentas.add(cuenta);
                mostrarMensaje("Notificación", "Cuenta agregada",
                        "La cuenta ha sido agregada con éxito", Alert.AlertType.INFORMATION);
                mostrarCantidadCuentas();
                limpiarCampos();
            } else {
                mostrarMensaje("Error", "Cuenta no agregada",
                        "La cuenta no pudo ser agregada", Alert.AlertType.ERROR);
            }
        }
    }

    private void limpiarCampos() {
       bankNameField.clear();
       accountIdField.clear();
       accountTypeComboBox.setValue(null);
    }

    private Cuenta buildDataCuenta() {
        String tipoCuenta = accountTypeComboBox.getValue();
        int idCuenta = parsearIdCuenta(accountIdField.getText());

        if (idCuenta == -1) {
            return null;
        }

        if ("Cuenta Ahorro".equals(tipoCuenta)) {
            return new CuentaAhorrro(
                    0,
                    bankNameField.getText(),
                    idCuenta,
                    clienteLogueado);

        } else if ("Cuenta Corriente".equals(tipoCuenta)) {
            return new CuentaCorriente(
                    0,
                    bankNameField.getText(),
                    idCuenta,
                    clienteLogueado);
        } else {
            return null;
        }
    }

    private int parsearIdCuenta(String id) {
        if (id == null || id.trim().isEmpty()) {
            return -1;
        }

        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            mostrarMensaje("Datos Invalidos", "Datos invalidos", "Por favor ingresa digitos en lugar de palabras",
                    Alert.AlertType.ERROR);
            return -1;

        }
    }

    private boolean validarDatos(Cuenta cuenta) {
        String mensaje = "";

        if (cuenta.getBanco().isEmpty()) {
            mensaje += "- El banco es requerido.\n";

        }

        if (String.valueOf(cuenta.getNumeroCuenta()).isEmpty() || String.valueOf(cuenta.getNumeroCuenta()) == null) {
            mensaje += "Debes crear un número para tu cuenta.\n";
        }

        if (accountTypeComboBox.getValue() == null || accountTypeComboBox.getValue().trim().isEmpty()) {
            mensaje += "- Debes seleccionar un tipo de cuenta.\n";
        }

        if (!mensaje.isEmpty()) {
            mostrarMensaje("Notificación de validación", "Datos no válidos", mensaje, Alert.AlertType.WARNING);
            return false;
        }

        return true;
    }

}