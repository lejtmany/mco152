package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

class ScorePanel extends JPanel {
    
    private final Controller controller;
    
    public ScorePanel(Controller con) {
        controller = con;
        setBackground(Color.BLACK);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: " + controller.getScore(), 250, 50);
    }
}
