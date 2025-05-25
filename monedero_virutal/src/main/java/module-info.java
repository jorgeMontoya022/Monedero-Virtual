module co.edu.uniquindio.monedero_virtual {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires itextpdf;
    requires javafx.base;

    opens co.edu.uniquindio.monedero_virtual to javafx.fxml;
    exports co.edu.uniquindio.monedero_virtual;

    opens co.edu.uniquindio.monedero_virtual.view;
    exports co.edu.uniquindio.monedero_virtual.view;

    exports co.edu.uniquindio.monedero_virtual.utils;

    opens co.edu.uniquindio.monedero_virtual.model;
    exports co.edu.uniquindio.monedero_virtual.model;
}
