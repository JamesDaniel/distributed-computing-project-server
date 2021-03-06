/**
 * Created by t00126681 on 25/10/2016.
 */
import java.net.*;
/**
 * A class to use with MyServerDatagramSocket for
 * returning a message and the sender's address
 * @author M. L. Liu
 *
 * Reference:
 * Even though this code is included in my project, this code is not written by myself JamesMcGarr.
 * The author of this code is M. L. Liu
 */
public class DatagramMessage{
    private String message;
    private InetAddress senderAddress;
    private int senderPort;
    public void putVal(String message, InetAddress addr, int port) {
        this.message = message;
        this.senderAddress = addr;
        this.senderPort = port;
    }

    public String getMessage( ) {
        return this.message;
    }

    public InetAddress getAddress( ) {
        return this.senderAddress;
    }

    public int getPort( ) {
        return this.senderPort;
    }
} // end class
