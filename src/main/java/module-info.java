module com.example.spiderbotdemo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.spiderbotdemo to javafx.fxml;
    exports com.example.spiderbotdemo;
}