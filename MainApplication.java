import javax.swing.*;

public class MainApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginPage(); // Start with the Login page
        });
    }
}
