package bank.ui;

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
}
