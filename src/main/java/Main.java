public class Main {
  public static void main(String[] agrs) {
      Main start = new Main();
      start.run();
  }

  public int run() {
    var mainMenu = new Menu();

    mainMenu.mainMenu();

    return 0;
  }
}
