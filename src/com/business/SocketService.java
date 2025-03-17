package com.business;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class SocketService {
    private final InetAddress serverHost = InetAddress.getLocalHost();
    private final int serverPort = 7;
    private static final String endMessage = ".";
    private final FilesService filesService;

    public SocketService(FilesService filesService) throws IOException {
        this.filesService = filesService;
    }

    public String sendMessage(String txtMessage) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        //Instantiates a stream-mode socket and wait for a connection.
        StreamSocket mySocket = new StreamSocket(serverHost, serverPort);
        mySocket.sendMessage(txtMessage);
        String echo = mySocket.receiveMessage();
        System.out.println("echo: " + echo);
        mySocket.sendMessage(endMessage);
        mySocket.close();
        return echo;
    }

    public void startServer() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, CertificateException, IOException, UnrecoverableKeyException {
        SSLServerSocketFactory sslServerSocketFactory = StreamSocket.getServerSocketContext("herong.jks").getServerSocketFactory();
        try (SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(serverPort, 50, serverHost)) {
            System.out.println("Echo server ready.");
            while (true) {
                System.out.println("Waiting for a connection.");
                SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
                System.out.println("connection accepted");

                Thread theThread = new Thread(new EchoServerThread(new StreamSocket(sslSocket), filesService));
                theThread.start();
            }
        } catch (Exception ex) {
            System.out.println("Server error: " + ex.getMessage());
        }
    }
}
