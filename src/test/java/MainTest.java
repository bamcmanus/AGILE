import org.junit.Before;
import org.junit.Test;

public class MainTest {
  Main mainClass;

  @Before
  public void setup() {
    mainClass = new Main();
  }

  @Test
  public void menuShouldStartAndExit() {
    mainClass.run();
  }
}
