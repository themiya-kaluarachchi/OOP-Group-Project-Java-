module com.example.javaminiproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.dotenv;
    requires org.json;
    requires mysql.connector.j;
    requires java.sql;
    requires java.desktop;
    requires annotations;


    opens com.example.javaminiproject to javafx.fxml;
    exports com.example.javaminiproject;
}