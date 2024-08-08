import javax.swing.*;
import java.awt.*;

public class GreatScorePage extends JFrame {
    public GreatScorePage() {
        setTitle("Great Score Page");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Background Panel
        setContentPane(new BackgroundPanel("D:\\quiz\background.jpg")); // Change to your background image path

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false); // Make sure the panel is transparent to show background

        JLabel titleLabel = new JLabel("Congratulations!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        // Placeholder content
        JLabel messageLabel = new JLabel("Your score has been recorded.");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(messageLabel);

        // Back Button
        JButton backButton = new JButton("Back to Home");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> {
            new HomePage(); // Navigate to HomePage
            dispose(); // Close GreatScorePage
        });
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    // Custom panel to display background image
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GreatScorePage::new);
    }
}
