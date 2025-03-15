package com.UI;

import com.business.MessageService;
import com.data.User;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private final WindowsManager windowsManager;
    private final User currentUser;

    public MainMenu(User user, WindowsManager windowsManager, MessageService messageService) {
        this.windowsManager = windowsManager;
        this.currentUser = user;
        this.setTitle("com.business.client_server_code.Main menu");
        JPanel panel = new JPanel();
        this.setResizable(false);
        panel.setLayout(null);

        JLabel label = new JLabel("Welcome to the TCP client, " + currentUser.getUsername() + "!");
        label.setBounds(80, 20, 400, 21);
        label.setFont(new Font("Verdana", Font.BOLD, 18));

        JButton logOutButton = new JButton("LogOut");
        logOutButton.setBounds(80, 400, 120, 28);
        logOutButton.setFont(new Font("Times new Roman", Font.PLAIN, 25));
        logOutButton.addActionListener(l -> {
            this.closeWindow();
            this.windowsManager.openIntroductionWindow();
            this.windowsManager.setUser(null);
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(240, 400, 100, 28);
        exitButton.setFont(new Font("Times new Roman", Font.PLAIN, 25));
        exitButton.addActionListener(l -> {
            System.exit(0);
        });

        JLabel uploadMessageLabel = new JLabel("<html>Please enter the message to upload to the server<br>or message ID to find from the server</html>");
        uploadMessageLabel.setBounds(50, 60, 450, 40);
        uploadMessageLabel.setFont(new Font("Verdana", Font.PLAIN, 15));

        JTextField messageTxt = new JTextField(30);
        messageTxt.setFont(new Font("Verdana", Font.PLAIN, 16));
        messageTxt.setBounds(100, 110, 300, 25);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JButton searchMessage = new JButton("Search message");
        searchMessage.setBounds(100, 140, 300, 25);
        searchMessage.setFont(new Font("Times new Roman", Font.PLAIN, 18));
        searchMessage.addActionListener(l -> {
            try {
                listModel.clear();
                String message = messageService.getMessageById(messageTxt.getText());
                listModel.addElement(message);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(searchMessage, "Smt went wrong: " + ex.getMessage());
            }
        });

        JButton uploadTxt = new JButton("Upload message");
        uploadTxt.setBounds(100, 190, 300, 25);
        uploadTxt.setFont(new Font("Times new Roman", Font.PLAIN, 18));
        uploadTxt.addActionListener(l -> {
            String txtMessage = messageTxt.getText();
            if (txtMessage.length() < 3) {
                JOptionPane.showMessageDialog(uploadTxt, "Validation error: message is less than 3 characters");
                try {
                    messageService.uploadMessage(txtMessage);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(uploadTxt, "Validation error: " + ex.getMessage());
                }
            }
        });

        JList<String> messageList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(messageList);
        scrollPane.setBounds(50, 270, 450, 120);
        scrollPane.setFont(new Font("Verdana", Font.PLAIN, 15));

        JButton messagesButton = new JButton("Get all messages from the server");
        messagesButton.setBounds(100, 220, 350, 25);
        messagesButton.setFont(new Font("Times new Roman", Font.PLAIN, 18));
        messagesButton.addActionListener(l -> {
            try {
                listModel.clear();
                String[] messages = messageService.getAllMessages();
                for (String message : messages) {
                    listModel.addElement(message);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(uploadTxt, "Validation error: " + ex.getMessage());
            }

        });

        panel.add(label);
        panel.add(exitButton);
        panel.add(logOutButton);
        panel.add(messageTxt);
        panel.add(uploadMessageLabel);
        panel.add(messagesButton);
        panel.add(uploadTxt);
        panel.add(scrollPane);
        panel.add(searchMessage);
        this.add(panel);
        this.setSize(530, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void closeWindow() {
        this.setVisible(false);
        this.dispose();
    }
}