package pong;

import com.sun.javafx.scene.traversal.Direction;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javafx.scene.layout.Border;


/*
 * Ben Forgy
 * Nov 8, 2014
 */

public class GameController  implements Context, Controller{
    private final int DEFAULT_PADDLE_SPEED = 1;
    private int padelHits = 0;
    private Board board;
    private Rectangle border;
    private Paddle paddle;
    private Ball ball;
    private PongView view;
    
    
    public GameController(Rectangle boardDimension, Point paddleDimension, int ballRadius, int ballDeg){
        board = new Board(boardDimension);
        paddle = new Paddle(DEFAULT_PADDLE_SPEED,new Rectangle(board.getMinX() + 10,board.getMinY() + (board.getMaxY() / 2),paddleDimension.x,paddleDimension.y));
        ball = new Ball((int)boardDimension.getWidth()/2, (int)boardDimension.getHeight()/2, ballRadius, ballDeg);
        border = new Rectangle(board.getMinX() - 4,board.getMinY()-2,(int)board.getMaxX()+2,(int)board.getMaxY()+2);
    }

    protected GameController(Board board, Paddle paddle, Ball ball) {
        this.board = board;
        this.paddle = paddle;
        this.ball = ball;
        border = new Rectangle(board.getMinX() - 4,board.getMinY()-2,(int)board.getMaxX()+2,(int)board.getMaxY()+2);
    }
    
    protected void setView(PongView pv){
        view = pv;
    }
    
    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public Paddle getPaddle() {
        return paddle;
    }

    @Override
    public Ball getBall() {
        return ball;
    }
    
    public Rectangle getBorder(){
        return border;
    }
    
    public boolean moveBall(){
        ArrayList<Line2D> outsideLines = new ArrayList<>();
        if(ball.getBody().getMinX() < board.getMinX() + 1){
            outsideLines.add(board.getLeft());
            padelHits++;
        }
        else if(ball.getBody().getMaxX() > board.getMaxX() - 1){
            outsideLines.add(board.getRight());
        }
        if(ball.getBody().getMinY() < board.getMinY() + 1){
            outsideLines.add(board.getTop());
        }
        else if(ball.getBody().getMaxY() > board.getMaxY() - 1){
            outsideLines.add(board.getBottom());
        }
        if(ball.getBody().getMinX() < paddle.getMaxX() +2){
            outsideLines.add(paddle.getTop());
            outsideLines.add(paddle.getRight());
            outsideLines.add(paddle.getBottom());
            outsideLines.add(paddle.getLeft());
        }
        ball.move(outsideLines);
        if(ball.getBody().getMinX() < paddle.getMinX()){
//            while(ball.getBody().getMinX() > board.getMinX() ){
//                ball.move(null);
//            }
            view.endGame();
            return false;
        }
        if(ball.hitPaddle()){
            updatePaddleHits();
        }
        return true;
    }
    
    public boolean movePaddle(Direction direction){
            ArrayList<Line2D> outsideLines = new ArrayList<>();
            if(ball.getBody().getMinX() < paddle.getMaxX() +2){
            outsideLines.add(paddle.getTop());
            outsideLines.add(paddle.getRight());
            outsideLines.add(paddle.getBottom());
            outsideLines.add(paddle.getLeft());
        }
            ball.handleCollisions(outsideLines);
            if(direction.equals(Direction.UP)){
              return  paddle.moveUp(board);
            }else if(direction.equals(Direction.DOWN)){
              return  paddle.moveDown(board);
            }else if(direction.equals(Direction.RIGHT)){
               return paddle.moveRight(board);
            }else if(direction.equals(Direction.LEFT)){
               return paddle.moveLeft(board);
            }
        return false;
    }
    
    @Override
    public void updatePaddleHits(){
        view.updateScore(++padelHits);
    }
}
