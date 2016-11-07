import java.net.InetAddress;

/**
 * Created by user on 07/11/16.
 */
public class DataPacket {
    public InetAddress host;
    public int port;
    public byte[] data;
    public DataPacket(InetAddress a, int b, byte[] c) {
        this.host = a;
        this.port = b;
        this.data = c;
    }
}
