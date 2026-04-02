import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PongGame extends JPanel implements KeyListener, ActionListener {
    private int ballX = 250, ballY = 250, ballDX = 2, ballDY = 2;
    private int paddle1Y = 200, paddle2Y = 200;
    private final int PADDLE_HEIGHT = 80, PADDLE_WIDTH = 10;
    private final int BALL_SIZE = 20;
    private Timer timer;

    public PongGame() {
        setFocusable(true);
        addKeyListener(this);
        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Ball
        g.setColor(Color.WHITE);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);

        // Paddles
        g.fillRect(30, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(getWidth() - 40, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ballX += ballDX;
        ballY += ballDY;

        // Bounce off top and bottom
        if (ballY <= 0 || ballY >= getHeight() - BALL_SIZE) {
            ballDY = -ballDY;
        }

        // Bounce off paddles
        if (ballX <= 40 && ballY + BALL_SIZE >= paddle1Y && ballY <= paddle1Y + PADDLE_HEIGHT) {
            ballDX = -ballDX;
        }
        if (ballX >= getWidth() - 60 && ballY + BALL_SIZE >= paddle2Y && ballY <= paddle2Y + PADDLE_HEIGHT) {
            ballDX = -ballDX;
        }

        // Reset if ball goes out
        if (ballX < 0 || ballX > getWidth()) {
            ballX = getWidth() / 2;
            ballY = getHeight() / 2;
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        // Player 1 controls (W/S)
        if (key == KeyEvent.VK_W && paddle1Y > 0) paddle1Y -= 10;
        if (key == KeyEvent.VK_S && paddle1Y < getHeight() - PADDLE_HEIGHT) paddle1Y += 10;

        // Player 2 controls (Up/Down)
        if (key == KeyEvent.VK_UP && paddle2Y > 0) paddle2Y -= 10;
        if (key == KeyEvent.VK_DOWN && paddle2Y < getHeight() - PADDLE_HEIGHT) paddle2Y += 10;
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        PongGame game = new PongGame();
        frame.add(game);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

