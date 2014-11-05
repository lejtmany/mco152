
package alarmclock4;

import java.util.TimeZone;


public class InternationalClock extends Clock implements IClock{
    
    private final TimeZone tz;
    private final boolean dst;
    
    public InternationalClock(TimeZone tz, boolean dst){
        this.tz = tz;
        this.dst = dst;
    }
    
    public TimeZone getTimeZone(){
        return tz;
    }
    
    public boolean getDST(){
        return dst;   
    }
    
}
