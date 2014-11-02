package alarmclock4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class AlarmPlayer implements IAlarmPlayer{

    private String soundFile;
    private AudioPlayer musicPlayer;
    private ContinuousAudioDataStream loop = null;
    private int duration;
    private boolean isPlaying;

    AlarmPlayer(String file, int duration) {
        this.duration = duration;
        soundFile = file;
        try {
            AudioStream backgroundMusic = new AudioStream(new FileInputStream(soundFile));
            AudioData musicData = backgroundMusic.getData();
            musicPlayer = AudioPlayer.player;
            loop = new ContinuousAudioDataStream(musicData);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public boolean isPlaying(){
        return isPlaying;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {     
        Thread t = new Thread(new Runnable() {
            
            @Override
            public void run() {
                isPlaying = true;
                musicPlayer.start(loop);
                try {
                    Thread.sleep(duration * 1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                musicPlayer.stop(loop);
                isPlaying = false;
            }
        });
        t.start();
    }
}
