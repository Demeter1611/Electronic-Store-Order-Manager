module com.example.proiectlab4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires javafaker;


    exports com.example.proiectlab4.javafx;
    opens com.example.proiectlab4.javafx to javafx.fxml;
}