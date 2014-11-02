package alarmclock4;

import java.net.InetAddress;
import java.util.Date;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.NtpV3Packet;
import org.apache.commons.net.ntp.TimeInfo;

public class TimeLookup {

	// List of time servers: http://tf.nist.gov/tf-cgi/servers.cgi
	// Do not query time server more than once every 4 seconds
	public static final String TIME_SERVER = "nist1-ny.ustiming.org";

	public static void main(String[] args) throws Exception {
		NTPUDPClient timeClient = new NTPUDPClient();
		InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
		TimeInfo timeInfo = timeClient.getTime(inetAddress);
		NtpV3Packet message = timeInfo.getMessage();
		long serverTime = message.getTransmitTimeStamp().getTime();
		Date time = new Date(serverTime);
		System.out.println("Time from " + TIME_SERVER + ": " + time);
	}
}