package alarmclock4;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author BenForgy
 * @author NahumTwersky
 * @author YisraelNechamkin
 */
public class InternationalTimeTest {

    private InternationalTime time235959_530DST,
            time000000_0,
            timeRandom;

    @Before
    public void setUp() {
        time235959_530DST = new InternationalTime(new Time(23, 59, 59), new TimeZone(+5, 30), true);
        time000000_0 = new InternationalTime(0, 0, 0, 0, 0, false);
        timeRandom = new InternationalTime(11, 1, 21, -10, 0, true);
    }

    @Test
    public void testGetLocalTime() {
        InternationalTime it = new InternationalTime(5, 5, 5, -5, 0, false);
        Time result = it.getLocalTime();
        Time expResult = new Time(5, 5, 5);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetLocalTime_DST() {
        InternationalTime it = new InternationalTime(5, 5, 5, -5, 0, true);
        Time result = it.getLocalTime();
        Time expResult = new Time(5, 5, 5);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetLocalTime_backwardOverMidnight() {
        InternationalTime it = new InternationalTime(4, 5, 5, 5, 0, false);
        Time result = it.getLocalTime();
        Time expResult = new Time(4, 5, 5);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetLocalTime_backwardOverMidnight_DST() {
        InternationalTime it = new InternationalTime(4, 5, 5, 5, 0, true);
        Time result = it.getLocalTime();
        Time expResult = new Time(4, 5, 5);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetLocalTime_forwardOverMidnight() {
        InternationalTime it = new InternationalTime(22, 5, 5, -5, 0, false);
        Time result = it.getLocalTime();
        Time expResult = new Time(22, 5, 5);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetLocalTime_forwardOverMidnight_DST() {
        InternationalTime it = new InternationalTime(22, 5, 5, -5, 0, true);
        Time result = it.getLocalTime();
        Time expResult = new Time(22, 5, 5);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetUTCTime() {
        InternationalTime it = new InternationalTime(5, 5, 5, -5, 0, false);
        Time result = it.getUTCTime();
        Time expResult = new Time(10, 5, 5);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetUTCTime_backwardOverMidnight() {
        InternationalTime it = new InternationalTime(4, 5, 5, 14, 0, false);
        Time result = it.getUTCTime();
        Time expResult = new Time(14, 5, 5);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetUTCTime_backwardOverMidnight_DST() {
        InternationalTime it = new InternationalTime(4, 5, 5, 14, 0, true);
        Time result = it.getUTCTime();
        Time expResult = new Time(13, 5, 5);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetUTCTime_forwardOverMidnight() {
        InternationalTime it = new InternationalTime(22, 5, 5, -5, 0, false);
        Time result = it.getUTCTime();
        Time expResult = new Time(3, 5, 5);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetUTCTime_forwardOverMidnight_DST() {
        InternationalTime it = new InternationalTime(22, 5, 5, -5, 0, true);
        Time result = it.getUTCTime();
        Time expResult = new Time(2, 5, 5);
        assertEquals(expResult, result);
    }

    @Test
    public void testSetHour() {
        int hour = 0;
        InternationalTime instance = timeRandom;
        instance.setHour(hour);
        assertEquals(instance.getHour(), hour);
    }

    @Test
    public void testSetMinute() {
        int minute = 0;
        InternationalTime instance = timeRandom;
        instance.setMinute(minute);
        assertEquals(instance.getMinute(), minute);
    }

    @Test
    public void testGetHour() {
        InternationalTime instance = time000000_0;
        int expResult = 0;
        int result = instance.getHour();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetMinute() {
        InternationalTime instance = time235959_530DST;
        int expResult = 59;
        int result = instance.getMinute();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetSecond() {
        InternationalTime instance = time235959_530DST;
        int expResult = 59;
        int result = instance.getSecond();
        assertEquals(expResult, result);
    }

    @Test
    public void testEquals_true() {
        InternationalTime instance = new InternationalTime(23, 59, 59, +5, 30, true);
        assertTrue(instance.equals(time235959_530DST));
    }

    @Test
    public void testEquals_false() {
        assertFalse(time000000_0.equals(time235959_530DST));
    }

    @Test
    public void testCompareTo_0() {
        InternationalTime instance = new InternationalTime(time235959_530DST);
        assertTrue(instance.compareTo(time235959_530DST) == 0);
    }

    @Test
    public void testCompareTo_greaterThan0() {
        InternationalTime instance = time235959_530DST;
        assertTrue(instance.compareTo(timeRandom) > 0);
    }

    @Test
    public void testCompareTo_lessThan0() {
        InternationalTime instance = new InternationalTime(time000000_0);
        assertTrue(instance.compareTo(timeRandom) < 0);
    }

    @Test
    public void testGetTimeSeconds_0() {
        assertEquals(time000000_0.getTimeInSeconds(), 0);
    }

    @Test
    public void testGetTimeSeconds_max() {
        assertEquals(time235959_530DST.getTimeInSeconds(), 24 * 60 * 60 - 1);
    }
    
    @Test
    public void testCompareToITN_0_allSame(){       
        InternationalTime instance = new InternationalTime(time000000_0);
        assertEquals(time000000_0.compareToITN(instance), 0);
    }
    @Test
    public void testCompareToITN_0_DSTDif(){       
        InternationalTime instance = new InternationalTime(1, 0, 0, 0, 0, true);
        assertEquals(0, time000000_0.compareToITN(instance));
    }
    @Test
    public void testCompareToITN_0_tzDif(){       
        InternationalTime instance = new InternationalTime(1, 0, 0, 1, 0, false);
        assertEquals(0, time000000_0.compareToITN(instance));
    }
}
