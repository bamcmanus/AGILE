import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.io.File;
import org.junit.Test;

public class ClientTest {
  Client client = new Client();

  @Test
  public void promptConnectionInfoHappyPath() {
    client = new Client("Brent\npassword\nlinux.cs.pdx.edu");
    client.promptConnectionInfo();
    assertThat(client.user.username, equalTo("Brent"));
    assertThat(client.user.password, equalTo("password"));
    assertThat(client.user.hostname, equalTo("linux.cs.pdx.edu"));
  }

  @Test
  public void baseConstructorInitializationHappyPath() {
    assertThat(client.session, equalTo(null));
  }

  @Test
  public void verifyDisplayLocalDirectoriesAndFiles() {
    Client client = new Client();
    File directory = new File(".");
    int expected = 1;
    int actual = client.displayLocalFiles(directory);
    assertThat(expected, equalTo(actual));
  }

}
