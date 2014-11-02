package alarmclock4;

import alarmclock4.AlarmClock;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author BenForgy
 */
public class AlarmClock1Test {

    boolean isPlaying;

    @Before
    public void SetUp() {
        isPlaying = false;
    }

    @Test
    public void testMain() {
        Main.main(null);
    }

    @Test
    public void testAlarm_true() {
        IClock clock = mock(IClock.class);
        when(clock.getTime()).thenReturn(new Time(1, 0, 0));
        IAlarmPlayer listener = new IAlarmPlayer() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                isPlaying = true;
            }

            @Override
            public boolean isPlaying() {
                return true;
            }
        };
        AlarmClock alarm = new AlarmClock(clock, listener);
        alarm.setAlarm(1, 0);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Assert.assertFalse(isPlaying);
    }

}
