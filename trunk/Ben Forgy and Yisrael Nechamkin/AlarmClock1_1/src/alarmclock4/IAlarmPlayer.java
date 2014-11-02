package alarmclock4;

import java.awt.event.ActionListener;

/**
 * Required custom ActionListener to add 'isPlaying' method.
 * This solves an issue that was presented by range checking for alarm time:
 * the clock class would call the ActionListener multiple times, which is bad.
 * Now it only calls it once, which is good.
 */

public interface IAlarmPlayer extends ActionListener{
    public boolean isPlaying();
}
