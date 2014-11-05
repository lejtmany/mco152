/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package alarmClock4;

import  alarmclock4.AlarmClock;
import  alarmclock4.AlarmPlayer;
import  alarmclock4.Clock;
import  alarmclock4.IClock;
import alarmclock4.NISTClock;
import alarmclock4.NISTServer;
import  alarmclock4.Time;
import alarmclock4.TimeServer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.UnknownHostException;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class AlarmPlayerTest {
    
    public AlarmPlayerTest() {
    }
    
    @Before
    public void setUp(){
        
    }
    

    @Test
    public void testPlay() {
      
    }

    @Test
    public void testIsAlarmOn() {
    }

    @Test
    public void testActionPerformed() throws InterruptedException {
        Clock clock = mock(Clock.class);
      when(clock.getTime()).thenReturn(new Time(1,0,0));
      AlarmPlayer ap = mock(AlarmPlayer.class);       
       AlarmClock ac = new AlarmClock(clock, ap);
       ac.setAlarmTime(1, 0, 0);
       Thread.sleep(1000);
       verify(ap).actionPerformed(anyObject());     
    }
    
    @Test
    public void testNISTTimeWhenServerIsDown() throws IOException, InterruptedException{
        Clock localClock = new Clock();
        TimeServer mockServer = mock(NISTServer.class);
        when(mockServer.getServerTimeInMillis()).thenThrow(UnknownHostException.class);
        NISTClock nistClock = new NISTClock(mockServer);
        Time test = nistClock.getTime();
        Thread.sleep(5000);
        Time test2 = nistClock.getTime();
        Time test3 = localClock.getTime();
        for(int i = 0; i < 10; i++ ){
            System.out.println(nistClock.getTime().toString());
            Assert.assertTrue(Math.abs(nistClock.getTime().getTotalSeconds() - localClock.getTime().getTotalSeconds()) <= 500);
            Thread.sleep(1000);
        }
    }
    
}
