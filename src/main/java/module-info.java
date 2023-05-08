module com.example.smtpmailsender {
    requires javafx.controls;
    requires javafx.fxml;
    requires activation;
    requires java.mail;


    opens com.example.smtpmailsender to javafx.fxml;
    exports com.example.smtpmailsender;
    exports com.example.smtpmailsender.mailSender;
    opens com.example.smtpmailsender.mailSender to javafx.fxml;
}