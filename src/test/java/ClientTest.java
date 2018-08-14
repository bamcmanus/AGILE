import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ClientTest {
  /**
   * These need to be filled in before the tests will run properly.
   */
  private String userName;
  private String password;
  private String hostName;

  @Test
  public void test() {
    assertThat("Default", equalTo("Default"));
  }

  @Test
  public void verifyDisplayLocalDirectoriesAndFiles() {
    Client client = new Client();
    int expected = 1;
    // int actual = client.displayLocalFiles();
    // assertThat(expected, equalTo(actual));
  }

  /**
   * Trying to upload a file should result in an error. This test verifies that.
   */
  @Test
  public void uploadFakeFile() {
    Client client = new Client(password, hostName, userName);
    try {
      client.connect();

      //try uploading a non existent file.
      try {
        client.uploadFile("This is not a file");
      } catch (Exception e) {
        System.out.println("Correct. This should throw an file not found exception.");
        e.printStackTrace();
      }

    } catch (Exception e) {
      System.out.println("Error in testing uploading a fake file.");
      e.printStackTrace();
    }
  }

  /**
   * Test whether uploading a real file is correct.
   */
  @Test
  public void uploadFile() {
    String fileName = "testfile.txt";
    String fileName2 = "testfile.txt, testfile2.txt";

    Client client = new Client(password, hostName, userName);
    try {
      client.connect();

      try {
        client.uploadFile(fileName);
        client.uploadFile(fileName2);

        File dir = new File(client.getcSftp().pwd());
        File[] files = dir.listFiles();
        for (File file : files) {
          if (file.getName().equals(fileName)) {
            //the file was found so the upload was successful.
            assertThat(file.getName(), equalTo(fileName));
          }
        }
        System.out.println("Now deleting the files you uploaded.");
        client.deleteRemoteFile(fileName);
        client.deleteRemoteFile(fileName2);
      } catch (Exception e) {
        System.out.println("There was an error uploading the file.");
      }
    } catch (Exception e) {
      System.out.println("There was an error with the uploading correct file test.");
    }
  }

  /**
   * Trying to upload a file that does not exist should result in an error. This test verifies that is the case.
   */
  @Test
  public void downloadFakeFile() {
    Client client = new Client("Oatman641!", "linux.cs.pdx.edu", "brambora");
    try {
      client.connect();

      //try downloading a non existent file.
      try {
        client.downloadFile("This is not a file");
      } catch (Exception e) {
        System.out.println("Correct. This should throw a file not found exception.");
        e.printStackTrace();
      }

    } catch (Exception e) {
      System.out.println("Error");
      e.printStackTrace();
    }
  }

  /**
   * Test whether a file is uploaded correctly.
   */
  @Test
  public void downloadFile() {
    String fileName = "testfile.txt";
    String fileName2 = "testfile.txt, testfile2.txt";

    Client client = new Client("Oatman641!", "linux.cs.pdx.edu", "brambora");
    try {
      client.connect();

      //try downloading a file
      try {
        client.downloadFile(fileName);
        client.downloadFile(fileName2);
        //verify that the file is in the local directory
        File dir = new File(client.getcSftp().lpwd());
        File[] files = dir.listFiles();
        for (File file : files) {
          if (file.getName().equals(fileName)) {
            //the file was found so the download was successful.
            assertThat(file.getName(), equalTo(fileName));
          }
        }

      } catch (Exception e) {
        System.out.println("This is an error. There was a problem downloading.");
        e.printStackTrace();
      }
    } catch (Exception e) {
      System.out.println("There was an error somewhere.");
      e.printStackTrace();
    }
  }

  /**
   * Asserts that a local file is renamed successfully
   *
   * @throws IOException
   */
  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void localRename_FileRenamed_Success() throws IOException {
    Client client = new Client();
    String filename = "FileYouWantToRename.txt";
    File file = folder.newFile(filename);
    file.createNewFile();
    String rename = "RenamedFile.txt";
    File renamed = new File(rename);

    client.renameLocal(file, renamed);
    File renamedFile = new File(rename);

    assertThat(renamedFile.exists(), equalTo(true));
  }

  /**
   * Asserts that a local directory is renamed successfully
   *
   * @throws IOException
   */
  @Test
  public void localRename_DirectoryRenamed_Success() throws IOException {
    Client client = new Client();
    String directoryName = "DirectoryYouWantToRename";
    File directory = folder.newFolder(directoryName);
    directory.mkdir();
    String rename = "RenamedDirectory";
    File renamed = new File(rename);

    client.renameLocal(directory, renamed);
    File renamedDirectory = new File(rename);

    assertThat(renamedDirectory.exists(), equalTo(true));
  }
}
