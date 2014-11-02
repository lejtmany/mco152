package alarmclock4;

import alarmclock4.Clock;
import alarmclock4.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author BenForgy
 */
public class ClockTest {

    @Test
    public void testCurrentTimeCompair() {
        Clock clock = new Clock();
        try {
            Time timeOne = clock.getTime();
            Thread.sleep(1000);
            Time timeTwo = clock.getTime();
            Thread.sleep(1000);
            Time timeThree = clock.getTime();
            
            assertTrue(timeOne.compareTo(timeTwo) < 0);
            assertTrue(timeThree.compareTo(timeTwo) > 0);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClockTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
