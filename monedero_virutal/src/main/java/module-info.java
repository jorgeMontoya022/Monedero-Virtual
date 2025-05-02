module co.edu.uniquindio.monedero_virtual {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.uniquindio.monedero_virtual to javafx.fxml;
    exports co.edu.uniquindio.monedero_virtual;

    opens co.edu.uniquindio.monedero_virtual.view;
    exports co.edu.uniquindio.monedero_virtual.view;
}
