package bank.ui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Super class for controllers.
 *
 */
public class Controller {

  @FXML
  protected Button errorButton;
  protected UserAccess userAccess;

  /**
   * Utility method for switching to a new scene.
   *
   * @param controller the current controller
   * @param node an arbitrary {@link Node} in the current scene
   * @param file the new fxml file to show
   * @return the {@link FXMLLoader} with the new scene loaded
   * @throws IOException when file is invalid
   */
  public FXMLLoader newScene(Object controller, Node node, String file) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(controller.getClass().getResource(file));
    Parent parent = fxmlLoader.load();
    Stage stage = (Stage) node.getScene().getWindow();
    stage.setScene(new Scene(parent));
    stage.show();
    return fxmlLoader;
  }

  /**
   * Utility method for showing error.
   *
   * @param message the error message to show
   */
  public void showError(String message) {
    errorButton.setText(message);
    errorButton.setVisible(true);
  }

  /**
   * Utility method for dismissing error.
   *
   */
  public void dismissError() {
    errorButton.setVisible(false);
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
   * Gets userAccess.
   *
   * @return UserAccess
   */
  public UserAccess getUserAccess() {
    return userAccess;
  }
}
