package bank.ui;

import java.io.IOException;

import bank.core.User;
import bank.persistence.UserDataStorage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Button createUserButton,confirmButton;
    @FXML
    private TextField passwordField,ssnField;
    @FXML
    private Text errorText;

    @FXML
    private void openCreateUser() throws IOException {
        newScene("CreateUser.fxml");
    }

    @FXML
    private void login() throws IOException {
        UserDataStorage storage = new UserDataStorage();
        User user = storage.getUser(ssnField.getText());
        if (user == null || !user.getPassword().equals(passwordField.getText())) {
            errorText.setText("Incorrect social security number or password");
        }
        else {
            OverviewController controller = newScene("Overview.fxml").getController();
            controller.setUser(user);
        }
    }

    private FXMLLoader newScene(String file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(file));
        Parent parent = fxmlLoader.load();
        Stage stage = (Stage) createUserButton.getScene().getWindow();
        stage.setScene(new Scene(parent));
        stage.show();
        return fxmlLoader;
    }

}
