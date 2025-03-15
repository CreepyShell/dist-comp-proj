package com.UI;

import com.business.AuthService;
import com.business.MessageService;
import com.data.User;

public class WindowsManager {
    private static WindowsManager windowsManagerInstance = null;
    private User currentUser;
    private final AuthService authenticationService;
    private IntroductionWindow introductionWindow;
    private AuthWindow authWindow;
    private MainMenu mainMenu;
    private MessageService messageService = new MessageService();

    private WindowsManager(User user, AuthService authenticationService) {
        currentUser = user;
        this.authenticationService = authenticationService;
    }

    public static WindowsManager getInstance(User user, AuthService authenticationService) {
        if (windowsManagerInstance == null)
            windowsManagerInstance = new WindowsManager(user, authenticationService);
        return windowsManagerInstance;
    }

    public void openIntroductionWindow() {
        introductionWindow = new IntroductionWindow(this);
    }

    public void openAuthWindowLogin() {
        authWindow = new AuthWindow(true, this, authenticationService);
    }

    public void openAuthWindowRegister() {
        authWindow = new AuthWindow(false, this, authenticationService);
    }

    public void openMainMenuWindow(User currentUser) {
        mainMenu = new MainMenu(currentUser, this, messageService);
    }

    public void setUser(User user) {
        this.currentUser = user;
    }
}