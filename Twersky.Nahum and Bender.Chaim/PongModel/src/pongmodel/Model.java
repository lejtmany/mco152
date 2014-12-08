package pongmodel;

import java.awt.Point;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

/**
 *
 * @author Chaim Bender
 * @author Nahum Twersky
 */
public class Model {

    private final int height, width, ballDiameter, paddleDistanceFromWall,
            paddleWidth, paddleLength, gameOverScore = 10,
            paddleMovementDistance = 1;
    private Point ballLocation, p1Paddle, p2Paddle;
    private int p1Score, p2Score, ballXDirection = 1, ballYDirection = 1,
            ballMovementUpdateTime = 10;
    private boolean gameOver;
    private Timer ballMovementTimer;

    public Model(int width, int height) {
        this.width = width;
        this.height = height;
        ballDiameter = 10;
        paddleDistanceFromWall = 20;
        paddleWidth = 5;
        paddleLength = 50;
        ballLocation = new Point();
        p1Paddle = new Point(paddleDistanceFromWall, (int) Math.floor(height / 2 - paddleLength / 1));
        p2Paddle = new Point(width - paddleDistanceFromWall - paddleWidth, (int) Math.floor(height / 2 - paddleLength / 1));
        newGame();
    }

    public Model() {
        this(500, 500);
    }

    public void newGame() {
        p1Score = 0;
        p2Score = 0;
        placeBallInMiddle();
        gameOver = false;
        setUpBallTimer();
    }
    
    public void playGame(){
        ballMovementTimer.start();
    }

    public void pauseGame(){
        ballMovementTimer.stop();
    }

    private void setUpBallTimer() {
        ballMovementTimer = new Timer(ballMovementUpdateTime, (ActionEvent e) -> {
            updateBall();
        }); 
        ballMovementTimer.stop();
    }

    private void placeBallInMiddle() {
        ballLocation.setLocation(width / 2, height / 2);
    }

    public void updateBall() {
        checkIfBallIsTouchingLeftOrRightWall();
        checkIfBallIsTouchingPaddle();
        checkIfBallIsTouchingTopOrBottomWall();
        ballLocation.translate(ballXDirection, ballYDirection);
    }


    private void checkIfBallIsTouchingPaddle() {
        if (touchingP1Paddle() || touchingP2Paddle()) {
            reverseBallYDirection();
            reverseBallXDirection();
        }
    }

    private boolean touchingP1Paddle() {
        return (ballLocation.x == paddleDistanceFromWall + paddleWidth)
                && (ballLocation.y + ballDiameter >= p1Paddle.y && ballLocation.y <= p1Paddle.y + paddleLength);
    }

    private boolean touchingP2Paddle() {
        return (ballLocation.x + ballDiameter == width - paddleDistanceFromWall - paddleWidth)
                && (ballLocation.y + ballDiameter >= p2Paddle.y && ballLocation.y <= p2Paddle.y + paddleLength);
    }

    private void checkIfBallIsTouchingTopOrBottomWall() {
        if (ballLocation.y == 0 || ballLocation.y == height - ballDiameter) {
            reverseBallYDirection();
        }
    }

    private void checkIfBallIsTouchingLeftOrRightWall() {
        boolean playerScored = false;
        if (ballLocation.x == 0) {
            incrementScore(false);
            playerScored = true;
        } else if (ballLocation.x + ballDiameter == width) {
            incrementScore(true);
            playerScored = true;
        }
        if (playerScored) {
            placeBallInMiddle();
            reverseBallXDirection();
        }
    }

    private void reverseBallXDirection() {
        ballXDirection = -ballXDirection;
    }

    private void reverseBallYDirection() {
        ballYDirection = -ballYDirection;
    }

    public void moveP1PaddleUp() {
        if (p1Paddle.y != 0) {
            p1Paddle.translate(0, -paddleMovementDistance);
        }
    }

    public void moveP1PaddleDown() {
        if (p1Paddle.y + paddleLength != height) {
            p1Paddle.translate(0, paddleMovementDistance);
        }
    }

    public void moveP2PaddleUp() {
        if (p2Paddle.y != 0) {
            p2Paddle.translate(0, -paddleMovementDistance);
        }
    }

    public void moveP2PaddleDown() {
        if (p2Paddle.y + paddleLength != height) {
            p2Paddle.translate(0, paddleMovementDistance);
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getBallDiameter() {
        return ballDiameter;
    }

    public int getPaddleDistanceFromWall() {
        return paddleDistanceFromWall;
    }

    public int getPaddleWidth() {
        return paddleWidth;
    }

    public int getPaddleLength() {
        return paddleLength;
    }

    public Point getBallLocation() {
        return ballLocation;
    }

    public int getP1Score() {
        return p1Score;
    }

    public int getP2Score() {
        return p2Score;
    }

    public Point getP1Paddle() {
        return p1Paddle;
    }

    public Point getP2Paddle() {
        return p2Paddle;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void incrementScore(boolean player1) {
        if (player1) {
            p1Score++;
        } else {
            p2Score++;
        }
        checkIfGameOver();
    }

    private void checkIfGameOver() {
        if (p1Score == gameOverScore || p2Score == gameOverScore) {
            gameOver = true;
        }
    }

}
