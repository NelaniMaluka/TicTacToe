package main.java.org.example;

public class MinimaxAI {
    private final String ai;
    private final String human;
    private final int boardSize;
    private final int winLength; // 3 or 4 depending on board size

    public MinimaxAI(String ai, String human, int boardSize) {
        this.ai = ai;
        this.human = human;
        this.boardSize = boardSize;
        this.winLength = (boardSize > 4) ? 4 : 3; // same rule you used
    }

    // Public method: Hard difficulty (full minimax)
    public int[] findBestMove(String[][] board) {
        return findBestMoveWithDepthLimit(board, Integer.MAX_VALUE);
    }

    // Public method: Medium difficulty (depth-limited minimax)
    public int[] findBestMoveWithDepthLimit(String[][] board, int depthLimit) {
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j].isEmpty()) {
                    board[i][j] = ai;
                    int moveVal = minimax(board, 0, false, depthLimit);
                    board[i][j] = "";

                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

    // Minimax algorithm (supports depth limit)
    private int minimax(String[][] board, int depth, boolean isMaximizing, int depthLimit) {
        int score = evaluate(board);

        if (score == 10) return score - depth;
        if (score == -10) return score + depth;
        if (!isMovesLeft(board)) return 0;
        if (depth >= depthLimit) return 0; // stop searching deeper

        int best;
        if (isMaximizing) {
            best = Integer.MIN_VALUE;
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    if (board[i][j].isEmpty()) {
                        board[i][j] = ai;
                        best = Math.max(best, minimax(board, depth + 1, false, depthLimit));
                        board[i][j] = "";
                    }
                }
            }
        } else {
            best = Integer.MAX_VALUE;
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    if (board[i][j].isEmpty()) {
                        board[i][j] = human;
                        best = Math.min(best, minimax(board, depth + 1, true, depthLimit));
                        board[i][j] = "";
                    }
                }
            }
        }
        return best;
    }

    // Evaluates board state
    private int evaluate(String[][] board) {
        // Horizontal & Vertical
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j <= boardSize - winLength; j++) {
                // Horizontal
                String first = board[i][j];
                if (!first.isEmpty() && checkLine(board, i, j, 0, 1, first)) {
                    return first.equals(ai) ? 10 : -10;
                }
                // Vertical
                first = board[j][i];
                if (!first.isEmpty() && checkLine(board, j, i, 1, 0, first)) {
                    return first.equals(ai) ? 10 : -10;
                }
            }
        }

        // Diagonal (top-left to bottom-right)
        for (int i = 0; i <= boardSize - winLength; i++) {
            for (int j = 0; j <= boardSize - winLength; j++) {
                String first = board[i][j];
                if (!first.isEmpty() && checkLine(board, i, j, 1, 1, first)) {
                    return first.equals(ai) ? 10 : -10;
                }
            }
        }

        // Diagonal (top-right to bottom-left)
        for (int i = 0; i <= boardSize - winLength; i++) {
            for (int j = winLength - 1; j < boardSize; j++) {
                String first = board[i][j];
                if (!first.isEmpty() && checkLine(board, i, j, 1, -1, first)) {
                    return first.equals(ai) ? 10 : -10;
                }
            }
        }

        return 0; // no winner
    }

    // Checks line of given length
    private boolean checkLine(String[][] board, int row, int col, int dRow, int dCol, String player) {
        for (int k = 0; k < winLength; k++) {
            if (!board[row + k * dRow][col + k * dCol].equals(player)) {
                return false;
            }
        }
        return true;
    }

    // Checks if there are moves left
    private boolean isMovesLeft(String[][] board) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j].isEmpty()) return true;
            }
        }
        return false;
    }
}
