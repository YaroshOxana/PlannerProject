module com.planner {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;

    opens com.planner to javafx.fxml;
    exports com.planner;
}