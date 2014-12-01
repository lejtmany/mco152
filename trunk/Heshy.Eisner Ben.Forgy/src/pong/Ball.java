package pong;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.Collection;

/*
 * Ben Forgy, COPYRIGHT
 * Nov 8, 2014
 */
public class Ball {

    private final int radius;
    private final Degree directionOfTravel;
    private int currentDirPower;
    private final Octagon body;
    private boolean hitPaddle;

    public Ball(int x, int y, int radius_in, int dirOfTravel) {
        radius = radius_in;
        body = new Octagon(new Point(x, y), radius);
        directionOfTravel = new Degree(dirOfTravel);
        currentDirPower = 0;
    }

    Ball(Ball ball) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Point getCenter() {
        return new Point(body.center);
    }

    public int getRadius() {
        return radius;
    }

    public Octagon getBody() {
        return body;
    }

    /**
     * COPYRIGHT ME
     */
    public void move(Collection<Line2D> nearBySurfaces) {
        currentDirPower = (currentDirPower + directionOfTravel.get()) % 360;
        if (currentDirPower < 45) {
            body.moveCenterBy(1, 0);
        } else if (currentDirPower < 90) {
            body.moveCenterBy(1, -1);
            currentDirPower -= 45;
        } else if (currentDirPower < 135) {
            body.moveCenterBy(0, -1);
            currentDirPower -= 90;
        } else if (currentDirPower < 180) {
            body.moveCenterBy(-1, -1);
            currentDirPower -= 135;
        } else if (currentDirPower < 225) {
            body.moveCenterBy(-1, 0);
            currentDirPower -= 180;
        } else if (currentDirPower < 270) {
            body.moveCenterBy(-1, 1);
            currentDirPower -= 225;
        } else if (currentDirPower < 315) {
            body.moveCenterBy(0, 1);
            currentDirPower -= 270;
        } else if (currentDirPower < 360) {
            body.moveCenterBy(1, 1);
            currentDirPower -= 315;
        }
        if (nearBySurfaces != null && !nearBySurfaces.isEmpty()) {
            handleCollisions(nearBySurfaces);
        }

    }

    protected void handleCollisions(Collection<Line2D> nearBySurfaces) {
//        System.out.println("-- isColliding --");
        boolean hitCorner = false;
        int i = 0;
        Line2D collidingOutsideLine = null;
        Line2D collidingBallLine = null;
        for (Line2D outsideLine : nearBySurfaces) {
            for (Line2D ballLine : body.getLinesByDeg(directionOfTravel.get())) {
                if (ballLine.intersectsLine(outsideLine)) {
                    hitPaddle(outsideLine);
                    if (i > 0) {
                        hitCorner = true;
                        if(hitPaddle){
                            hitCornerOfPadel();
                        }else{
                            hitCornerOfBoard();
                        }
                    }
                    i++;
                    collidingOutsideLine = outsideLine;
                    collidingBallLine = ballLine;
                    //return true;
                    break;
                }
            }
        }

        if (i >= 1) {
            if (!hitCorner) {
                changeDirectionBasedOnLine(collidingOutsideLine, collidingBallLine);
                offsetBall(collidingOutsideLine);
            }else{
                move(nearBySurfaces);
            }
            currentDirPower = 0;
        }
        //return false;
    }
    
    private void hitPaddle(Line2D outsideLine){
         hitPaddle = (Math.abs(outsideLine.getX1() - outsideLine.getX2()) < 200 &&
                Math.abs(outsideLine.getY1() - outsideLine.getY2()) < 200);
    }
    
    public boolean hitPaddle(){
        boolean temp = hitPaddle;
        hitPaddle = false;
        return temp;
    }

    private void hitCornerOfBoard() {
        System.out.println("-- hitCornerOfBoard --");
//        System.out.println("Before: " + directionOfTravel.get());
        directionOfTravel.set(directionOfTravel.get() - 180);
//        System.out.println("After: " + directionOfTravel.get());
    }

    private void hitCornerOfPadel() {
        System.out.println("-- hitCornerOfPadel --");
//        System.out.println("Before: " + directionOfTravel.get());
        directionOfTravel.set(180 - directionOfTravel.get());
//        System.out.println("After: " + directionOfTravel.get());
    }

    private void changeDirectionBasedOnLine(Line2D outsideLine, Line2D ballLine) {
//        System.out.println("-- changeDirectionBasedOnLines --");
//        System.out.println("Before: " + directionOfTravel.get());
        int dir = directionOfTravel.get();

        if (ballLine.getX1() == ballLine.getX2() || ballLine.getY1() == ballLine.getY2()) {
            if (outsideLine.getX1() == outsideLine.getX2()) {
//                System.out.println("LEFT|RIGHT");
                directionOfTravel.set(180 - dir);
            } else if (outsideLine.getY1() == outsideLine.getY2()) {
//                System.out.println("UP|DOWN");
                directionOfTravel.set(360 - dir);
            }
//            System.out.println("After: " + directionOfTravel.get());
        } else {
            hitCornerOfPadel();
        }
    }

