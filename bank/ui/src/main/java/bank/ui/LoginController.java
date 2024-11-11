package bank.ui;

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
public class LoginController extends Controller {

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
  public void openCreateUser() throws IOException {
    newScene(this, createUserButton, "CreateUser.fxml");
  }

  /**
   * If valid, log in using submitted ssn and password.
   *
   * @throws IOException when file is invalid
   */
  @FXML
  public void login() throws IOException {
    try {
      if (userAccess == null) {
        setUserAccess(new UserAccess());
      }
      try {
        userAccess.getUserRequest(ssnField.getText(), passwordField.getText());
        FXMLLoader fxmlLoader = newScene(this, loginButton, "Overview.fxml");
        OverviewController controller = fxmlLoader.getController();
        controller.setUserAccess(userAccess);
        controller.update();

      } catch (Exception e) {
        showError(e.getMessage());
      }
    } catch (URISyntaxException e) {
      showError(e.getMessage());
    }
  }
}
