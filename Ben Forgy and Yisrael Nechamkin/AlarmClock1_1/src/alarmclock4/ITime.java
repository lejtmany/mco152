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
        int seconds = i + getSecond();
        int minutes = seconds /60 + getMinute();
        setHour((getHour() + minutes / 60) % 24);
        setMinute(minutes % 60);
        setSecond(seconds % 60);
        return this;
    }
}
