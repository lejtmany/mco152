package pong;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PongGUI extends JFrame {

    PongPanel pongPanel;
    ScorePanel scorePanel;

    public PongGUI(Controller con) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);

        JPanel panel = new JPanel();

        pongPanel = new PongPanel(con);
        pongPanel.setPreferredSize(new Dimension(500, 500));
        scorePanel = new ScorePanel(con);
        scorePanel.setPreferredSize(new Dimension(500, 50));
        panel.add(scorePanel);
        panel.add(pongPanel);
        setContentPane(panel);

        setVisible(true);
        setResizable(false);

    }
}
