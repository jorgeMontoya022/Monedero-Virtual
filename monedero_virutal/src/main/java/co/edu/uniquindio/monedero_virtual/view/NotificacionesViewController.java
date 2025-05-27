package co.edu.uniquindio.monedero_virtual.view;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.monedero_virtual.model.Cliente;
import co.edu.uniquindio.monedero_virtual.model.Notificacion;
import co.edu.uniquindio.monedero_virtual.ownStructures.ownLists.OwnCircularList;
import co.edu.uniquindio.monedero_virtual.utils.Sesion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class NotificacionesViewController extends CoreViewController   {
    

    Cliente clienteLogueado;

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
    private TableColumn<Notificacion, String> dateColumn;

    @FXML
    private TableColumn<Notificacion, String> messageColumn;

    @FXML
    private TableView<Notificacion> notificationsTable;

    @FXML
    private TableColumn<Notificacion, String> typeColumn;

    @FXML
    void onCloseNotifications(ActionEvent event) {
        Stage currentStage = (Stage) closeButton.getScene().getWindow();

        if (currentStage != null) {
            currentStage.close();
        }

    }

    @FXML
    void initialize() {
        clienteLogueado = (Cliente) Sesion.getInstance().getCliente();
        initView();

    }

  

    private void initView() {
         initDataBinding();
         getNotificaciones();
         
        

    }

    private void initDataBinding() {
       dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaNotifiacion().toString()));
       messageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMensaje()));
       typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoNotificacion().toString()));
    }

   private void getNotificaciones() {
    OwnCircularList<Notificacion> listaCircular = clienteLogueado.getListaNotificacion();
    ObservableList<Notificacion> listaObservable = FXCollections.observableArrayList();

    for (Notificacion noti : listaCircular) {
        listaObservable.add(noti);
    }

    notificationsTable.setItems(listaObservable);
}


}