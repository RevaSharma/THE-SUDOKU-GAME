module csci205_final_project {
    requires java.base;
    requires java.desktop;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    exports org.csci205Team08;
    exports org.csci205Team08.controllers;
    exports org.csci205Team08.model;
    opens org.csci205Team08 to javafx.fxml;
    opens org.csci205Team08.controllers to javafx.fxml;
}