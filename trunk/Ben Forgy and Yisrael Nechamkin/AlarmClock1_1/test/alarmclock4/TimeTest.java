package alarmclock4;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author BenForgy
 * @author YisraelNechamkin
 */
public class TimeTest {

    private Time time235959,
            time000000,
            timeRandom;

    @Before
    public void setUp() {
        time235959 = new Time(23, 59, 59);
        time000000 = new Time(0, 0, 0);
        timeRandom = new Time(11, 1, 21);
    }

    @Test
    public void testSetHour() {
        int hour = 0;
        Time instance = timeRandom;
        instance.setHour(hour);
        assertEquals(instance.getHour(), hour);
    }

    @Test
    public void testSetMinute() {
        int minute = 0;
        Time instance = timeRandom;
        instance.setMinute(minute);
        assertEquals(instance.getMinute(), minute);
    }

    @Test
    public void testGetHour() {
        Time instance = time000000;
        int expResult = 0;
        int result = instance.getHour();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetMinute() {
        Time instance = time235959;
        int expResult = 59;
        int result = instance.getMinute();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetSecond() {
        Time instance = time235959;
        int expResult = 59;
        int result = instance.getSecond();
        assertEquals(expResult, result);
    }

    @Test
    public void testEquals_true() {
        Time instance = new Time(23, 59, 59);
        assertTrue(instance.equals(time235959));
    }

    @Test
    public void testEquals_false() {
        assertFalse(time000000.equals(time235959));
    }

    @Test
    public void testCompareTo_0() {
        Time instance = new Time(23, 59, 59);
        assertTrue(instance.compareTo(time235959) == 0);
    }

    @Test
    public void testCompareTo_greaterThan0() {
        Time instance = new Time(23, 59, 59);
        assertTrue(instance.compareTo(timeRandom) > 0);
    }

    @Test
    public void testCompareTo_lessThan0() {
        Time instance = new Time(10, 59, 59);
        assertTrue(instance.compareTo(timeRandom) < 0);
    }

    @Test
    public void testGetTimeSeconds_0() {
        assertEquals(time000000.getTimeInSeconds(), 0);
    }

    @Test
    public void testGetTimeSeconds_max() {
        assertEquals(time235959.getTimeInSeconds(), 24 * 60 * 60 - 1);
    }

    @Test
    public void testAddSeconds_1() {
        assertEquals(time000000.addSeconds(1).getTimeInSeconds(), 1);
    }

    @Test
    public void testAddSeconds_paramOverflow() {
        int orig = timeRandom.getTimeInSeconds();
        assertEquals(timeRandom.addSeconds(3600).getTimeInSeconds(), orig + 3600);
    }
    
    @Test
    public void testAddSeconds_stateOverflow() {
        assertEquals(time235959.addSeconds(2).getTimeInSeconds(), 1);
    }
}
