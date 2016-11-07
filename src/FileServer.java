import java.util.ArrayList;

/**
 * This module contains the application logic of an echo server
 * which uses a connectionless datagram socket for interprocess
 * communication.
 * A command-line argument is required to specify the server port.
 * @author M. L. Liu
 */

public class FileServer {
    public static void main(String[] args) {
        int serverPort = 3001;

        ArrayList<LoggedInUser> currentLoggedInUsers = new ArrayList<>();

        try
        {
            MyServerDatagramSocket mySocket = new MyServerDatagramSocket(serverPort);
            System.out.println("Login server ready.");


            LoginVerifier login = new LoginVerifier();
            FileTransferManager fileTransfer = new FileTransferManager(login);
            while (true)
            {
                //DatagramMessage request = mySocket.receiveMessageAndSender();
                DataPacket receivedPacket = mySocket.receivePacketAndSender();

                String protocol = ReadFilePacket.getProtocol(receivedPacket.data);
                int requestType = Integer.parseInt(protocol);
                System.out.println("Request type: " + requestType);

                switch (requestType) {
                    case 100:
                        System.out.println("Log-on request made.");
                        login.handleLoginRequest(receivedPacket, mySocket);
                        break;
                    case 200:
                        System.out.println("File Upload request made.");
                        //fileTransfer.processFileUploadRequest(request, mySocket);
                        break;
                    case 300:
                        System.out.println("File Download request made.");
                        break;
                    case 400:
                        System.out.println("Log-off request made.");
                        login.handleLogoutRequest(receivedPacket, mySocket);
                        break;
                    default:
                        System.out.println("Unrecognised request type made.");
                }


            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace( );
        }
    }
}
