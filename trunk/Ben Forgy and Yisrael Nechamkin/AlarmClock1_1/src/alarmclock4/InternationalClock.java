package alarmclock4;

import java.util.Calendar;

/*
 * Ben Forgy
 * Oct 24, 2014
 */
public class InternationalClock implements IClock {

    private TimeZone tz;
    private boolean isDST;

    public InternationalClock(int tzHour, int tzMinute, boolean dst) {
        tz = new TimeZone(tzHour, tzMinute);
        isDST = dst;
    }

    public InternationalClock(TimeZone timeZone, boolean dst) {
        tz = timeZone;
        isDST = dst;
    }

    @Override
    public InternationalTime getTime() {
        Calendar calendar = Calendar.getInstance();
        return new InternationalTime(calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND), tz.hour, tz.minute, isDST);
    }

    /**
     * @return the tz
     */
    public TimeZone getTz() {
        return tz;
    }

    /**
     * @param tz the tz to set
     */
    public void setTz(TimeZone tz) {
        this.tz = tz;
    }

    /**
     * @return the isDST
     */
    public boolean isDST() {
        return isDST;
    }

    /**
     * @param isDST the isDST to set
     */
    public void setIsDST(boolean isDST) {
        this.isDST = isDST;
    }

}
