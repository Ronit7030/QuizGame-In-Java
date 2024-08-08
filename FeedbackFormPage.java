import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;

public class FeedbackFormPage extends JFrame {
    private JTextField nameText, emailText;
    private JTextArea commentsText;
    private JRadioButton goodButton, badButton;
    private ButtonGroup ratingGroup;
    private JSpinner dateSpinner; // Date spinner for user to select date

    public FeedbackFormPage() {
        setTitle("Feedback Form Page");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Path to your background image
        String backgroundImagePath = "D:\\quiz\\background.jpg"; // Ensure this path is correct

        // Panel with background image
        BackgroundPanel backgroundPanel = new BackgroundPanel(new ImageIcon(backgroundImagePath).getImage());
        backgroundPanel.setLayout(new BorderLayout());

        // Main container to organize components
        JPanel mainContainer = new JPanel();
        mainContainer.setOpaque(false);
        mainContainer.setLayout(new BorderLayout());

        // Navbar
        JPanel navbar = createNavbar();
        navbar.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent background for visibility
        mainContainer.add(navbar, BorderLayout.NORTH);

        // Title
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        JLabel titleLabel = new JLabel("Feedback Form", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Increase font size
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 40))); // More space before title
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space after title
        mainContainer.add(titlePanel, BorderLayout.CENTER);

        // Feedback form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setOpaque(false); // Make form panel transparent
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE); // Set label color to white
        formPanel.add(nameLabel, gbc);
        nameText = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(nameText, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE); // Set label color to white
        formPanel.add(emailLabel, gbc);
        emailText = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(emailText, gbc);

        // Date
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setForeground(Color.WHITE); // Set label color to white
        formPanel.add(dateLabel, gbc);
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        gbc.gridx = 1;
        formPanel.add(dateSpinner, gbc);

        // Rating
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel ratingLabel = new JLabel("Rating:");
        ratingLabel.setForeground(Color.WHITE); // Set label color to white
        formPanel.add(ratingLabel, gbc);
        goodButton = new JRadioButton("Good");
        badButton = new JRadioButton("Bad");
        ratingGroup = new ButtonGroup();
        ratingGroup.add(goodButton);
        ratingGroup.add(badButton);
        JPanel ratingPanel = new JPanel(new FlowLayout());
        ratingPanel.setOpaque(false); // Make rating panel transparent
        ratingPanel.add(goodButton);
        ratingPanel.add(badButton);
        gbc.gridx = 1;
        formPanel.add(ratingPanel, gbc);

        // Comments
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel commentsLabel = new JLabel("Comments:");
        commentsLabel.setForeground(Color.WHITE); // Set label color to white
        formPanel.add(commentsLabel, gbc);
        commentsText = new JTextArea(2, 20);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(new JScrollPane(commentsText), gbc);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitButtonListener());
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        formPanel.add(submitButton, gbc);

        // Add form panel with 2 lines of space from title
        JPanel formContainer = new JPanel();
        formContainer.setOpaque(false);
        formContainer.setLayout(new BorderLayout());
        formContainer.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.NORTH); // 2 lines of space
        formContainer.add(formPanel, BorderLayout.CENTER);
        mainContainer.add(formContainer, BorderLayout.CENTER);

        // Add main container to background panel
        backgroundPanel.add(mainContainer, BorderLayout.CENTER);
        add(backgroundPanel);
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
            dispose(); // Close FeedbackFormPage when navigating
        }
    }

    
    // Action Listener for Submit Button
    private class SubmitButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Collecting form data
        String name = nameText.getText();
        String email = emailText.getText();
        LocalDate date = ((JSpinner.DateEditor) dateSpinner.getEditor()).getModel().getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        String rating = goodButton.isSelected() ? "Good" : "Bad";
        String comments = commentsText.getText();

        // Database insertion logic
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizgame", "root", "");
             PreparedStatement ps = conn.prepareStatement("INSERT INTO feedback (name, email, date, rating, comments) VALUES (?, ?, ?, ?, ?)")) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setDate(3, java.sql.Date.valueOf(date));
            ps.setString(4, rating);
            ps.setString(5, comments);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(FeedbackFormPage.this, "Feedback submitted successfully!");

                // Clear form fields
                nameText.setText("");
                emailText.setText("");
                dateSpinner.setValue(new java.util.Date()); // Reset to current date
                ratingGroup.clearSelection(); // Clear radio button selection
                commentsText.setText("");
            } else {
                JOptionPane.showMessageDialog(FeedbackFormPage.this, "Failed to submit feedback.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(FeedbackFormPage.this, "An error occurred while submitting feedback.");
        }
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
        SwingUtilities.invokeLater(() -> new FeedbackFormPage());
    }
}
