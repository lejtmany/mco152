package alarmclock3;

import java.util.TimeZone;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.CopticChronology;

/**
 *
 * @author Nahum Twersky
 */
public class InternationalClock implements IClock {

    DateTime dt;
    boolean isDST;

    public InternationalClock(TimeZone zone, boolean isDST) {
        DateTimeZone dtz = DateTimeZone.forTimeZone(zone);
        Chronology chrono = CopticChronology.getInstance(dtz);
        dt = new DateTime(chrono);
        this.isDST = isDST;
    }

    @Override
    public Time getTime() {
        if (!isDST) {
            return new Time(dt.getHourOfDay() + 1, dt.getMinuteOfHour(), dt.getSecondOfMinute());
        }
        return new Time(dt.getHourOfDay(), dt.getMinuteOfHour(), dt.getSecondOfMinute());
    }

}
