package alarmclock4;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.NtpV3Packet;
import org.apache.commons.net.ntp.TimeInfo;


public class NISTClock implements IClock{

   // public static final String TIME_SERVER = "nist1-ny.ustiming.org";
    public static final String TIME_SERVER = "time.nist.gov";
    //counter to ensure NIST not queried more than every four seconds
    private int syncCounter = 0;
    private Time lastTime;
    private Timer timer = new Timer();
    private TimeServer server;
    
    public NISTClock(TimeServer server){
        super();
        this.server = server;
        Calendar cal = Calendar.getInstance();
        lastTime = new Time(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
        timer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    lastTime.addSeconds(1);
                }
            }, 0 , 1000);
       
    }
    
               
    @Override
    public Time getTime() {
        Time time = new Time();
        if(syncCounter % 5 == 0){
            try {  
                time = getTimeFromServer();
            } catch (IOException ex) {
                time = lastTime;
                System.out.println("Couldn't get time from NIST Server at " + new Date(System.currentTimeMillis()).toString());
            }
            lastTime = time;
        }           
        else
            time = lastTime;
        syncCounter++;
        return time;
    }
    
    private Time dateToTime(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new Time(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
    }
    
    private Time getTimeFromServer() throws IOException{
        // List of time servers: http://tf.nist.gov/tf-cgi/servers.cgi
	// Do not query time server more than once every 4 seconds        
        Date time;       
        long serverTime;
       // try {
        serverTime = server.getServerTimeInMillis();
       // } catch (Exception ex) { 
        //    return lastTime; 
       // }
        time = new Date(serverTime);      
        return dateToTime(time);
    }
       
}


	
