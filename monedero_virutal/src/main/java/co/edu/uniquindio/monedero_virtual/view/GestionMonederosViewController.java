package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import co.edu.uniquindio.monedero_virtual.controller.GestionMonederosController;
import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.model.Cuenta;
import co.edu.uniquindio.monedero_virtual.model.Monedero;
import co.edu.uniquindio.monedero_virtual.model.Notificacion;
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

public class GestionMonederosViewController extends CoreViewController implements ObserverView {

    GestionMonederosController gestionMonederosController;
    Cliente clienteLogueado;
    ObservableList<Monedero> listaMonederos = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button clearButton;

    @FXML
    private Button crearMonederoButton;

    @FXML
    private TableColumn<Monedero, String> cuentaAsociadaColumn;

    @FXML
    private ComboBox<Cuenta> cuentaComboBox;

    @FXML
    private TableColumn<Monedero, String> idMonederoColumn;

    @FXML
    private TextField idMonederoField;

    @FXML
    private TableView<Monedero> monederosTable;

    @FXML
    private TableColumn<Monedero, String> montoActualColumn;

    @FXML
    private TableColumn<Monedero, String> nombreColumn;

    @FXML
    private TextField nombreMonederoField;

    @FXML
    private TextField montoInicialField;

    @FXML
    private Button notificationButton;

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
    void onCrearMonedero(ActionEvent event) {
        agregarMonedero();
    }

    @FXML
    void initialize() {
        gestionMonederosController = new GestionMonederosController();
        clienteLogueado = (Cliente) Sesion.getInstance().getCliente();
        ObserverManagement.getInstance().addObserver(TipoEvento.CLIENTE, this);
        ObserverManagement.getInstance().addObserver(TipoEvento.DEPOSITO, this);
        ObserverManagement.getInstance().addObserver(TipoEvento.TRANSFERENCIA, this);
        ObserverManagement.getInstance().addObserver(TipoEvento.RETIRO, this);
        initView();
    }

    private void initView() {
        initDataBinding();
        getMonederos();
        initializeDataCombobox();
        monederosTable.getItems().clear();
        monederosTable.setItems(listaMonederos);
        mostrarInformacion();
    }

    private void initDataBinding() {
        idMonederoColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        nombreColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNombreMonedero())));
        cuentaAsociadaColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCuenta())));
        montoActualColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonto())));
    }

    private void getMonederos() {
        int idCliente = clienteLogueado.getId();
        listaMonederos.clear();
        listaMonederos.addAll(gestionMonederosController.getMonederosCliente(idCliente));
    }

    private void initializeDataCombobox() {
        cuentaComboBox.getItems().clear();
        int idCliente = clienteLogueado.getId();
        cuentaComboBox.getItems().addAll(gestionMonederosController.getCuentasUsuario(idCliente));
    }


    private void limpiarCampos() {
        cuentaComboBox.getSelectionModel().clearSelection();
        nombreMonederoField.clear();
        idMonederoField.clear();
        montoInicialField.clear();
    }

    private void mostrarInformacion() {
        String nombreCompleto = clienteLogueado.getNombreCompleto();
        String primerNombre = nombreCompleto.split(" ")[0]; // divide por espacios y toma el primero
        userNameLabel.setText("Tus Monederos, " + primerNombre);

    }


    private void agregarMonedero() {
        Monedero monedero = buildMonedero();
        if (monedero == null) {
            mostrarMensaje("Error", "Datos no validos", "Ingrese correctamente los datos para el monedero",
                    Alert.AlertType.ERROR);
            return;
        }
        if (validarDatos(monedero)) {
            if (gestionMonederosController.agregarMonedero(monedero)) {
                listaMonederos.add(monedero);
                
                mostrarMensaje("Notificación", "Monedero creado",
                        "El monedero ha sido creado con éxito", Alert.AlertType.INFORMATION);
                ObserverManagement.getInstance().notifyObservers(TipoEvento.MONEDERO);
                limpiarCampos();

                 Notificacion monederoNotificacion = new Notificacion(
                        "Has creado el monedero "+monedero.getNombreMonedero() ,
                        TipoNotifiacion.INFORMACION,
                        LocalDate.now());

                    clienteLogueado.getListaNotificacion().add(monederoNotificacion);
            } else {
                mostrarMensaje("Error", "Monedero no creado",
                        "No se pudo crear el monedero", Alert.AlertType.ERROR);
            }
        }
    }

    private Monedero buildMonedero() {
        int id = 0;
        String idText = idMonederoField.getText();
        if (idText != null && !idText.trim().isEmpty()) {
            try {
                id = Integer.parseInt(idText);

                if (id <= 0) {
                    mostrarMensaje("Error", "El id debe ser mayor a cero",
                            "Por favor, ingrese un id valido", Alert.AlertType.ERROR);
                    return null;
                }
            } catch (NumberFormatException e) {
                mostrarMensaje("Error", "El id debe ser un número",
                        "Por favor, ingrese un id válido", Alert.AlertType.ERROR);
                return null;
            }
        } else {
            mostrarMensaje("Error", "Id requerido",
                    "Por favor, ingrese un id válido", Alert.AlertType.ERROR);
            return null;
        }

        double montoInicial = 0;
        String montoText = montoInicialField.getText();
        if(montoText == null){
            montoInicial = 0;
        }else if (montoText != null && !montoText.trim().isEmpty()) {
            try {
                montoInicial = Double.parseDouble(montoText);

                if (montoInicial < 0) {
                    mostrarMensaje("Error", "El monto inicial debe ser mayor o igual a cero",
                            "Por favor, ingrese un monto inicial valido", Alert.AlertType.ERROR);
                    return null;
                }
            } catch (NumberFormatException e) {
                mostrarMensaje("Error", "El monto inicial debe ser un número",
                        "Por favor, ingrese un monto inicial válido", Alert.AlertType.ERROR);
                return null;
            }
        }

        return new Monedero(
                id,
                montoInicial,
                nombreMonederoField.getText(),
                cuentaComboBox.getValue());
    }

    private boolean validarDatos(Monedero monedero) {
        String mensaje = "";

        String idText = idMonederoField.getText();
        if (idText == null || idText.trim().isEmpty()) {
            mensaje += "-El id es requerido.\n";
        } else {
            try {
                int id = Integer.parseInt(idText.trim());
                if (id <= 0) {
                    mensaje += "- El id debe ser mayor a cero.\n";
                }
            } catch (NumberFormatException e) {
                mensaje += "-El id debe ser un número válido.\n";
            }
        }
        if (monedero.getNombreMonedero() == null) {
            mensaje += "-El nombre es requerido.\n";
        }
        if (monedero.getCuenta() == null) {
            mensaje += "-La cuenta es requerida.\n";
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
            case CLIENTE:
                mostrarInformacion();
                break;

            default:
                break;
        }

    }

}
