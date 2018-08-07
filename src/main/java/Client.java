import com.jcraft.jsch.*;

import java.io.File;
import java.util.Properties;
import java.util.Scanner;
import java.util.Vector;

import static java.lang.System.out;

class Client {
  private Scanner scanner = new Scanner(System.in);
  private int TIMEOUT;
  private JSch jsch;
  private ChannelSftp cSftp;
  User user;
  Session session;

  /**
   * Class constructor
   */
  public Client() {
    user = new User();
    jsch = new JSch();
    session = null;
    cSftp = new ChannelSftp();
    TIMEOUT = 10000;
  }

  /**
   * Paramatarized constructor used for unit testing timeout
   * @param timeout int with the timeout duration in ms
   */
  public Client(int timeout) {
    TIMEOUT = timeout;
    user = new User("bmcmanus\npassword\nlinux.cs.pdx.edu\n");
    jsch = new JSch();
    session = null;
    cSftp = new ChannelSftp();
  }

  /**
   * Class constructor allows for hijacking of the scanner for unit tests
   * @param argsForScanner  String for initializing a scanner object
   */
  public Client(String argsForScanner) {
    TIMEOUT = 10000;
    user = new User(argsForScanner);
    jsch = new JSch();
    session = null;
    cSftp = new ChannelSftp();
  }

  /**
   * Prompts for connection information
   */
  void promptConnectionInfo() {
    user.getUsername();
    user.getPassword();
    user.getHostname();
  }

  /**
   * Initiates connection
   */
  void connect() throws JSchException {
    session = jsch.getSession(user.username, user.hostname, 22); //throws
    session.setPassword(user.password);
    Properties config = new Properties();
    config.put("StrictHostKeyChecking", "no");
    session.setConfig(config);

    out.println("Establishing Connection...");
    session.connect(TIMEOUT); // throws

    Channel channel = session.openChannel("sftp"); // throws
    channel.setInputStream(null);
    channel.connect(TIMEOUT); //throws
    cSftp = (ChannelSftp) channel;

    out.println("Successful SFTP connection");
  }

  /**
   * Terminates connection
   */
  void disconnect() {
    cSftp.exit();
    session.disconnect();
  }

  /**
   * Lists all directories and files on the user's local machine (from the current directory).
   */
  void  displayLocalFiles() {
    File dir = new File(cSftp.lpwd());
    printLocalWorkingDir();
    File[] files = dir.listFiles();
    if(files != null) {
      int count = 0;
      for (File file : files) {
        if (count == 5) {
          count = 0;
          out.println();
        }
        out.print(file.getName() + "    ");
        ++count;
      }
      out.println("\n");
    }
  }

  /**
   * Lists all directories and files on the user's remote machine.
   */
  void displayRemoteFiles() throws SftpException {
    printRemoteWorkingDir();
    Vector remoteDir = cSftp.ls(cSftp.pwd());
    if (remoteDir != null) {
      int count = 0;
      for (int i = 0; i < remoteDir.size(); ++i) {
        if (count == 5) {
          count = 0;
          out.println();
        }
        Object dirEntry = remoteDir.elementAt(i);
        if (dirEntry instanceof ChannelSftp.LsEntry)
          out.print(((ChannelSftp.LsEntry) dirEntry).getFilename() + "    ");
        ++count;
      }
      out.println("\n");
    }
  }

  /**
   * Create a directory on the user's remote machine.
   */
  void createRemoteDir() throws SftpException {
    out.println("Enter the name of the new directory: ");
    String newDir = scanner.next();
    cSftp.mkdir(newDir);
  }

  /**
   * Print current working local path
   */
  void printLocalWorkingDir() {
    String lpwd = cSftp.lpwd();
    out.println("This is your current local working directory: " + lpwd + "\n");
  }

  /**
   * Print current working remote path
   */
  void printRemoteWorkingDir() throws SftpException {
    String pwd = cSftp.pwd();
    out.println("This is your current remote working directory: " + pwd + "\n");
  }

  /**
   * Change current working local path
   */
  void changeLocalWorkingDir() throws SftpException {
    String newDir;
    String lpwd = cSftp.lpwd();
    out.println("This is your current local working directory: " + lpwd + "\n");
    out.println("Enter the path of the directory you'd like to change to: ");
    newDir = scanner.next();
    cSftp.lcd(newDir);
    lpwd = cSftp.lpwd();
    out.println("This is your new current local working directory: " + lpwd + "\n");
  }

  /**
   * Change current working remote path
   */
  void changeRemoteWorkingDir() throws SftpException {
    String newDir;
    String pwd = cSftp.pwd();
    out.println("This is your current local working directory: " + pwd + "\n");
    out.println("Enter the path of the directory you'd like to change to: ");
    newDir = scanner.next();
    cSftp.cd(newDir);
    pwd = cSftp.pwd();
    out.println("This is your new current local working directory: " + pwd + "\n");
  }

  /**
   * Upload file to current remote directory path
   */
  void uploadFile(String filename) throws SftpException {
    cSftp.put(filename, filename);
    String pwd = cSftp.pwd();
    out.println("The file has been uploaded to: " + pwd);
  }

  /**
   * Download file to current local directory path
   */
  void downloadFile(String filename) throws SftpException{
    cSftp.get(filename,filename);
    String lpwd = cSftp.lpwd();
    out.println("The file has been downloaded to: " + lpwd);
  }

  /**
   * Create a directory on the user's local machine.
   */
  void createLocalDir() {
	out.println("Enter the name of the new directory: ");
	String dirName = scanner.next();
    String path = cSftp.lpwd() + "/" + dirName;
    File newDir = new File(path);
    if (!newDir.mkdir())
      out.println("Error creating local directory.");
  }

}