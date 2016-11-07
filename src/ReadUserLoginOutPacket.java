/**
 * Created by user on 07/11/16.
 */
public class ReadUserLoginOutPacket {
    public static String getUsername(byte[] data) {
        byte[] username = new byte[4];
        System.arraycopy(data, 3, username, 0, username.length);
        System.out.println("name of user is: " + new String(username));
        return new String(username);
    }
    public static String getUserPass(byte[] data) {
        byte[] password = new byte[4];
        System.arraycopy(data, 7, password, 0, password.length);
        System.out.println("user password is: " + new String(password));
        return new String(password);
    }
    public static int getProtocol(byte[] data) {
        byte[] proto = new byte[3];
        System.arraycopy(data, 0, proto, 0, proto.length);
        return Integer.parseInt(new String(proto));
    }
}
