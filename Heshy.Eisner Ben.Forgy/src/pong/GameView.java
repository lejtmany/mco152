/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pong;

import com.sun.javafx.scene.traversal.Direction;
import java.awt.Point;
import java.awt.Rectangle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Heshy
 * can't be used because javafx doesn't use a non empty constructor
 */
public class GameView extends Application implements PongView{
    int scoreX ,scoreY;
    Rectangle bounds;
    GameController gameCont;
    String score ="",gameOverText= "GAME OVER";
    GraphicsContext graphicsContext;
    Timeline timer;
    
    
    public GameView(GameController gameController, Rectangle boardBounds,Point scoreLocation){
        super();
        gameCont = gameController;
        bounds = boardBounds;
        scoreX = scoreLocation.x;
        scoreY = scoreLocation.y;
    }
        
    
    @Override
    public void start(Stage primaryStage) {
        

        StackPane root = new StackPane();
        root.setLayoutX(3);
        root.setLayoutY(3);

        Canvas canvas = new Canvas(600, 600);

        graphicsContext = canvas.getGraphicsContext2D();
        
        //draw background
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0,canvas.getWidth(), canvas.getHeight());
        
        //draw border
        graphicsContext.setStroke(Color.LIME);
        Rectangle border =gameCont.getBorder();
        graphicsContext.strokeRect(border.x,border.y,border.width - 48,border.height - 23);
        
        //draw board
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
        
        //draw paddle
        Rectangle paddle = gameCont.getPaddle().getDimensions();
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
        
        //draw empty score
        updateScore(0);

        //set up ball movement
        graphicsContext.setLineWidth(1);
        Point lastPos = new Point(0, 0);
        timer = new Timeline(new KeyFrame(
                Duration.millis(10),
                ae -> {
                    gameCont.moveBall();
                    Rectangle bounds = gameCont.getBall().getBody().getBoundingBox();
                    graphicsContext.setFill(Color.BLACK);
                    graphicsContext.fillRect(lastPos.x, lastPos.y, (int) bounds.getWidth(),
                            (int) bounds.getHeight());
                    graphicsContext.setFill(Color.WHITE);
                    graphicsContext.fillOval((int) bounds.getMinX(),
                            (int) bounds.getMinY(),
                            (int) bounds.getWidth(),
                            (int) bounds.getHeight());

                    lastPos.x = (int) bounds.getMinX();
                    lastPos.y = (int) bounds.getMinY();
                }));
        timer.setCycleCount(Timeline.INDEFINITE);

        timer.play();

        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                KeyCode source = event.getCode();
                erasePaddle(gameCont.getPaddle());
                if (source == KeyCode.UP) {
                    System.out.println("up was pressed");
                    gameCont.movePaddle(Direction.UP);
                    
                } else if (source == KeyCode.DOWN) {
                    System.out.println("down was pressed");
                    gameCont.movePaddle(Direction.DOWN);
                }
                /*
                when changed to mulitplayer with horizontal paddles add 
                more keycode conditions here
                */
                System.out.println(gameCont.getPaddle().getDimensions());
                drawPaddle(gameCont.getPaddle(),graphicsContext);
            }
        });
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(700);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Pong");

        primaryStage.show();
    }
    
    private void erasePaddle(Paddle p){
        Rectangle paddle = p.getDimensions();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
    }
    private void drawPaddle(Paddle p,GraphicsContext gc){
        Rectangle paddle = p.getDimensions();
        gc.setFill(Color.WHITE);
        gc.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
    }
    
    private void eraseScore(){
        Color oldColor = (Color)graphicsContext.getFill();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillText(score, scoreX, scoreY);
        graphicsContext.setFill(oldColor);
    }
        
    @Override
    public void updateScore(int newScore){
        eraseScore();
        Color oldColor = (Color)graphicsContext.getFill();
        score = "Score: ";
        if(newScore != 0){
            score += newScore;
        }
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(score, scoreX, scoreY);
        graphicsContext.setFill(oldColor);
    }

    @Override
    public void endGame() {
        timer.stop();
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillText(gameOverText,300 ,bounds.getMaxY() /2);
    }
    
    
    public void startGame(String[] args){
        Application.launch(args);
    };

    @Override
    public void restartGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    
}
