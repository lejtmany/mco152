package alarmclock4;
/*
 * Ben Forgy
 * Oct 27, 2014
 */

public interface ITime {

    public void setHour(int hour);

    public void setMinute(int minute);

    public void setSecond(int second);

    public int getHour();

    public int getMinute();

    public int getSecond();

    public int getTimeInSeconds();

    public int compareTo(ITime other);

    public default ITime addSeconds(int i) {
        setHour((getHour() + i / 3600) % 24);
        setMinute((getMinute() + i / 60) % 60);
        setSecond((getSecond() + i) % 60);
        return this;
    }
}
