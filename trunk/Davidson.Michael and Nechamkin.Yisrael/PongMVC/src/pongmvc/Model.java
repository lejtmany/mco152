package pongmvc;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Davidson and Yisrael Nechamkin
 */
public class Model {

    private final Point ball;
    private final Rectangle paddle1,
            paddle2;

    private int ballWidthAndHeight = 10,
            paddleWidth = 5,
            paddleLength = 50;

    private boolean isTwoPlayers;
    private int ballSpeed;

    private int translateBallX = -1,
            translateBallY = -1,
            translatePaddleY1,
            translatePaddleY2,
            minXBound = 50, minYBound = 50,
            maxX_ForBall, maxY_ForBall,
            score1 = 0,
            score2 = 0;

    private boolean isGameOver = false;

    private final Rectangle boundsOfGame = new Rectangle();

    Model() {
        ball = new Point(540, 250);
        paddle1 = new Rectangle(70, 250, paddleWidth, paddleLength);
        paddle2 = new Rectangle(530 - paddleWidth, 250, paddleWidth, paddleLength);

        Timer ballTimer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                updateBall();
            }
        });
        ballTimer.start();

        Timer paddleTimer = new Timer(2, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                updatePaddles();
            }
        });
        paddleTimer.start();
    }

    private void getMaxX_and_MaxYBoundary() {
        maxX_ForBall = (getBoundsOfGame().width + getBoundsOfGame().x) - getBallWidthAndHeight(); //540
        maxY_ForBall = (getBoundsOfGame().y + getBoundsOfGame().height) - getBallWidthAndHeight(); //540
    }

    private void updateBall() {
        getBall().translate(getTranslateBallX(), getTranslateBallY());
        if (isGameOver()) {
            translateBallX = 0;
            translateBallY = 0;
        } else if (getBall().y <= getMinYBound() || getBall().y >= getMaxY_ForBall()) {
            translateBallY = -getTranslateBallY();
        } else if (getBall().x == getPaddle1().x + getPaddle1().width
                && (getBall().y >= getPaddle1().y
                && getBall().y <= getPaddle1().y + getPaddle1().height)) {
            translateBallX = -getTranslateBallX();
            score1++;
        } else if (isTwoPlayers()
                && getBall().x == getPaddle2().x + getPaddle2().width
                && (getBall().y >= getPaddle2().y
                && getBall().y <= getPaddle2().y + getPaddle2().height)) {
            translateBallX = -getTranslateBallX();
            score2++;
        }
        isGameOver = checkGameOver();
    }

    private void updatePaddles() {
        getPaddle1().translate(0, getTranslatePaddleY1());
        getPaddle2().translate(0, getTranslatePaddleY2());
        translatePaddleY1 = 0;
        translatePaddleY2 = 0;
    }

    private boolean checkGameOver() {
        return (int) getBall().getX() <= getMinXBound();
    }

    public Point getBall() {
        return new Point(ball);
    }

    public Rectangle getPaddle1() {
        return new Rectangle(paddle1);
    }

    public Rectangle getPaddle2() {
        return new Rectangle(paddle2);
    }

    public int getBallWidthAndHeight() {
        return ballWidthAndHeight;
    }

    public int getPaddleWidth() {
        return paddleWidth;
    }

    public int getPaddleLength() {
        return paddleLength;
    }

    public boolean isTwoPlayers() {
        return isTwoPlayers;
    }

    public int getBallSpeed() {
        return ballSpeed;
    }

    public int getTranslateBallX() {
        return translateBallX;
    }

    public int getTranslateBallY() {
        return translateBallY;
    }

    public int getTranslatePaddleY1() {
        return translatePaddleY1;
    }

    public int getTranslatePaddleY2() {
        return translatePaddleY2;
    }

    public int getMinXBound() {
        return minXBound;
    }

    public int getMinYBound() {
        return minYBound;
    }

    public int getMaxX_ForBall() {
        return maxX_ForBall;
    }

    public int getMaxY_ForBall() {
        return maxY_ForBall;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Rectangle getBoundsOfGame() {
        return new Rectangle(boundsOfGame);
    }
}
