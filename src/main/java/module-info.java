module com.project.connect4.connect4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.project.connect4 to javafx.fxml;
    exports com.project.connect4;
}