package bank.rest;

import bank.core.Account;
import bank.core.Bank;
import bank.core.User;
import java.io.IOException;
import java.net.URI;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * The service implementation.
 */
@RestController
@RequestMapping(BankController.BANK_SERVICE_PATH)
public class BankController {

  public static final String BANK_SERVICE_PATH = "bank";

  @Autowired
  private BankService bankService;


  /**
   * returns the {@link Bank} instance of the BankService.
   *
   * @return the Bank instance
   */
  private Bank getBank() {
    return bankService.getBank();
  }


  /**
   * Validates if ssn and password matches.
   *
   * @param ssn the ssn of the {@link User}
   * @param password the password of the User
   * @return User if credentials are correct
   * @throws NoSuchElementException if user does not exist
   * @throws IllegalAccessException if credentials are incorrect
   */
  private User validateUser(String ssn, String password) throws IllegalAccessException {
    User user = getBank().getUser(ssn);
    if (user == null) {
      throw new NoSuchElementException("User does not exist.");
    }
    getBank().verifyCredentials(ssn, password);
    return user;
  }

  /**
   * Returns the {@link Account} with specified account number.
   *
   * @param accountNr the account number of the Account
   * @return the Account if it exists or else null
   */
  public Account getAccount(long accountNr) {
    return getBank().getAccountByNumber(accountNr);
  }


  /**
   * Saves the {@link User} to the {@link BankService}.
   *
   * @throws IOException if it fails to save Users
   */
  private void saveUsers() throws IOException {
    bankService.saveUsers();

  }

  /**
   * Functional interface for a {@link ResponseEntity} Supplier.
   */
  @FunctionalInterface
  public interface ResponseEntitySupplier<T> {
    /**
     * Gets a {@link ResponseEntity} instance.
     *
     * @return a ResponseEntity containing the response data
     * @throws Exception if an error occurs while obtaining the response
     */
    ResponseEntity<T> get() throws Exception;
  }

