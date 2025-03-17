import com.UI.WindowsManager;
import com.business.AuthService;
import com.business.FilesService;
import com.business.SocketService;
import com.data.User;

public class Client {
    public static void main(String[] args) {
        FilesService filesService = new FilesService();
        SocketService socketService = new SocketService(filesService);
        AuthService authService = new AuthService();
        User user = null;
        WindowsManager manager = WindowsManager.getInstance(user, authService, socketService);
        manager.openIntroductionWindow();
    }
}