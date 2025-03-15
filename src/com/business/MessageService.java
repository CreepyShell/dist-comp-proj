package com.business;

import com.business.client_server_code.EchoClientHelper2;
import com.business.client_server_code.EchoServerThread;
import com.business.client_server_code.MyStreamSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Objects;

public class MessageService {

    public void uploadMessage(String txtMessage) {
        try {
            String hostName = "localhost";
            String portNum = "7";
            boolean done = false;
            while (!done) {
                EchoClientHelper2 helper = new EchoClientHelper2(hostName, portNum);
                String echo = helper.getEcho(txtMessage);
                System.out.println(echo);
                if(Objects.equals(echo, "done")){
                    done = true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String[] getAllMessages() {
        return new String[]{"String1", "String2"};
    }

    public String getMessageById(String id) {
        return "Test message";
    }

    public void startServer(int serverPort) {
        String message;
        try {
            ServerSocket myConnectionSocket =
                    new ServerSocket(serverPort);
            System.out.println("Echo server ready.");
            while (true) {
                System.out.println("Waiting for a connection.");
                MyStreamSocket myDataSocket = new MyStreamSocket(myConnectionSocket.accept());
                System.out.println("connection accepted");

                message = myDataSocket.receiveMessage();
                System.out.println("message received: " + message);

                if(message.startsWith("<all>")){
                    myDataSocket.sendMessage("All you fucking data: ");
                }
                myDataSocket.sendMessage(message);
                Thread theThread = new Thread(new EchoServerThread(myDataSocket));
                theThread.start();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
