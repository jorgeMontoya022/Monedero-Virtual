package co.edu.uniquindio.monedero_virtual.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.utils.Sesion;

public class ContainerViewController implements Initializable {

    Cliente clienteLogueado;

    @FXML
    private Button accountsButton;

    @FXML
    private Button transactionsButton;

    @FXML
    private Button transfersButton;

    @FXML
    private Button statsButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button toggleMenuButton;

    @FXML
    private Button retirosButton;

    @FXML
    private Button depositsButton;

    @FXML
    private Label currentSectionLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private StackPane contentArea;

    // Referencias a las vistas incluidas con fx:include
    @FXML
    private Node gestionCuentasView;

    @FXML
    private Node gestionMovimientosView;

    @FXML
    private Node gestionTransferenciasView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentSectionLabel.setText("Mis Cuentas");
        highlightSelectedButton("cuentas");

        clienteLogueado = (Cliente) Sesion.getInstance().getCliente();
        
        mostrarDatosCliente(clienteLogueado);

    }

    private void mostrarDatosCliente(Cliente clienteLogueado2) {
        String nombreCompleto = clienteLogueado.getNombreCompleto();
        String[] partes = nombreCompleto.trim().split("\\s+"); // Maneja múltiples espacios

        String primerNombre = partes.length > 0 ? partes[0] : "";
        String primerApellido = partes.length > 1 ? partes[1] : "";

        userNameLabel.setText(primerNombre +" "+ primerApellido);
    }

    @FXML
    private void onAccountsButtonClicked(ActionEvent event) {
        showView("cuentas");
        currentSectionLabel.setText("Mis Cuentas");
        highlightSelectedButton("cuentas");
    }

    @FXML
    private void onTransactionsButtonClicked(ActionEvent event) {
        showView("movimientos");
        currentSectionLabel.setText("Movimientos");
        highlightSelectedButton("movimientos");
    }

    @FXML
    private void onTransfersButtonClicked(ActionEvent event) {
        showView("transferencias");
        currentSectionLabel.setText("Mis Transferencias");
        highlightSelectedButton("transferencias");
    }

    @FXML
    private void onRetirosButtonClicked(ActionEvent event) {
        // Esta funcionalidad aún no está implementada
        showMessage("Retiros");
        highlightSelectedButton("retiros");
    }

    @FXML
    private void onStatsButtonClicked(ActionEvent event) {
        // Esta funcionalidad aún no está implementada
        showMessage("Estadísticas");
        highlightSelectedButton("estadisticas");
    }

    @FXML
    private void onDepositsButtonClicked(ActionEvent event) {
        // Esta funcionalidad aún no está implementada
        showMessage("Depósitos");
        highlightSelectedButton("depósitos");
    }

    @FXML
    private void onLogoutButtonClicked(ActionEvent event) {
        // Implementar lógica de cierre de sesión
        System.out.println("Cerrando sesión...");
        // Aquí podría ir la lógica para volver a la pantalla de login
    }

    @FXML
    private void onToggleMenuButtonClicked(ActionEvent event) {
        // Implementar lógica para expandir/contraer el menú lateral
        System.out.println("Alternando visibilidad del menú...");
    }

    /**
     * Muestra la vista seleccionada y oculta las demás
     * 
     * @param viewName Nombre de la vista a mostrar (cuentas, movimientos)
     */
    private void showView(String viewName) {
        // Ocultar todas las vistas primero
        gestionCuentasView.setVisible(false);
        gestionMovimientosView.setVisible(false);
        gestionTransferenciasView.setVisible(false);

        // Mostrar la vista seleccionada
        switch (viewName) {
            case "cuentas":
                gestionCuentasView.setVisible(true);
                break;
            case "movimientos":
                gestionMovimientosView.setVisible(true);
                break;
            case "transferencias":
                gestionTransferenciasView.setVisible(true);
                break;
            default:
                // Por defecto mostrar la vista de cuentas
                gestionCuentasView.setVisible(true);
                break;
        }
    }

    /**
     * Muestra un mensaje para vistas no implementadas
     * 
     * @param sectionName Nombre de la sección
     */
    private void showMessage(String sectionName) {
        // Ocultar todas las vistas
        gestionCuentasView.setVisible(false);
        gestionMovimientosView.setVisible(false);
        gestionTransferenciasView.setVisible(false);

        // Mostrar mensaje en la etiqueta de sección
        currentSectionLabel.setText(sectionName + " (No implementado)");

        System.out.println("Vista de " + sectionName + ": Funcionalidad no implementada");
    }

    /**
     * Resalta el botón seleccionado en el menú
     * 
     * @param section Nombre de la sección cuyo botón debe resaltarse
     */
    private void highlightSelectedButton(String section) {
        // Estilo por defecto para todos los botones
        String defaultStyle = "-fx-background-color: transparent;";
        String selectedStyle = "-fx-background-color: rgba(102, 126, 234, 0.1); -fx-text-fill: #667eea;";

        // Restablecer todos los botones al estilo por defecto
        accountsButton.setStyle(defaultStyle);
        transactionsButton.setStyle(defaultStyle);
        transfersButton.setStyle(defaultStyle);
        statsButton.setStyle(defaultStyle);
        retirosButton.setStyle(defaultStyle);
        depositsButton.setStyle(defaultStyle);

        // Aplicar estilo al botón seleccionado
        switch (section) {
            case "cuentas":
                accountsButton.setStyle(selectedStyle);
                break;
            case "movimientos":
                transactionsButton.setStyle(selectedStyle);
                break;
            case "transferencias":
                transfersButton.setStyle(selectedStyle);
                break;
            case "estadisticas":
                statsButton.setStyle(selectedStyle);
                break;
            case "retiros":
                retirosButton.setStyle(selectedStyle);
                break;
            case "depósitos":
                depositsButton.setStyle(selectedStyle);
                break;

        }
    }
}