import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
import java.awt.event.*;
import java.text.DecimalFormat;

public class Easy {

    private class MineTile extends JButton {
        int r;
        int c;

        public MineTile(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    int tilesize = 70;
    int cols;
    int rows;
    int boardwidth;
    int boardheight;

    JFrame frame = new JFrame();
    JLabel label = new JLabel();
    JLabel timerLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    int mineCount;

    MineTile[][] board;
    ArrayList<MineTile> mineList;
    Random random = new Random();

    int tilesClicked = 0;
    boolean gameOver = false;

    Timer countdownTimer;
    int timeLeft = 480; // Time in seconds
    DecimalFormat timeFormat = new DecimalFormat("00");

    public Easy() {
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Minesweeper/Easy Category");

        // Start the game
        startNewGame();
    }

    public void startNewGame() {
        while (true) {
            String rowsInput = JOptionPane.showInputDialog(frame, "Enter number of rows (5 to 10):");
            String colsInput = JOptionPane.showInputDialog(frame, "Enter number of columns (5 to 10):");

            // Convert input to integers
            try {
                rows = Integer.parseInt(rowsInput);
                cols = Integer.parseInt(colsInput);

                // Check if rows and columns are within the allowed range
                if (rows >= 5 && rows <= 10 && cols >= 5 && cols <= 10) {
                    break; // Exit the loop if input is valid
                } else {
                    JOptionPane.showMessageDialog(frame, "Rows and columns must be between 5 and 10. Please try again.");
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid numbers.");
            }
        }

        // Adjust board dimensions
        boardwidth = tilesize * cols;
        boardheight = tilesize * rows;

        // Calculate total number of tiles
        int totalTiles = rows * cols;

        // Calculate the number of mines (10% of the total number of tiles)
        mineCount = totalTiles / 10;

        // Initialize the board with the user-defined rows and columns
        board = new MineTile[rows][cols];

        // Setup UI components
        setupUI();

        // Initialize the game
        initializeGame();
    }

    public void setupUI() {
        frame.getContentPane().removeAll(); // Remove previous components
        frame.repaint(); // Repaint the frame

        label.setText("Minesweeper: " + mineCount);
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setOpaque(true);

        timerLabel.setText("Time left: 00:20");
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        timerLabel.setHorizontalAlignment(JLabel.CENTER);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(label, BorderLayout.NORTH);
        textPanel.add(timerLabel, BorderLayout.SOUTH);

        boardPanel.setLayout(new GridLayout(rows, cols));
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                MineTile tile = new MineTile(r, c);
                board[r][c] = tile;
                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, 25));
                tile.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (gameOver) {
                            return;
                        }
                        MineTile tile = (MineTile) e.getSource();
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (tile.getText().isEmpty()) {
                                if (mineList.contains(tile)) {
                                    revealMines();
                                } else {
                                    checkMine(tile.r, tile.c);
                                }
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            if (tile.getText().isEmpty() && tile.isEnabled()) {
                                tile.setText("🚩");
                            } else if ("🚩".equals(tile.getText())) {
                                tile.setText("");
                            }
                        }
                    }
                });

                boardPanel.add(tile);
            }
        }

        frame.add(textPanel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
    }

    public void initializeGame() {
        mineList = new ArrayList<>();
        int mineLeft = mineCount;
        while (mineLeft > 0) {
            int r = random.nextInt(rows);
            int c = random.nextInt(cols);

            MineTile tile = board[r][c];
            if (!mineList.contains(tile)) {
                mineList.add(tile);
                mineLeft--;
            }
        }
        tilesClicked = 0; // Reset tile count
        gameOver = false; // Reset game over flag
        label.setText("Minesweeper: " + mineCount); // Reset label

        startTimer();
    }

    public void startTimer() {
        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameOver) {
                    countdownTimer.stop();
                    return;
                }
                timeLeft--;
                if (timeLeft <= 0) {
                    gameOver = true;
                    label.setText("Game Over! Time's Up!");
                    countdownTimer.stop();
                }

                int minutes = timeLeft / 60;
                int seconds = timeLeft % 60;
                timerLabel.setText("Time left: " + timeFormat.format(minutes) + ":" + timeFormat.format(seconds));
            }
        });
        countdownTimer.start();
    }

    public void revealMines() {
        for (MineTile tile : mineList) {
            tile.setText("💣");
        }
        gameOver = true;
        label.setText("Game Over! You hit a mine!");
        countdownTimer.stop();
    }

    void checkMine(int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= cols) {
            return;
        }
        MineTile tile = board[r][c];
        if (!tile.isEnabled()) {
            return;
        }
        tile.setEnabled(false);
        tilesClicked++;

        int minesFound = 0;

        minesFound += countMine(r - 1, c - 1);
        minesFound += countMine(r - 1, c);
        minesFound += countMine(r - 1, c + 1);
        minesFound += countMine(r, c - 1);
        minesFound += countMine(r, c + 1);
        minesFound += countMine(r + 1, c - 1);
        minesFound += countMine(r + 1, c);
        minesFound += countMine(r + 1, c + 1);

        if (minesFound > 0) {
            tile.setText(Integer.toString(minesFound));
        } else {
            checkMine(r - 1, c - 1);
            checkMine(r - 1, c);
            checkMine(r - 1, c + 1);
            checkMine(r, c - 1);
            checkMine(r, c + 1);
            checkMine(r + 1, c - 1);
            checkMine(r + 1, c);
            checkMine(r + 1, c + 1);
        }

        if (tilesClicked == rows * cols - mineList.size()) {
            gameOver = true;
            label.setText("Congratulations! Mines Cleared!");
            countdownTimer.stop();
        }
    }

    int countMine(int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= cols) {
            return 0;
        }
        return mineList.contains(board[r][c]) ? 1 : 0;
    }
}
