import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by user on 07/11/16.
 */
public class FileTransferManager {
    public LoginVerifier loginVerifier;
    public FileTransferManager(LoginVerifier loginVerifier) {
        this.loginVerifier = loginVerifier;
    }
    public void processFileUploadRequest(DataPacket packet, MyServerDatagramSocket socket) {
        String uploadingUser = this.loginVerifier.getUsernameFromHost(packet);

        try {
            if ( uploadingUser.trim().equals("UnknownUser")) {
                System.out.println("Upload attempted by user who is not logged in. Exiting.");
                socket.sendMessage(packet.host, packet.port, "upload request received by server.");
                return;
            }
            String user = this.loginVerifier.getUsernameFromHost(packet);
            System.out.println("User: " + user + " made upload request.");

            String fileName = ReadFilePacket.getFileName(packet.data);
            byte[] fileData = ReadFilePacket.getFileContent(packet.data);
            createFileFromNameAndBytes(user,fileName,fileData);
            socket.sendMessage(packet.host, packet.port, "upload request received by server.");
            System.out.println("upload request processed.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void createFileFromNameAndBytes(String user, String name, byte[] bytes) throws IOException {
        new File(user).mkdir();
        Path path = Paths.get(user + "/" + name);
        Files.write(path,bytes);
    }
}
