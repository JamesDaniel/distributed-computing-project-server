
/**
 * This module contains the application logic of an echo server
 * which uses a connectionless datagram socket for interprocess
 * communication.
 * A command-line argument is required to specify the server port.
 * @author M. L. Liu
 */

public class LoginServer {
    public static void main(String[] args) {
        int serverPort = 3001;

        try
        {
            MyServerDatagramSocket mySocket = new MyServerDatagramSocket(serverPort);
            System.out.println("Login server ready.");
            while (true)
            {
                DatagramMessage request = mySocket.receiveMessageAndSender();

                String message = request.getMessage();
                System.out.println("Login request received");

                String username = message.substring(13,message.indexOf(":password:"));
                String password = message.substring(message.indexOf(":password:")+10,message.length());

                if (username.trim().equals("john") && password.trim().equals("pass"))
                {
                    System.out.println("Successful login made.");
                    int hashCode =  (username + password).hashCode();
                    mySocket.sendMessage(request.getAddress(), request.getPort(), "200:" + hashCode);
                }
                else
                {
                    System.out.println("Failed login made.");
                    mySocket.sendMessage(request.getAddress(), request.getPort(), "201:failure");
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace( );
        }
    }
}
