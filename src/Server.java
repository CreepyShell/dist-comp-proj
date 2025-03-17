import com.business.FilesService;
import com.business.SocketService;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        FilesService filesService = new FilesService();
        SocketService socketService = new SocketService(filesService);
        socketService.startServer(7);
    }
}