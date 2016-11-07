import java.io.File;
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
    public void processFileDownloadRequest(DataPacket packet, MyServerDatagramSocket socket, LoginVerifier verifyLogin) {
        String downloadingUser = this.loginVerifier.getUsernameFromHost(packet);

        try {
            if (downloadingUser.trim().equals("UnknownUser")) {
                System.out.println("Download attempted by user who is not logged in. Exiting.");
                socket.sendMessage(packet.host, packet.port, "download request received by server.");
                return;
            }
            String user = this.loginVerifier.getUsernameFromHost(packet);
            System.out.println("User: " + user + " made download request.");

            sendFileToUser(packet,socket,verifyLogin);
            //socket.sendMessage(packet.host, packet.port, "upload request received by server.");
            System.out.println("upload request processed.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void sendFileToUser(DataPacket packet, MyServerDatagramSocket socket, LoginVerifier verifyLogin) {
        String fileName = ReadFilePacket.getFileName(packet.data);
        String requester = verifyLogin.getUsernameFromHost(packet);

        try {
            byte[] data = FileSystemUtils.getBytesFromPath(requester + "/" + fileName.trim());
            int length = data.length;
            byte[] bytesForPacket = PackageFilePacket.packagedPacket("500",length,fileName,data);
            socket.sendFile(packet.host, packet.port, bytesForPacket);
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
