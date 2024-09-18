package bank.ui;

import java.io.IOException;

import bank.core.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class OverviewController {

    @FXML
    private Button returnButton;
    @FXML
    private Text welcomeText;
    @SuppressWarnings("unused")
    private User user;

    @FXML
    private void OpenLogin() throws IOException {
        newScene("Login.fxml");
    }

    public void setUser(User user) {
        this.user = user;
        welcomeText.setText("Welcome, "+user.getName()+"!");
    }

    private FXMLLoader newScene(String file) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(file));
    Parent parent = fxmlLoader.load();
    Stage stage = (Stage) returnButton.getScene().getWindow();
    stage.setScene(new Scene(parent));
    stage.show();
    return fxmlLoader;
    }
}
