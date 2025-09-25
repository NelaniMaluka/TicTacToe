package main.java.org.example;

import java.awt.*;
import javax.swing.*;

public class TicTacToe {
    int boarderWidth = 600;
    int boarderHeight = 650;

    JFrame frame = new JFrame("Nelani's Tic-Tac-Toe");

    public TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boarderWidth, boarderHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        IntroScreen intro = new IntroScreen(cardLayout, mainPanel);

        mainPanel.add(intro.createIntroPanel(), "Intro");

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        cardLayout.show(mainPanel, "Intro");
    }
}
