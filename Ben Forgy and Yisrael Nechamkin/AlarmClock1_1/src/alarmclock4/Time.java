package alarmclock4;

public class Time implements ITime{
    
    private int hour, minute, second;
    
    public Time(int hour, int minute, int second){
        if((hour > 23 || hour < 0) 
        || (minute > 59 || minute < 0) 
        || (second > 59 || second < 0))
            throw new IllegalArgumentException("Must be a time.");
        this.hour = hour;
        this.minute = minute;
        this.second = second;

    }

    public Time(ITime that) {
        hour = that.getHour();
        minute = that.getMinute();
        second = that.getSecond();
    }
    
    @Override
    public void setHour(int hour){
        if(hour > 23 || hour < 0)
            throw new IllegalArgumentException("Must be a time.");
        this.hour = hour;
    }
    @Override
    public void setMinute(int minute){
        if(minute > 59 || minute < 0)
            throw new IllegalArgumentException("Must be a time.");
        this.minute = minute;
    }
    @Override
    public void setSecond(int second){
        if(second > 59 || second < 0)
            throw new IllegalArgumentException("Must be a time.");
        this.second = second;
    }
    
    @Override
    public int getHour(){
        return hour;     
    }
    @Override
    public int getMinute(){
        return minute;
    }
    @Override
    public int getSecond(){
        return second;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.hour;
        hash = 71 * hash + this.minute;
        hash = 71 * hash + this.second;
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
        return this.compareTo(other) == 0;
    }
    
    @Override
    public int getTimeInSeconds(){
        int mins = minute + hour * 60;
        return second + mins * 60;
    }

    @Override
    public int compareTo(ITime that) {
        
        int timeInSeconds1 = getTimeInSeconds(),
                timeInSeconds2 = that.getTimeInSeconds();
        
        if(that.getHour() == 23 && timeInSeconds1 < 5){
            timeInSeconds1 += 3600 * 24;
        }
        if(this.getHour() == 23 && timeInSeconds2 < 5){
            timeInSeconds2 += 3600 * 24;
        }
        return timeInSeconds1 - timeInSeconds2;
        
    }


}
