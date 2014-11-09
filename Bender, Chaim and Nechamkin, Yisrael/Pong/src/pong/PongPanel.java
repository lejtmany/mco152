package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

class PongPanel extends JPanel {

    private final int ballSize = 10,
            gameWindowSize = 500,
            borderSize = 2,
            paddleDistanceFromLeft = 20,
            paddleWidth = 5,
            paddleLength = 50;
    private int paddleHeight = 200;

    private int ballXDir = 1, ballYDir = 1;

    private Point ball = new Point(100, 100);
    private final Controller controller;
    boolean gameOver = false;
    Timer t;
    long lastMovement = System.nanoTime();

    public PongPanel(Controller con) {
        controller = con;
        setFocusable(true);
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Color.WHITE, borderSize));

        t = new Timer(10, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (gameOver) {
                    repaint();
                    t.stop();
                }
                updateBall();
                repaint();
                
            }
        });
        t.start();

        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (!gameOver) {
                    long time = System.nanoTime();
                    if (time >= lastMovement + 2e7) {
                        lastMovement = time;
                        if (e.getKeyCode() == KeyEvent.VK_UP) {
                            movePaddle(true);
                        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                            movePaddle(false);
                        }
                    }
                }else{
                    if(e.getKeyCode() == KeyEvent.VK_ENTER){
                        resetGame();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }
    
    void resetGame(){
        controller.setScore(0);
        ball.x = 200;
        ball.y = 200;
        gameOver = false;
        t.start();
    }

    private void movePaddle(boolean direction) {
        if (direction) {
            paddleHeight -= Math.min(1, paddleHeight);
        } else {
            paddleHeight += (Math.min(1, gameWindowSize - paddleHeight - paddleLength));
        }
    }

    private void updateBall() {
        if (ball.x > 0) {
            if (ball.x + ballSize == gameWindowSize) {
                ballXDir = -ballXDir;
            }
            if (ball.y == 0 || ball.y == gameWindowSize - ballSize) {
                ballYDir = -ballYDir;
            }
            if (ball.x == paddleDistanceFromLeft + paddleWidth
                    && (ball.y >= paddleHeight - ballSize && ball.y <= paddleHeight + paddleLength)) {
                ballXDir = -ballXDir;
                controller.setScore(controller.getScore() + 1);
            }
            ball.translate(ballXDir, ballYDir);
        } else {
            gameOver = true;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (!gameOver) {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 10));
            g.drawString("GAME OVER!!!!! Press 'Enter' to restart", 20, 20);
        }
        g.fillRect(paddleDistanceFromLeft, paddleHeight, paddleWidth, paddleLength);

        g.fillOval(ball.x, ball.y, ballSize, ballSize);
    }
}
