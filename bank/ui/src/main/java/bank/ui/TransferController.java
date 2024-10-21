package bank.ui;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import bank.core.Account;
import bank.core.User;
import bank.persistence.UserDataStorage;
import bank.persistence.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class TransferController {
  @FXML
  private ImageView logoutIcon;
  @FXML
  private User user;
  @FXML
  private ChoiceBox<String> transferToField;
  @FXML
  private ChoiceBox<String> transferFromField;
  @FXML
  private TextField transferAmountField;


  @FXML
  private void openOverview() throws IOException {
    FXMLLoader fxmlLoader = UiUtils.newScene(this, logoutIcon, "Overview.fxml");
    OverviewController controller = fxmlLoader.getController();
    controller.setUser(user);
  }

  // Må endres etter bytte til REST-api modell
  @FXML
  private void handleTransfer() {
    List<Account> userAccounts = user.getAccounts();
    String fromAccName = transferFromField.getValue();
    String toAccName = transferToField.getValue();
    Double amount = 0.0;
    try {
      amount = Double.parseDouble(transferAmountField.getText());
    } catch (NumberFormatException e) {
      //Error handling
    }

    Optional<Account> toAccount =
        userAccounts.stream().filter(Account -> toAccName.equals(Account.getName())).findFirst();
    Optional<Account> fromAccount =
        userAccounts.stream().filter(Account -> fromAccName.equals(Account.getName())).findFirst();
    if (toAccount.isPresent() && fromAccount.isPresent()) {
      user.transferTo(amount, fromAccount.get(), toAccount.get());
    }
    UserDataStorage uds = new UserDataStorage(Utils.path);
    uds.updateUserData(user);
  }

  public void update() {
    List<Account> accounts = user.getAccounts();
    List<String> accountNames = accounts.stream().map(Account::getName).collect(Collectors.toList());
    transferToField.getItems().addAll(accountNames);
    transferFromField.getItems().addAll(accountNames);
  }

  public void setUser(User user) {
    this.user = user;
  }

}
