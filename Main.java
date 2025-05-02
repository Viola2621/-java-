import javax.swing.SwingUtilities;

public class Main {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        PingJiaSupermarket supermarket = new PingJiaSupermarket();
        supermarket.initializeGUI();
    });
}

}
