import java.util.Scanner;
import static java.lang.System.out;

class Menu {
    //members
    private Scanner sc = new Scanner(System.in);
    private int option;

    //methods
    int mainMenu() {
            out.println("1. Establish Connection");
            out.println("2. Exit");
            out.println("Enter an above option number:");
            //Tests for incorrect input
            while (!sc.hasNextInt()) {
                out.print("Enter a valid option: ");
                sc.nextLine();
            }
            option = sc.nextInt();
            return option;
    }

    /**
     * This is a working menu once a connection is established.  Method displays all options, prompts
     * for a choice and returns that choice as an integer.
     *
     * @return an int with a valid option number
     */
    int workingMenu() {
        out.println("1.  List Directories");
        out.println("2.  Get File/Files");
        out.println("3.  Put File/Files");
        out.println("4.  Create Directory");
        out.println("5.  Delete File/Directory");
        out.println("6.  Change permissions");
        out.println("7.  Copy Directory");
        out.println("8.  Rename File/Directory");
        out.println("9.  View Log History");
        out.println("10. Close Connection");
        out.println("Enter an above option number:");
        //Tests for incorrect input
        while (!sc.hasNextInt()) {
            out.print("Enter a valid option: ");
            sc.nextLine();
        }
        option = sc.nextInt();
        return option;
    }
}
