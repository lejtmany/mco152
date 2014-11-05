/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclockV4;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

/**
 *
 * @author Heshy
 */
public class InternationalClockTest implements ICLockTest {

    InternationalClock clock;

    @Before
    @Override
    public void setUp() {
        clock = new InternationalClock("America/New_York");
    }

    @Test
    @Override
    public void compareTime() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        Time t = clock.getTime();
        assertThat(t.getHours()).isEqualTo(cal.getTime().getHours());
        assertThat(t.getMinutes()).isEqualTo(cal.getTime().getMinutes());
        assertThat(t.getSeconds()).isEqualTo(cal.getTime().getSeconds());
    }

}
