/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author jrobinson
 */
class PongPanel extends JPanel {

    
    private Point ball = new Point(100,100);
    public PongPanel() {
        setBackground(Color.BLACK);
        Timer t = new Timer(50, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                updateBall();
                repaint();
                System.out.println(ball);
            }
        });
        t.start();
    }
    
    private void updateBall()
    {
        ball.translate(2, 2);
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: 8", 250, 10);
        
        g.fillOval(ball.x, ball.y, 10, 10);
    }
    
}
