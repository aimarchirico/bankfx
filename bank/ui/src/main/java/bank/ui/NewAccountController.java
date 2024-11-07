package bank.ui;

import bank.core.Account;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Controller class for <code>NewAccount.fxml</code>.
 */
public class NewAccountController extends Controller {

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
   * Open overview scene.
   *
   * @throws IOException when file is invalid
   */

  private void openOverview(Node node) throws IOException {
    FXMLLoader fxmlLoader = newScene(this, node, "Overview.fxml");
    OverviewController controller = fxmlLoader.getController();
    controller.setUserAccess(userAccess);
  }

  /**
   * Uses the openOverview function with the right input.
   *
   * @throws IOException when file is invalid
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
      showError(e.getMessage());
    }

  }
}
