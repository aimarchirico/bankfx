module bank.ui {
  requires bank.core;
  requires bank.persistence;
  requires javafx.controls;
  requires javafx.fxml;
  requires com.fasterxml.jackson.databind;
  requires java.net.http;

  opens bank.ui to javafx.graphics, javafx.fxml;
}
