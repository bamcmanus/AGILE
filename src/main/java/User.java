import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User class for SFTP project.  Used to gather information about a connection
 *
 * @see {@link Client}
 */
class User {

  /**
   * Field for password string
   */
  String password;
  /**
   * Field for username string
   */
  String username;
  /**
   * Field for host name string
   */
  String hostname;
  /**
   * Field for an IO scanner used for input
   */
  private Scanner scanner = new Scanner(System.in);

  /**
   * Default constructor initializes all fields to null
   */
  User() {
    password = null;
    hostname = null;
    username = null;
  }

  /**
   * Prompts the user for a valid password
   * @return  The password input
   */
  String getPassword() {
    System.out.println("Enter your password:");
    password = scanner.next();
    while (password == null || password.isEmpty()) {
      System.out.println("You did not enter a password. Enter your password:");
      password = scanner.next();
    }
    return password;
  }

  /**
   * Prompts the user for a valid username. Usernames must be alpha numeric of size 8-20
   * @return  The username input
   */
  String getUsername() {
    System.out.println("Enter your username:");
    username = scanner.next();
    while (username == null || username.isEmpty() || !verifyUsername(username)) {
      System.out.println("That was not a valid username.  Please enter 8-20 alpha numeric " +
          "characters.");
      username = scanner.next();
    }
    return username;
  }

  /**
   * Checks if a string is a valid username
   * @param toVerify  a string to be checked against a regular expression
   * @return  <code>true</code> if the string is a valid username
   *          <code>false</code> otherwise
   */
  boolean verifyUsername(String toVerify) {
    String userNamePattern = "^(?=.{2,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
    Pattern pattern = Pattern.compile(userNamePattern);
    Matcher matcher = pattern.matcher(toVerify);
    return matcher.matches();
  }

  /**
   * Prompts the user for a valid host name. Host names must be alpha numeric + ".".  They
   * cannot be longer than 255 characters.  Each host name segment cannot exceed 63 characters.
   * They must end and start with alpha numeric characters.
   * @return  The host name input
   */
  String getHostname() {
    System.out.println("Enter your hostname:");
    hostname = scanner.next();
    while (hostname == null || hostname.isEmpty() || !verifyHostName(hostname)) {
      System.out.println("That was not a valid Host Name.");
      System.out.println("Valid host names are no longer than 255 alpha numeric characters and \n" +
          "dashes. Each segment of the host name cannot be longer than 63 characters.");
      hostname = scanner.next();
    }
    return hostname;
  }

  /**
   * Checks if a string is a valid host name
   * @param toVerify  a string to be checked against a regular expression
   * @return  <code>true</code> if the string is a valid host name
   *          <code>false</code> otherwise
   */
  boolean verifyHostName(String toVerify) {
    if (toVerify.length() > 255)
      return false;
    String userNamePattern = "^([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])(\\." +
        "([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9]))*$";
    Pattern pattern = Pattern.compile(userNamePattern);
    Matcher matcher = pattern.matcher(toVerify);
    return matcher.matches();
  }

}