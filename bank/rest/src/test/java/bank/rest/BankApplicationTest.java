package bank.rest;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import bank.persistence.UserPersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.mockito.MockedStatic;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

/**
 * Test class for {@link BankApplication}.
 */
@SpringBootTest(classes = BankApplication.class)
public class BankApplicationTest {

  @Autowired
  private ApplicationContext context;

  /**
   * Test that the Spring Boot application context loads successfully.
   */
  @Test
  @DisplayName("Test context loaded")
  public void contextLoads() {
    assertNotNull(context, "The application context should have loaded.");
  }

  /**
   * Test that the userModule bean is correctly loaded and is of the correct type.
   */
  @Test
  @DisplayName("Test user module")
  public void testUserModuleBean() {
    try (MockedStatic<UserPersistence> mockedStatic = mockStatic(UserPersistence.class)) {
      mockedStatic.when(UserPersistence::createModule).thenReturn(new SimpleModule());
      Module userModule = context.getBean("userModule", Module.class);
      assertNotNull(userModule, "The userModule bean should not be null.");
      assertTrue(userModule instanceof Module, "The userModule bean should be of type Module.");
    }
  }
}
