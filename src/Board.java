import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {

    // Declare images for apple, dot (body part of the snake), and head of the snake
    private Image apple;
    private Image dot;
    private Image head;

    // Constants for the game
    private final int ALL_DOTS = 900; // Maximum number of possible dots on the board
    private final int DOT_SIZE = 10;  // Size of each dot (snake segment)
    private final int RANDOM_POSITION = 29; // Used to calculate random positions for the apple

    // Coordinates of the apple
    private int apple_x;
    private int apple_y;

    // Arrays to store the x and y coordinates of all dots in the snake
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    // Direction flags for snake movement
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    // Number of dots in the snake
    private int dots;
    // Timer for the game loop
    private Timer timer;

    // Constructor
    Board() {
        addKeyListener(new TAdapter()); // Add keyboard listener for controlling the snake
        setBackground(Color.BLACK); // Set background color of the game board
        setFocusable(true); // Make the JPanel focusable to receive key events

        loadImages(); // Load images for the game
        initGame(); // Initialize the game
    }

    // Method to load images
    public void loadImages() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/apple.png"));
        apple = i1.getImage();

        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("icons/dot.png"));
        dot = i2.getImage();

        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icons/head.png"));
        head = i3.getImage();
    }

    // Method to initialize the game
    public void initGame() {
        dots = 3; // Initial length of the snake

        // Initialize the snake's position
        for (int i = 0; i < dots; i++) {
            y[i] = 50; // All segments are at the same y-coordinate
            x[i] = 50 - i * DOT_SIZE; // x-coordinate is spaced out based on DOT_SIZE
        }

        locateApple(); // Place the apple on the board

        timer = new Timer(140, this); // Initialize timer to call actionPerformed every 140ms
        timer.start(); // Start the timer
    }

    // Method to place the apple at a random position
    public void locateApple() {
        int r = (int)(Math.random() * RANDOM_POSITION); // Random x position
        apple_x = r * DOT_SIZE; // Scale position to match grid

        r = (int)(Math.random() * RANDOM_POSITION); // Random y position
        apple_y = r * DOT_SIZE; // Scale position to match grid
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g); // Draw the current state of the game
    }

    // Method to draw the game components
    public void draw(Graphics g) {
        g.drawImage(apple, apple_x, apple_y, this); // Draw the apple

        for (int i = 0; i < dots; i++) {
            if (i == 0) {
                g.drawImage(head, x[i], y[i], this); // Draw the head of the snake
            } else {
                g.drawImage(dot, x[i], y[i], this); // Draw the body of the snake
            }
        }

        Toolkit.getDefaultToolkit().sync(); // Synchronize the toolkit for smooth rendering
    }

    // Method to move the snake
    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1]; // Shift the position of each segment to follow the head
            y[i] = y[i - 1];
        }

        // Update the head position based on the current direction
        if (leftDirection) {
            x[0] = x[0] - DOT_SIZE;
        }
        if (rightDirection) {
            x[0] = x[0] + DOT_SIZE;
        }
        if (upDirection) {
            y[0] = y[0] - DOT_SIZE;
        }
        if (downDirection) {
            y[0] = y[0] + DOT_SIZE;
        }
    }

    // Method to check if the snake has eaten the apple
    public void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) { // If the head's position matches the apple's position
            dots++; // Increase the length of the snake
            locateApple(); // Place a new apple on the board
        }
    }

    // Method to check for collisions
    public void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) { // Check if head collides with the body
                timer.stop(); // Stop the game
            }
        }

        // Check if the head collides with the walls
        if (y[0] >= 400) {
            timer.stop();
        }
        if (y[0] < 0) {
            timer.stop();
        }
        if (x[0] >= 450) {
            timer.stop();
        }
        if (x[0] < 0) {
            timer.stop();
        }
    }

    // Method to handle game over
    public void gameOver() {
        if (!timer.isRunning()) {
            JOptionPane.showMessageDialog(this, "Game Over"); // Show game over message
            System.exit(0); // Exit the game
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkApple(); // Check if the apple is eaten
        checkCollision(); // Check for collisions
        move(); // Move the snake
        gameOver(); // Check if the game is over
        repaint(); // Refresh the screen
    }

    // Inner class to handle key events
    public class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            // Change direction based on the key pressed
            if (key == KeyEvent.VK_LEFT && !rightDirection) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if (key == KeyEvent.VK_RIGHT && !leftDirection) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if (key == KeyEvent.VK_UP && !downDirection) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if (key == KeyEvent.VK_DOWN && !upDirection) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}
