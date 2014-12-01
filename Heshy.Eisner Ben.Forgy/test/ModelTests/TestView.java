package ModelTests;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import pong.GameController;

/*
 * Ben Forgy
 * Nov 10, 2014
 */
public class TestView extends JFrame {

    JPanel canvas;
    Rectangle bounds = new Rectangle(0, 0, 100, 110);
    GameController gc;

    public TestView() {
        super("Pong");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(0, 0, 400, 300);
        gc = new GameController(bounds, new Rectangle(0, 0, 0, 0), 4, 31);

        MyJPanel canvas = new MyJPanel();
        canvas.setBounds(50, 50, 300, 200);
        canvas.setMaximumSize(new Dimension(200, 200));
        canvas.setBackground(Color.black);
        canvas.setOpaque(true);
        Timer t = new Timer(10, new ActionListener() {
                    Point lastPos = new Point(0, 0);

        long start, end;

            @Override
            public void actionPerformed(ActionEvent e) {
                gc.moveBall();
                Graphics g = canvas.getGraphics();
                            g.setColor(Color.black);
            g.fillRect(lastPos.x, lastPos.y, (int)gc.getBall().getBody().getBoundingBox().getWidth()- 1,
                    (int)gc.getBall().getBody().getBoundingBox().getHeight() - 1);
            g.setColor(Color.white);
            g.fillOval((int)gc.getBall().getBody().getBoundingBox().getMinX() + 1,
                    (int)gc.getBall().getBody().getBoundingBox().getMinY() + 1,
                    (int)gc.getBall().getBody().getBoundingBox().getWidth() - 2,
                    (int)gc.getBall().getBody().getBoundingBox().getHeight() - 2);
            
            lastPos.x = (int)gc.getBall().getBody().getBoundingBox().getMinX() + 1;
            lastPos.y = (int)gc.getBall().getBody().getBoundingBox().getMinY() + 1;
            }
        });
        this.add(canvas);
        canvas.setVisible(true);
        this.setVisible(true);
        t.start();
    }

    public static void main(String[] args) {
        TestView tv = new TestView();
    }

    private class MyJPanel extends JPanel {

    }

}
