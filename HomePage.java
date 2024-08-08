import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {

    public HomePage() {
        setTitle("Home Page");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Path to your background image
        String backgroundImagePath = "D:\\quiz\\background.jpg"; // Ensure this path is correct

        // Panel with background image
        BackgroundPanel backgroundPanel = new BackgroundPanel(new ImageIcon(backgroundImagePath).getImage());
        backgroundPanel.setLayout(new BorderLayout());

        // Welcome title
        JLabel welcomeTitle = new JLabel("Welcome to Quiz Cracker", JLabel.CENTER);
        welcomeTitle.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeTitle.setForeground(Color.WHITE); // Set text color
        welcomeTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        backgroundPanel.add(welcomeTitle, BorderLayout.NORTH);

        // Main container to organize components
        JPanel mainContainer = new JPanel();
        mainContainer.setOpaque(false);
        mainContainer.setLayout(new BorderLayout());

        // Navbar
        JPanel navbar = new JPanel();
        navbar.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        navbar.setOpaque(false); // Make navbar transparent

        String[] navItems = {"Home", "Rules", "Great Score", "Feedback"};
        for (String item : navItems) {
            JButton button = new JButton(item);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBackground(new Color(255, 255, 255, 200)); // Light background with some transparency
            button.setForeground(Color.BLACK);
            button.addActionListener(new NavbarActionListener());
            navbar.add(button);
        }

        mainContainer.add(navbar, BorderLayout.NORTH);

        // Center container for subtitle and levels
        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.setOpaque(false); // Make it transparent

        // Subtitle
        JLabel subtitle = new JLabel("Let's start genius for quiz cracker quiz...", JLabel.CENTER);
        subtitle.setFont(new Font("Arial", Font.BOLD, 20));
        subtitle.setForeground(Color.WHITE);
        subtitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        centerContainer.add(subtitle, BorderLayout.NORTH);

        // Panel for levels
        JPanel levelPanel = new JPanel();
        levelPanel.setLayout(new GridLayout(2, 3, 20, 20)); // 3x2 grid with gaps
        levelPanel.setOpaque(false); // Make level panel transparent

        for (int i = 1; i <= 6; i++) { // Example for 6 levels in a 3x2 grid
            JButton levelButton = new JButton("Level " + i);
            levelButton.setFont(new Font("Arial", Font.BOLD, 12)); // Decrease font size
            levelButton.setPreferredSize(new Dimension(80, 30)); // Smaller size for level buttons
            levelButton.setActionCommand(String.valueOf(i));
            levelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int level = Integer.parseInt(e.getActionCommand());
                    new QuizPage(level); // Open QuizPage for selected level
                    dispose(); // Close HomePage
                }
            });
            levelPanel.add(levelButton);
        }

        centerContainer.add(levelPanel, BorderLayout.CENTER);

        mainContainer.add(centerContainer, BorderLayout.CENTER);

        backgroundPanel.add(mainContainer, BorderLayout.CENTER);
        add(backgroundPanel);
        setVisible(true);
    }

    // Action Listener for Navbar
    private class NavbarActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "Home":
                    new HomePage();
                    break;
                case "Rules":
                    new RulesPage();
                    break;
                case "Great Score":
                    new GreatScorePage();
                    break;
                case "Feedback":
                    new FeedbackFormPage();
                    break;
                default:
                    break;
            }
            dispose(); // Close HomePage when navigating
        }
    }

    // Custom JPanel to handle background image
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(Image backgroundImage) {
            this.backgroundImage = backgroundImage;
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
        SwingUtilities.invokeLater(() -> new HomePage());
    }
}
