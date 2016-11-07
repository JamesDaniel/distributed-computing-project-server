import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by user on 06/11/16.
 */
public class LoginVerifier {
    public ArrayList<ValidUser> authorisedUsers = new ArrayList<>();
    public ArrayList<User> currentlyLoggedInUsers = new ArrayList<>();
    public LoginVerifier() {
        authorisedUsers.add(new ValidUser("john","pass"));
    }
    public boolean verifyLogin(DatagramMessage request) {
        String message = request.getMessage();
        String username = message.substring(13,message.indexOf(":password:")).trim();
        String password = message.substring(message.indexOf(":password:")+10,message.length()).trim();

        if (findValidUser(username, password)) {

            System.out.println("Login request received");

            InetAddress host = request.getAddress();
            int port = request.getPort();
            int hashCode =  (username + password).hashCode();
            currentlyLoggedInUsers.add(new User(username, password, host, port, hashCode));
            System.out.println("logged on users: " + currentlyLoggedInUsers.size());
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
    public void handleLoginRequest(DatagramMessage loginRequest, MyServerDatagramSocket socket) {
        try {
            if (this.verifyLogin(loginRequest)) {
                System.out.println("Successful login made.");
                socket.sendMessage(loginRequest.getAddress(), loginRequest.getPort(), "200:123");
            } else {
                System.out.println("Failed login made.");
                socket.sendMessage(loginRequest.getAddress(), loginRequest.getPort(), "201:failure");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void handleLogoutRequest(DatagramMessage logoutRequest, MyServerDatagramSocket socket) {
        String message = logoutRequest.getMessage();
        try {
            String username = message.substring(13,message.indexOf(":password:")).trim();
            int locationInList = -1;
            for (int i = 0; i<currentlyLoggedInUsers.size(); i++) {
                if (currentlyLoggedInUsers.get(i).getName().equals(username)) {
                    locationInList = i;
                    break;
                }
            }
            if (locationInList > -1) {
                currentlyLoggedInUsers.remove(locationInList);
                System.out.println("logout successful.");
            } else {
                System.out.println("logout not successful. user is not logged in yet.");
            }
            socket.sendMessage(logoutRequest.getAddress(), logoutRequest.getPort(), "200:1234");
        }
        catch (Exception ex) {

        }
    }
    public String getUsernameFromHost(DatagramMessage request) {
        InetAddress host = request.getAddress();
        int port = request.getPort();
        for (User user: currentlyLoggedInUsers) {
            if (user.getHost().equals(host) && user.getPort() == port) {
                return user.getName();
            }
        }
        return "UnknownUser";
    }
}