    private void offsetBall(Line2D outsideLine) {
        if (getCenter().y > outsideLine.getY1()) {
            body.moveCenterBy(0, 1);
        } else if (getCenter().y < outsideLine.getY1()) {
            body.moveCenterBy(0, -1);
        }
        if (getCenter().x > outsideLine.getX1()) {
            body.moveCenterBy(1, 0);
        } else if (getCenter().x < outsideLine.getX1()) {
            body.moveCenterBy(-1, 0);
        }
    }

    public class Octagon implements Surface {

        private Line2D.Double top, bottom, left, right;
        private Line2D.Double topLeft, topRight, bottomLeft, bottomRight;
        private final int radius;
        private Point center;

        private Octagon(Point center, int radius) {
            this.center = center;
            this.radius = radius;

            buildLines();
        }

        private Rectangle makeBoundingBox() {
            return new Rectangle(center.x - radius, center.y - radius, radius * 2 - 1, radius * 2 - 1);
        }

        private void moveCenterBy(int x, int y) {
            center.x += x;
            center.y += y;
            reBuildLines();
        }

        private void buildLines() {
            int lineOffset = (radius + 1) / 2;
            top = new Line2D.Double(center.x - lineOffset, center.y - lineOffset,
                    center.x + lineOffset, center.y - lineOffset);

            bottom = new Line2D.Double(center.x - lineOffset, center.y + lineOffset,
                    center.x + lineOffset, center.y + lineOffset);
            
            left = new Line2D.Double(center.x - radius, center.y - lineOffset,
                    center.x - lineOffset, center.y + lineOffset);
            
            right = new Line2D.Double(center.x + radius, center.y - lineOffset,
                    center.x + lineOffset, center.y + lineOffset);

            topLeft = new Line2D.Double(top.getP1(), left.getP1());
            
            topRight = new Line2D.Double(top.getP2(), right.getP1());
           
            bottomLeft = new Line2D.Double(bottom.getP1(), left.getP2());
            
            bottomRight = new Line2D.Double(bottom.getP2(), right.getP2());
        }

        // minimize new object creation.
        private void reBuildLines() {
            int lineOffset = (radius + 1) / 2;
            top.setLine(center.x - lineOffset, center.y - lineOffset,
                    center.x + lineOffset, center.y - lineOffset);
            
            bottom.setLine(center.x - lineOffset, center.y + lineOffset,
                    center.x + lineOffset, center.y + lineOffset);
            
            left.setLine(center.x - lineOffset, center.y - lineOffset,
                    center.x - lineOffset, center.y + lineOffset);
            
            right.setLine(center.x + lineOffset, center.y - lineOffset,
                    center.x + lineOffset, center.y + lineOffset);

            topLeft.setLine(top.getP1(), left.getP1());
            
            topRight.setLine(top.getP2(), right.getP1());
            
            bottomRight.setLine(bottom.getP1(), left.getP2());
            
            bottomLeft.setLine(bottom.getP2(), right.getP2());
        }

        @Override
        public Line2D getTop() {
//            System.out.println("getTop");
            return new Line2D.Double(top.getP1(), top.getP2());
        }

        @Override
        public Line2D getBottom() {
//            System.out.println("getBottom");
            return new Line2D.Double(bottom.getP1(), bottom.getP2());
        }

        @Override
        public Line2D getLeft() {
//            System.out.println("getLeft");
            return new Line2D.Double(left.getP1(), left.getP2());
        }

        @Override
        public Line2D getRight() {
//            System.out.println("getRight");
            return new Line2D.Double(right.getP1(), right.getP2());
        }

        public Line2D getTopRight() {
//            System.out.println("getTopRight");
            return new Line2D.Double(topRight.getP1(), topRight.getP2());
        }

        public Line2D getTopLeft() {
//            System.out.println("getTopLeft");
            return new Line2D.Double(topLeft.getP1(), topLeft.getP2());
        }

        public Line2D getBottomRight() {
//            System.out.println("getBottomRight");
            return new Line2D.Double(bottomRight.getP1(), bottomRight.getP2());
        }

        public Line2D getBottomLeft() {
//            System.out.println("getBottomRight");
            return new Line2D.Double(bottomLeft.getP1(), bottomLeft.getP2());
        }

        @Override
        public Line2D[] getLinesByDeg(int deg) {
            Line2D[] lines = new Line2D[3];
            if (deg >= 0 && deg <= 90) {
                lines[0] = getTop();
                lines[1] = getRight();
                lines[2] = getTopRight();
            } else if (deg <= 180) {
                lines[0] = getLeft();
                lines[1] = getTop();
                lines[2] = getTopLeft();
            } else if (deg <= 270) {
                lines[0] = getBottom();
                lines[1] = getLeft();
                lines[2] = getBottomLeft();
            } else if (deg <= 360) {
                lines[0] = getRight();
                lines[1] = getBottom();
                lines[2] = getBottomRight();
            }
            return lines;
        }

        public Rectangle getBoundingBox() {
            return makeBoundingBox();
        }

        @Override
        public int getMinX() {
            return center.x - radius;
        }

        @Override
        public int getMinY() {
            return center.y - radius;
        }

        @Override
        public int getMaxX() {
            return center.x + radius;
        }

        @Override
        public int getMaxY() {
            return center.y + radius;
        }

    }

}