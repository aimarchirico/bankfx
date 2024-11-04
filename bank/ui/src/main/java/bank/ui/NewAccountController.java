package bank.ui;

import bank.core.Account;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.Node;


/**
 * Controller class for <code>NewAccount.fxml</code>.
 */
public class NewAccountController {
  private UserAccess userAccess;


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

  /**
   * Sets the currently active {@link UserAccess}.
   *
   * @param userAccess the UserAccess to set as active
   */
  public void setUserAccess(UserAccess userAccess) {
    this.userAccess = userAccess;
  }

  /**
   * Open overview scene.
   *
   * @throws IOException when file is invalid
   */
  
  private void openOverview(Node node) throws IOException {
    FXMLLoader fxmlLoader = UiUtils.newScene(this, node, "Overview.fxml");
    OverviewController controller = fxmlLoader.getController();
    controller.setUserAccess(userAccess);
  }

  /**
   * Uses the openOverview function with the right input.
   * @throws IOException 
   */
  @FXML
  private void goBack() throws IOException {
    openOverview(backIcon);
  }

  /**
   * Create a new account.
   *
   * @throws IOException when file is invalid
   */
  @FXML
  private void newAccount() throws IOException {
    try {
      userAccess.createAccountRequest(new Account(0.0, nameField.getText(), typeField.getText()));
      openOverview(confirmButton);
    } catch (Exception e) {
      UiUtils.showError(errorButton, e.getMessage());
    }

  }

  /**
   * Dismiss error message.
   * Delegates to UiUtils.
   */
  @FXML
  private void dismissError() throws IOException {
    UiUtils.dismissError(errorButton);
  }
}
