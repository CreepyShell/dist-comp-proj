import com.business.FilesService;
import com.business.SocketService;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class Server {
    public static void main(String[] args) throws IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException, CertificateException, UnrecoverableKeyException {
        FilesService filesService = new FilesService();
        SocketService socketService = new SocketService(filesService);
        socketService.startServer();
    }
}