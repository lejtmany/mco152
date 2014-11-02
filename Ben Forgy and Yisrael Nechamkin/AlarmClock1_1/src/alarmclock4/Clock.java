package alarmclock4;

import java.util.Calendar;

public class Clock implements IClock{
    
    @Override
    public Time getTime(){
        Time current;      
        Calendar calendar = Calendar.getInstance();
        current = new Time(calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            calendar.get(Calendar.SECOND));
        return current;
    }
    

}

