package bank.ui;

import bank.core.Account;
import bank.core.User;
import bank.persistence.UserPersistence;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


/**
 * Manages user-related actions, including login, account management, and financial operations. Uses
 * HTTP requests to communicate with a RESTful backend.
 */
public class UserAccess {

  private final URI endpointUri;
  private final ObjectMapper objectMapper;
  private static final String APPLICATION_JSON = "application/json";
  private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";
  private static final String ACCEPT_HEADER = "Accept";
  private static final String CONTENT_TYPE_HEADER = "Content-Type";
  private HttpClient httpClient;
  private User user;

  /**
   * Constructs a UserAccess object with a predefined endpoint URI.
   *
   * @throws URISyntaxException if the endpoint URI syntax is invalid
   */
  public UserAccess() throws URISyntaxException {
    httpClient = HttpClient.newBuilder().build();
    endpointUri = new URI("http://localhost:8080/bank/");
    objectMapper = UserPersistence.createObjectMapper();
  }

  /**
   * Sets the HTTP Client to use for requests.
   *
   * @param httpClient the HttpClient to set
   */
  public void setHttpClient(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  /**
   * Sets the currently active User.
   *
   * @param user the User to set as active
   */
  public void setUser(User user) {
    this.user = user;
  }



  /**
   * Gets the currently active User.
   *
   * @return the current User, or null if no user is set
   */
  public User getUser() {
    return user;
  }

  /**
   * Ensures that a User is logged in before allowing further actions.
   *
   * @throws IllegalStateException if no User is logged in
   */
  public void checkLogin() {
    if (user == null) {
      throw new IllegalStateException("Not logged in.");
    }
  }

  /**
   * Initiates a transfer or payment between {@link Account}s.
   *
   * @param action the action type, either "transfer" or "payment"
   * @param source the source account number
   * @param target the target account number
   * @param amount the amount to transfer or pay
   */
  public void transferOrPaymentRequest(String action, long source, long target, double amount) {
    executePostRequest(
        "users/" + user.getSsn() + "/accounts/" + source + "/" 
        + action + "?password=" + user.getPassword(),
        "&targetAccount=" + target + "&amount=" + amount);
  }

  /**
   * Requests a deposit or withdrawal from a specific {@link Account}.
   *
   * @param action the action type, either "deposit" or "withdraw"
   * @param account the account number
   * @param amount the amount to deposit or withdraw
   */
  public void depositOrWithdrawRequest(String action, long account, double amount) {
    executePostRequest(
        "users/" + user.getSsn() + "/accounts/" + account + "/" 
        + action + "?password=" + user.getPassword(),
        "&amount=" + amount);
  }

  /**
   * Requests {@link User} information from the Bank.
   *
   * @param ssn the User's social security number
   * @param password the User's password
   */
  public void getUserRequest(String ssn, String password) {
    executeGetRequest("users/" + ssn + "?password=" + password);
  }

  /**
   * Deletes the currently logged in {@link User} from the Bank.
   */
  public void deleteUserRequest() {
    executeDeleteRequest("users/" + user.getSsn() + "?password=" + user.getPassword());
    setUser(null);
  }

  /**
   * Creates a new {@link User} in the Bank.
   *
   * @param user the User to create
   * @throws RuntimeException if it fails to serialize User
   */
  public void createUserRequest(User user) {
    try {
      String body = objectMapper.writeValueAsString(user);
      executePostRequest("users/" + user.getSsn(), body, APPLICATION_JSON, false);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to serialize User: " + e.getMessage());
    }
  }

  /**
   * Creates a new {@link Account} for the logged in {@link User}.
   *
   * @param account the Account to create
   * @throws RuntimeException if it fails to serialize Account
   */
  public void createAccountRequest(Account account) {
    try {
      String body = objectMapper.writeValueAsString(account);
      executePostRequest(
          "users/" + user.getSsn() + "/accounts/" + account.getAccountNumber() 
          + "?password=" + user.getPassword(),
          body, APPLICATION_JSON, true);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to serialize Account: " + e.getMessage());
    }
  }

  /**
   * Deletes a specified {@link Account} for the logged in {@link User}.
   *
   * @param account the account number
   */
  public void deleteAccountRequest(long account) {
    executeDeleteRequest("users/" + user.getSsn() + "/accounts/" 
        + account + "?password=" + user.getPassword());
  }

  /**
   * Initiates a transfer between two {@link Account}s.
   *
   * @param source the source account number
   * @param target the target account number
   * @param amount the amount to transfer
   */
  public void transferRequest(long source, long target, double amount) {
    transferOrPaymentRequest("transfer", source, target, amount);
  }

  /**
   * Initiates a payment between two {@link Account}s.
   *
   * @param source the source account number
   * @param target the target account number
   * @param amount the amount to pay
   */
  public void paymentRequest(long source, long target, double amount) {
    transferOrPaymentRequest("payment", source, target, amount);
  }

  /**
   * Initiates a withdrawal from an {@link Account}.
   *
   * @param account the account number
   * @param amount the amount to withdraw
   */
  public void withdrawRequest(long account, double amount) {
    depositOrWithdrawRequest("withdraw", account, amount);
  }

  /**
   * Initiates a deposit to an {@link Account}.
   *
   * @param account the account number
   * @param amount the amount to deposit
   */
  public void depositRequest(long account, double amount) {
    depositOrWithdrawRequest("deposit", account, amount);
  }

  /**
   * Executes an HTTP POST request with the specified path and body content.
   *
   * @param path the URL path for the POST request
   * @param body the content body for the POST request
   */
  private void executePostRequest(String path, String body) {
    executePostRequest(path, body, APPLICATION_FORM_URLENCODED, true);
  }

  /**
   * Executes an HTTP POST request with specified path, body, and content type.
   *
   * @param path the URL path for the POST request
   * @param body the content body for the POST request
   * @param contentType the Content-Type header value
   * @param needsLogin whether the request requires a logged-in {@link User}
   */
  private void executePostRequest(String path, String body, 
      String contentType, boolean needsLogin) {
    try {
      if (needsLogin) {
        checkLogin();
      }
      HttpRequest request = HttpRequest.newBuilder(endpointUri.resolve(path))
          .header(ACCEPT_HEADER, APPLICATION_JSON)
          .header(CONTENT_TYPE_HEADER, contentType)
          .POST(HttpRequest.BodyPublishers.ofString(body)).build();
      handleResponse(httpClient
          .send(request, HttpResponse.BodyHandlers.ofString()));
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("Failed to process POST request: " + e.getMessage());
    }
  }

  /**
   * Executes an HTTP GET request with the specified path.
   *
   * @param path the URL path for the GET request
   */
  private void executeGetRequest(String path) {
    try {
      HttpRequest request = HttpRequest.newBuilder(endpointUri.resolve(path))
          .header(ACCEPT_HEADER, APPLICATION_JSON)
          .header(CONTENT_TYPE_HEADER, APPLICATION_FORM_URLENCODED).GET().build();
      handleResponse(httpClient
          .send(request, HttpResponse.BodyHandlers.ofString()));
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("Failed to process GET request: " + e.getMessage());
    }
  }

  /**
   * Executes an HTTP DELETE request with the specified path.
   *
   * @param path the URL path for the DELETE request
   */
  private void executeDeleteRequest(String path) {
    try {
      checkLogin();
      HttpRequest request = HttpRequest.newBuilder(endpointUri.resolve(path))
          .header(ACCEPT_HEADER, APPLICATION_JSON)
          .header(CONTENT_TYPE_HEADER, APPLICATION_FORM_URLENCODED).DELETE().build();
      handleResponse(httpClient
          .send(request, HttpResponse.BodyHandlers.ofString()));
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("Failed to process DELETE request: " + e.getMessage());
    }
  }

  /**
   * Handles HTTP responses, updating the user instance if the response status is 200 or 201.
   *
   * @param response the HTTP response to handle
   */
  private void handleResponse(HttpResponse<String> response) {
    if (response.statusCode() != 200 && response.statusCode() != 201) {
      throw new RuntimeException(response.body());
    }
    if (response.statusCode() == 200 || response.statusCode() == 201) {
      try {
        setUser(objectMapper.readValue(response.body(), User.class));
      } catch (JsonMappingException e) {
        throw new RuntimeException("Failed to map response to User: " + e.getMessage());
      } catch (JsonProcessingException e) {
        throw new RuntimeException("Failed to process response JSON: " + e.getMessage());
      }
    }
  }

}
