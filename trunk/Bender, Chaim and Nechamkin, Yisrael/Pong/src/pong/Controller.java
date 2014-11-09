package pong;

public class Controller {

    PongGUI gui = new PongGUI(this);

    private int score = 0;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        gui.scorePanel.repaint();
    }
}
