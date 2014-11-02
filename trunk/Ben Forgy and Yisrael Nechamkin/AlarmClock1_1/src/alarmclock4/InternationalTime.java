package alarmclock4;

import java.util.Objects;

/*
 * @author Ben Forgy
 * @author Nahum Twersky
 * Sep 21, 2014
 */

public class InternationalTime implements ITime {

    private TimeZone tz;
    private boolean isDST;
    private Time TimeUTC;

    public InternationalTime(int hour, int minute, int second,
            int tzHour, int tzMinute, boolean isDST) {
        if (isDST) {
            hour = (hour - 1);
        }
        TimeUTC = new Time(((hour - tzHour) + 24) % 24,
                ((minute - tzMinute) + 60) % 60, second);
        this.isDST = isDST;
        tz = new TimeZone(tzHour, tzMinute);
    }

    public InternationalTime(Time time, TimeZone timeZone, boolean isDST) {
        this(time.getHour(), time.getMinute(), time.getSecond(),
                timeZone.hour, timeZone.minute, isDST);
    }

    public InternationalTime(InternationalTime time) {
        this(time.getLocalTime(), time.tz, time.isDST);
    }

    public Time getLocalTime() {
        int offset = isDST ? 25 : 24;
        return new Time((TimeUTC.getHour() + tz.hour + offset) % 24,
                (TimeUTC.getMinute() + tz.minute + 60) % 60,
                TimeUTC.getSecond());
    }

    public Time getUTCTime() {
        return new Time(TimeUTC);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            InternationalTime that = (InternationalTime) obj;
            return this.getUTCTime().equals(that.getUTCTime())
                    && this.tz.equals(that.tz) && this.isDST == that.isDST;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.tz);
        hash = 97 * hash + (this.isDST ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.TimeUTC);
        return hash;
    }

    @Override
    public void setHour(int hour) {
        if (hour > 23 || hour < 0) {
            throw new IllegalArgumentException("Must be a time.");
        }
        if (isDST) {
            hour -= 1;
        }
        hour -= tz.hour;
        hour = (hour + 24) % 24;
        TimeUTC.setHour(hour);
    }

    @Override
    public void setMinute(int minute) {
        if (minute > 60 || minute < 0) {
            throw new IllegalArgumentException("Must be a time.");
        }
        minute -= tz.minute;
        minute = (minute + 60) % 60;
        TimeUTC.setMinute(minute);
    }

    @Override
    public void setSecond(int second) {
        if (second > 60 || second < 0) {
            throw new IllegalArgumentException("Must be a time.");
        }
        TimeUTC.setSecond(second);
    }
    
    public void setTimeZone(int hour, int min){
        tz = new TimeZone(hour, min);
    }
    public void setTimeZone(TimeZone tz_in){
        tz = new TimeZone(tz_in.hour, tz_in.minute);
    }
    public void setDST(boolean DST){
        isDST = DST;
    }

    @Override
    public int getHour() {
        return (TimeUTC.getHour() + tz.hour + (isDST ? 25 : 24)) % 24;
    }

    @Override
    public int getMinute() {
        return (TimeUTC.getMinute() + tz.minute + 60) % 60;
    }

    @Override
    public int getSecond() {
        return TimeUTC.getSecond();
    }
    
    public TimeZone getTimeZone(){
        return new TimeZone(tz.hour, tz.minute);
    }

    @Override
    public int getTimeInSeconds() {
        return getHour() * 3600 + getMinute() * 60 + getSecond();
    }

    @Override
    public int compareTo(ITime that) {
        int timeInSeconds1 = getTimeInSeconds(),
                timeInSeconds2 = that.getTimeInSeconds();
        
        if (that.getHour() == 23 && timeInSeconds1 < 5) {
            timeInSeconds1 += 3600 * 24;
        }
        if (this.getHour() == 23 && timeInSeconds2 < 5) {
            timeInSeconds2 += 3600 * 24;
        }
        return timeInSeconds1 - timeInSeconds2;
    }

    public int compareToITN(InternationalTime that) {
        return this.getUTCTime().compareTo(that.getUTCTime());
    }
}
