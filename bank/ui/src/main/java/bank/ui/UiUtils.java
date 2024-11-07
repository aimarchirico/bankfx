package bank.ui;

import javafx.scene.Parent;
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
