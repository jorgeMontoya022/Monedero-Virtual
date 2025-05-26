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
import co.edu.uniquindio.monedero_virtual.view.obeserver.ObserverManagement;
import co.edu.uniquindio.monedero_virtual.view.obeserver.ObserverView;
import co.edu.uniquindio.monedero_virtual.view.obeserver.TipoEvento;
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

public class GestionDepositoViewController extends CoreViewController implements ObserverView {

    GestionDepositosController depositosController;
    Cliente clienteLogueado;
    ObservableList<Transaccion> listaDepositos = FXCollections.observableArrayList();

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
        ObserverManagement.getInstance().addObserver(TipoEvento.CUENTA, this);
        ObserverManagement.getInstance().addObserver(TipoEvento.MONEDERO, this);
        ObserverManagement.getInstance().addObserver(TipoEvento.CLIENTE, this);
        initView();
    }

    @FXML
    private void onDeposit(ActionEvent event) {
        realizarDeposito();
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
        idTransaccionColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdTransaccion()));
        fechaColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFechaTransaccion())));
        montoColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonto())));
        cuentaColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCuenta())));
        monederoColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonedero())));
        descripcionColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
    }

    private void getDepositos() {
        int idCliente = clienteLogueado.getId();
        listaDepositos.clear();
        listaDepositos.addAll(depositosController.getDepositosCliente(idCliente));
    }

    private void initializeDataCombobox() {
        cuentaComboBox.getItems().clear();
        monederoComboBox.getItems().clear();
        int idCliente = clienteLogueado.getId();
        cuentaComboBox.getItems().addAll(depositosController.getCuentasUsuario(idCliente));
        monederoComboBox.getItems().addAll(depositosController.getMonederosCliente(idCliente));
    }

    private void mostrarInformacion() {
        String nombreCompleto = clienteLogueado.getNombreCompleto();
        String primerNombre = nombreCompleto.split(" ")[0]; // divide por espacios y toma el primero
        userNameLabel.setText("Tus depósitos, " + primerNombre);
    }

    private void realizarDeposito() {
        Deposito deposito = buildDeposito();
        if (deposito == null) {
            mostrarMensaje("Error", "Datos no validos", "Error al construir el depósito", Alert.AlertType.ERROR);
            return;
        }

        if (validarDatos(deposito)) {
            if (mostrarMensajeConfirmacion("Desea realizar el depósitov")) {
                if (depositosController.realizarDeposito(deposito)) {
                    listaDepositos.add(deposito);
                    ObserverManagement.getInstance().notifyObservers(TipoEvento.DEPOSITO);
                    limpiarCampos();
                } else {
                    mostrarMensaje("Error", "Depósito no realizado", "No se pudo realizar el depósito",
                            Alert.AlertType.ERROR);
                }
            } else {
                mostrarMensaje("Operación cancelada", "Depósito no realizado", "Ha cancelado le depósito",
                        Alert.AlertType.WARNING);
            }
        }

    }

    private Deposito buildDeposito() {
        String idTransaccion;
        SecureRandom random = new SecureRandom();
        idTransaccion = String.format("%09d", random.nextInt(100_000_000));

        double monto = 0;
        String montoText = montoField.getText();
        if (montoText != null && !montoText.trim().isEmpty()) {
            try {
                monto = Double.parseDouble(montoText);

                if (monto <= 0) {
                    mostrarMensaje("Error", "El monto debe ser mayor a cero",
                            "Por favor, ingrese un monto valido", Alert.AlertType.ERROR);
                    return null;
                }
            } catch (NumberFormatException e) {
                mostrarMensaje("Error", "El monto debe ser un número",
                        "Por favor, ingrese un monto válido", Alert.AlertType.ERROR);
                return null;
            }
        } else {
            mostrarMensaje("Error", "Monto requerido",
                    "Por favor, ingrese un monto válido", Alert.AlertType.ERROR);
            return null;
        }

        return new Deposito(
                idTransaccion,
                LocalDate.now(),
                monto,
                descripcionField.getText(),
                cuentaComboBox.getValue(),
                monederoComboBox.getValue());
    }

    private boolean validarDatos(Deposito deposito) {
        String mensaje = "";

        if (deposito.getDescripcion().isEmpty()) {
            mensaje += "-La descripción es requerida.\n";

        }
        if (deposito.getMonedero() == null) {
            mensaje += "-El monedero es requerido.\n";
        }

        if (deposito.getCuenta() == null) {
            mensaje += "-La cuenta es requerida.\n";
        }

        String montoText = montoField.getText();
        if (montoText == null || montoText.trim().isEmpty()) {
            mensaje += "-El monto es requerido.\n";
        } else {
            try {
                double monto = Double.parseDouble(montoText.trim());
                if (monto <= 0) {
                    mensaje += "- El monto debe ser mayor a cero.\n";
                }
            } catch (NumberFormatException e) {
                mensaje += "-El monto debe ser un número válido.\n";
            }
        }

        if (!mensaje.isEmpty()) {
            mostrarMensaje("Notificación de validación", "Datos no válidos", mensaje, Alert.AlertType.WARNING);
            return false;
        }
        return true;

    }

    @Override
    public void updateView(TipoEvento event) {
        switch (event) {
            case CUENTA:
                initializeDataCombobox();
                break;
            case MONEDERO:
                initializeDataCombobox();
            case CLIENTE:
                mostrarInformacion();
                break;

            default:
                break;
        }

    }

}