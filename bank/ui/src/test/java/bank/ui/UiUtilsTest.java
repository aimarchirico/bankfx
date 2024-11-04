package bank.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

/**
 * Test class for {@link UiUtils}.
 */
public class UiUtilsTest extends ApplicationTest {
  
  /**
   * Setup headless test support.
   */
  @BeforeAll
  public static void setupHeadless() {
    UiUtils.supportHeadless();
  }

  /**
   * Loads initial scene with a root node having a specific ID.
   *
   * @param stage the top level JavaFX container
   */
  @Override
  public void start(Stage stage) {
    VBox root = new VBox();
    root.setId("testRoot");
    stage.setScene(new Scene(root, 400, 300));
    stage.show();
  }

  /**
   * Test headless mode properties.
   */
  @Test
  @DisplayName("Test support headless mode")
  public void testSupportHeadless() {
    System.setProperty("headless", "true");
    UiUtils.supportHeadless();

    assertTrue("glass".equals(System.getProperty("testfx.robot")));
    assertTrue("true".equals(System.getProperty("testfx.headless")));
    assertTrue("sw".equals(System.getProperty("prism.order")));
    assertTrue("t2k".equals(System.getProperty("prism.text")));
    assertTrue("true".equals(System.getProperty("java.awt.headless")));
  }

  /**
   * Test finding a scene root with a specific ID.
   */
  @Test
  @DisplayName("Test findSceneRootWithId with existing ID")
  public void testFindSceneRootWithExistingId() {
    Parent root = UiUtils.findSceneRootWithId("testRoot");
    assertNotNull(root, "Root with ID 'testRoot' should be found");
  }

  /**
   * Test finding a scene root with a non-existing ID.
   */
  @Test
  @DisplayName("Test findSceneRootWithId with non-existing ID")
  public void testFindSceneRootWithNonExistingId() {
    Parent root = UiUtils.findSceneRootWithId("nonExistentId");
    assertNull(root, "Root with non-existing ID should return null");
  }
}


