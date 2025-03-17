import com.business.FilesService;
import com.business.SocketService;

public class Server {
    public static void main(String[] args) {
        FilesService filesService = new FilesService();
        SocketService socketService = new SocketService(filesService);
        socketService.startServer(7);
    }
}