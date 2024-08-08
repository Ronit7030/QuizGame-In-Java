import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RulesPage extends JFrame {

    public RulesPage() {
        setTitle("Rules Page");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with background image
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Navbar
        JPanel navbar = createNavbar();
        navbar.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent background for visibility
        mainPanel.add(navbar, BorderLayout.NORTH);

        // Rules content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel titleLabel = new JLabel("Game Rules and Instructions");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridy = 0;
        contentPanel.add(titleLabel, gbc);

        // Rules Text Area
        JTextArea rulesText = new JTextArea(10, 40);
        rulesText.setText("1. Each level contains 10 questions.\n" +
                "2. every question for 30 second time otherwise skipped current question.\n" +
                "3. every question 1 mark.\n" +
                "4. Answer all questions to the best of your knowledge.\n" +
                "5. No negative marking.\n" +
                "6. Scores are calculated based on correct answers.\n" +
                "7. this game only clear knowledge and fun.\n" +
                "8. Good luck and have fun!");
        rulesText.setFont(new Font("Arial", Font.PLAIN, 16));
        rulesText.setEditable(false);
        rulesText.setLineWrap(true);
        rulesText.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(rulesText);
        gbc.gridy = 1;
        contentPanel.add(scrollPane, gbc);

        // Add content panel to main panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add main panel to frame
        add(mainPanel);
        setVisible(true);
    }

    // Method to create a navbar with consistent style
    private JPanel createNavbar() {
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

        return navbar;
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
            dispose(); // Close RulesPage when navigating
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RulesPage());
    }
}
