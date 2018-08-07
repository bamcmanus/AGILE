import com.jcraft.jsch.JSchException;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ClientTest {
	private Client client;

	@Test
	public void promptConnectionInfoHappyPath() {
		client = new Client("bmcmanus\npassword\nlinux.cs.pdx.edu\n");
		client.promptConnectionInfo();
		assertThat(client.user.username, equalTo("Brent"));
		assertThat(client.user.password, equalTo("password"));
		assertThat(client.user.hostname, equalTo("linux.cs.pdx.edu"));
	}

	@Test
	public void baseConstructorInitializationHappyPath() {
		client = new Client();
		assertThat(client.session, equalTo(null));
		assertThat(client.user.username, equalTo(null));
		assertThat(client.user.username, equalTo(null));
		assertThat(client.user.hostname, equalTo(null));
	}

	@Test (expected = JSchException.class)
	public void connectThrowsJSchExceptionWithBadHost() throws JSchException {
		client = new Client("bmcmanus\npassword\nlnux.cs.pdx.edu\n");
		client.promptConnectionInfo();
		client.connect();
	}

	@Test (expected = JSchException.class)
	public void connectThrowsJSchExceptionWithBadUsername() throws JSchException {
		client = new Client("badName\npassword\nlinux.cs.pdx.edu\n");
		client.promptConnectionInfo();
		client.connect();
	}

	@Test (expected = JSchException.class)
	public void connectThrowsJSchExceptionWhenSessionTimeOut() throws JSchException {
		client = new Client(1);
		client.promptConnectionInfo();
		client.connect();
	}

}