import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class Tic_Tac_Toe extends JFrame implements MouseListener 
{
    private static final long serialVersionUID = 1L;

    // Game State Variables 
    private String[] board = new String[9];  // Stores "X", "O", or "" for each cell
    private boolean xTurn = true;            // True if it's X's turn, false if O's turn
    private boolean gameEnd = false;         // True if the game has ended

    // Score Counters 
    private int playerXWin = 0;              // Count of wins for player X
    private int playerOWin = 0;              // Count of wins for player O
    private int tie = 0;                     // Count of tie games
    private int counter = 0;                 // Number of moves made in current game

    // GUI Variables 
    private JPanel mainPanel;                // Main panel for drawing the game
    private final int CELL_SIZE = 200;       // Width and height of each Tic Tac Toe cell
    private final int BOARD_TOP = 150;       // Vertical offset for top of board

    // Main Method 
    public static void main(String[] args) {new Tic_Tac_Toe();}

    // Constructor 
    public Tic_Tac_Toe() 
    {
        setTitle("Tic Tac Toe");
        setSize(800, 900);                     // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);           // Center window on screen

        // Initialize board with empty strings
        for (int i = 0; i < board.length; i++) board[i] = "";

        // Create custom panel for drawing
        mainPanel = new JPanel() 
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                drawTitle(g);     // Draw game title
                drawBoard(g);     // Draw Tic Tac Toe grid
                drawMarks(g);     // Draw X and O marks
                drawScoreboard(g);// Draw current scores
            }
        };

        mainPanel.setBackground(Color.BLACK);  // Set background color
        mainPanel.addMouseListener(this);      // Add mouse listener to detect clicks
        add(mainPanel);                        // Add panel to frame
        setVisible(true);                      // Show window
    }

    // Draw Title
    private void drawTitle(Graphics g) 
    {
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.setColor(Color.WHITE);
        String title = "Tic Tac Toe";

        // Center title horizontally
        int x = (getWidth() - g.getFontMetrics().stringWidth(title)) / 2;
        g.drawString(title, x, 50);
        
        // Draw a mini subtitle for current turn
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String turnText = xTurn ? "Player X's Turn" : "Player O's Turn";
        int turnX = (getWidth() - g.getFontMetrics().stringWidth(turnText)) / 2;
        g.drawString(turnText, turnX, 90);  // Draw it just below the title
    }

    // Draw Tic Tac Toe Grid 
    private void drawBoard(Graphics g) 
    {
        g.setColor(Color.WHITE);

        // Calculate horizontal offset to center the grid
        int offsetX = (getWidth() - 3 * CELL_SIZE) / 2;

        // Draw vertical lines
        g.drawLine(offsetX + CELL_SIZE, BOARD_TOP, offsetX + CELL_SIZE, BOARD_TOP + 3 * CELL_SIZE);
        g.drawLine(offsetX + 2 * CELL_SIZE, BOARD_TOP, offsetX + 2 * CELL_SIZE, BOARD_TOP + 3 * CELL_SIZE);

        // Draw horizontal lines
        g.drawLine(offsetX, BOARD_TOP + CELL_SIZE, offsetX + 3 * CELL_SIZE, BOARD_TOP + CELL_SIZE);
        g.drawLine(offsetX, BOARD_TOP + 2 * CELL_SIZE, offsetX + 3 * CELL_SIZE, BOARD_TOP + 2 * CELL_SIZE);
    }

    // Draw X and O Marks
    private void drawMarks(Graphics g) 
    {
        g.setFont(new Font("Arial", Font.BOLD, 100));  // Set font size for X and O
        FontMetrics metrics = g.getFontMetrics();
        int offsetX = (getWidth() - 3 * CELL_SIZE) / 2; // Horizontal centering

        for (int i = 0; i < board.length; i++) 
        {
            String mark = board[i];

            // Only draw non-empty cells
            if (!mark.equals("")) 
            {
                int row = i / 3;
                int col = i % 3;

                // Center the mark within the cell
                int x = offsetX + col * CELL_SIZE + (CELL_SIZE - metrics.stringWidth(mark)) / 2;
                int y = BOARD_TOP + row * CELL_SIZE + (CELL_SIZE + metrics.getAscent()) / 2 - 10;

                // Use red for X, blue for O
                g.setColor(mark.equals("X") ? Color.RED : Color.BLUE);
                g.drawString(mark, x, y);
            }
        }
    }

    // Draw Scoreboard 
    private void drawScoreboard(Graphics g) 
    {
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.setColor(Color.WHITE);

        String scoreText = "Player X Wins: " + playerXWin + "   |   Player O Wins: " + playerOWin + "   |   Ties: " + tie;

        // Center scoreboard horizontally
        int x = (getWidth() - g.getFontMetrics().stringWidth(scoreText)) / 2;
        g.drawString(scoreText, x, BOARD_TOP + 3 * CELL_SIZE + 50);
    }

    // Handle Mouse Clicks 
    public void mouseClicked(MouseEvent e) 
    {
        if (gameEnd) return; // Ignore clicks if game ended

        // Calculate horizontal offset for centering
        int offsetX = (getWidth() - 3 * CELL_SIZE) / 2;

        // Convert mouse coordinates to row and column
        int col = (e.getX() - offsetX) / CELL_SIZE;
        int row = (e.getY() - BOARD_TOP) / CELL_SIZE;
        int index = row * 3 + col;

        // If clicked inside a valid empty cell, place mark
        if (col >= 0 && col < 3 && row >= 0 && row < 3 && board[index].equals("")) 
        {
            board[index] = xTurn ? "X" : "O";
            counter++;
            xTurn = !xTurn;
            mainPanel.repaint();   // Redraw board
            checkGameOver();       // Check for win or tie
        }
    }

    // Check for Win or Tie 
    private void checkGameOver() 
    {
        String winner = null;

        // Check rows for a win
        for (int i = 0; i < 9; i += 3)
            if (!board[i].equals("") && board[i].equals(board[i + 1]) && board[i].equals(board[i + 2]))
                winner = board[i];

        // Check columns for a win
        for (int i = 0; i < 3; i++)
            if (!board[i].equals("") && board[i].equals(board[i + 3]) && board[i].equals(board[i + 6]))
                winner = board[i];

        // Check diagonals for a win
        if (!board[0].equals("") && board[0].equals(board[4]) && board[0].equals(board[8]))
            winner = board[0];
        if (!board[2].equals("") && board[2].equals(board[4]) && board[2].equals(board[6]))
            winner = board[2];

        // If a winner is found
        if (winner != null) 
        {
            gameEnd = true;

            // Update score
            if (winner.equals("X")) playerXWin++;
            else playerOWin++;

            // Show dialog with "Play Again" button
            int choice = JOptionPane.showOptionDialog(this, "Player " + winner + " wins!", "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Play Again"}, "Play Again");

            if (choice == 0) restartGame();
        } 
        // If all cells filled and no winner => tie
        else if (counter == 9) 
        {
            gameEnd = true;
            tie++;

            int choice = JOptionPane.showOptionDialog(this, "It's a tie!", "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Play Again"}, "Play Again");

            if (choice == 0) restartGame();
        }
    }

    // Restart the Game
    private void restartGame() 
    {
        for (int i = 0; i < board.length; i++) board[i] = ""; // Clear board
        gameEnd = false;     								  // Reset gameEnd flag
        xTurn = true;        								  // X starts
        counter = 0;         								  // Reset move counter
        mainPanel.repaint(); 								  // Redraw empty board
    }

    // Unused MouseListener Methods 
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
