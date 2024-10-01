package bank.ui;

import bank.core.User;
import bank.persistence.UserDataStorage;
import bank.persistence.Utils;
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
   * Dismiss error message.
   * Delegates to UiUtils.
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
  private void createUser() throws IOException {
    try {
      UserDataStorage storage = new UserDataStorage(Utils.path);
      if (storage.getUser(ssnField.getText()) != null) {
        UiUtils.showError(errorButton, "User already registered");
      } else {
        User user = new User(ssnField.getText(), nameField.getText(), passwordField.getText());
        storage.writeUserData(user);
        FXMLLoader fxmlLoader = UiUtils.newScene(this, backIcon, "Overview.fxml");
        OverviewController controller = fxmlLoader.getController();
        controller.setUser(user);
      }

    } catch (Exception e) {
      UiUtils.showError(errorButton, e.getMessage());
    }
  }
}
