package alarmclock4;
/*
 * Ben Forgy
 * Oct 24, 2014
 */

public class TimeZone {

    public final int hour, minute;

    public TimeZone(int tzHour, int tzMinute) {
        if (tzHour < -12 || tzHour > 14) {
            throw new IllegalArgumentException(
                    "The time-zone hour must be between -12 and 14.");
        }
        if (tzMinute != 0 && tzMinute != -30 && tzMinute != 30 
                                                    && tzMinute != 45) {
            throw new IllegalArgumentException(
                    "The time-zone minute must be -30, 0, 30, or 45.");
        }
        hour = tzHour;
        minute = tzMinute;
    }
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + this.hour;
        hash = 19 * hash + this.minute;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TimeZone other = (TimeZone) obj;
        if (this.hour != other.hour) {
            return false;
        }
        if (this.minute != other.minute) {
            return false;
        }
        return true;
    }
}
