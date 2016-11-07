import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by user on 06/11/16.
 */
public class LoginVerifier {
    public ArrayList<ValidUser> authorisedUsers = new ArrayList<>();
    public ArrayList<LoggedInUser> currentlyLoggedInLoggedInUsers = new ArrayList<>();
    public LoginVerifier() {
        authorisedUsers.add(new ValidUser("john","pass"));
    }
    public boolean verifyLogin(DataPacket packet, MyServerDatagramSocket socket) {
        byte[] data = packet.data;
        String username = ReadUserLoginOutPacket.getUsername(data);
        String password = ReadUserLoginOutPacket.getUserPass(data);

        if (findValidUser(username, password)) {

            System.out.println("Login request received");

            InetAddress host = packet.host;
            int port = packet.port;
            currentlyLoggedInLoggedInUsers.add(new LoggedInUser(username, password, host, port));
            System.out.println("logged on users: " + currentlyLoggedInLoggedInUsers.size());
            return true;
        }
        return false;
    }
    private boolean findValidUser(String username, String password) {
        for (ValidUser user: authorisedUsers) {
            if (user.username.equals(username) &&
                user.password.equals(password)) {
                return true;
            }
        }
        return false;
    }
    public void handleLoginRequest(DataPacket dataReceived, MyServerDatagramSocket socket) {
        try {
            if (this.verifyLogin(dataReceived, socket)) {
                System.out.println("Successful login made.");
                socket.sendMessage(dataReceived.host, dataReceived.port, "200:123");
            } else {
                System.out.println("Failed login made.");
                socket.sendMessage(dataReceived.host, dataReceived.port, "201:failure");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void handleLogoutRequest(DataPacket packet, MyServerDatagramSocket socket) {
        byte[] data = packet.data;
        String username = ReadUserLoginOutPacket.getUsername(data);
        String password = ReadUserLoginOutPacket.getUserPass(data);

        try {
            int locationInList = -1;
            for (int i = 0; i< currentlyLoggedInLoggedInUsers.size(); i++) {
                if (currentlyLoggedInLoggedInUsers.get(i).getName().equals(username)) {
                    locationInList = i;
                    break;
                }
            }
            if (locationInList > -1) {
                currentlyLoggedInLoggedInUsers.remove(locationInList);
                System.out.println("logout successful.");
            } else {
                System.out.println("logout not successful. user is not logged in yet.");
            }
            socket.sendMessage(packet.host, packet.port, "500");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public String getUsernameFromHost(DataPacket packet) {
        InetAddress host = packet.host;
        for (LoggedInUser loggedInUser : currentlyLoggedInLoggedInUsers) {
            if (loggedInUser.getHost().equals(host)) {
                return loggedInUser.getName();
            }
        }
        return "UnknownUser";
    }
}
