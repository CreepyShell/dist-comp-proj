import com.business.FilesService;
import com.business.SocketService;

public class Server {
    public static void main(String[] args) {
        try {
            FilesService filesService = new FilesService();
            SocketService socketService = new SocketService(filesService);
            socketService.startServer();
        } catch (Exception ex) {
            System.out.println("Exception occurred, please review: " + ex.getMessage());
        }
    }
}