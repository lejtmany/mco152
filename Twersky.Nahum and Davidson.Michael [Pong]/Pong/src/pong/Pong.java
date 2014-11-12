package pong;

import javax.swing.JFrame;

public class Pong {

    public static void main(String[] args) {
        new PongGUI();
    }
    
}

class PongGUI extends JFrame
{
    public PongGUI()
    {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setVisible(true);
        setResizable(false);
        add( new PongPanel());
    
    }
    
}
