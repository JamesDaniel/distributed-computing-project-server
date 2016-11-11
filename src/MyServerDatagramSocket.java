/**
 * Created by t00126681 on 25/10/2016.
 */
import java.net.*;
import java.io.*;
import java.util.Date;

/**
 * A subclass of DatagramSocket which contains
 * methods for sending and receiving messages
 * @author M. L. Liu
 *
 * Reference:
 * Even though this code is included in my project, this code is not written by myself JamesMcGarr.
 * The author of this code is M. L. Liu
 */

public class MyServerDatagramSocket extends DatagramSocket {
    static final int MAX_LEN = 100;
    MyServerDatagramSocket(int portNo) throws SocketException{
        super(portNo);
    }
    public void sendMessage(InetAddress receiverHost,
                            int receiverPort,
                            String message)
            throws IOException {
        byte[ ] sendBuffer = message.getBytes( );
        DatagramPacket datagram =
                new DatagramPacket(sendBuffer, sendBuffer.length,
                        receiverHost, receiverPort);
        this.send(datagram);
    } // end sendMessage

    public DataPacket receivePacketAndSender( ) throws IOException {
        byte[ ] receiveBuffer = new byte[MAX_LEN];
        DatagramPacket datagram = new DatagramPacket(receiveBuffer, MAX_LEN);
        this.receive(datagram);
        return new DataPacket(datagram.getAddress(),
                              datagram.getPort(),
                              datagram.getData());
    }

    public DatagramMessage receiveMessageAndSender( )
            throws IOException {
        byte[ ] receiveBuffer = new byte[MAX_LEN];
        DatagramPacket datagram =
                new DatagramPacket(receiveBuffer, MAX_LEN);
        this.receive(datagram);
        // create a DatagramMessage object, to contain message
        //   received and sender's address
        DatagramMessage returnVal = new DatagramMessage( );
        returnVal.putVal(new String(receiveBuffer),
                datagram.getAddress( ),
                datagram.getPort( ));
        return returnVal;
    }
    public void sendFile(InetAddress receiverHost,
                         int receiverPort,
                         byte[] file) throws IOException {
        DatagramPacket datagram = new DatagramPacket(file, file.length, receiverHost, receiverPort);
        this.send(datagram);
    }
} //end class
