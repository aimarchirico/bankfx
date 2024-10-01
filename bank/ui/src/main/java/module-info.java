module bank.ui {
  requires bank.core;
  requires bank.persistence;
  requires javafx.controls;
  requires javafx.fxml;

  opens bank.ui to javafx.fxml, javafx.graphics;
}