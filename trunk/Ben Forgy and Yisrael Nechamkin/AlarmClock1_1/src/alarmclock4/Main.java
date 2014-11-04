package alarmclock4;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ben Forgy
 * @author Yisrael Nechamkin Oct 29, 2014
 */
public class Main {

    public static void main(String[] args) {

        String[] urls;
        urls = new String[]{"nist1-pa.ustiming.org", "time-c.nist.gov",
            "nist1-macon.macon.ga.us", "wolfnisttime.com",
            "nist1-lnk.binary.net"};
        try {
            NISTClock n = new NISTClock("urls.xml");
            new AlarmClock(n, new AlarmPlayer("gong.au", 60));
            new AlarmClock(new Clock(), new AlarmPlayer("gong.au", 60));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
