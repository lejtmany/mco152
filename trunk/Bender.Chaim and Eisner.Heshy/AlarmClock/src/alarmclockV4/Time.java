package alarmclockV4;

public class Time implements Comparable<Time>, Cloneable {

    private int hours, minutes, seconds;

    public Time() {
    }

    public Time(int hours, int minutes, int seconds) {
        // TODO data validation
        setHours(hours);
        setMinutes(minutes);
        setSeconds(seconds);
    }

    /**
     * @return the hours
     */
    public int getHours() {
        return hours;
    }

    /**
     * @param hours the hours to set
     */
    public Time setHours(int hours) {
        this.hours = hours;
        return this;
    }

    /**
     * @return the minutes
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * @param minutes the minutes to set
     */
    public Time setMinutes(int minutes) {
        this.minutes = minutes;
        return this;
    }

    /**
     * @return the seconds
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * @param seconds the seconds to set
     */
    public Time setSeconds(int seconds) {
        this.seconds = seconds;
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.hours;
        hash = 59 * hash + this.minutes;
        hash = 59 * hash + this.seconds;
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
        final Time other = (Time) obj;
        if (this.hours != other.hours) {
            return false;
        }
        if (this.minutes != other.minutes) {
            return false;
        }
        if (this.seconds != other.seconds) {
            return false;
        }
        return true;
    }

    
    @Override
    public int compareTo(Time that) {
        return this.getTotalSeconds() - that.getTotalSeconds();
    }

    /**
     * Number of seconds in the day
     *
     * @return
     */
    public int getTotalSeconds() {
        return ((hours * 60) + minutes) * 60 + seconds;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d %s",
                hours == 0 || hours == 12 ? 12 : hours % 12, minutes, seconds,
                hours < 12 ? "AM" : "PM");
    }

    @Override
    public Object clone() {
        return new Time(this.hours, this.minutes, this.seconds);
    }

    public void addSeconds(int s) {
        seconds += s;
        if (seconds >= 60) {
            seconds %= 60;
            if (++minutes >= 60) {
                minutes %= 60;
                if (++hours >= 24) {
                    hours %= 24;
                }
            }
        }
    }
}
