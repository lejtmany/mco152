package pong;

import java.awt.Rectangle;
import java.awt.geom.Line2D;

/*
 * Ben Forgy
 * Nov 9, 2014
 */
public class Board implements Surface {
    private Rectangle body;
    public Board(int startingX,int startingY,int width,int height){
        body = new Rectangle(startingX, startingY, width, height); 
    }

    public Board(Rectangle boardDimension) {
        body = new Rectangle(boardDimension);
    }
    
    public int getWidth(){
        return (int)body.getWidth();
    }
    public int getHeight(){
        return (int)body.getHeight();
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
        Line2D[] array = new Line2D[2];
        if (deg <= 90) {
            array[0] = getRight();
            array[1] = getTop();
        } else if (deg <= 180) {
            array[0] = getTop();
            array[1] = getLeft();
        } else if (deg <= 270) {
            array[0] = getLeft();
            array[1] = getBottom();
        } else if(deg <= 360){
            array[0] = getBottom();
            array[1] = getRight();
        }
        return array;
    }

    @Override
    public int getMinX() {
        return (int)body.getMinX();
    }

    @Override
    public int getMinY() {
        return (int)body.getMinY();
    }

    @Override
    public int getMaxX() {
        return (int)body.getMaxX();
    }

    @Override
    public int getMaxY() {
        return (int)body.getMaxY();
    }

}