  /**
   * Utility method to return an appropriate {@link ResponseEntity}. 
   * If supplier throws an exception, the ResponseEntity will include the error message.
   *
   * @param supplier the {@link ResponseEntitySupplier}
   * @return a ResponseEntity instance
   */
  private ResponseEntity<?> handleResponse(ResponseEntitySupplier<?> supplier) {
    try {
      return supplier.get();
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    } catch (IOException e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    } catch (IllegalAccessException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  /**
   * Gets the corresponding {@link User} from the {@link Bank} instance.
   *
   * @param ssn the ssn of the User
   * @param password the password of the User
   * @return a 200 OK including the User if successful, or else the exception message
   */
  @GetMapping(path = "/users/{ssn}")
  public ResponseEntity<?> getUser(@PathVariable("ssn") String ssn, 
      @RequestParam("password") String password) {
    return handleResponse(() -> {
      User user = validateUser(ssn, password);
      return ResponseEntity.ok(user);
    });
  }

  /**
   * Adds a {@link User} to the {@link Bank} instance.
   *
   * @param ssn the ssn of the User
   * @param user the User to add
   * @return a 201 Created including the User if successful, or else the exception message
   */
  @PostMapping(path = "/users/{ssn}")
  public ResponseEntity<?> addUser(@PathVariable("ssn") String ssn, @RequestBody User user) {
    return handleResponse(() -> {
      if (!ssn.equals(user.getSsn())) {
        throw new IllegalAccessException("Cannot add User to mismatched SSN.");
      }
      getBank().addUser(user);
      saveUsers();
      URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(ssn).toUri();
      return ResponseEntity.created(location).body(user);
    });
  }

  /**
   * Deletes a {@link User} from the {@link Bank} instance.
   *
   * @param ssn the ssn of the User
   * @param password the password of the User
   * @return a 200 OK including the User if successful, or else the exception message
   */
  @DeleteMapping(path = "/users/{ssn}")
  public ResponseEntity<?> removeUser(@PathVariable("ssn") String ssn, 
      @RequestParam("password") String password) {
    return handleResponse(() -> {
      User user = validateUser(ssn, password);
      getBank().removeUser(user);
      saveUsers();
      return ResponseEntity.ok(user);
    });
  }

  /**
   * Adds an {@link Account} to the {@link Bank} instance.
   *
   * @param ssn the ssn of the User
   * @param accountNr the account number of the Account
   * @param password the password of the User
   * @param account the Account to add
   * @return a 201 Created including the User if successful, or else the exception message
   */
  @PostMapping(path = "/users/{ssn}/accounts/{account}")
  public ResponseEntity<?> addAccount(@PathVariable("ssn") String ssn, 
      @PathVariable("account") long accountNr, @RequestParam("password") String password, 
      @RequestBody Account account) {
    return handleResponse(() -> {
      if (accountNr != account.getAccountNumber()) {
        throw new IllegalAccessException("Cannot add Account to mismatched account number.");
      }
      User user = validateUser(ssn, password);
      getBank().addAccount(user, account);
      saveUsers();
      URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(ssn).toUri();
      return ResponseEntity.created(location).body(user);
    });
  }

  /**
   * Deletes an Account.
   *
   * @param ssn the ssn of the User
   * @param account the account number of the Account
   * @param password the password of the User
   * @return a 200 OK including the User if successful, or else the exception message
   */
  @DeleteMapping(path = "/users/{ssn}/accounts/{account}")
  public ResponseEntity<?> removeAccount(@PathVariable("ssn") String ssn, 
      @PathVariable("account") long account, @RequestParam("password") String password) {
    return handleResponse(() -> {
      User user = validateUser(ssn, password);
      getBank().removeAccount(user, getAccount(account));
      saveUsers();
      return ResponseEntity.ok(user);
    });
  }

  /**
   * Request a transfer.
   *
   * @param ssn the ssn of the User
   * @param source the source account number
   * @param password the password of the User
   * @param target the target account number
   * @param amount the amount to transfer
   * @return a 200 OK including the User if successful, or else the exception message
   */
  @PostMapping(path = "/users/{ssn}/accounts/{sourceAccount}/transfer") 
  public ResponseEntity<?> transfer(@PathVariable("ssn") String ssn, 
      @PathVariable("sourceAccount") long source, @RequestParam("password") String password, 
      @RequestParam("targetAccount") long target, @RequestParam("amount") double amount) {
    return handleResponse(() -> {
      User user = validateUser(ssn, password);
      getBank().transfer(user, getAccount(source), getAccount(target), amount);
      saveUsers();
      return ResponseEntity.ok(user);
    });
  }

  /**
   * Request a payment.
   *
   * @param ssn the ssn of the User
   * @param source the source account number
   * @param password the password of the User
   * @param target the target account number
   * @param amount the amount to pay
   * @return a 200 OK including the User if successful, or else the exception message
   */
  @PostMapping(path = "/users/{ssn}/accounts/{sourceAccount}/payment") 
  public ResponseEntity<?> payment(@PathVariable("ssn") String ssn, 
      @PathVariable("sourceAccount") long source, @RequestParam("password") String password, 
      @RequestParam("targetAccount") long target, @RequestParam("amount") double amount) {
    return handleResponse(() -> {
      User user = validateUser(ssn, password);
      getBank().payment(user, getAccount(source), getAccount(target), amount);
      saveUsers();
      return ResponseEntity.ok(user);
    });
  }

  /**
   * Request a withdrawal.
   *
   * @param ssn the ssn of the User
   * @param account the account number
   * @param password the password of the User
   * @param amount the amount to withdraw
   * @return a 200 OK including the User if successful, or else the exception message
   */
  @PostMapping(path = "/users/{ssn}/accounts/{account}/withdraw") 
  public ResponseEntity<?> withdraw(@PathVariable("ssn") String ssn, 
      @PathVariable("account") long account, @RequestParam("password") String password, 
      @RequestParam("amount") double amount) {
    return handleResponse(() -> {
      User user = validateUser(ssn, password);
      getBank().withdraw(user, getAccount(account), amount);
      saveUsers();
      return ResponseEntity.ok(user);
    });
  }

  /**
   * Request a deposit.
   *
   * @param ssn the ssn of the User
   * @param account the account number
   * @param password the password of the User
   * @param amount the amount to deposit
   * @return a 200 OK including the User if successful, or else the exception message
   */
  @PostMapping(path = "/users/{ssn}/accounts/{account}/deposit") 
  public ResponseEntity<?> deposit(@PathVariable("ssn") String ssn, 
      @PathVariable("account") long account, @RequestParam("password") String password, 
      @RequestParam("amount") double amount) {
    return handleResponse(() -> {
      User user = validateUser(ssn, password);
      getBank().deposit(user, getAccount(account), amount);
      saveUsers();
      return ResponseEntity.ok(user);
    });
  }

}
