import controller.MainController;

/**
 * Entry point...
 */
public class Main {
    private final MainController mainController = new MainController();

    private void launch() {
        try {
            mainController.launch();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main entryPoint = new Main();
        entryPoint.launch();
    }
}
