package co.edu.uniquindio.monedero_virtual;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Solvit - Inicio de sesión");
        stage.setScene(scene);
        stage.setWidth(450);
        stage.setHeight(800);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}