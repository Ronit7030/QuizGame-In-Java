import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizPage extends JFrame {
    private int level;
    private int currentQuestionIndex = 0;
    private List<Question> questions = new ArrayList<>();
    private JLabel questionLabel;
    private JLabel infoLabel; // Label to display level and question index
    private JRadioButton[] options;
    private ButtonGroup optionsGroup;
    private int score = 0;
    private JProgressBar progressBar;
    private int totalQuestions;

    public QuizPage(int level) {
        this.level = level;
        setTitle("Quiz - Level " + level);
        setSize(800, 800); // Adjusted size for better fit
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Path to your background image
        String backgroundImagePath = "D:\\quiz\\background.jpg"; // Ensure this path is correct

        // Panel with background image
        BackgroundPanel backgroundPanel = new BackgroundPanel(new ImageIcon(backgroundImagePath).getImage());
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Info Label to display current level and question index
        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoLabel.setForeground(Color.WHITE); // Set text color to white
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        backgroundPanel.add(infoLabel, gbc);

        // Question label
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionLabel.setForeground(Color.WHITE); // Set text color to white
        gbc.gridy = 1;
        backgroundPanel.add(questionLabel, gbc);

        options = new JRadioButton[4];
        optionsGroup = new ButtonGroup();
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < options.length; i++) {
            options[i] = new JRadioButton();
            options[i].setFont(new Font("Arial", Font.PLAIN, 16));
            options[i].setForeground(Color.WHITE); // Set text color to white
            options[i].setOpaque(false); // Make background transparent
            optionsGroup.add(options[i]);
            gbc.gridy = 2 + i;
            backgroundPanel.add(options[i], gbc);
        }

        // Progress bar
        progressBar = new JProgressBar(0, 100); // Set max value to 100 for percentage
        progressBar.setValue(0); // Start at 0%
        progressBar.setStringPainted(true);
        progressBar.setForeground(Color.BLUE); // Set progress bar color to blue
        progressBar.setBackground(Color.WHITE); // Set background color for progress bar
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        backgroundPanel.add(progressBar, gbc);

        // Next button
        JButton nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.setForeground(Color.BLACK); // Set text color to black
        nextButton.setBackground(Color.WHITE); // Set background color to white
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    loadNextQuestion();
                } else {
                    showResult();
                    new GreatScorePage(); // Open GreatScorePage
                    dispose(); // Close QuizPage
                }
                updateProgressBar();
            }
        });

        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(nextButton, gbc);

        add(backgroundPanel);
        loadQuestionsFromDatabase();
        loadNextQuestion();
        setVisible(true);
    }

    private void loadQuestionsFromDatabase() {
        String tableName = "Level" + level;
        String query = "SELECT * FROM " + tableName;
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizgame", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String questionText = rs.getString("Question");
                String optionA = rs.getString("OptionA");
                String optionB = rs.getString("OptionB");
                String optionC = rs.getString("OptionC");
                String optionD = rs.getString("OptionD");
                String correctAnswer = rs.getString("CorrectAnswer");
                questions.add(new Question(questionText, optionA, optionB, optionC, optionD, correctAnswer));
            }
            totalQuestions = questions.size();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadNextQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            return;
        }
        Question question = questions.get(currentQuestionIndex);
        questionLabel.setText(question.getQuestion());
        options[0].setText(question.getOptionA());
        options[1].setText(question.getOptionB());
        options[2].setText(question.getOptionC());
        options[3].setText(question.getOptionD());
        optionsGroup.clearSelection();
        infoLabel.setText("Level: " + level + " | Question: " + (currentQuestionIndex + 1) + "/" + totalQuestions);
    }

    private void checkAnswer() {
        Question question = questions.get(currentQuestionIndex);
        for (JRadioButton option : options) {
            if (option.isSelected() && option.getText().equals(question.getCorrectAnswer())) {
                score++;
                break;
            }
        }
    }

    private void showResult() {
        JOptionPane.showMessageDialog(this, "Your score: " + score + "/" + totalQuestions);
    }

    private void updateProgressBar() {
        int progress = (int) (((double) (currentQuestionIndex + 1) / totalQuestions) * 100);
        progressBar.setValue(progress);
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
        SwingUtilities.invokeLater(() -> new QuizPage(1)); // Example level
    }
}
