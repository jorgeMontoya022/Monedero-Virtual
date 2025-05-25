package co.edu.uniquindio.monedero_virtual.view;

import java.util.Optional;
import java.util.function.Function;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Node;

public abstract class CoreViewController {

    /**
     * Muestra un mensaje al usuario con el estilo de Solvi
     * 
     * @param title     Título de la ventana
     * @param header    Encabezado del mensaje
     * @param content   Contenido del mensaje
     * @param alertType Tipo de alerta (ERROR, WARNING, INFORMATION, CONFIRMATION)
     */
    public void mostrarMensaje(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.initStyle(StageStyle.TRANSPARENT);

        // Personalizar el DialogPane
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                CoreViewController.class.getResource("/co/edu/uniquindio/monedero_virtual/alerts.css")
                        .toExternalForm());
        dialogPane.getStyleClass().add("solvi-alert");

        // Agregar clases de estilo específicas según el tipo de alerta
        switch (alertType) {
            case ERROR:
                dialogPane.getStyleClass().add("error-alert");
                break;
            case WARNING:
                dialogPane.getStyleClass().add("warning-alert");
                break;
            case INFORMATION:
                dialogPane.getStyleClass().add("info-alert");
                break;
            case CONFIRMATION:
                dialogPane.getStyleClass().add("confirmation-alert");
                break;
        }

        // Crear el área de texto
        TextArea textArea = new TextArea(content);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.getStyleClass().add("solvi-text-area");

        // Configurar el TextArea para que use todo el ancho
        textArea.setMaxWidth(Double.MAX_VALUE);

        // Calcular el número de líneas necesarias
        String[] lines = content.split("\n");
        int totalLines = 0;

        for (String line : lines) {
            int lineLength = line.length();
            int estimatedLines = (int) Math.ceil(lineLength / 60.0); // Asumiendo ~60 caracteres por línea
            totalLines += Math.max(1, estimatedLines);
        }

        // Establecer la altura basada en el número de líneas
        double lineHeight = 20; // altura aproximada por línea en píxeles
        textArea.setPrefRowCount(totalLines);
        textArea.setPrefHeight((totalLines + 1) * lineHeight);
        textArea.setMinHeight((totalLines + 1) * lineHeight);

        // Deshabilitar la barra de desplazamiento usando CSS
        textArea.setStyle("-fx-scrollbar-vertical: false; -fx-scrollbar-horizontal: false;");

        // Configurar el DialogPane
        dialogPane.setContent(textArea);

        // Ajustar tamaño para coincidir con la interfaz de bienvenida
        dialogPane.setPrefWidth(350);

        alert.showAndWait();
    }

    /**
     * Muestra un mensaje de confirmación al usuario con el estilo de Solvi
     * 
     * @param message Mensaje a mostrar
     * @return true si el usuario confirma, false en caso contrario
     */
    public boolean mostrarMensajeConfirmacion(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initStyle(StageStyle.TRANSPARENT);

        // Personalizar el DialogPane
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                CoreViewController.class.getResource("/co/edu/uniquindio/monedero_virtual/alerts.css")
                        .toExternalForm());
        dialogPane.getStyleClass().addAll("solvi-alert", "confirmation-alert");

        // Personalizar los botones
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        okButton.getStyleClass().add("confirm-button");

        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        cancelButton.getStyleClass().add("cancel-button");

        // Ajustar tamaño para coincidir con la interfaz de bienvenida
        dialogPane.setPrefWidth(350);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public void browseWindow(String nameFileFxml, String titleWindow, ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nameFileFxml));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(titleWindow);
            stage.setMaximized(true);
            Image icon = new Image(getClass().getResourceAsStream("/co/edu/uniquindio/monedero_virtual/solvi.png.png"));
            stage.getIcons().add(icon);
            stage.show();

            closeWindow(actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void openWindow(String nameFileFxml, String titleWindow, Stage ownerStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nameFileFxml));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(titleWindow);

            Image icon = new Image(getClass().getResourceAsStream("/co/edu/uniquindio/monedero_virtual/solvi.png.png"));
            stage.getIcons().add(icon);

            // Establecer el propietario de la nueva ventana
            if (ownerStage != null) {
                stage.initOwner(ownerStage);
            }

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected  <T> void initializeComboBox(ComboBox<T> comboBox,
                                        ObservableList<T> items,
                                        Function<T, String> displayFunction) {
        comboBox.setItems(items);
        comboBox.setCellFactory(lv -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : displayFunction.apply(item));
            }
        });
        comboBox.setButtonCell(new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : displayFunction.apply(item));
            }
        });
    }

}
