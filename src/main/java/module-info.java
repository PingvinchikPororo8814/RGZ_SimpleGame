module com.students.rgz {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.students.rgz to javafx.fxml;
    exports com.students.rgz;
}