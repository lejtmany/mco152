package alarmClock3;

import alarmclock3.NISTClock;
import alarmclock3.Clock;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

/**
 *
 * @author Nahum Twersky
 */
public class NISTClockTest {
    
    @Test
    public void testGetTime() {
        NISTClock instance = new NISTClock(5000);
        
        int resultTotalSeconds = instance.getTime().getTotalSeconds(),
            expectedTotalSeconds = new Clock().getTime().getTotalSeconds(),
            theDifferenceBetweenTheTimes = expectedTotalSeconds - resultTotalSeconds;
        
        assertThat(theDifferenceBetweenTheTimes).isLessThanOrEqualTo(10);
    }

}
