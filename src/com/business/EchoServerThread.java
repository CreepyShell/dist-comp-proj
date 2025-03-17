package com.business;

import com.data.Message;

import java.util.ArrayList;

/**
 * This module is to be used with a concurrent Echo server.
 * Its run method carries out the logic of a client session.
 *
 * @author M. L. Liu
 */

public class EchoServerThread implements Runnable {
    static final String endMessage = ".";
    private final FilesService filesService;
    MyStreamSocket myDataSocket;

    public EchoServerThread(MyStreamSocket myDataSocket, FilesService filesService) {
        this.myDataSocket = myDataSocket;
        this.filesService = filesService;
    }

    public void run() {
        boolean done = false;
        String message;
        try {
            while (!done) {
                message = myDataSocket.receiveMessage();
                System.out.println("message received: " + message);
                if ((message.trim()).equals(endMessage)) {
                    //Session over; close the data socket.
                    System.out.println("Session over.");
                    myDataSocket.close();
                    done = true;
                } else if (message.trim().startsWith("{upload_txt}")) {
                    Message messObj = new Message(message.replace("{upload_txt}", ""));
                    if (filesService.writeInFile(messObj)) {
                        myDataSocket.sendMessage("Wrote in file successfully");
                        continue;
                    }
                } else if (message.trim().startsWith("{get_all_txt}")) {
                    ArrayList<Message> messages = filesService.readMessages();
                    StringBuilder result = new StringBuilder();
                    for (Message m : messages) {
                        result.append(m.toString()).append("|");
                    }
                    myDataSocket.sendMessage(result.toString());
                    continue;
                } else if (message.trim().startsWith("{id}")) {
                    Message messObj = filesService.getMessageById(message.replace("{id}", "").trim());
                    String result = messObj == null ? "Not found" : messObj.toString();
                    myDataSocket.sendMessage(result);
                    continue;
                }
                myDataSocket.sendMessage("Unknown operation");
            }
        } catch (Exception ex) {
            System.out.println("Exception caught in thread: " + ex);
        }
    }
}
