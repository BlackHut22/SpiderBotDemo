module com.example.spiderbotdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;


    opens com.example.spiderbotdemo to javafx.fxml;
    exports com.example.spiderbotdemo;
}