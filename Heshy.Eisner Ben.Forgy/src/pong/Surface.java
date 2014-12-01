package pong;

import java.awt.geom.Line2D;

/**
 * Ben Forgy
 * Nov 8, 2014
 */

public interface Surface {
    
    public Line2D getTop();
    public Line2D getBottom();
    public Line2D getLeft();
    public Line2D getRight();
    public Line2D[] getLinesByDeg(int deg);
    public int getMinX();
    public int getMinY();
    public int getMaxX();
    public int getMaxY();
}
