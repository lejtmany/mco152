package alarmClock4;

import alarmclock4.Time;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;


public class TimeTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

     @Test
     public void CtorDefaultIsMidnight() {
         Time t = new Time();
         assertThat(t).isEqualToComparingFieldByField(new Time(0,0,0));
     }

   
    /**
     * Test of compareTo method, of class Time.
     */
    @Test
    public void testCompareTo() {
        Time before = new Time(1,4,5),
             after = new Time(2,0,1);
        
        assertThat(before.compareTo(after)).isLessThan(0);
        assertThat(after.compareTo(before)).isGreaterThan(0);  
        assertThat(after.compareTo(after)).isEqualTo(0);       
        assertThat(before.compareTo(before)).isEqualTo(0);       

    }

    /**
     * Test of getTotalSeconds method, of class Time.
     */
    @Test
    public void testGetTotalSeconds() {
        assertThat(new Time(1,4,5).getTotalSeconds()).isEqualTo(3600+4*60+5);
    }
}
