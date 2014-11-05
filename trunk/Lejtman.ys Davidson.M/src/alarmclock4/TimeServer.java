
package alarmclock4;

import java.io.IOException;
import java.net.UnknownHostException;

public interface TimeServer {
   
    public long getServerTimeInMillis() throws IOException, UnknownHostException;
}
