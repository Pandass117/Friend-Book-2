module com.friendbook {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.friendbook to javafx.fxml;
    exports com.friendbook;
}