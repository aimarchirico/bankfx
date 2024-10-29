package bank.ui;

import bank.core.User;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Controller class for <code>CreateUser.fxml</code>.
 */
public class CreateUserController {

  @FXML
  private ImageView backIcon;
  @FXML
  private Button registerButton;
  @FXML
  private TextField nameField;
  @FXML
  private TextField passwordField;
  @FXML
  private TextField ssnField;
  @FXML
  private Button errorButton;

  private UserAccess userAccess;

  /**
   * Default constructor that initializes a new UserAccess instance.
   */
  public CreateUserController() {
    try{
      this.userAccess = new UserAccess();
    }
    catch (Exception e) {
      UiUtils.showError(errorButton, "Failed to create user: " + e.getMessage());
    }
  }

  /**
     * Set userAccess.
     * 
     * @param userAccess instanse of UserAccess
     */
    public void setUserAccess(UserAccess userAccess) {
      this.userAccess = userAccess;
  }

  /**
   * Open login scene.
   *
   * @throws IOException when file is invalid
   */
  @FXML
  private void openLogin() throws IOException {
    UiUtils.newScene(this, backIcon, "Login.fxml");
  }

  /**
   * Dismiss error message. Delegates to UiUtils.
   */
  @FXML
  private void dismissError() {
    UiUtils.dismissError(errorButton);
  }

  /**
   * Create a new {@link User} from submitted SSN, name and password.
   *
   * @throws IOException when file is invalid
   */
  @FXML
  public void createUser() throws IOException {
    try {
      User user = new User(ssnField.getText(), nameField.getText(), passwordField.getText());
      userAccess.createUserRequest(user);
      userAccess.setUser(user);

      FXMLLoader fxmlLoader = UiUtils.newScene(this, backIcon, "Overview.fxml");
      OverviewController controller = fxmlLoader.getController();
      controller.setUserAccess(userAccess);

    } catch (Exception e) {
      UiUtils.showError(errorButton, "Failed to create user: " + e.getMessage());
    }
  }
}
