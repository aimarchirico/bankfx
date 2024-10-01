package bank.ui;

import bank.core.User;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * Controller class for <code>Overview.fxml</code>.
 */
public class OverviewController {

  @FXML
  private ImageView logoutIcon;
  @FXML
  private ImageView paymentIcon;
  @FXML
  private ImageView transferIcon;
  @FXML
  private ImageView withdrawalIcon;
  @FXML
  private ImageView depositIcon;
  @FXML
  private Text paymentText;
  @FXML
  private Text transferText;
  @FXML
  private Text withdrawalText;
  @FXML
  private Text depositText;
  @FXML
  private Text welcomeText;
  @FXML
  private User user;


  /**
   * Open login scene.
   *
   * @throws IOException when file is invalid
   */
  @FXML
  private void openLogin() throws IOException {
    UiUtils.newScene(this, logoutIcon, "Login.fxml");
  }


  /**
   * Set the current {@link User} and update welcome text.
   *
   * @param user the current user
   */
  public void setUser(User user) {
    this.user = user;
    welcomeText.setText("Welcome, " + this.user.getName() + "!");
  }
}
