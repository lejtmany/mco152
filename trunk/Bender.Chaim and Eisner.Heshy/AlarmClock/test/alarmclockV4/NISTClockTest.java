/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclockV4;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

/**
 *
 * @author Heshy
 */
public class NISTClockTest implements ICLockTest {

    IClock NISTClock;

    @Before
    @Override
    public void setUp() {
        NISTClock = new NISTClock();
    }

    @Test
    @Override
    public void compareTime() {
        InternationalClock NyTime = new InternationalClock("America/New_York");
        assertThat(NyTime.getTime()).isEqualTo(NISTClock.getTime());
    }

}
