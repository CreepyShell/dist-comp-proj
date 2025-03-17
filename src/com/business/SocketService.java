package com.business;

import java.net.ServerSocket;

public class SocketService {
    private final FilesService filesService;

    public SocketService(FilesService filesService) {
        this.filesService = filesService;
    }

    public String sendMessage(String txtMessage, int portNum) {
        try {
            String hostName = "localhost";
            EchoClientHelper2 helper = new EchoClientHelper2(hostName, portNum);
            String echo = helper.getEcho(txtMessage);
            System.out.println("echo: " + echo);
            helper.done();
            return echo;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "Error occurred";
    }

    public void startServer(int serverPort) {
        try {
            ServerSocket myConnectionSocket = new ServerSocket(serverPort);
            System.out.println("Echo server ready.");
            while (true) {
                System.out.println("Waiting for a connection.");
                MyStreamSocket myDataSocket = new MyStreamSocket(myConnectionSocket.accept());
                System.out.println("connection accepted");

                Thread theThread = new Thread(new EchoServerThread(myDataSocket, filesService));
                theThread.start();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
