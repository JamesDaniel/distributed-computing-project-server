/**
 * Created by user on 07/11/16.
 */
public class FileTransferManager {
    public LoginVerifier loginVerifier;
    public FileTransferManager(LoginVerifier loginVerifier) {
        this.loginVerifier = loginVerifier;
    }
    public void processFileUploadRequest(DatagramMessage request, MyServerDatagramSocket socket) {
        String uploadingUser = this.loginVerifier.getUsernameFromHost(request);

        try {
            if ( uploadingUser.trim().equals("UnknownUser")) {
                System.out.println("Upload attempted by user who is not logged in. Exiting.");
                socket.sendMessage(request.getAddress(), request.getPort(), "upload request received by server.");
                return;
            }

            System.out.println("User: " + this.loginVerifier.getUsernameFromHost(request) + " made upload request.");

            socket.sendMessage(request.getAddress(), request.getPort(), "upload request received by server.");
            System.out.println("upload request processed.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
