package main.java.org.example;

import java.awt.*;
import javax.swing.*;

public class TicTacToe {
    // Window dimensions
    int boarderWidth = 600;
    int boarderHeight = 650;

    // Main game window
    JFrame frame = new JFrame("Nelani's Tic-Tac-Toe");

    // Constructor initializes the window and first screen
    public TicTacToe() {
        // Configure main frame (game window)
        frame.setVisible(true);
        frame.setSize(boarderWidth, boarderHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // CardLayout allows switching between multiple screens (Intro/Game/etc.)
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout); // main container panel

        // Create Intro screen and add it as the first view
        IntroScreen intro = new IntroScreen(cardLayout, mainPanel);
        mainPanel.add(intro.createIntroPanel(), "Intro");

        // Add main panel to the frame
        frame.add(mainPanel);
        frame.setLocationRelativeTo(null); // keep window centered
        frame.setVisible(true); // show window

        // Display Intro screen by default
        cardLayout.show(mainPanel, "Intro");
    }
}
