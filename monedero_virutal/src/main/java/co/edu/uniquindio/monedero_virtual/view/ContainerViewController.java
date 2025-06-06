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
import co.edu.uniquindio.monedero_virtual.view.obeserver.ObserverManagement;
import co.edu.uniquindio.monedero_virtual.view.obeserver.ObserverView;
import co.edu.uniquindio.monedero_virtual.view.obeserver.TipoEvento;

public class ContainerViewController extends CoreViewController implements Initializable, ObserverView {

    Cliente clienteLogueado;

    @FXML
    private Button accountsButton;

    @FXML
    private Button transactionsButton;

    @FXML
    private Button transfersButton;

    

    @FXML
    private Button logoutButton;

    @FXML
    private Button toggleMenuButton;

    @FXML
    private Button retirosButton;

    @FXML
    private Button depositsButton;

    @FXML
    private Button customerDataButton;

    @FXML
    private Button puntosButton;

    @FXML
    private Button monederosButton;

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

    @FXML
    private Node datosClienteView;

    @FXML
    private Node gestionDepositosView;

    @FXML
    private Node gestionMonederosView;

    @FXML
    private Node gestionPuntosView;

    @FXML
    private Node gestionRetirosView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentSectionLabel.setText("Mis Cuentas");
        highlightSelectedButton("cuentas");

        clienteLogueado = (Cliente) Sesion.getInstance().getCliente();

        ObserverManagement.getInstance().addObserver(TipoEvento.CLIENTE, this);

        mostrarDatosCliente(clienteLogueado);

    }

    private void mostrarDatosCliente(Cliente clienteLogueado2) {
        String nombreCompleto = clienteLogueado.getNombreCompleto();
        String[] partes = nombreCompleto.trim().split("\\s+"); // Maneja múltiples espacios

        String primerNombre = partes.length > 0 ? partes[0] : "";
        String primerApellido = partes.length > 1 ? partes[1] : "";

        userNameLabel.setText(primerNombre + " " + primerApellido);
    }

    @FXML
    private void onDataCustomerClicked(ActionEvent event) {
        showView("perfil");
        currentSectionLabel.setText("Mi Perfil");
        highlightSelectedButton("perfil");
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
        showView("Retiros");
        currentSectionLabel.setText("Mis retiros");
        highlightSelectedButton("retiros");
    }

    @FXML
    private void onMonederosButtonClicked(ActionEvent event) {
        showView("Monederos");
        currentSectionLabel.setText("Mis monederos");
        highlightSelectedButton("monederos");

    }

    @FXML
    private void onPuntosButtonClicked(ActionEvent event) {
        showView("Puntos");
        currentSectionLabel.setText("Mis Puntos");
        highlightSelectedButton("puntos");
    }

    

    @FXML
    private void onDepositsButtonClicked(ActionEvent event) {
        // Esta funcionalidad aún no está implementada
        showView("depósitos");
        currentSectionLabel.setText("Mis depósitos");
        highlightSelectedButton("depósitos");
    }

    @FXML
    private void onLogoutButtonClicked(ActionEvent event) {
        if (mostrarMensajeConfirmacion("¿Deseas cerrar sesión?")) {
            Sesion.getInstance().closeSesion();
            browseWindow("/co/edu/uniquindio/monedero_virtual/bienvenida-view.fxml", "Solvi - Bienvenido/a", event);
        }

    }

    @FXML
    private void onToggleMenuButtonClicked(ActionEvent event) {

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
        datosClienteView.setVisible(false);
        gestionDepositosView.setVisible(false);
        gestionMonederosView.setVisible(false);
        gestionPuntosView.setVisible(false);
        gestionRetirosView.setVisible(false);

        // Mostrar la vista seleccionada
        switch (viewName) {
            case "perfil":
                datosClienteView.setVisible(true);
                break;
            case "cuentas":
                gestionCuentasView.setVisible(true);
                break;
            case "movimientos":
                gestionMovimientosView.setVisible(true);
                break;
            case "transferencias":
                gestionTransferenciasView.setVisible(true);
                break;
            case "depósitos":
                gestionDepositosView.setVisible(true);
                break;
            case "Monederos":
                gestionMonederosView.setVisible(true);
                break;
            case "Puntos":
                gestionPuntosView.setVisible(true);
                break;
            case "Retiros":
                gestionRetirosView.setVisible(true);
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
        datosClienteView.setVisible(false);
        gestionDepositosView.setVisible(false);
        gestionMonederosView.setVisible(false);
        gestionPuntosView.setVisible(false);
        gestionRetirosView.setVisible(false);

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
        retirosButton.setStyle(defaultStyle);
        monederosButton.setStyle(defaultStyle);
        puntosButton.setStyle(defaultStyle);
        depositsButton.setStyle(defaultStyle);
        customerDataButton.setStyle(defaultStyle);

        // Aplicar estilo al botón seleccionado
        switch (section) {
            case "perfil":
                customerDataButton.setStyle(selectedStyle);
                break;

            case "cuentas":
                accountsButton.setStyle(selectedStyle);
                break;
            case "movimientos":
                transactionsButton.setStyle(selectedStyle);
                break;
            case "transferencias":
                transfersButton.setStyle(selectedStyle);
                break;
            case "retiros":
                retirosButton.setStyle(selectedStyle);
                break;
            case "depósitos":
                depositsButton.setStyle(selectedStyle);
                break;
            case "monederos":
                monederosButton.setStyle(selectedStyle);
                break;
            case "puntos":
                puntosButton.setStyle(selectedStyle);
                break;

        }
    }

    @Override
    public void updateView(TipoEvento event) {
        switch (event) {
            case CLIENTE:
                mostrarDatosCliente(clienteLogueado);
                break;

            default:
                break;
        }
    }
}