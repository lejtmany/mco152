

package alarmclock4;

import static alarmclock4.NISTClock.TIME_SERVER;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.NtpV3Packet;
import org.apache.commons.net.ntp.TimeInfo;

public class NISTServer implements TimeServer{

    @Override
    public long getServerTimeInMillis() throws IOException, UnknownHostException {
        long serverTime; 
        NTPUDPClient timeClient = new NTPUDPClient();
        InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
        TimeInfo timeInfo = timeClient.getTime(inetAddress); 
        NtpV3Packet message = timeInfo.getMessage();
        return serverTime = message.getTransmitTimeStamp().getTime(); 
    }
    
}
