package pong;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

/*
 * @author Ben Forgy
 * Nov 9, 2014
 */
public class Paddle implements Surface {

    private Rectangle body;
    private int speed;

    public Paddle(int startingX, int startingY, int width, int height) {
        body = new Rectangle(startingX, startingY, width, height);
    }

    Paddle(int speed, Rectangle padelDimension) {
        body = new Rectangle(padelDimension);
       
        this.speed = speed;
    }

    @Override
    public Line2D getTop() {
        return new Line2D.Double(body.getMinX(), body.getMinY(), body.getMaxX(), body.getMinY());
    }

    @Override
    public Line2D getBottom() {
        return new Line2D.Double(body.getMinX(), body.getMaxY(), body.getMaxX(), body.getMaxY());
    }

    @Override
    public Line2D getLeft() {
        return new Line2D.Double(body.getMinX(), body.getMinY(), body.getMinX(), body.getMaxY());
    }

    @Override
    public Line2D getRight() {
        return new Line2D.Double(body.getMaxX(), body.getMinY(), body.getMaxX(), body.getMaxY());
    }

    @Override
    public Line2D[] getLinesByDeg(int deg) {
        Line2D[] array = new Line2D[3];
        if (deg <= 90 || deg >= 270) {
            array[0] = getLeft();
        } else {
            array[0] = getRight();
        }
        array[1] = getTop();
        array[2] = getBottom();
        return array;
    }

    @Override
    public int getMinX() {
        return (int) body.getMinX();
    }

    @Override
    public int getMinY() {
        return (int) body.getMinY();
    }

    @Override
    public int getMaxX() {
        return (int) body.getMaxX();
    }

    @Override
    public int getMaxY() {
        return (int) body.getMaxY();
    }

    public boolean moveUp(Surface context) {
        int nextLocation = getMinY() - speed;
        if (nextLocation <= context.getMinY()) {
            body.translate(0, -(getMinY()  - context.getMinY()));
            return false;
        }
        body.translate(0, -speed);
        return true;
    }

    public boolean moveDown(Surface context) {
        int nextLocation = getMaxY() + speed;
        if (nextLocation >= context.getMaxY()) {
            body.translate(0, (context.getMaxY() - getMaxY()));
            return false;
        }
        body.translate(0, speed);
        return true;
    }

    public boolean moveLeft(Surface context) {
        int nextLocation = getMinX() - speed;
        if (nextLocation <= context.getMinX()) {
            return false;
        }
        body.translate(-speed, 0);
        return true;
    }

    public boolean moveRight(Surface context) {
        int nextLocation = getMaxX() + speed;
        if (nextLocation >= context.getMaxX()) {
            return false;
        }
        body.translate(speed, 0);
        return true;
    }

    public Rectangle getDimensions() {
        return new Rectangle(body);
    }

}
