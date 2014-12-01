package pong;

import com.sun.javafx.scene.traversal.Direction;
import java.awt.Point;
import java.awt.Rectangle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
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
 * @author BenForgy
 */
public class PongMain extends Application implements PongView {

    private int scoreLabelX = 299, scoreLabelY = 11, movePaddle = 0;
    private int BALL_DELAY = 10, PADDLE_SPEED = 10;
    private int ballAngle = 54;
    private Board board;
    private Paddle paddle;
    private Ball ball;
    private GameController gameCont;
    private String score = "", gameOverText = "GAME OVER";
    private GraphicsContext graphicsContext;
    private Timeline timer;
    private Scene scene;
    private Rectangle border;

    @Override
    public void start(Stage primaryStage) {
        setUpGame();

        StackPane root = new StackPane();

        Canvas canvas = new Canvas(600, 600);

        graphicsContext = canvas.getGraphicsContext2D();

        //draw background
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //draw border
        graphicsContext.setStroke(Color.LIGHTGRAY);
        graphicsContext.strokeRect(border.x, border.y, border.getWidth() - 48, border.getHeight() - 23);

        //draw board
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(board.getMinX(), board.getMinY(), board.getWidth(), board.getHeight());

        //draw paddle
        Rectangle paddleDim = gameCont.getPaddle().getDimensions();
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(paddleDim.x, paddleDim.y, paddleDim.width - 3, paddleDim.height - 2);

        //draw empty score
        updateScore(0);

        //set up ball movement
        graphicsContext.setLineWidth(1);
        Point lastPos = new Point(0, 0);
        timer = new Timeline(new KeyFrame(
                Duration.millis(BALL_DELAY),
                ae -> {
                    updatePaddle(paddle);
                    updateBall(lastPos);
                }));
        timer.setCycleCount(Timeline.INDEFINITE);

        timer.play();

        root.getChildren().add(canvas);
        scene = new Scene(root);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                updateBoard(event);
            }

        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                movePaddle = 0;
            }
        });
        scene.setOnKeyTyped(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                updateBoard(event);
            }

        });
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(600);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Pong");

        primaryStage.show();
    }

    private void setUpGame() {
        board = new Board(50, 25, 500, 500);
        paddle = new Paddle(PADDLE_SPEED, new Rectangle(board.getMinX() + 10, board.getMinY() + (board.getMaxY() / 2), 12, 52));
        ball = new Ball((board.getMaxX() / 2) + 38, (board.getMaxY() / 2), 5, ballAngle);
        gameCont = new GameController(board, paddle, ball);
        border = gameCont.getBorder();

        gameCont.setView(this);
    }

    private void updateBall(Point lastPos) {
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
    }

    private void updateBoard(KeyEvent event) {
        KeyCode source = event.getCode();
        erasePaddle(gameCont.getPaddle());
        if (source == KeyCode.UP) {
            //System.out.println("up was pressed");
            movePaddle = -1;

        } else if (source == KeyCode.DOWN) {
            //System.out.println("down was pressed");
            movePaddle = 1;
        }
        /*
         when changed to mulitplayer with horizontal paddles add 
         more keycode conditions here
         */
        //System.out.println(gameCont.getPaddle().getDimensions());

    }

    private void updatePaddle(Paddle p) {
        erasePaddle(p);
        if (movePaddle > 0) {
            gameCont.movePaddle(Direction.DOWN);
        } else if (movePaddle < 0) {
            gameCont.movePaddle(Direction.UP);
        }
        drawPaddle(p);
    }

    private void erasePaddle(Paddle p) {
        Rectangle paddle = p.getDimensions();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
    }

    private void drawPaddle(Paddle p) {
        Rectangle paddle = p.getDimensions();
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(paddle.x, paddle.y, paddle.width - 3, paddle.height - 2);
    }

    private void eraseScore() {
        Color oldColor = (Color) graphicsContext.getFill();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillText(score, scoreLabelX, scoreLabelY);
        graphicsContext.setFill(oldColor);
    }

    @Override
    public void updateScore(int newScore) {
        eraseScore();
        Color oldColor = (Color) graphicsContext.getFill();
        score = "Score: ";
        if (newScore != 0) {
            score += newScore;
        }
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(score, scoreLabelX, scoreLabelY);
        graphicsContext.setFill(oldColor);
    }

    @Override
    public void endGame() {
        timer.stop();
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillText(gameOverText, 300, board.getMaxY() / 2);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    restartGame();
                }
            }
        });
    }

    @Override
    public void restartGame() {
        eraseScore();
        erasePaddle(paddle);
        updateScore(0);
        setUpGame();
        drawPaddle(paddle);
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillText(gameOverText, 300, board.getMaxY() / 2);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                updateBoard(event);
            }
        });
        timer.play();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PongMain pong = new PongMain();
        launch(args);
    }

}
