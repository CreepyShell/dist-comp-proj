package com.UI;

import com.business.AuthService;
import com.data.User;

import javax.management.OperationsException;
import javax.swing.*;
import java.awt.*;

public class AuthWindow {
    private final JFrame auth;
    private User currentUser;
    private final AuthService authenticationService;

    public AuthWindow(boolean isLogin, WindowsManager windowsManager, AuthService service) {
        this.authenticationService = service;
        int marginButton = 80;
        String text = "Register";
        if (isLogin) {
            text = "Login";
            marginButton = 0;
        }
        auth = new JFrame(text);
        JPanel panel = new JPanel();
        auth.setResizable(false);
        panel.setLayout(null);

        JLabel label = new JLabel("Please " + text.toLowerCase());
        label.setBounds(150, 20, 400, 35);
        label.setFont(new Font("Verdana", Font.PLAIN, 30));

        JLabel usernameLabel = new JLabel("Enter your username: ");
        usernameLabel.setBounds(50, 80, 200, 21);
        usernameLabel.setFont(new Font("Verdana", Font.PLAIN, 16));

        JTextField usernameTextF = new JTextField(30);
        usernameTextF.setFont(new Font("Verdana", Font.PLAIN, 16));
        usernameTextF.setBounds(240, 80, 250, 25);

        JLabel passwordLabel = new JLabel("Enter your password: ");
        passwordLabel.setBounds(50, 120, 200, 21);
        passwordLabel.setFont(new Font("Verdana", Font.PLAIN, 16));

        JPasswordField passwordField = new JPasswordField(30);
        passwordField.setFont(new Font("Verdana", Font.PLAIN, 16));
        passwordField.setBounds(240, 120, 250, 25);

        JButton authButton = new JButton(text);
        authButton.setBounds(180, 170 + marginButton, 150, 50);
        authButton.setFont(new Font("Times new Roman", Font.PLAIN, 25));
        authButton.addActionListener(l -> {
            String password = String.valueOf(passwordField.getPassword());
            if (isLogin) {
                try {
                    String username = usernameTextF.getText();
                    currentUser = authenticationService.login(password, username);
                    this.closeWindow();
                    windowsManager.setUser(currentUser);
                    windowsManager.openMainMenuWindow(currentUser);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(authButton, ex.getMessage());
                } catch (OperationsException ex) {
                    JOptionPane.showMessageDialog(authButton, "Validation error: " + ex.getMessage());
                }
                return;
            }
            try {
                String username = usernameTextF.getText();
                currentUser = new User(username, password);
                currentUser = authenticationService.register(currentUser.getUsername(), currentUser.getPassword());
                this.closeWindow();
                windowsManager.openMainMenuWindow(currentUser);
                windowsManager.setUser(currentUser);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(authButton, ex.getMessage());
            } catch (OperationsException ex) {
                JOptionPane.showMessageDialog(authButton, "Validation error: " + ex.getMessage());
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(400, 10, 100, 30);
        backButton.setFont(new Font("Times new Roman", Font.PLAIN, 25));
        backButton.addActionListener(l -> {
            this.closeWindow();
            windowsManager.openIntroductionWindow();
        });

        panel.add(label);
        panel.add(usernameLabel);
        panel.add(passwordLabel);
        panel.add(authButton);
        panel.add(backButton);
        panel.add(passwordField);
        panel.add(usernameTextF);

        auth.add(panel);
        auth.setSize(530, 380);
        auth.setLocationRelativeTo(null);
        auth.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        auth.setVisible(true);
    }

    public void closeWindow() {
        auth.setVisible(false);
        auth.dispose();
    }
}