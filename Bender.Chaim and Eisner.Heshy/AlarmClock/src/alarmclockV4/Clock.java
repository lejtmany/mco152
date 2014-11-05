package alarmclockV4;

import java.util.Calendar;

public class Clock implements IClock{
    public Time getTime() {
        Calendar now = Calendar.getInstance();
        return new Time(
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND));
    }
}
