import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegistrationPage extends JFrame {
    private JTextField nameText, userText, emailText;
    private JPasswordField passwordText, confirmPasswordText;

    public RegistrationPage() {
        setTitle("Registration Page");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Path to your background image
        String backgroundImagePath = "D:\\quiz\\background.jpg"; // Ensure this path is correct

        // Panel with background image
        BackgroundPanel backgroundPanel = new BackgroundPanel(new ImageIcon(backgroundImagePath).getImage());
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel titleLabel = new JLabel("Registration for Quiz Cracker");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE); // Set text color to white
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(titleLabel, gbc);

        // Name
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE); // Set text color to white
        backgroundPanel.add(nameLabel, gbc);
        nameText = new JTextField(20);
        gbc.gridx = 1;
        backgroundPanel.add(nameText, gbc);

        // Username
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE); // Set text color to white
        backgroundPanel.add(usernameLabel, gbc);
        userText = new JTextField(20);
        gbc.gridx = 1;
        backgroundPanel.add(userText, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE); // Set text color to white
        backgroundPanel.add(passwordLabel, gbc);
        passwordText = new JPasswordField(20);
        gbc.gridx = 1;
        backgroundPanel.add(passwordText, gbc);

        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setForeground(Color.WHITE); // Set text color to white
        backgroundPanel.add(confirmPasswordLabel, gbc);
        confirmPasswordText = new JPasswordField(20);
        gbc.gridx = 1;
        backgroundPanel.add(confirmPasswordText, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 5;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE); // Set text color to white
        backgroundPanel.add(emailLabel, gbc);
        emailText = new JTextField(20);
        gbc.gridx = 1;
        backgroundPanel.add(emailText, gbc);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setForeground(Color.WHITE); // Set text color to white
        registerButton.setBackground(Color.BLACK); // Set background color for button
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        backgroundPanel.add(registerButton, gbc);

        // Register Button Action Listener
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                String confirmPassword = new String(confirmPasswordText.getPassword());
                String email = emailText.getText();

                if (name.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Connect to the database and insert the data
                String url = "jdbc:mysql://localhost:3306/quizgame?useSSL=false";
                String dbUser = "root"; // Replace with your database username
                String dbPassword = ""; // Replace with your database password

                // Ensure the column names are correctly spelled and match the database
                String sql = "INSERT INTO registration (Name, Username, Password, ConfirmPass, Email) VALUES (?, ?, ?, ?, ?)";

                try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, name);
                    pstmt.setString(2, username);
                    pstmt.setString(3, password);
                    pstmt.setString(4, confirmPassword); // Ensure this matches the column name in the database
                    pstmt.setString(5, email);

                    int rowsInserted = pstmt.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        new LoginPage(); // Open the login page after successful registration
                        dispose(); // Close the registration page
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(backgroundPanel);
        setVisible(true);
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
        SwingUtilities.invokeLater(() -> new RegistrationPage());
    }
}
