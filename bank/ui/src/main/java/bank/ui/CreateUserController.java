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

public class CreateUserController {

    @FXML
    private Button returnButton,confirmButton;
    @FXML
    private TextField nameField,passwordField,ssnField;
    @FXML
    private Text errorText;

    @FXML
    private void OpenLogin() throws IOException {
        newScene("Login.fxml");
    }

    @FXML
    private void createUser() throws IOException {
        try {
            User user = new User(ssnField.getText(),nameField.getText(),passwordField.getText());
            UserDataStorage storage = new UserDataStorage();
            storage.writeUserData(user);
            OverviewController controller = newScene("Overview.fxml").getController();
            controller.setUser(user);
        }
        catch (Exception e) {
            errorText.setText(e.getMessage());
        }
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
