package alarmclock3;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.NtpV3Packet;
import org.apache.commons.net.ntp.TimeInfo;

/**
 *
 * @author Nahum Twersky
 */
public class NISTClock implements IClock {

    Properties prop;
    public String timeServer;
    private final Collection<Object> serverNames;
    private int timeoutPeriod;

    public NISTClock(int timeoutPeriod) {

        this.timeoutPeriod = timeoutPeriod;
        prop = new Properties();
        try {
            prop.load(new FileInputStream("NIST Servers.txt"));
        } catch (IOException ex) {
            Logger.getLogger(NISTClock.class.getName()).log(Level.SEVERE, null, ex);
        }
        serverNames = prop.values();

    }

    /**
     * Loops through NIST servers in .txt file
     *
     * @return NIST or null
     */
    @Override
    public Time getTime() {
        Time nistTime = null;
        Iterator<Object> iterator = serverNames.iterator();
        for (String name = (String) iterator.next(); iterator.hasNext() && nistTime == null; name = (String) iterator.next()) {
            System.out.println("Trying " + name);
            nistTime = retrieveNIST(name.trim(), timeoutPeriod);
        }
        return nistTime;
    }

    /**
     * if server is reachable, will get time. if not, will be null
     *
     * @param serverName
     * @return NIST or null
     */
    private Time retrieveNIST(String serverName, int timeout) {
        Time time = null;
        NTPUDPClient timeClient = new NTPUDPClient();
        try {
            InetAddress inetAddress = InetAddress.getByName(serverName);
            if (inetAddress.isReachable(timeout)) {
                TimeInfo timeInfo = timeClient.getTime(inetAddress);
                NtpV3Packet message = timeInfo.getMessage();
                long serverTime = message.getTransmitTimeStamp().getTime();
                Date d = new Date(serverTime);
                time = new Time(d.getHours(), d.getMinutes(), d.getSeconds());
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(NISTClock.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NISTClock.class.getName()).log(Level.SEVERE, null, ex);
        }
        return time;
    }
}
