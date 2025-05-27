package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ResourceBundle;

import co.edu.uniquindio.monedero_virtual.controller.GestionRetirosController;
import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.model.Cuenta;
import co.edu.uniquindio.monedero_virtual.model.Monedero;
import co.edu.uniquindio.monedero_virtual.model.Notificacion;
import co.edu.uniquindio.monedero_virtual.model.Retiro;
import co.edu.uniquindio.monedero_virtual.model.Transaccion;
import co.edu.uniquindio.monedero_virtual.model.enums.TipoNotifiacion;
import co.edu.uniquindio.monedero_virtual.utils.Sesion;
import co.edu.uniquindio.monedero_virtual.view.obeserver.ObserverManagement;
import co.edu.uniquindio.monedero_virtual.view.obeserver.ObserverView;
import co.edu.uniquindio.monedero_virtual.view.obeserver.TipoEvento;
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

public class GestionRetirosViewController extends CoreViewController implements ObserverView {

    GestionRetirosController gestionRetirosController;
    Cliente clienteLogueado;
    ObservableList<Transaccion> listaRetiros = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button clearButton;

    @FXML
    private TableColumn<Retiro, String> cuentaColumn;

    @FXML
    private ComboBox<Cuenta> cuentaComboBox;

    @FXML
    private TableColumn<Retiro, String> descripcionColumn;

    @FXML
    private TextField descripcionField;

    @FXML
    private TableColumn<Retiro, String> fechaTransaccionColumn;

    @FXML
    private TableColumn<Retiro, String> idTransaccionColumn;

    @FXML
    private TableColumn<Retiro, String> monederoColumn;

    @FXML
    private ComboBox<Monedero> monederoComboBox;

    @FXML
    private TableColumn<Retiro, String> montoColumn;

    @FXML
    private TextField montoField;

    @FXML
    private Button notificationButton;

    @FXML
    private Button retiroButton;

    @FXML
    private TableView<Transaccion> retirosTable;

    @FXML
    private Label userNameLabel;

    @FXML
    void onAbrirNotificaciones(ActionEvent event) {
        Stage ownerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        openWindow("/co/edu/uniquindio/monedero_virtual/gestion-notificaciones-view.fxml", "Mis notificaciones",
                ownerStage);

    }

    @FXML
    void onClearFields(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    void onRetiro(ActionEvent event) {
        realizarRetiro();
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

    private void limpiarCampos() {
        montoField.clear();
        cuentaComboBox.getSelectionModel().clearSelection();
        monederoComboBox.getSelectionModel().clearSelection();
        descripcionField.clear();
    }

    private void initView() {
        initDataBinding();
        getRetiros();
        initializeDataCombobox();
        retirosTable.getItems().clear();
        retirosTable.setItems(listaRetiros);
        mostrarInformacion();
    }

    private void initDataBinding() {
        idTransaccionColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdTransaccion()));
        fechaTransaccionColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFechaTransaccion())));
        montoColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonto())));
        descripcionColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        cuentaColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCuenta())));
        monederoColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonedero())));
    }

    private void getRetiros() {
        int idCliente = clienteLogueado.getId();
        listaRetiros.clear();
        listaRetiros.addAll(gestionRetirosController.getRetirosCliente(idCliente));
    }

    private void initializeDataCombobox() {
        cuentaComboBox.getItems().clear();
        monederoComboBox.getItems().clear();
        int idCliente = clienteLogueado.getId();
        cuentaComboBox.getItems().addAll(gestionRetirosController.getCuentasUsuario(idCliente));
        monederoComboBox.getItems().addAll(gestionRetirosController.getMonederosCliente(idCliente));
    }

    private void mostrarInformacion() {
        String nombreCompleto = clienteLogueado.getNombreCompleto();
        String primerNombre = nombreCompleto.split(" ")[0]; // divide por espacios y toma el primero
        userNameLabel.setText("Tus retiros, " + primerNombre);
    }

    private void realizarRetiro() {
        Retiro retiro = buildRetiro();
        if (retiro == null) {
            mostrarMensaje("Error", "Datos no validos", "Error al construir el retiro", Alert.AlertType.ERROR);
            return;
        }

        if (validarDatos(retiro)) {
            retiro.setLimiteRetiro(retiro.getMonedero().getMonto());
            if (mostrarMensajeConfirmacion("Desea realizar el retiro?")) {
                if (gestionRetirosController.realizarRetiro(retiro)) {
                    listaRetiros.add(retiro);
                    mostrarMensaje("Información", "Retiro exitoso", "El retiro se ha realizado con éxito", Alert.AlertType.INFORMATION);
                    ObserverManagement.getInstance().notifyObservers(TipoEvento.RETIRO);
                    limpiarCampos();

                    Notificacion retirNotificacion = new Notificacion(
                            "Has retirado " + retiro.getMonto() + " de la cuenta " + retiro.getCuenta().getBanco() + "-"
                                    + retiro.getCuenta().getNumeroCuenta(),
                            TipoNotifiacion.INFORMACION,
                            LocalDate.now());
                    clienteLogueado.getListaNotificacion().add(retirNotificacion);
                } else {
                    mostrarMensaje("Error", "Retiro no realizado",
                            "No se pudo realizar el retiro.\nIntentó retirar más que la cantidad disponible en su monedero",
                            Alert.AlertType.ERROR);
                }
            } else {
                mostrarMensaje("Operación cancelada", "Retiro no realizado", "Ha cancelado el retiro",
                        Alert.AlertType.WARNING);
            }
        }

    }

    private Retiro buildRetiro() {
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

        return new Retiro(
                idTransaccion,
                LocalDate.now(),
                monto,
                descripcionField.getText(),
                cuentaComboBox.getValue(),
                0,
                monto * 0.05,
                monederoComboBox.getValue());
    }

    private boolean validarDatos(Retiro retiro) {
        String mensaje = "";

        if (retiro.getDescripcion().isEmpty()) {
            mensaje += "-La descripción es requerida.\n";

        }
        if (retiro.getMonedero() == null) {
            mensaje += "-El monedero es requerido.\n";
        }

        if (retiro.getCuenta() == null) {
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
