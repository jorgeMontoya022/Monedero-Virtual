package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import co.edu.uniquindio.monedero_virtual.controller.GestionCuentasController;
import co.edu.uniquindio.monedero_virtual.controller.GestionMovimientosController;
import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.model.Cuenta;
import co.edu.uniquindio.monedero_virtual.model.Deposito;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.Node;

public class GestionMovimientosViewController extends CoreViewController {

    GestionMovimientosController gestionMovimientosController;

    Cliente clienteLogueado;

    ObservableList<Transaccion> listaTransacciones = FXCollections.observableArrayList();

    Transaccion transaccionSeleccionada;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Transaccion, String> accountColumn;

    @FXML
    private Button clearButton;

    @FXML
    private TableColumn<Transaccion, String> dateColumn;

    @FXML
    private TableColumn<Transaccion, String> descriptionColumn;

    @FXML
    private TableColumn<Transaccion, String> idColumn;

    @FXML
    private TableColumn<Transaccion, String> amountColumn;

    @FXML
    private ComboBox<Cuenta> cbCuenta;

    @FXML
    private Button notificationButton;

    @FXML
    private Button reportButton;

    @FXML
    private Button searchButton;

    @FXML
    private DatePicker dpFechaFin;

    @FXML
    private DatePicker dpFechaInicio;

    @FXML
    private Label totalTransactionsLabel;

    @FXML
    private TableView<Transaccion> transactionsTable;

    @FXML
    private TableColumn<Transaccion, String> typeColumn;

    @FXML
    private Label userNameLabel;

    @FXML
    private TableColumn<Transaccion, String> walletColumn;

    @FXML
    void onAbrirNotificaciones(ActionEvent event) {
        Stage ownerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        openWindow("/view/notification-view.fxml", "Notificaciones", ownerStage);

    }

    @FXML
    void onClearFilters(ActionEvent event) {
        limpiarFiltros();

    }

    @FXML
    void onGenerateReport(ActionEvent event) {

    }

    @FXML
    void onSearchTransactions(ActionEvent event) {
        buscarTransaccionesEntreFechas();

    }

    @FXML
    void initialize() {
        gestionMovimientosController = new GestionMovimientosController();
        clienteLogueado = (Cliente) Sesion.getInstance().getCliente();
        mostrarInformacion(clienteLogueado);
        initView();

    }

    private void mostrarInformacion(Cliente clienteLogueado) {
        String nombreCompleto = clienteLogueado.getNombreCompleto();
        String primerNombre = nombreCompleto.split(" ")[0]; // divide por espacios y toma el primero
        userNameLabel.setText("Tus Movimientos, " + primerNombre);

    }

    private void initView() {
        initDataBinding();
        getTransacciones();
        initializeDataCombobox();
        actualizarTotalTransacciones(listaTransacciones.size());
        transactionsTable.getItems().clear();
        transactionsTable.setItems(listaTransacciones);
        // listenerSelection();

    }

    private void initDataBinding() {
        accountColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCuenta().getBanco()
                        + " - " + cellData.getValue().getCuenta().getNumeroCuenta()));
        amountColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonto())));
        walletColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getMonedero().getNombreMonedero()));
        idColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIdTransaccion())));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getTipoTransaccion(cellData.getValue())));
        dateColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getFechaTransaccion().toString()));
        descriptionColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));

    }

    private String getTipoTransaccion(Transaccion transaccion) {
        if (transaccion instanceof Deposito) {
            return "Depósito";
        } else if (transaccion instanceof Transferencia) {
            return "Transferencia";
        } else {
            return "Retiro";
        }

    }

    private void getTransacciones() {
        int idCliente = clienteLogueado.getId();
        listaTransacciones.clear();
        listaTransacciones.addAll(gestionMovimientosController.getTrasaccionesCliente(idCliente));
    }

    private void buscarTransaccionesEntreFechas() {
        if (validarDatosBusqueda()) {
            Cuenta cuentaSeleccionada = cbCuenta.getValue();
            LocalDate fechaInicio = dpFechaInicio.getValue();
            LocalDate fechaFin = dpFechaFin.getValue();

            // Filtrar transacciones usando streams (similar al ejemplo que me mostraste)
            List<Transaccion> transaccionesFiltradas = listaTransacciones.stream()
                    .filter(transaccion -> transaccion.getCuenta().getNumeroCuenta() == cuentaSeleccionada
                            .getNumeroCuenta())
                    .filter(transaccion -> {
                        LocalDate fechaTransaccion = transaccion.getFechaTransaccion();
                        return (fechaTransaccion.isEqual(fechaInicio) || fechaTransaccion.isAfter(fechaInicio)) &&
                                (fechaTransaccion.isEqual(fechaFin) || fechaTransaccion.isBefore(fechaFin));
                    })
                    .collect(Collectors.toList());

            // Actualizar la tabla con los resultados filtrados
            transactionsTable.setItems(FXCollections.observableArrayList(transaccionesFiltradas));
            actualizarTotalTransacciones(transaccionesFiltradas.size());

            if (transaccionesFiltradas.isEmpty()) {
                mostrarMensaje("Resultado de búsqueda", "Sin resultados",
                        "No se encontraron transacciones en el rango de fechas seleccionado.",
                        Alert.AlertType.INFORMATION);
            }
        }
    }

    private boolean validarDatosBusqueda() {
        String mensaje = "";

        if (cbCuenta.getValue() == null) {
            mensaje += "- La cuenta es requerida.\n";
        }

        if (dpFechaInicio.getValue() == null) {
            mensaje += "- La fecha de inicio es requerida.\n";
        }

        if (dpFechaFin.getValue() == null) {
            mensaje += "- La fecha de fin es requerida.\n";
        }

        // Validar que la fecha de inicio no sea posterior a la fecha de fin
        if (dpFechaInicio.getValue() != null && dpFechaFin.getValue() != null) {
            if (dpFechaInicio.getValue().isAfter(dpFechaFin.getValue())) {
                mensaje += "- La fecha de inicio no puede ser posterior a la fecha de fin.\n";
            }
        }

        if (!mensaje.isEmpty()) {
            mostrarMensaje("Validación de búsqueda", "Datos no válidos", mensaje, Alert.AlertType.WARNING);
            return false;
        }

        return true;
    }

    private void limpiarFiltros() {
        cbCuenta.getSelectionModel().clearSelection();
        dpFechaInicio.setValue(null);
        dpFechaFin.setValue(null);

        // Mostrar todas las transacciones nuevamente
      
    }

    private void actualizarTotalTransacciones(int cantidad) {
        totalTransactionsLabel.setText("Total: " + cantidad + " transacciones");
    }

    private void initializeDataCombobox() {
        // Reutilizar el controller de cuentas para obtener las cuentas del cliente
        GestionCuentasController gestionCuentasController = new GestionCuentasController();
        ObservableList<Cuenta> cuentasCliente = FXCollections.observableArrayList(
                gestionCuentasController.getCuentasUsuario(clienteLogueado.getId()));

        initializeComboBox(cbCuenta, cuentasCliente,
                cuenta -> cuenta.getBanco() + " - " + cuenta.getNumeroCuenta());
    }

}