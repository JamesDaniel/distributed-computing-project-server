import java.net.InetAddress;

/**
 * Created by user on 06/11/16.
 */
public class User {
    private String username;
    private String password;
    private InetAddress host;
    private int port;
    private int hash;
    public User(String name, String pass, InetAddress host, int port, int hash) {
        setName(name);
        setPass(pass);
        setHost(host);
        setPort(port);
        setHash(hash);
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
    public void setHash(int hash) {
        this.hash = hash;
    }
    public int getHash() {
        return this.hash;
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
