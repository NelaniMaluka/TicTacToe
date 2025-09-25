package main.java.org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class TicTacToeUI {
    private JLabel textLabel;
    private JButton[][] board;
    private JLabel xScoreLabel;
    private JLabel oScoreLabel;
    private String human;
    private String ai;
    private String currentPlayer;
    private int boardSize;
    private boolean gameOver = false;
    private boolean isTie = false;
    private int turns = 0;
    private int humanScore = 0;
    private int aiScore = 0;

    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private GameSettings gameSettings;

    // Constructor: inject cardLayout + mainPanel (so we can go "Back")
    public TicTacToeUI(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
    }

    public JPanel gameUi(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        boardSize = gameSettings.boardSize();

        human = gameSettings.playAs();
        ai = !human.equals("X") ? "X" : "O";

        board = new JButton[boardSize][boardSize];
        currentPlayer = human;

        JPanel gamePanel = new JPanel(new BorderLayout());

        JPanel headingPanel = new JPanel(new BorderLayout());
        headingPanel.setMaximumSize(headingPanel.getPreferredSize());
        headingPanel.setBackground(Color.darkGray);
        headingPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));

        // X score
        xScoreLabel = new JLabel("Human: " + humanScore, SwingConstants.CENTER);
        xScoreLabel.setBackground(Color.darkGray);
        xScoreLabel.setForeground(Color.white);
        xScoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        xScoreLabel.setOpaque(true);

        // y score
        oScoreLabel = new JLabel("AI: " + aiScore, SwingConstants.CENTER);
        oScoreLabel.setBackground(Color.darkGray);
        oScoreLabel.setForeground(Color.white);
        oScoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        oScoreLabel.setOpaque(true);

        // Heading text
        textLabel = new JLabel("Tic-Tac-Toe", SwingConstants.CENTER);
        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 30));
        textLabel.setOpaque(true);

        headingPanel.add(xScoreLabel, BorderLayout.WEST);
        headingPanel.add(textLabel);
        headingPanel.add(oScoreLabel, BorderLayout.EAST);
        gamePanel.add(headingPanel, BorderLayout.NORTH);

        // Board
        JPanel boardPanel = new JPanel(new GridLayout(boardSize, boardSize));
        boardPanel.setBackground(Color.darkGray);

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                JButton tile = new JButton();
                board[row][col] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 80));
                tile.setFocusable(false);

                tile.addActionListener(e -> handleMove(tile));
            }
        }

        gamePanel.add(boardPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(Color.darkGray);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        // Reset Button
        JButton resetButton = new JButton("Reset Game");
        resetButton.setFont(new Font("Arial", Font.BOLD, 15));
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.setMaximumSize(new Dimension(150, 25));
        resetButton.addActionListener(e -> resetGame());

        // Back Button
        JButton backButton = new JButton("Back to Home");
        backButton.setFont(new Font("Arial", Font.BOLD, 15));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(150, 25));
        backButton.addActionListener(e -> {
            resetGame();
            cardLayout.show(mainPanel, "Intro");
        });

        footerPanel.add(resetButton, BorderLayout.WEST);
        footerPanel.add(backButton, BorderLayout.EAST);
        gamePanel.add(footerPanel, BorderLayout.SOUTH);

        return gamePanel;
    }

    private void handleMove(JButton tile) {
        if (gameOver)
            return;

        if (tile.getText().isEmpty()) {
            tile.setText(currentPlayer);
            turns++;
            checkWinner();

            if (!gameOver) {
                currentPlayer = Objects.equals(currentPlayer, human) ? ai : human;
                textLabel.setText(currentPlayer + "'s turn");
            }

            if (!gameOver && currentPlayer.equals(ai)) {
                aiMove(); // let the AI play
            }

            if (gameOver && !isTie) {
                if (currentPlayer.equals(human))
                    xScoreLabel.setText("Human: " + ++humanScore);
                if (currentPlayer.equals(ai))
                    oScoreLabel.setText("AI: " + ++aiScore);
            }
        }
    }

    private void checkWinner() {
        int length = 3;
        if (boardSize > 4)
            length = 4;

        // Horizontal
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col <= boardSize - length; col++) { // length = 3
                if (checkLine(row, col, 0, 1, length))
                    return;
            }
        }

        // Vertical
        for (int col = 0; col < boardSize; col++) {
            for (int row = 0; row <= boardSize - length; row++) {
                if (checkLine(row, col, 1, 0, length))
                    return;
            }
        }

        // Diagonal (top-left to bottom-right)
        for (int row = 0; row <= boardSize - length; row++) {
            for (int col = 0; col <= boardSize - length; col++) {
                if (checkLine(row, col, 1, 1, length))
                    return;
            }
        }

        // Anti-diagonal (top-right to bottom-left)
        for (int row = 0; row <= boardSize - length; row++) {
            for (int col = length - 1; col < boardSize; col++) {
                if (checkLine(row, col, 1, -1, length))
                    return;
            }
        }

        // Tie
        if (turns == boardSize * boardSize) {
            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++)
                    setTie(board[row][col]);
            }
            textLabel.setText("Tie");
            gameOver = true;
            isTie = true;
        }
    }

    // Helper method to check a line in any direction
    private boolean checkLine(int startRow, int startCol, int deltaRow, int deltaCol, int length) {
        String firstVal = board[startRow][startCol].getText();
        if (firstVal.isEmpty())
            return false;

        for (int i = 1; i < length; i++) {
            int r = startRow + i * deltaRow;
            int c = startCol + i * deltaCol;
            if (!board[r][c].getText().equals(firstVal)) {
                return false; // mismatch
            }
        }

        // All matched → declare winner
        for (int i = 0; i < length; i++) {
            int r = startRow + i * deltaRow;
            int c = startCol + i * deltaCol;
            setWinner(board[r][c]);
        }
        gameOver = true;
        return true;
    }

    private void setWinner(JButton tile) {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer + " wins!");
    }

    private void setTie(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
    }

    // Reset game so it's clean next time
    private void resetGame() {
        currentPlayer = gameSettings.playAs();
        gameOver = false;
        isTie = false;
        turns = 0;
        textLabel.setText("Tic-Tac-Toe");
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                board[row][col].setText("");
                board[row][col].setBackground(Color.darkGray);
                board[row][col].setForeground(Color.white);
            }
        }
    }

    // AI move
    private void aiMove() {
        List<int[]> emptyCells = getAvailbleBlocks();
        MinimaxAI aiEngine = new MinimaxAI(ai, human, boardSize);
        if (emptyCells.isEmpty())
            return;
        int row;
        int col;

        if (gameSettings.difficulty().equals("Hard")) {
            String[][] state = getBoardState();
            int[] bestMove = aiEngine.findBestMove(state);
            row = bestMove[0];
            col = bestMove[1];
        } else if (gameSettings.difficulty().equals("Medium")) {
            String[][] state = getBoardState();
            int[] bestMove = aiEngine.findBestMoveWithDepthLimit(state, 2); // only look ahead 2 moves
            row = bestMove[0];
            col = bestMove[1];
        } else {
            int[] move = emptyCells.get(new Random().nextInt(emptyCells.size()));
            row = move[0];
            col = move[1];
        }

        JButton tile = board[row][col];
        tile.setText(ai);
        turns++;
        checkWinner();

        if (!gameOver) {
            currentPlayer = human; // switch back
            textLabel.setText(human + "'s turn");
        }
    }

    // Helper to get empty cells
    private List<int[]> getAvailbleBlocks() {
        List<int[]> emptyCells = new ArrayList<>();

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board[row][col].getText().isEmpty()) {
                    emptyCells.add(new int[] { row, col });
                }
            }
        }
        return emptyCells;
    }

    // Helper to get the current state og the board
    private String[][] getBoardState() {
        String[][] state = new String[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                state[row][col] = board[row][col].getText();
            }
        }
        return state;
    }

}
