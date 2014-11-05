/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package alarmclockV4;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author Heshy
 */
public class InternationalClock implements IClock {

     private final String tz;

    public InternationalClock(String timezone) {
        tz = timezone;
    }
    
    @Override
    public Time getTime(){
        DateTimeZone dtz = DateTimeZone.forID(tz);
        DateTime dt = new DateTime(dtz);
        int hr, min, sec;
        hr = dt.getHourOfDay();
        min = dt.getMinuteOfHour();
        sec = dt.getSecondOfMinute();
        return new Time(hr, min, sec);
    }
    
    public String getTimeZone(){
        return tz;
    }
}
