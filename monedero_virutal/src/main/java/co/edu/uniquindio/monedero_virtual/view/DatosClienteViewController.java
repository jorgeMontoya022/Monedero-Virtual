package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.uniquindio.monedero_virtual.controller.DatosClienteController;
import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.utils.Sesion;
import co.edu.uniquindio.monedero_virtual.view.obeserver.ObserverManagement;
import co.edu.uniquindio.monedero_virtual.view.obeserver.ObserverView;
import co.edu.uniquindio.monedero_virtual.view.obeserver.TipoEvento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DatosClienteViewController extends CoreViewController implements ObserverView {

    DatosClienteController datosClienteController;
    Cliente clienteLogueado;
    private boolean isEditing = false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnEditarPerfil;

    @FXML
    private Button notificationButton;

    @FXML
    private TextField txtCelular;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombreCompleto;

    @FXML
    private TextField txtRango;

    @FXML
    private DatePicker dpFechaNacimiento;

    @FXML
    private Label userNameLabel;

    @FXML
    void onAbrirNotificaciones(ActionEvent event) {

    }

    @FXML
    void onEditarPerfil(ActionEvent event) {
        if (!isEditing) {
            // Habilitar modo edición
            enableEditingMode();
        } else {
            // Confirmar cambios
            updateClient();
        }
    }

    private void enableEditingMode() {
        // Habilitar campos para edición (excepto ID y Rango)
        txtNombreCompleto.setDisable(false);
        txtNombreCompleto.setEditable(true);

        txtCorreo.setDisable(false);
        txtCorreo.setEditable(true);

        txtCelular.setDisable(false);
        txtCelular.setEditable(true);

        txtDireccion.setDisable(false);
        txtDireccion.setEditable(true);

        dpFechaNacimiento.setDisable(false);

        // Cambiar texto del botón
        btnEditarPerfil.setText("Confirmar Cambios");
        isEditing = true;
    }

    private void disableEditingMode() {
        // Deshabilitar campos
        txtNombreCompleto.setDisable(true);
        txtNombreCompleto.setEditable(false);

        txtCorreo.setDisable(true);
        txtCorreo.setEditable(false);

        txtCelular.setDisable(true);
        txtCelular.setEditable(false);

        txtDireccion.setDisable(true);
        txtDireccion.setEditable(false);

        dpFechaNacimiento.setDisable(true);

        // Restaurar texto del botón
        btnEditarPerfil.setText("Editar Perfil");
        isEditing = false;
    }

    private void updateClient() {
        boolean clientUpdated = false;
        Cliente clienteData = buildClienteData();

        if (validateData(clienteData)) {
            if (!hasChanges(clienteLogueado, clienteData)) {
                mostrarMensaje("Información", "No se han realizado cambios",
                        "Por favor, realice cambios para actualizar", Alert.AlertType.INFORMATION);
                return;
            }

            clientUpdated = datosClienteController.actualizarCliente(clienteLogueado, clienteData);

            if (clientUpdated) {
                mostrarMensaje("Información", "Cliente actualizado",
                        "El cliente ha sido actualizado correctamente", Alert.AlertType.INFORMATION);

                // Actualizar el cliente en sesión
                clienteLogueado = datosClienteController.getClienteById(clienteLogueado.getId());
                Sesion.getInstance().setCliente(clienteLogueado);
                ObserverManagement.getInstance().notifyObservers(TipoEvento.CLIENTE);

                // Actualizar la vista
                mostrarInformacion(clienteLogueado);
                disableEditingMode();
            } else {
                mostrarMensaje("Error", "Cliente no actualizado",
                        "No se pudo actualizar el cliente", Alert.AlertType.ERROR);
            }
        }
    }

    private boolean hasChanges(Cliente clienteOriginal, Cliente clienteNuevo) {
        return !clienteOriginal.getNombreCompleto().equals(clienteNuevo.getNombreCompleto()) ||
                !clienteOriginal.getEmail().equals(clienteNuevo.getEmail()) ||
                !clienteOriginal.getCelular().equals(clienteNuevo.getCelular()) ||
                !clienteOriginal.getDirección().equals(clienteNuevo.getDirección()) ||
                !clienteOriginal.getFechaNacimiento().equals(clienteNuevo.getFechaNacimiento());
    }

    private Cliente buildClienteData() {
        Cliente clienteData = new Cliente();
        clienteData.setId(clienteLogueado.getId());
        clienteData.setNombreCompleto(txtNombreCompleto.getText().trim());
        clienteData.setEmail(txtCorreo.getText().trim());
        clienteData.setCelular(txtCelular.getText().trim());
        clienteData.setDirección(txtDireccion.getText().trim());
        clienteData.setFechaNacimiento(dpFechaNacimiento.getValue());
        clienteData.setTipoRango(clienteLogueado.getTipoRango()); // No se cambia el rango

        // Mantener otros datos que no se editan
        if (clienteLogueado.getListaCuentas() != null) {
            clienteData.setListaCuentas(clienteLogueado.getListaCuentas());
        }
        if (clienteLogueado.getListaNotificacion() != null) {
            clienteData.setListaNotificacion(clienteLogueado.getListaNotificacion());
        }

        return clienteData;
    }

    private boolean validateData(Cliente cliente) {
        String mensaje = "";

        if (cliente.getNombreCompleto() == null || cliente.getNombreCompleto().trim().isEmpty()) {
            mensaje += "- El nombre completo es requerido.\n";
        }

        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            mensaje += "- El correo electrónico es requerido.\n";
        } else if (!validarCorreo(cliente.getEmail().trim())) {
            mensaje += "- El formato del correo es inválido.\n";
        }

        if (cliente.getDirección() == null || cliente.getDirección().trim().isEmpty()) {
            mensaje += "- La dirección es requerida.\n";
        }

        if (cliente.getFechaNacimiento() == null) {
            mensaje += "- La fecha de nacimiento es requerida.\n";
        } else if (!esAdulto(cliente.getFechaNacimiento())) {
            mensaje += "- Debes ser mayor de edad.\n";
        }

        if (cliente.getCelular() == null || cliente.getCelular().trim().isEmpty()) {
            mensaje += "- El número de celular es requerido.\n";
        } else if (!validarTelefono(cliente.getCelular().trim())) {
            mensaje += "- El teléfono ingresado no es válido.\n";
        }

        if (String.valueOf(cliente.getId()).isEmpty() || String.valueOf(cliente.getId()) == null) {
            mensaje += "- El número de documento es requerido.\n";
        }

        if (!mensaje.isEmpty()) {
            mostrarMensaje("Notificación de validación", "Datos no válidos", mensaje, Alert.AlertType.WARNING);
            return false;
        }

        return true;
    }

    private boolean validarTelefono(String celular) {
        return (celular.length() == 10 && celular.startsWith("3") && celular.matches("\\d+"));
    }

    private boolean validarCorreo(String correo) {
        Pattern patron = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = patron.matcher(correo);
        return matcher.find();
    }

   
    private boolean esAdulto(LocalDate fechaNacimiento) {
        return fechaNacimiento != null && fechaNacimiento.plusYears(18).isBefore(LocalDate.now().plusDays(1));
    }

    @FXML
    void initialize() {
        datosClienteController = new DatosClienteController();
        clienteLogueado = (Cliente) Sesion.getInstance().getCliente();
        ObserverManagement.getInstance().addObserver(TipoEvento.CUENTA, this);
        ObserverManagement.getInstance().addObserver(TipoEvento.TRANSFERENCIA, this);
        ObserverManagement.getInstance().addObserver(TipoEvento.RETIRO, this);
        ObserverManagement.getInstance().addObserver(TipoEvento.DEPOSITO, this);

        mostrarInformacion(clienteLogueado);

        // Inicializar campos como deshabilitados
        disableEditingMode();
    }

    private void mostrarInformacion(Cliente clienteLogueado) {
        txtNombreCompleto.setText(clienteLogueado.getNombreCompleto());
        txtCorreo.setText(clienteLogueado.getEmail());
        txtCelular.setText(clienteLogueado.getCelular());
        txtDireccion.setText(clienteLogueado.getDirección());
        txtId.setText(String.valueOf(clienteLogueado.getId()));
        txtRango.setText(String.valueOf(clienteLogueado.getTipoRango()));
        dpFechaNacimiento.setValue(clienteLogueado.getFechaNacimiento());

        String nombreCompleto = clienteLogueado.getNombreCompleto();
        if (nombreCompleto != null && !nombreCompleto.isEmpty()) {
            String primerNombre = nombreCompleto.split(" ")[0];
            userNameLabel.setText("Tus datos, " + primerNombre);
        }
    }

    @Override
    public void updateView(TipoEvento event) {
        switch (event) {
            case CUENTA:
                mostrarInformacion(clienteLogueado);
                break;
            case TRANSFERENCIA:
                mostrarInformacion(clienteLogueado);
                break;
            case DEPOSITO:
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