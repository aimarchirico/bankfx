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
  @FXML
  private UserAccess userAccess;


  /**
   * Open CreateUser scene.
   *
   * @throws IOException when file is invalid
   */
  @FXML
  public void openCreateUser() throws IOException {
    UiUtils.newScene(this, createUserButton, "CreateUser.fxml");
  }

  

  /**
   * Dismiss error message. Delegates to UiUtils.
   */
  @FXML
  public void dismissError() {
    UiUtils.dismissError(errorButton);
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
        FXMLLoader fxmlLoader = UiUtils.newScene(this, loginButton, "Overview.fxml");
        OverviewController controller = fxmlLoader.getController();
        controller.setUserAccess(userAccess);

      } catch (Exception e) {
        UiUtils.showError(errorButton, e.getMessage());
      }
    } catch (URISyntaxException e) {
      UiUtils.showError(errorButton, e.getMessage());
    }
  }



  /**
   * Sets userAccess
   * 
   * @param userAccess
   */
  public void setUserAccess(UserAccess userAccess) {
    this.userAccess = userAccess;
  }


  /**
   * Gets userAccess
   * 
   * @return UserAccess
   */
  public UserAccess getUserAccess() {
    return this.userAccess;
  }

}
