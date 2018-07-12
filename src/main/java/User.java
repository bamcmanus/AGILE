import java.util.Scanner;

class User {
    //members
    private String pw;
    private String un;
    private String hn;
    private Scanner scanner = new Scanner(System.in);
    private String error = "Error";

    //methods
    private boolean promptPassword() {
        System.out.println("Enter your password:");
        pw = scanner.next();
        if (pw == null || pw.isEmpty())
            return false;
        return true;
    }

    String getPassword() {
        if (promptPassword())
            return pw;
        else
            return error;
    }

    private boolean promptUsername() {
        System.out.println("Enter your username:");
        un = scanner.next();
        if (un == null || un.isEmpty())
            return false;
        return true;
    }

    String getUsername() {
        if (promptUsername())
            return un;
        else
            return error;
    }

    private boolean promptHostname() {
        System.out.println("Enter your hostname:");
        hn = scanner.next();
        if (hn == null || hn.isEmpty())
            return false;
        return true;
    }

    String getHostname() {
        if (promptHostname())
            return hn;
        else
            return error;
    }
}

