package bank.ui;

import bank.core.User;
import java.io.IOException;
import java.net.URISyntaxException;
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
   * Dismiss error message. Delegates to UiUtils.
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
    try {
      UserAccess userAccess = new UserAccess();
      try {
        userAccess.getUserRequest(ssnField.getText(), passwordField.getText());
        FXMLLoader fxmlLoader = UiUtils.newScene(this, loginButton, "Overview.fxml");
        OverviewController controller = fxmlLoader.getController();
        controller.setUserAccess(userAccess);

      } catch (Exception e) {
        UiUtils.showError(errorButton, e.getMessage());
      }
    } catch (URISyntaxException e) {
      UiUtils.showError(errorButton, "Error contacting server");
    }
  }

}
