import com.jcraft.jsch.*;


public class Client {

  public void Sftp() {
    var jsch = new JSch();
    int option;

    var menu = new Menu();
    if (menu.mainMenu() == 2) {
      System.out.println("Goodbye");
      System.exit(0);
    } else {
      try {
        var user = new User();
        String username = user.getUsername();
        String password = user.getPassword();
        String hostname = user.getHostname();
        Session session = jsch.getSession(username, hostname, 22);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        System.out.println("Establishing Connection...");
        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp cSftp = (ChannelSftp) channel;

        System.out.println("Successful SFTP connection");

        do {
          option = menu.workingMenu();
          switch (option) {
            case 1: //list directories: local and remote options
              System.out.println("Listing directories...");
              break;

            case 2: //get file/files: which files, put where
              System.out.println("Getting files...");
              break;

            case 3: //put file/files: which files put where
              System.out.println("Putting Files...");
              break;

            case 4: //create directory: name?, where?
              System.out.println("Creating directories...");
              break;

            case 5: //delete file/directory
              System.out.println("Deleting directories...");
              break;

            case 6: //change permissions
              System.out.println("Changing permissions...");
              break;

            case 7: //copy directory
              System.out.println("Copying directories...");
              break;

            case 8: //rename file
              System.out.println("Renaming files...");
              break;

            case 9: //view log history
              System.out.println("Viewing log history...");
              break;

            case 10: //exit
              System.out.println("Closing connection...");
              cSftp.exit();
              break;

            default:
              System.out.println("Try again");
              break;
          }
        } while (option != 10);
      } catch (Exception e) {
        System.out.println("Client error");
      }
    }
    System.exit(0);
  }
}

