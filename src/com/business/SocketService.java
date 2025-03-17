package com.business;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class SocketService {
    private StreamSocket mySocket;
    private final InetAddress serverHost = InetAddress.getLocalHost();
    private final int serverPort = 7;
    private static final String endMessage = ".";
    private final FilesService filesService;

    public SocketService(FilesService filesService) throws IOException {
        this.filesService = filesService;
    }

    public String sendMessage(String txtMessage) throws IOException {
        //Instantiates a stream-mode socket and wait for a connection.
        mySocket = new StreamSocket(serverHost, serverPort);
        mySocket.sendMessage(txtMessage);
        String echo = mySocket.receiveMessage();
        System.out.println("echo: " + echo);
        mySocket.sendMessage(endMessage);
        mySocket.close();
        return echo;
    }

    public void startServer(int serverPort) {
        try (ServerSocket myConnectionSocket = new ServerSocket(serverPort)) {
            System.out.println("Echo server ready.");
            while (true) {
                System.out.println("Waiting for a connection.");
                StreamSocket myDataSocket = new StreamSocket(myConnectionSocket.accept());
                System.out.println("connection accepted");

                Thread theThread = new Thread(new EchoServerThread(myDataSocket, filesService));
                theThread.start();
            }
        } catch (Exception ex) {
            System.out.println("Server error: " + ex.getMessage());
        }
    }
}
