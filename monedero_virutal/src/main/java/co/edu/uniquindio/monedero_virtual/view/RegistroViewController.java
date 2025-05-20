package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.IllegalFormatException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.uniquindio.monedero_virtual.controller.RegistroController;
import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.model.enums.TipoRango;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class RegistroViewController extends CoreViewController {

    RegistroController registroController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dpFechaNacimiento;

    @FXML
    private Button registerButton;

    @FXML
    private TextField txtCelular;

    @FXML
    private PasswordField txtConfirmarContraseña;

    @FXML
    private PasswordField txtContraseña;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDirección;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombreCompleto;

    @FXML
    void onIniciarSesion(ActionEvent event) {
        browseWindow("/co/edu/uniquindio/monedero_virtual/login-view.fxml", "Solvi - Inicio de Sesión", event);

    }

    @FXML
    void onRegistrarUsuario(ActionEvent event) {
        registrarUsuario(event);
    }

    @FXML
    void onLimpiarCampos(ActionEvent event) {
        limpiarCampos();

    }

    @FXML
    void onRecordarDatos(ActionEvent event) {

    }

    @FXML
    void initialize() {
        registroController = new RegistroController();

    }

    private void registrarUsuario(ActionEvent event) {
        Cliente cliente = buildDataCliente();
        if (cliente == null) {
            mostrarMensaje("Notificación", "Registro en registro",
                    "No te pudiste registrar con éxito, vuelve a intentarlo mas tarde.", Alert.AlertType.ERROR);
            return;
        }

        if (validarDatos(cliente)) {
            if (registroController.registrarUsuario(cliente)) {
                mostrarMensaje("Notificación", "Usuario registrado", "Te has registrado correctamente",
                        Alert.AlertType.INFORMATION);
                browseWindow("/co/edu/uniquindio/monedero_virtual/login-view.fxml", "Solvi - Inicio de Sesión", event);
                limpiarCampos();
            } else {
                mostrarMensaje("Error", "Usuario no registrado", "El usuario no pudo ser registrado",
                        Alert.AlertType.ERROR);
            }
        }

    }

    private Cliente buildDataCliente() {
        int contrasenia = parsearContrasenia(txtContraseña.getText());
        int id = parsearId(txtId.getText());
        return new Cliente(
                txtNombreCompleto.getText(),
                txtCelular.getText(),
                txtCorreo.getText(),
                contrasenia,
                dpFechaNacimiento.getValue(),
                LocalDate.now(),
                txtDirección.getText(),
                TipoRango.BRONCE,
                id);
    }

    private int parsearId(String id) {
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

    private int parsearContrasenia(String contrasenia) {
        if (contrasenia == null || contrasenia.trim().isEmpty()) {
            return -1;

        }
        try {
            return Integer.parseInt(contrasenia);
        } catch (NumberFormatException e) {
            mostrarMensaje("Datos Invalidos", "Datos invalidos", "Por favor ingresa digitos en lugar de palabras",
                    Alert.AlertType.ERROR);
            return -1;
        }
    }

    private void limpiarCampos() {
    txtNombreCompleto.clear();
    txtCelular.clear();
    txtId.clear();
    txtCorreo.clear();
    txtDirección.clear();
    txtContraseña.clear();
    txtConfirmarContraseña.clear();
    dpFechaNacimiento.setValue(null); 
}

    private boolean validarDatos(Cliente cliente) {
        String mensaje = "";
        if (cliente.getNombreCompleto().isEmpty()) {
            mensaje += "- El nombre es requerido.\n";
        }

        if (String.valueOf(cliente.getContraseña()).isEmpty() || String.valueOf(cliente.getContraseña()) == null) {
            mensaje += "- Debes crear una contraseña";
        } else if (!validarContraseña(cliente.getContraseña())) {
            mensaje += "- La contraseña debe contener exactamente 4 dígitos numéricos diferentes.\n";
        }

        if (cliente.getEmail().isEmpty()) {
            mensaje += "- El correo electrónico es requerido.\n";
        } else if (!validarCorreo(cliente.getEmail())) {
            mensaje += "- El formato del correo es invalid.\n";
        }

        if (cliente.getDirección().isEmpty()) {
            mensaje += "- La dirección es requerida.\n";
        }

        if (cliente.getFechaNacimiento() == null) {
            mensaje += "- La fecha de nacimiento es requerida.\n";
        }

        if (cliente.getCelular().isEmpty()) {
            mensaje += "- El número de celular es requerido.\n";

        } else if (!validarTeledono(cliente.getCelular())) {
            mensaje += "- El teléfono ingresado no es valido.\n";
        }

        if (!confirmarContraseña(txtConfirmarContraseña.getText(), cliente.getContraseña())) {
            mensaje += "- La confirmación de la contraseña es incorrecta.\n";
        }

        if (String.valueOf(cliente.getId()).isEmpty() || String.valueOf(cliente.getId()) == null) {
            mensaje += "- El número de documento es requerido.\n";
        } else if (!validarId(cliente.getId())) {
            mensaje += "- La cedula no es valida";
        }

        if (!mensaje.isEmpty()) {
            mostrarMensaje("Notificación de validación", "Datos no validos", mensaje, Alert.AlertType.WARNING);
            return false;
        }

        return true;

    }

    private boolean validarId(int id) {
        String identificacion = String.valueOf(id);
        return (identificacion.length() == 8 || identificacion.length() == 10);
    }

    private boolean confirmarContraseña(String contraseñaConfirmada, int contraseña) {
        String str = String.valueOf(contraseña);

        if (str.equals(contraseñaConfirmada)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validarTeledono(String celular) {
        return (celular.length() == 10 && celular.startsWith("3") && celular.matches("\\d+"));
    }

    private boolean validarCorreo(String correo) {
        Pattern patron = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = patron.matcher(correo);
        return matcher.find();
    }

    private boolean validarContraseña(int contraseña) {
        String str = String.valueOf(contraseña);

        // Validar que tenga exactamente 4 dígitos
        if (str.length() != 4) {
            return false;
        }

        // Validar que todos los dígitos sean diferentes
        Set<Character> digitosUnicos = new HashSet<>();
        for (char c : str.toCharArray()) {
            digitosUnicos.add(c);
        }

        return digitosUnicos.size() == 4;
    }

}
