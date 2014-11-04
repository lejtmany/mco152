package alarmclock4;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.NtpV3Packet;
import org.apache.commons.net.ntp.TimeInfo;

public class NISTClock implements IClock {
    
    private final Properties properties;
    private final String fileName;
    private int index;
    private long lastQuery = System.currentTimeMillis();
    private ITime lastTime = null;
    
    public NISTClock(String[] urls, String fileName) throws IOException {
        this.fileName = fileName;
        properties = new Properties();
        for (int i = 0; i < urls.length; i++) {
            properties.setProperty("url" + i, urls[i]);
        }
        OutputStream out = new FileOutputStream(fileName);
        properties.storeToXML(out, null);
    }
    
    public NISTClock(String fileName) throws IOException {
        this.fileName = fileName;
        properties = new Properties();
        InputStream in = new FileInputStream(fileName);
        properties.loadFromXML(in);
    }
    
    public void addUrl(String url) throws IOException {
        properties.setProperty("url" + properties.size(), url);
        properties.storeToXML(new FileOutputStream(fileName), null);
    }
    
    @Override
    public ITime getTime() {
        int difference = (int) (System.currentTimeMillis() - lastQuery);
        if (difference < 10000 && lastTime != null) {
            return new Time(lastTime).addSeconds((difference + 500) / 1000);
        }
        NTPUDPClient timeClient = new NTPUDPClient();
        InetAddress inetAddress;
        TimeInfo timeInfo = null;
        boolean retrievedTime = false;
        for (Iterator it = properties.values().iterator(); it.hasNext() && !retrievedTime;) {
            Object url = it.next();
            try {
                inetAddress = InetAddress.getByName((String) url);
                timeInfo = timeClient.getTime(inetAddress);
                retrievedTime = true;
            } catch (IOException ex) {
                if (!it.hasNext()) {
                    throw new UnableToConnectException(ex);
                }
                Logger.getLogger(NISTClock.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        NtpV3Packet message = timeInfo.getMessage();
        long serverTime = message.getTransmitTimeStamp().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(serverTime);
        lastTime = new Time(calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
        lastQuery = System.currentTimeMillis();
        return lastTime;
    }
    
    public class UnableToConnectException extends RuntimeException {

        UnableToConnectException(Throwable t) {
            super(t);
        }
    }
}
