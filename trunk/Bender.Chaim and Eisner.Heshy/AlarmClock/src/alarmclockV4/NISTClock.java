/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclockV4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.NtpV3Packet;
import org.apache.commons.net.ntp.TimeInfo;

/**
 *
 * @author Heshy
 */
public class NISTClock implements IClock {

    // List of time servers: http://tf.nist.gov/tf-cgi/servers.cgi
    // Do not query time server more than once every 4 seconds
    public static final String[] TIME_SERVERS = {"time.nist.gov", "nist1-ny.ustiming.org",
        "time-c.nist.gov", "wolfnisttime.com", "ntp-nist.ldsbc.edu"};
    int currentServerIndex = 0;
    boolean serverReached = false;
    Properties serverProperty = new Properties();

    public NISTClock() throws FileNotFoundException, IOException {
        serverProperty.load(new FileInputStream("server names.txt"));
    }

    /**
     * if returned Time is null servers were unreachable or IO exception was
     * thrown
     *
     * @return local time
     */
    @Override
    public Time getTime() {
        Time toReturn = null;
        NTPUDPClient timeClient = new NTPUDPClient();
        InetAddress inetAddress;
        for (Object URL : serverProperty.keySet()) {
            try {
                inetAddress = InetAddress.getByName(serverProperty.getProperty((String) URL));
                TimeInfo timeInfo = timeClient.getTime(inetAddress);
                NtpV3Packet message = timeInfo.getMessage();
                long serverTime = message.getTransmitTimeStamp().getTime();
                Date time = new Date(serverTime);
                Calendar cal = Calendar.getInstance();
                cal.setTime(time);
                toReturn = new Time(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
                return toReturn;

            } catch (UnknownHostException ex) {
                    Logger.getLogger(NISTClock.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NISTClock.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return toReturn;
    }

}
