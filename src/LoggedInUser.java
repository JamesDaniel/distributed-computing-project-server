import java.net.InetAddress;

/**
 * Created by user on 06/11/16.
 */
public class LoggedInUser {
    private String username;
    private String password;
    private InetAddress host;
    private int port;
    public LoggedInUser(String name, String pass, InetAddress host, int port) {
        setName(name);
        setPass(pass);
        setHost(host);
        setPort(port);
    }
    public void setName(String name) {
        this.username = name;
    }
    public String getName() {
        return this.username;
    }
    public void setPass(String pass) {
        this.password = pass;
    }
    public String getPass() {
        return this.password;
    }
    public InetAddress getHost() {
        return host;
    }
    public void setHost(InetAddress host) {
        this.host = host;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
}
