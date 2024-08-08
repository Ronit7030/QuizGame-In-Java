import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginPage extends JFrame {
    private JTextField userText;
    private JPasswordField passwordText;
    private JCheckBox rememberMeCheckBox;

    public LoginPage() {
        setTitle("Login Page");
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
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Login for Quiz Cracker");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE); // Set text color to white
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the title
        backgroundPanel.add(titleLabel, gbc);

        // Username
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE); // Set text color to white
        backgroundPanel.add(usernameLabel, gbc);
        userText = new JTextField(20);
        gbc.gridx = 1;
        backgroundPanel.add(userText, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE); // Set text color to white
        backgroundPanel.add(passwordLabel, gbc);
        passwordText = new JPasswordField(20);
        gbc.gridx = 1;
        backgroundPanel.add(passwordText, gbc);

        // Remember Me
        rememberMeCheckBox = new JCheckBox("Remember Me");
        rememberMeCheckBox.setForeground(Color.WHITE); // Set text color to white
        rememberMeCheckBox.setBackground(Color.BLACK); // Set background color to black
        rememberMeCheckBox.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font size for checkbox
        rememberMeCheckBox.setPreferredSize(new Dimension(200, 30)); // Increase the size of the checkbox
        gbc.gridx = 1; gbc.gridy = 3;
        backgroundPanel.add(rememberMeCheckBox, gbc);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setForeground(Color.WHITE); // Set text color to white
        loginButton.setBackground(Color.BLACK); // Set background color for button
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        backgroundPanel.add(loginButton, gbc);

        // Registration Link
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setForeground(Color.WHITE); // Set text color to white
        registerButton.setBackground(Color.BLACK); // Set background color for button
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        backgroundPanel.add(registerButton, gbc);

        // Login Button Action Listener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());

                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizgame", "root", "");
                     PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM registration WHERE Username = ? AND Password = ?")) {
                    pstmt.setString(1, username);
                    pstmt.setString(2, password);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        new HomePage(); // Open HomePage
                        dispose(); // Close LoginPage
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Register Button Action Listener
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrationPage(); // Open RegistrationPage
                dispose(); // Close LoginPage
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
        SwingUtilities.invokeLater(() -> new LoginPage());
    }
}
