module c482.c482_marco {
    requires javafx.controls;
    requires javafx.fxml;


    opens c482.c482_marco to javafx.fxml;
    exports c482.c482_marco;
    exports Controller;
    opens Controller to javafx.fxml;
    opens Model to javafx.base;

}