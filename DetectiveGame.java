import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Java Detective Game
 * 
 * This mystery-solving game allows a player to act as a detective by reviewing clues,
 * questioning suspects, and making an accusation. The game uses a graphical interface
 * built with Swing as per our SME session lesson.
 */
public class DetectiveGame extends JFrame implements ActionListener {

    // GUI Components 
    private JTextArea displayArea; // Main text area to show game content (clues, suspect info, results)
    private JButton startButton, cluesButton, suspectsButton, accuseButton, exitButton, saveButton;
    private String detectiveName;  // Stores the player's name

    // === Constructor: Sets up the main game window ===
    public DetectiveGame(String detectiveName) {
        this.detectiveName = detectiveName;

        setTitle("🕵️ Java Detective"); // Window title
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the window on screen as per the example given during the SME session.
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245)); // Light gray background for frame

        // === Display Area: Where text output appears ===
        displayArea = new JTextArea("Welcome, Detective " + detectiveName + "! Press 'Start Case' to begin.\n");
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setEditable(false);
        displayArea.setBackground(new Color(255, 250, 240)); // Ivory-like background
        displayArea.setForeground(new Color(33, 33, 33)); // Dark gray text
        displayArea.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        add(new JScrollPane(displayArea), BorderLayout.CENTER); // Scrollable in case of overflow

        // === Button Panel: Contains all interaction buttons ===
        JPanel buttonPanel = new JPanel(new GridLayout(1, 6, 10, 10));
        buttonPanel.setBackground(new Color(70, 130, 180)); // Steel blue

        // Initialize buttons
        startButton = new JButton("Start Case");
        cluesButton = new JButton("View Clues");
        suspectsButton = new JButton("Question Suspects");
        accuseButton = new JButton("Make Accusation");
        saveButton = new JButton("Save Notes");
        exitButton = new JButton("Exit");

        // Add button styling and listeners
        for (JButton btn : new JButton[] { startButton, cluesButton, suspectsButton, accuseButton, saveButton, exitButton }) {
            btn.setBackground(Color.WHITE); // White background
            btn.setForeground(new Color(25, 25, 112)); // Midnight blue text
            btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btn.setFocusPainted(false); // Removes focus border
            btn.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255))); // Dodger blue outline
            btn.addActionListener(this); // Connect button to event handler
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.SOUTH); // Add buttons at the bottom of the frame
        setVisible(true);
    }

    // === Handles all button actions ===
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            // Start the case scenario
            displayArea.setText("📍 Crime Scene: A priceless painting was stolen from the museum.\n" +
                    "Detective " + detectiveName + ", you must find out who did it!\n");
            getContentPane().setBackground(new Color(245, 245, 245)); // Reset background
        } else if (e.getSource() == cluesButton) {
            // Show clues related to the crime
            displayArea.setText("🔍 Clues:\n" +
                    "- Broken window\n" +
                    "- Footprint size 10\n" +
                    "- Glove with initials 'M.T.'\n" +
                    "- Security footage glitch\n");
        } else if (e.getSource() == suspectsButton) {
            // Show information about each suspect
            displayArea.setText("👤 Suspect Stories:\n\n" +
                    "1. Zwelibanzi Ntanzi - Security Guard:\n" +
                    "Zweli was the night guard on duty during the theft. He was recently reprimanded for sleeping on the job.\n\n" +

                    "2. Thembelani Tshaka - Art Curator:\n" +
                    "Thembelani has been under pressure to increase museum attendance and has access to security codes.\n\n" +

                    "3. Tevin Monayi - Janitor:\n" +
                    "Tevin was seen near the restricted area but claims he was cleaning. His gloves were found near the scene.\n");
        } else if (e.getSource() == accuseButton) {
            // Allow the player to make an accusation
            String[] options = { "Zwelibanzi Ntanzi", "Thembelani Tshaka", "Tevin Monayi" };
            int choice = JOptionPane.showOptionDialog(this,
                    "Who do you accuse?",
                    "Make Accusation",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            // Evaluate the choice (correct answer is Zweli)
            if (choice == 0) {
                displayArea.setText("✅ Correct! Zwelibanzi Ntanzi disabled the cameras and stole the painting.");
                getContentPane().setBackground(new Color(144, 238, 144)); // Green background for success
                JOptionPane.showMessageDialog(this, "🎉 Congratulations, Detective " + detectiveName + "! You solved the case!");
            } else if (choice >= 0) {
                displayArea.setText("❌ Wrong choice! The real thief got away. Try again.");
                getContentPane().setBackground(new Color(255, 182, 193)); // Pink for incorrect
                JOptionPane.showMessageDialog(this, "😞 Wrong suspect! Give it another shot, Detective " + detectiveName + ".");
            }
        } else if (e.getSource() == saveButton) {
            // Save the current investigation notes to a .txt file
            JFileChooser fileChooser = new JFileChooser(); // Open a file save dialog
            fileChooser.setDialogTitle("Save Investigation Log");
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                try {
                    File fileToSave = fileChooser.getSelectedFile(); // Get the chosen file
                    BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave));
                    writer.write(displayArea.getText()); // Write contents of the displayArea
                    writer.close();
                    JOptionPane.showMessageDialog(this, "✅ Notes saved successfully to:\n" + fileToSave.getAbsolutePath());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "⚠️ Error saving file:\n" + ex.getMessage());
                }
            }
        } else if (e.getSource() == exitButton) {
            // Exit the game
            JOptionPane.showMessageDialog(this, "Thanks for playing! Goodbye, Detective " + detectiveName + ".");
            System.exit(0);
        }
    }

    // === Entry Point: Prompts for player name and starts the game ===
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String name = JOptionPane.showInputDialog(null,
                    "Enter your name, Detective:",
                    "Detective Login",
                    JOptionPane.PLAIN_MESSAGE);

            if (name == null || name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "You must enter a name to play!");
                System.exit(0); // Exit if no name is entered
            } else {
                new DetectiveGame(name.trim()); // Start game with entered name
            }
        });
    }
}
