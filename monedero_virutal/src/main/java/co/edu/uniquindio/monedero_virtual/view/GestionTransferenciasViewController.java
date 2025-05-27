package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ResourceBundle;

import co.edu.uniquindio.monedero_virtual.controller.GestionTransferenciasController;
import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.model.Cuenta;
import co.edu.uniquindio.monedero_virtual.model.Monedero;
import co.edu.uniquindio.monedero_virtual.model.Notificacion;
import co.edu.uniquindio.monedero_virtual.model.Transaccion;
import co.edu.uniquindio.monedero_virtual.model.Transferencia;
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

public class GestionTransferenciasViewController extends CoreViewController implements ObserverView {

    GestionTransferenciasController transferenciasController;
    Cliente clienteLogueado;
    Transferencia transferenciaSeleccionada;
    ObservableList<Transaccion> listaTransferencias = FXCollections.observableArrayList();

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
    private Button revertTransferButton;

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
        Stage ownerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        openWindow("/co/edu/uniquindio/monedero_virtual/gestion-notificaciones-view.fxml", "Mis notificaciones",
                ownerStage);

    }

    @FXML
    void onClearFields(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    void onRevertTransfer(ActionEvent event) {
        revertirTransferencia();

    }

    @FXML
    void initialize() {
        transferenciasController = new GestionTransferenciasController();
        clienteLogueado = (Cliente) Sesion.getInstance().getCliente();
        ObserverManagement.getInstance().addObserver(TipoEvento.CUENTA, this);
        ObserverManagement.getInstance().addObserver(TipoEvento.MONEDERO, this);
        ObserverManagement.getInstance().addObserver(TipoEvento.CLIENTE, this);

        initView();

    }

    @FXML
    void onTransfer(ActionEvent event) {
        realizarTransferencia();
    }

    private void initView() {
        initDataBinding();
        getTransferencias();
        initializeDataCombobox();
        transfersTable.getItems().clear();
        transfersTable.setItems(listaTransferencias);
        listenerSelection();
        mostrarInformacion();
    }

    private void initDataBinding() {
        originAccountColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCuenta())));
        destinationAccountColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCuentaRecibe())));
        amountColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonto())));
        transferIdColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdTransaccion()));
        walletColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonedero())));
        dateColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFechaTransaccion())));
        descriptionColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
    }

    private void getTransferencias() {
        int idCliente = clienteLogueado.getId();
        listaTransferencias.clear();
        listaTransferencias.addAll(transferenciasController.getTransferenciasCliente(idCliente));
    }

    private void initializeDataCombobox() {
        int idCliente = clienteLogueado.getId();
        originAccountComboBox.getItems().clear();
        walletComboBox.getItems().clear();
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

    private void listenerSelection() {
        transfersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            transferenciaSeleccionada = (Transferencia) newSelection;
            mostrarInformacionCampos(transferenciaSeleccionada);

        });
    }

    private void mostrarInformacionCampos(Transferencia transferenciaSeleccionada) {
        if (transferenciaSeleccionada != null) {
            amountField.setText(String.valueOf(transferenciaSeleccionada.getMonto()));
            destinationAccountField
                    .setText(String.valueOf(transferenciaSeleccionada.getCuentaRecibe().getNumeroCuenta()));
            descriptionField.setText(transferenciaSeleccionada.getDescripcion());
            originAccountComboBox.setValue(transferenciaSeleccionada.getCuenta());
            walletComboBox.setValue(transferenciaSeleccionada.getMonedero());
        }
    }

    private void mostrarInformacion() {
        String nombreCompleto = clienteLogueado.getNombreCompleto();
        String primerNombre = nombreCompleto.split(" ")[0]; // divide por espacios y toma el primero
        userNameLabel.setText("Tus transferencias, " + primerNombre);
    }

    private void realizarTransferencia() {
        Transferencia transferencia = buildTransferencia();
        if (transferencia == null) {
            mostrarMensaje("Error", "Datos no válidos", "Error al construir la transferencia", Alert.AlertType.ERROR);
            return;
        }

        if (validarDatos(transferencia)) {
            if (mostrarMensajeConfirmacion("¿Desea realizar la transferencia")) {
                if (transferenciasController.realizarTransferencia(transferencia)) {
                    listaTransferencias.add(transferencia);
                    mostrarMensaje("Información", "Transferencia éxitosa", "La transferencia se ha realizado con éxito", Alert.AlertType.INFORMATION);
                    ObserverManagement.getInstance().notifyObservers(TipoEvento.TRANSFERENCIA);
                    limpiarCampos();

                     Notificacion transferenciaNotificacion = new Notificacion(
                            "Transferencia realizada de "+transferencia.getCuenta().getBanco()+"-"+transferencia.getCuentaRecibe().getNumeroCuenta() + "a "+transferencia.getCuentaRecibe().getBanco()+"-"+transferencia.getCuentaRecibe().getNumeroCuenta(),
                            TipoNotifiacion.INFORMACION,
                            LocalDate.now());
                    clienteLogueado.getListaNotificacion().add(transferenciaNotificacion);
                } else {
                    mostrarMensaje("Error", "Transferencia no realizada", "No se pudo realizar la transferencia",
                            Alert.AlertType.ERROR);
                }
            } else {
                mostrarMensaje("Operación cancelada", "Transferencia no realizada", "Ha cancelado la transferencia.",
                        Alert.AlertType.WARNING);
            }
        }
    }

    private void revertirTransferencia() {
    Cuenta cuentaSeleccionada = originAccountComboBox.getValue();

    if (cuentaSeleccionada == null) {
        mostrarMensaje("ERROR", "Cuenta no seleccionada",
                "Por favor, selecciona una cuenta para revertir la transferencia.", Alert.AlertType.WARNING);
        return;
    }

    if (mostrarMensajeConfirmacion("¿Deseás revertir la última transferencia de esta cuenta?")) {
        // Pedí la última transferencia antes de revertirla
        Transferencia ultimaTransferencia = transferenciasController.obtenerUltimaTransferencia(cuentaSeleccionada);

        if (ultimaTransferencia == null) {
            mostrarMensaje("Uff", "No se pudo revertir",
                    "No se encontró ninguna transferencia para esta cuenta.", Alert.AlertType.ERROR);
            return;
        }

        if (transferenciasController.revertirTransferencia(cuentaSeleccionada)) {

            // Eliminá visualmente la transferencia de la lista
            listaTransferencias.remove(ultimaTransferencia);

            mostrarMensaje("Información", "Reversión exitosa", "Se revirtió la última con éxito",
                    Alert.AlertType.INFORMATION);
            ObserverManagement.getInstance().notifyObservers(TipoEvento.TRANSFERENCIA);

            transfersTable.refresh(); // actualizá la tabla visualmente

             Notificacion transferenciaNotificacion = new Notificacion(
                        "Has revertido una transferencia",
                        TipoNotifiacion.INFORMACION,
                        LocalDate.now());
            clienteLogueado.getListaNotificacion().add(transferenciaNotificacion);

        } else {
            mostrarMensaje("Uff", "No se pudo revertir",
                    "Puede que no haya transferencias para revertir o error inesperado",
                    Alert.AlertType.ERROR);
        }
    }
}


    private Transferencia buildTransferencia() {
        String idTransaccion;
        SecureRandom random = new SecureRandom();
        idTransaccion = String.format("%09d", random.nextInt(100_000_000));

        // Parsear monto - solo verificar formato, no validar reglas de negocio
        double monto = 0;
        String montoText = amountField.getText();
        if (montoText != null && !montoText.trim().isEmpty()) {
            try {
                monto = Double.parseDouble(montoText);
            } catch (NumberFormatException e) {
                // No hacer nada, se validará en validarDatos()
            }
        }

        // Parsear número de cuenta destino - solo verificar formato
        int idCuentaDestino = 0;
        String idCuentaDestinoText = destinationAccountField.getText();
        if (idCuentaDestinoText != null && !idCuentaDestinoText.trim().isEmpty()) {
            try {
                idCuentaDestino = Integer.parseInt(idCuentaDestinoText);
            } catch (NumberFormatException e) {
                // No hacer nada, se validará en validarDatos()
            }
        }

        // Buscar cuenta destino (puede ser null si no existe)
        Cuenta cuentaDestino = null;
        if (idCuentaDestino > 0) {
            cuentaDestino = transferenciasController.buscarCuenta(idCuentaDestino);
        }

        // Construir transferencia siempre (incluso con valores por defecto)
        Transferencia transferencia = new Transferencia(
                idTransaccion,
                LocalDate.now(),
                monto,
                descriptionField.getText() != null ? descriptionField.getText() : "",
                originAccountComboBox.getValue(),
                cuentaDestino,
                walletComboBox.getValue());

        return transferencia;
    }

    private boolean validarDatos(Transferencia transferencia) {
        String mensaje = "";

        if (transferencia.getCuenta() == null) {
            mensaje += "- La cuenta de origen es requerida.\n";
        }

        if (transferencia.getCuentaRecibe() == null) {
            // Verificar si el campo está vacío o el número no es válido
            String cuentaDestinoText = destinationAccountField.getText();
            if (cuentaDestinoText == null || cuentaDestinoText.trim().isEmpty()) {
                mensaje += "- La cuenta de destino es requerida.\n";
            } else {
                try {
                    int numeroCuenta = Integer.parseInt(cuentaDestinoText.trim());
                    if (numeroCuenta <= 0) {
                        mensaje += "- El número de cuenta de destino debe ser mayor a cero.\n";
                    } else {
                        mensaje += "- La cuenta de destino no existe o no es válida.\n";
                    }
                } catch (NumberFormatException e) {
                    mensaje += "- El número de cuenta de destino debe ser un número válido.\n";
                }
            }
        }

        // Validar monto
        String montoText = amountField.getText();
        if (montoText == null || montoText.trim().isEmpty()) {
            mensaje += "- El monto es requerido.\n";
        } else {
            try {
                double monto = Double.parseDouble(montoText.trim());
                if (monto <= 0) {
                    mensaje += "- El monto debe ser mayor a cero.\n";
                }
            } catch (NumberFormatException e) {
                mensaje += "- El monto debe ser un número válido.\n";
            }
        }

        if (transferencia.getDescripcion() == null || transferencia.getDescripcion().trim().isEmpty()) {
            mensaje += "- La descripción es requerida.\n";
        }

        if (transferencia.getMonedero() == null) {
            mensaje += "- El monedero es requerido.\n";
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
            case MONEDERO:

                initializeDataCombobox();
                break;

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
