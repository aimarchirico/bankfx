package bank.ui;

import bank.core.User;
import bank.persistence.UserDataStorage;
import bank.persistence.Utils;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller class for <code>Login.fxml</code>.
 */
public class LoginController {

  @FXML
  private Button createUserButton;
  @FXML
  private Button loginButton;
  @FXML
  private PasswordField passwordField;
  @FXML
  private TextField ssnField;
  @FXML
  private Button errorButton;


  /**
   * Open CreateUser scene.
   *
   * @throws IOException when file is invalid
   */
  @FXML
  private void openCreateUser() throws IOException {
    UiUtils.newScene(this, createUserButton, "CreateUser.fxml");
  }

  /**
   * Dismiss error message.
   * Delegates to UiUtils.
   */
  @FXML
  private void dismissError() {
    UiUtils.dismissError(errorButton);
  }

  /**
   * If valid, log in using submitted ssn and password.
   *
   * @throws IOException when file is invalid
   */
  @FXML
  private void login() throws IOException {
    UserDataStorage storage = new UserDataStorage(Utils.path);
    User user = storage.getUser(ssnField.getText());
    if (user == null || !user.getPassword().equals(passwordField.getText())) {
      UiUtils.showError(errorButton, "Incorrect social security number or password");

    } else {
      FXMLLoader fxmlLoader = UiUtils.newScene(this, loginButton, "Overview.fxml");
      OverviewController controller = fxmlLoader.getController();
      controller.setUser(user);
    }
  }

}
