package pongmvc;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class ModelTest {

    Model model;

    @Before
    public void initModel() {
        model = new Model();
    }

    @Test
    public void testPaddleSize() {
        Assert.assertEquals(model.getPaddleLength(), model.getPaddle1().height);
        Assert.assertEquals(model.getPaddleLength(), model.getPaddle2().height);
        Assert.assertEquals(model.getPaddleWidth(), model.getPaddle1().width);
        Assert.assertEquals(model.getPaddleWidth(), model.getPaddle2().width);
    }
}
