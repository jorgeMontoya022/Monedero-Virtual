package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class NotificacionesViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnLimpiarNotificaciones;

    @FXML
    private Button btnMarcarLeido;

    @FXML
    private Button closeButton;

    @FXML
    private TableColumn<?, ?> dateColumn;

    @FXML
    private TableColumn<?, ?> messageColumn;

    @FXML
    private TableView<?> notificationsTable;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    void onCloseNotifications(ActionEvent event) {
        Stage currentStage = (Stage) closeButton.getScene().getWindow();

        if (currentStage != null) {
            currentStage.close();
        }

    }

    @FXML
    void onLimpiarNotificaciones(ActionEvent event) {

    }

    @FXML
    void onMarcarLeido(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnLimpiarNotificaciones != null : "fx:id=\"btnLimpiarNotificaciones\" was not injected: check your FXML file 'gestion-notificaciones-view.fxml'.";
        assert btnMarcarLeido != null : "fx:id=\"btnMarcarLeido\" was not injected: check your FXML file 'gestion-notificaciones-view.fxml'.";
        assert closeButton != null : "fx:id=\"closeButton\" was not injected: check your FXML file 'gestion-notificaciones-view.fxml'.";
        assert dateColumn != null : "fx:id=\"dateColumn\" was not injected: check your FXML file 'gestion-notificaciones-view.fxml'.";
        assert messageColumn != null : "fx:id=\"messageColumn\" was not injected: check your FXML file 'gestion-notificaciones-view.fxml'.";
        assert notificationsTable != null : "fx:id=\"notificationsTable\" was not injected: check your FXML file 'gestion-notificaciones-view.fxml'.";
        assert typeColumn != null : "fx:id=\"typeColumn\" was not injected: check your FXML file 'gestion-notificaciones-view.fxml'.";

    }

}