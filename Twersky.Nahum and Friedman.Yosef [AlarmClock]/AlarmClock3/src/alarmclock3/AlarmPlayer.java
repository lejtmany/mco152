package alarmclock3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import javax.swing.Timer;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class AlarmPlayer implements ActionListener{

    private AudioPlayer musicPlayer;
    private ContinuousAudioDataStream loop = null;
    private String gongFile = "gong.au";

    private Timer timer;

    public AlarmPlayer() {
        musicPlayer = AudioPlayer.player;
        try {
            AudioStream backgroundMusic = new AudioStream(new FileInputStream(gongFile));
            AudioData musicData = backgroundMusic.getData();
            loop = new ContinuousAudioDataStream(musicData);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        timer = new Timer(0, (ActionEvent e) -> {
            System.out.println("Off");
            musicPlayer.stop(loop);
        });
        timer.setRepeats(false);
    }

    public void play(int durationInSeconds) {
        if (isAlarmOn()) {
            return;
        }
        musicPlayer.start(loop);
        timer.setInitialDelay(durationInSeconds * 1000);
        timer.start();
    }

    private boolean isAlarmOn() {
        boolean val = timer != null && timer.isRunning();
        System.out.println(val);
        return val;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String durationString = e.getActionCommand();
        int durationInt = Integer.parseInt(durationString);
        play(durationInt);
    }

}
