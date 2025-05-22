package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.monedero_virtual.controller.DatosClienteController;
import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.utils.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DatosClienteViewController {

    DatosClienteController datosClienteController;

    Cliente clienteLogueado;

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

    }

    @FXML
    void initialize() {
        datosClienteController = new DatosClienteController();
        clienteLogueado = (Cliente) Sesion.getInstance().getCliente();
        mostrarInformacion(clienteLogueado);


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
        String primerNombre = nombreCompleto.split(" ")[0]; // divide por espacios y toma el primero
        userNameLabel.setText("Tus datos, " + primerNombre);
    }

}
