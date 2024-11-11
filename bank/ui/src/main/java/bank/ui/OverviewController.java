package bank.ui;

import bank.core.Account;
import bank.core.User;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


/**
 * Controller class for <code>Overview.fxml</code>.
 */
public class OverviewController extends Controller {

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
  private AnchorPane listRoot;
  @FXML
  private Button createAccountButton;
  @FXML
  private Button deleteAccountButton;
  @FXML
  private Button deleteUserButton;


  /**
   * Open login scene.
   *
   * @throws IOException when file is invalid
   */
  @FXML
  private void openLogin() throws IOException {
    newScene(this, logoutIcon, "Login.fxml");
  }

  /**
   * Open payment scene.
   *
   * @throws IOException when file is invalid
   */
  @FXML
  private void openPayment() throws IOException {
    FXMLLoader fxmlLoader = newScene(this, paymentIcon, "Payment.fxml");
    PaymentController paymentController = fxmlLoader.getController();
    paymentController.setUserAccess(userAccess);
    paymentController.update();
  }

  /**
   * Opens the {@link DepositController} scene for making a deposit.
   *
   * @throws IOException if the scene file is invalid
   */
  @FXML
  private void openDeposit() throws IOException {
    FXMLLoader fxmlLoader = newScene(this, depositIcon, "Deposit.fxml");
    DepositController controller = fxmlLoader.getController();
    controller.setUserAccess(userAccess);
    controller.update();
  }

  /**
   * Opens the {@link WithdrawalController} scene for making a withdrawal.
   *
   * @throws IOException if the scene file is invalid
   */
  @FXML
  private void openWithdrawal() throws IOException {
    FXMLLoader fxmlLoader = newScene(this, withdrawalIcon, "Withdrawal.fxml");
    WithdrawalController controller = fxmlLoader.getController();
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
    FXMLLoader fxmlLoader = newScene(this, createAccountButton, "NewAccount.fxml");
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
    FXMLLoader fxmlLoader = newScene(this, deleteAccountButton, "DeleteAccount.fxml");
    DeleteAccountController controller = fxmlLoader.getController();
    controller.setUserAccess(userAccess);
    controller.update();
  }

  /**
   * Attempts to delete user.
   */
  @FXML
  private void deleteUser() throws IOException {
    try {
      userAccess.deleteUserRequest();
      openLogin();
    } catch (Exception e) {
      showError(e.getMessage());
    }
  }

  /**
   * Opens the {@link TransferController} scene for initiating a transfer.
   *
   * @throws IOException if the scene file is invalid
   */
  @FXML
  private void openTransfer() throws IOException {
    FXMLLoader fxmlLoader = newScene(this, transferIcon, "Transfer.fxml");
    TransferController controller = fxmlLoader.getController();
    controller.setUserAccess(userAccess);
    controller.update();
  }

  /**
   * Refreshes accounts by requesting user data. 
   *
   */
  @FXML
  private void refresh() throws IOException {
    try {
      User user = userAccess.getUser();
      userAccess.getUserRequest(user.getSsn(), user.getPassword());
      update();
    } catch (Exception e) {
      showError(e.getMessage());
    }
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
  
      // Account name text
      Text accountName = new Text(account.getName() + ":");
      accountName.setFont(new Font("Verdana Bold", 8.0));
      accountName.setFill(Color.web("#e2e2e2"));
  
      // Account number text 
      TextField accountNumberField = new TextField(Long.toString(account.getAccountNumber()));
      accountNumberField.setFont(new Font("Verdana", 7.0));
      accountNumberField.setStyle("-fx-text-fill: #a2a2a2; -fx-background-color: transparent;");
      accountNumberField.setEditable(false); // Make it read-only
  
      // Balance text
      Text balance = new Text(String.valueOf(account.getBalance()) + " kr");
      balance.setFont(new Font("Verdana", 8.0));
      balance.setFill(Color.web("#e2e2e2"));
  
      // Add the texts to the AnchorPane
      anchorPane.getChildren().addAll(accountName, accountNumberField, balance);
  
      AnchorPane.setLeftAnchor(accountName, 14.0);
      AnchorPane.setTopAnchor(accountName, 10.0);
  
      AnchorPane.setLeftAnchor(accountNumberField, 8.0);
      AnchorPane.setTopAnchor(accountNumberField, 24.0);
  
      AnchorPane.setLeftAnchor(balance, 135.0);
      AnchorPane.setTopAnchor(balance, 18.0);
  
      listRoot.getChildren().add(anchorPane);
      AnchorPane.setTopAnchor(anchorPane, 24.0 + i * 53);
      AnchorPane.setLeftAnchor(anchorPane, 8.0);
      i++;
    }
  }



  /**
   * Set the current {@link UserAccess} and update welcome text.
   *
   * @param userAccess the current user
   */
  @Override
  public void setUserAccess(UserAccess userAccess) {
    this.userAccess = userAccess;
    welcomeText.setText("Welcome, " + this.userAccess.getUser().getName());
  }

}
