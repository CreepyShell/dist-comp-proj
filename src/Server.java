import com.business.MessageService;

public class Server {
    public static void main(String[] args) {
//        AuthService authService = new AuthService();
//        User user = null;
//        WindowsManager manager = WindowsManager.getInstance(user, authService);
//        manager.openIntroductionWindow();

        MessageService messageService = new MessageService();
        messageService.startServer(7);
    }
}