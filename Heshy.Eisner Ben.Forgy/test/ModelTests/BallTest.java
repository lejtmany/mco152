
package ModelTests;

import java.awt.Rectangle;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pong.*;

/**
 *
 * @author BenForgy
 */
public class BallTest {
    
    GameController game;
    @Before
    public void setUp() {
        Rectangle bd = new Rectangle(0, 0, 20, 20);
        Rectangle pd = new Rectangle(0, 0, 0, 0);
        game = new GameController(bd, pd, 1, 45);
    }
    
    @Test
    public void testMove(){
        Assert.assertEquals(10, game.getBall().getCenter().y);
        game.moveBall();
        Assert.assertEquals(11, game.getBall().getCenter().y);
    }
    
    @Test
    public void testCollision(){
        for(int i = 0; i < 100; i++){
            System.out.println(i + " ------------ " + game.getBall().getCenter());
            game.moveBall();
        }
        Assert.assertTrue(game.getBall().getCenter().x >= 0 && game.getBall().getCenter().x <= game.getBoard().getMaxX());
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
