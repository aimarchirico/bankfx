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
 * Controller class for <code>NewAccount.fxml</code>.
 */
public class NewAccountController {
  private User user;


  @FXML
  private ImageView backIcon;
  @FXML
  private Button confirmButton;
  @FXML
  private TextField nameField;
  @FXML
  private TextField typeField;
  @FXML
  private Button errorButton;

  public void setUser(User user) {
    this.user = user;
  }

  @FXML
  private void openOverview() throws IOException {
    FXMLLoader fxmlLoader = UiUtils.newScene(this, backIcon, "Overview.fxml");
    OverviewController controller = fxmlLoader.getController();
    controller.setUser(user);
  }

  @FXML
  private void newAccount() throws IOException {
    try {
      user.createAccount(typeField.getText(), nameField.getText());
      UserDataStorage storage = new UserDataStorage(Utils.path);
      storage.updateUserData(user);
      openOverview();
    } catch (Exception e) {
      UiUtils.showError(errorButton, e.getMessage());
    }

  }

  @FXML
  private void dismissError() throws IOException {
    UiUtils.dismissError(errorButton);
  }
}
