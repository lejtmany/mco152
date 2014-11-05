package alarmclockV4;

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
    private int playDurationInSeconds;

    private Timer timer;

    public AlarmPlayer(int durationInSeconds) {
        playDurationInSeconds = durationInSeconds;
        musicPlayer = AudioPlayer.player;
        try {
            AudioStream backgroundMusic = new AudioStream(new FileInputStream(gongFile));
            AudioData musicData = backgroundMusic.getData();
            loop = new ContinuousAudioDataStream(musicData);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        timer = new Timer(0,
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Off");
                        musicPlayer.stop(loop);
                    }
                });
        timer.setRepeats(false);
    }

    private void play(int durationInSeconds) {
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
    public void actionPerformed(ActionEvent ae) {
        play(playDurationInSeconds);
    }

    public void setDurationInSeconds(int durationInSeconds){
        playDurationInSeconds = durationInSeconds;
    }
    
    public int getDurationInSeconds(){
        return playDurationInSeconds;
    }
        
}
