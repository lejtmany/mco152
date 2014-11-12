package alarmClock3;

import alarmclock3.Clock;
import alarmclock3.InternationalClock;
import java.util.TimeZone;
import static org.assertj.core.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Nahum Twersky
 */
public class InternationalClockTest {

    InternationalClock nyTimeDST, nyTime_NotDST, israelTimeDST;
    Clock systemClock;

    @Before
    public void setUp() {
        nyTimeDST = new InternationalClock(TimeZone.getDefault(), true);
        nyTime_NotDST = new InternationalClock(TimeZone.getDefault(), false);
        israelTimeDST = new InternationalClock(TimeZone.getTimeZone("Israel"), true);
        systemClock = new Clock();
    }

    @Test
    public void testGetTime() {
        assertThat(nyTimeDST.getTime()).isEqualToComparingFieldByField(systemClock.getTime());
    }

    @Test
    public void testDST_NotDST_NotEqual() {
        int dstHours = nyTimeDST.getTime().getHours(),
            notDSTHours = nyTime_NotDST.getTime().getHours(),
            theHourDifferenceBetweenDST_and_NotDST = notDSTHours - dstHours;
        
        assertThat(theHourDifferenceBetweenDST_and_NotDST).isEqualTo(1);
    }

    @Test
    public void testGetIsraelTime() {
        int israelHours = israelTimeDST.getTime().getHours(),
            nyHours = nyTimeDST.getTime().getHours(),
            theHourDifferenceBetweenIsraelAndNewYork = israelHours - nyHours;
        
        assertThat(theHourDifferenceBetweenIsraelAndNewYork).isEqualTo(7);
    }
}
