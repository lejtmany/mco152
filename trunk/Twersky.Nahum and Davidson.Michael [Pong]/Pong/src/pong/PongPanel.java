package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author ntwersky
 * @author mdavidson
 */
class PongPanel extends JPanel implements KeyListener {

    private final Point startingPosition = new Point(540, 250);
    private Point ball = new Point(startingPosition);

    int ballWidthAndHeight = 10,
            translateBallX = -1,
            translateBallY = -1,
            translatePaddleY,
            minXBound = 50, minYBound = 50,
            maxX_ForBall, maxY_ForBall,
            score = 0;

    boolean isGameOver;

    Rectangle boundsOfGame = new Rectangle(),
            player1 = new Rectangle(70, 250, 5, 50);

    JPanel panel = new JPanel();

    public PongPanel() {

        setBackground(Color.BLACK);
        setLayout(null);

        panel.setBackground(Color.DARK_GRAY);
        panel.setVisible(true);
        add(panel);

        setFocusable(true);
        addKeyListener(this);

//        This is where we set the size of the inner panel for the game.
//        It's 50 from left, 50 from right, and the size is 500x500
        panel.setBounds(minXBound, minYBound, 500, 500);

//        panel.setBounds(new Rectangle(50,50, 500, 500));
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

//        We will use this to torture the ball to stay in the game.
//        Don't forget boundsOfGame is a rectangle
        boundsOfGame = panel.getBounds();

        getMaxX_and_MaxYBoundary();

        Timer ballTimer = new Timer(5, (ActionEvent ae) -> {
            updateBall();
            repaint();
        });
        ballTimer.start();

        Timer paddleTimer = new Timer(2, (ActionEvent ae) -> {
            updatePaddle();
            repaint();
        });
        paddleTimer.start();

    }

    /**
     * Used to properly calculate when the ball should "rebound" off wall
     * farthest from the paddle.
     */
    private void getMaxX_and_MaxYBoundary() {
        maxX_ForBall = (boundsOfGame.width + boundsOfGame.x) - ballWidthAndHeight; //540
        maxY_ForBall = (boundsOfGame.y + boundsOfGame.height) - ballWidthAndHeight; //540
    }

    private void updateBall() {
        ball.translate(translateBallX, translateBallY);
        if (isGameOver) {
            translateBallX = 0;
            translateBallY = 0;
        } else if (ball.getX() > maxX_ForBall) {
            translateBallX = -translateBallX;
        } else if (ball.getY() <= minYBound || ball.getY() >= maxY_ForBall) {
            translateBallY = -translateBallY;
        } else if (ball.getX() == player1.getX() + player1.width
                && (ball.getY() >= player1.getY()
                && ball.getY() <= player1.getY() + player1.height)) {
            translateBallX = -translateBallX;
            score++;
        }
        isGameOver = checkGameOver();

    }

    private void updatePaddle() {
        player1.translate(0, translatePaddleY);
        translatePaddleY = 0;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP && player1.y > minYBound) {
            translatePaddleY = -10;
        }
        if (keyCode == KeyEvent.VK_DOWN && player1.y < maxY_ForBall - player1.height + ballWidthAndHeight) {
            translatePaddleY = 10;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        /*if(isGameOver)
         {
         ball = startingPosition;
         isGameOver = false;
         }*/
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Default", Font.PLAIN, 20));
        g.drawString("Score: " + score, 250, 20);

        g.fillOval(ball.x, ball.y, ballWidthAndHeight, ballWidthAndHeight);
        g.fillRect(player1.x, player1.y, 5, 50);
        if (isGameOver) {
            g.drawString("GAME OVER!", 235, 275);
            //g.drawString("Press any key to restart.", 235, 295);
        }
    }

    private boolean checkGameOver() {
        return ball.x <= minXBound;
    }

}
