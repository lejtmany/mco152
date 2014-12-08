package PongModelTests; 

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import pongmodel.Model;

/**
 *
 * @author Chaim Bender
 * @author Nahum Twersky
 */
public class ModelTest {

    Model model;

    @Before
    public void initializer() {
        model = new Model();
    }

    @Test
    public void prePlayTest() {
        assertEquals(model.getP1Score(), 0);
        assertEquals(model.getP2Score(), 0);
    }
    
    @Test
    public void ballPositionTest(){
        int ballX = model.getBallLocation().x;
        int ballY = model.getBallLocation().y;
        model.updateBall();
        assertEquals(ballX + 1, model.getBallLocation().x);
        assertEquals(ballY + 1, model.getBallLocation().y);
   }
    
    @Test
    public void paddleUpdateTest(){
        int paddle1 = model.getP1Paddle().y;
        int paddle2 = model.getP2Paddle().y;
        model.moveP1PaddleUp();
        model.moveP2PaddleDown();
        assertEquals(paddle1 - 1 , model.getP1Paddle().y);
        assertEquals(paddle2 + 1, model.getP2Paddle().y);
    }
    
    @Test
    public void paddle1MovementRange(){
        while(model.getP1Paddle().y != 0){
            model.moveP1PaddleUp();
        }
        assertEquals(model.getP1Paddle().y, 0);
        model.moveP1PaddleUp();
        assertEquals(model.getP1Paddle().y, 0);
        while(model.getP1Paddle().y  + model.getPaddleLength() != model.getHeight()){
            model.moveP1PaddleDown();
        }
        assertEquals(model.getP1Paddle().y, model.getHeight() - model.getPaddleLength());
        model.moveP1PaddleDown();
        assertEquals(model.getP1Paddle().y, model.getHeight() - model.getPaddleLength());
    }
    
    @Test
    public void paddle2MovementRange(){
        while(model.getP2Paddle().y != 0){
            model.moveP2PaddleUp();
        }
        assertEquals(model.getP2Paddle().y, 0);
        model.moveP2PaddleUp();
        assertEquals(model.getP2Paddle().y, 0);
        while(model.getP2Paddle().y  + model.getPaddleLength() != model.getHeight()){
            model.moveP2PaddleDown();
        }
        assertEquals(model.getP2Paddle().y, model.getHeight() - model.getPaddleLength());
        model.moveP2PaddleDown();
        assertEquals(model.getP2Paddle().y, model.getHeight() - model.getPaddleLength());
    }
    
    @Test
    public void scoreTester(){
        assertEquals(model.getP1Score(), 0);
        while(model.getP2Paddle().y != 0){
            model.moveP2PaddleUp();
        }
        while(model.getBallLocation().x != model.getWidth() - model.getBallDiameter()){
            model.updateBall();
        }
        model.updateBall();
        assertEquals(model.getP1Score(), 1);
    }
    
    public void gameSimulator(){
        while(model.getP2Score() != 10){
            model.updateBall();
            if(model.getBallLocation().x == model.getP2Paddle().x-1){
                if(model.getP2Paddle().y + model.getPaddleLength() < model.getBallLocation().y ){
                    while(model.getP2Paddle().y + model.getPaddleLength() < model.getBallLocation().y ){
                        model.moveP2PaddleDown();
                    }
                    model.moveP2PaddleDown();
                }else if(model.getP2Paddle().y > model.getBallLocation().y + model.getBallDiameter()){
                    while(model.getP2Paddle().y > model.getBallLocation().y + model.getBallDiameter()){
                        model.moveP2PaddleUp();
                    }
                    model.moveP2PaddleUp();
                }
            }
        }
        assertEquals(model.getP2Score(), 10);
        assertEquals(model.getP1Score(), 0);
        assertTrue(model.isGameOver());
    }
}
