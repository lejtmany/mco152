/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclockV4;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try {
            NISTClock = new NISTClock();
        } catch (IOException ex) {
            Logger.getLogger(NISTClockTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @Override
    public void compareTime() {
        InternationalClock NyTime = new InternationalClock("America/New_York");
        assertThat(NyTime.getTime().getTotalSeconds() -(NISTClock.getTime().getTotalSeconds())).isLessThanOrEqualTo(5);
    }

}
