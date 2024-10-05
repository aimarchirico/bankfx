package bank.ui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Class for utility methods used in the controllers.
 */
public class UiUtils {

  /**
   * Helper method used by tests needing to run headless. Retrieved from
   * (https://gitlab.stud.idi.ntnu.no/it1901/todo-list/-/blob/master/todolist/fxui/src/main/java/todolist/ui/TodoApp.java?ref_type=heads)
   */
  public static void supportHeadless() {
    if (Boolean.getBoolean("headless")) {
      System.setProperty("testfx.robot", "glass");
      System.setProperty("testfx.headless", "true");
      System.setProperty("prism.order", "sw");
      System.setProperty("prism.text", "t2k");
      System.setProperty("java.awt.headless", "true");
    }
  }

  /**
   * Utility method for switching to a new scene.
   *
   * @param controller the current controller
   * @param node an arbitrary {@link Node} in the current scene
   * @param file the new fxml file to show
   * @return the {@link FXMLLoader} with the new scene loaded
   * @throws IOException when file is invalid
   */
  public static FXMLLoader newScene(Object controller, Node node, String file) throws IOException {
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
   * @param errorButton the errorButton to update
   * @param message the error message to show
   */
  public static void showError(Button errorButton, String message) {
    errorButton.setText(message);
    errorButton.setVisible(true);
  }

  /**
   * Utility method for dismissing error.
   *
   * @param errorButton the errorButton to dismiss
   */
  public static void dismissError(Button errorButton) {
    errorButton.setVisible(false);
  }


  /**
   * Finds the root {@link Parent} of a scene by its id.
   * <p>
   * Code retrieved from:
   * https://gitlab.stud.idi.ntnu.no/it1901/todo-list/-/blob/master/todolist/fxui/src/test/java/todolist/ui/TodoAppTest.java
   * </p>
   *
   * @param id The fx:id of the root node you want to find
   * @return The root {@link Parent} if found, <code>null</code> otherwise
   */
  public static Parent findSceneRootWithId(String id) {
    for (Window window : Window.getWindows()) {
      if (window instanceof Stage && window.isShowing()) {
        var root = window.getScene().getRoot();
        if (id.equals(root.getId())) {
          return root;
        }
      }
    }
    return null;
  }
}
