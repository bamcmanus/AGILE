import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.io.ByteArrayInputStream;

public class MainTest {
  Main mainClass;

  @Before
  public void setup() {
    mainClass = new Main();
  }

  @Test
  public void menuShouldStartAndExit() {
    ByteArrayInputStream in = new ByteArrayInputStream("2".getBytes());
    System.setIn(in);
    assert(mainClass.run() == 0);
  }
}
