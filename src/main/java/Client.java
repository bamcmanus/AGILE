import com.jcraft.jsch.*;
import static java.lang.System.out;


class Client {
    //methods
    void sftp() {
        JSch jsch = new JSch(); //init jsch class
        int option = 0; //contains user input for menu navigation
        Menu menu = new Menu(); //init menu class
        boolean again = true;

        do {
            option = menu.mainMenu(); //main menu prompt

            //login condition
            if (option == 1) {
                try {
                    User user = new User();
                    String username = user.getUsername();
                    String password = user.getPassword();
                    String hostname = user.getHostname();
                    Session session = jsch.getSession(username, hostname, 22);
                    session.setPassword(password);
                    session.setConfig("StrictHostKeyChecking", "no");
                    out.println("Establishing Connection...");
                    session.connect();

                    Channel channel = session.openChannel("sftp");
                    channel.connect();
                    ChannelSftp cSftp = (ChannelSftp) channel;


                    out.println("Successful SFTP connection");

                    do {
                        option = menu.workingMenu(); //display working menu
                        switch (option) {
                            case 1: //list directories: local and remote options
                                out.println("Listing directories...");
                                break;

                            case 2: //get file/files: which files, put where
                                out.println("Getting files...");
                                break;

                            case 3: //put file/files: which files put where
                                out.println("Putting Files...");
                                break;

                            case 4: //create directory: name?, where?
                                out.println("Creating directories...");
                                break;

                            case 5: //delete file/directory
                                out.println("Deleting directories...");
                                break;

                            case 6: //change permissions
                                out.println("Changing permissions...");
                                break;

                            case 7: //copy directory
                                out.println("Copying directories...");
                                break;

                            case 8: //rename file
                                out.println("Renaming files...");
                                break;

                            case 9: //view log history
                                out.println("Viewing log history...");
                                break;

                            case 10: //exit
                                out.println("Closing connection...");
                                cSftp.exit();
                                session.disconnect();
                                again = false;
                                break;

                            default:
                                out.println("Try again");
                                break;
                        }
                    } while (again);
                } catch (Exception e) {
                    out.println("Client error");
                    e.printStackTrace();
                }
                again = true;
            }
            if (option == 2){
                out.println("Goodbye");
                again = false;
            }
        } while (again);
        //exit program
    }
}

