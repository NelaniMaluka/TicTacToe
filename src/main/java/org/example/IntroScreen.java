package main.java.org.example;

import javax.swing.*;
import java.awt.*;

public class IntroScreen {
    // CardLayout for switching between screens
    private final CardLayout cardLayout;
    // Main container panel holding all screens
    private final JPanel mainPanel;

    // Constructor to initialize layout and main panel reference
    public IntroScreen(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
    }

    // Method that creates and returns the Intro/Settings panel
    public JPanel createIntroPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical layout

        // Welcome label with styled HTML text
        String introText = "<html><div style='width:380px; text-align:center;"
                + "font-family:Arial;'>"
                + "Welcome to Nelani's Game!<br>Get ready to play Tic Tac Toe."
                + "</div></html>";

        JLabel label = new JLabel(introText, SwingConstants.CENTER);
        label.setBackground(Color.darkGray);
        label.setForeground(Color.white);
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setOpaque(true);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel for board size selection
        JPanel tilePanel = new JPanel();
        tilePanel.setBackground(Color.darkGray);
        tilePanel.setLayout(new BoxLayout(tilePanel, BoxLayout.X_AXIS));

        JLabel tileLabel = new JLabel("Board Size: ");
        tileLabel.setForeground(Color.white);
        tileLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Dropdown to select board size (3x3, 4x4, 5x5)
        Integer[] tileCountChoices = { 3, 4, 5 };
        JComboBox<Integer> tileCountDropdown = new JComboBox<>(tileCountChoices);
        tileCountDropdown.setPreferredSize(new Dimension(150, 25));
        tileCountDropdown.setMaximumSize(new Dimension(150, 25));

        // Add label + dropdown to tile panel
        tilePanel.add(tileLabel);
        tilePanel.add(Box.createHorizontalStrut(10)); // spacing
        tilePanel.add(tileCountDropdown);
        tilePanel.setMaximumSize(tilePanel.getPreferredSize());

        // Panel for difficulty selection
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setBackground(Color.darkGray);
        difficultyPanel.setLayout(new BoxLayout(difficultyPanel, BoxLayout.X_AXIS));

        JLabel difficultyLabel = new JLabel("Difficulty: ");
        difficultyLabel.setForeground(Color.white);
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Dropdown for difficulty options
        String[] difficultyChoices = { "Easy", "Medium", "Hard" };
        JComboBox<String> difficultyDropdown = new JComboBox<>(difficultyChoices);
        difficultyDropdown.setPreferredSize(new Dimension(150, 25));
        difficultyDropdown.setMaximumSize(new Dimension(150, 25));

        // Logic: if Hard is selected → lock board size to 3
        difficultyDropdown.addActionListener(e -> {
            String selectedDifficulty = (String) difficultyDropdown.getSelectedItem();
            if ("Hard".equals(selectedDifficulty)) {
                tileCountDropdown.setSelectedItem(3); // auto-set board size
                tileCountDropdown.setEnabled(false); // disable user change
            } else {
                tileCountDropdown.setEnabled(true); // re-enable for Easy/Medium
            }
        });

        difficultyPanel.add(difficultyLabel);
        difficultyPanel.add(Box.createHorizontalStrut(23)); // spacing
        difficultyPanel.add(difficultyDropdown);
        difficultyPanel.setMaximumSize(difficultyPanel.getPreferredSize());

        // Panel for choosing whether to play as X or O
        JPanel playerPanel = new JPanel();
        playerPanel.setBackground(Color.darkGray);
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.X_AXIS));

        JLabel playerLabel = new JLabel("Play as: ");
        playerLabel.setForeground(Color.white);
        playerLabel.setFont(new Font("Arial", Font.BOLD, 18));

        String[] playerChoices = { "X", "O" };
        JComboBox<String> playerDropdown = new JComboBox<>(playerChoices);
        playerDropdown.setPreferredSize(new Dimension(150, 25));
        playerDropdown.setMaximumSize(new Dimension(150, 25));

        playerPanel.add(playerLabel);
        playerPanel.add(Box.createHorizontalStrut(40)); // spacing
        playerPanel.add(playerDropdown);
        playerPanel.setMaximumSize(difficultyPanel.getPreferredSize());

        // Start Game button
        JButton button = new JButton("Start Game");
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(150, 25));

        // On click → gather settings and switch to game screen
        button.addActionListener(e -> {
            int boardSize = (Integer) tileCountDropdown.getSelectedItem();
            String difficulty = (String) difficultyDropdown.getSelectedItem();
            String playAs = (String) playerDropdown.getSelectedItem();

            GameSettings settings = new GameSettings(boardSize, difficulty, playAs);

            // Pass settings into game UI and show it
            mainPanel.add(new TicTacToeUI(cardLayout, mainPanel).gameUi(settings), "Game");
            cardLayout.show(mainPanel, "Game");
        });

        // Instruction label for win conditions
        JLabel abLabel = new JLabel(
                "<html><div style='width:380px; margin:20px; text-align:center;'>"
                        + "Note: In the 3x3 and 4x4 boards, a player wins by aligning 3 tiles, "
                        + "whereas in the 5x5 board, 4 aligned tiles are required to win."
                        + "</div></html>",
                SwingConstants.CENTER);
        abLabel.setBackground(Color.darkGray);
        abLabel.setForeground(Color.white);
        abLabel.setFont(new Font("Arial", Font.BOLD, 15));
        abLabel.setOpaque(true);
        abLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Instruction label for Hard mode restrictions
        JLabel nbLabel = new JLabel(
                "<html><div style='width:380px; margin:20px; text-align:center;'>"
                        + "Note: Due to computational constraints, Hard mode is available "
                        + "only for the 3×3 board format."
                        + "</div></html>",
                SwingConstants.CENTER);
        nbLabel.setBackground(Color.darkGray);
        nbLabel.setForeground(Color.white);
        nbLabel.setFont(new Font("Arial", Font.BOLD, 15));
        nbLabel.setOpaque(true);
        nbLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to main intro panel (with spacing)
        panel.add(Box.createVerticalStrut(30)); // top padding
        panel.add(label);
        panel.add(Box.createVerticalStrut(50));
        panel.add(tilePanel);
        panel.add(Box.createVerticalStrut(40));
        panel.add(difficultyPanel);
        panel.add(Box.createVerticalStrut(40));
        panel.add(playerPanel);
        panel.add(Box.createVerticalStrut(40));
        panel.add(button);
        panel.add(Box.createVerticalStrut(30));
        panel.add(abLabel);
        panel.add(Box.createVerticalStrut(0));
        panel.add(nbLabel);
        panel.add(Box.createVerticalGlue()); // push everything slightly upward

        return panel;
    }
}
