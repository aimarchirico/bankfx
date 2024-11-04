package bank.ui;

import bank.core.Account;
import java.io.IOException;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
  private Text welcomeText;
  @FXML
  private UserAccess userAccess;
  @FXML
  private AnchorPane listRoot;
  @FXML
  private Button createAccountButton;
  @FXML
  private Button deleteAccountButton;


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
   * Open payment scene.
   *
   * @throws IOException when file is invalid
   */
  @FXML
  private void openPayment() throws IOException {
    // FXMLLoader fxmlLoader = UiUtils.newScene(this, paymentIcon, "Payment.fxml");
    // PaymentController paymentController = fxmlLoader.getController();
    // paymentController.setUserAccess(userAccess);
  }

  @FXML
  private void openDeposit() throws IOException {
    FXMLLoader fxmlLoader = UiUtils.newScene(this, createAccountButton, "Deposit.fxml");
    DepositController controller = fxmlLoader.getController();
    controller.setUserAccess(userAccess);
    controller.update();
  }


  /**
   * Open new account scene.
   *
   * @throws IOException when file is invalid
   */
  @FXML
  private void openNewAccount() throws IOException {
    FXMLLoader fxmlLoader = UiUtils.newScene(this, createAccountButton, "NewAccount.fxml");
    NewAccountController controller = fxmlLoader.getController();
    controller.setUserAccess(userAccess);
  }

  /**
   * Open delete account scene.
   *
   * @throws IOException when file is invalid
   */
  @FXML
  private void deleteAccount() throws IOException {
    // FXMLLoader fxmlLoader = UiUtils.newScene(this, deleteAccountButton, "DeleteAccount.fxml");
    // DeleteAccountController controller = fxmlLoader.getController();
    // controller.setUserAccess(userAccess);
  }

  @FXML
  private void openTransfer() throws IOException {
    FXMLLoader fxmlLoader = UiUtils.newScene(this, createAccountButton, "Transfer.fxml");
    TransferController controller = fxmlLoader.getController();
    controller.setUserAccess(userAccess);
    controller.update();
  }

  /**
   * Updates the list of accounts.
   */
  public void update() {
    List<Account> accounts = userAccess.getUser().getAccounts();
    int i = 0;
    for (Account account : accounts) {

      AnchorPane anchorPane = new AnchorPane();
      anchorPane.setStyle("-fx-background-color: #1f1f1f; -fx-background-radius: 10;");
      anchorPane.setPrefHeight(46.0);
      anchorPane.setPrefWidth(249.0);

      Text accountName = new Text(account.getName() + ":");
      Text balance = new Text(String.valueOf(account.getBalance()) + " kr");
      accountName.setFont(new Font("Verdana Bold", 8.0));
      accountName.setFill((Color.web("#e2e2e2")));
      balance.setFont(new Font("Verdana", 8.0));
      balance.setFill((Color.web("#e2e2e2")));

      anchorPane.getChildren().add(accountName);
      anchorPane.getChildren().add(balance);


      AnchorPane.setLeftAnchor(accountName, 14.0);
      AnchorPane.setLeftAnchor(balance, 135.0);
      AnchorPane.setTopAnchor(accountName, 18.0);
      AnchorPane.setTopAnchor(balance, 18.0);

      listRoot.getChildren().add(anchorPane);
      AnchorPane.setTopAnchor(anchorPane, 24.0 + i * 53);
      AnchorPane.setLeftAnchor(anchorPane, 9.5);
      i++;
    }
  }



  /**
   * Set the current {@link UserAccess} and update welcome text.
   *
   * @param userAccess the current user
   */
  public void setUserAccess(UserAccess userAccess) {
    this.userAccess = userAccess;
    welcomeText.setText("Welcome, " + this.userAccess.getUser().getName() + "!");
    update();
  }

}
