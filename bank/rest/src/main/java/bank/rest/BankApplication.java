package bank.rest;

import bank.persistence.UserPersistence;
import com.fasterxml.jackson.databind.Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The Spring application.
 */
@SpringBootApplication
public class BankApplication {


  /**
   * Returns a module for deserializing User and Account.
   *
   * @return module from UserPersistence
   */
  @Bean
  public Module userModule() {
    return UserPersistence.createModule();
  }


  /**
   * Main method for running Spring Application.
   *
   * @param args optional arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(BankApplication.class, args);
  }
}

