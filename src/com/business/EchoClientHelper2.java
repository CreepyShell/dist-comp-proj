package com.business;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * This class is a module which provides the application logic
 * for an Echo client using stream-mode socket.
 *
 * @author M. L. Liu
 */

public class EchoClientHelper2 {

    static final String endMessage = ".";
    private MyStreamSocket mySocket;
    private InetAddress serverHost;
    private int serverPort;

    public EchoClientHelper2(String hostName, int portNum) throws IOException {
        this.serverHost = InetAddress.getByName(hostName);
        this.serverPort = portNum;
        //Instantiates a stream-mode socket and wait for a connection.
        this.mySocket = new MyStreamSocket(this.serverHost, this.serverPort);
        System.out.println("Connection request made");
    }

    public String getEcho(String message) throws IOException {
        String echo = "";
        mySocket.sendMessage(message);
        // now receive the echo
        echo = mySocket.receiveMessage();
        return echo;
    }

    public void done() throws IOException {
        mySocket.sendMessage(endMessage);
        mySocket.close();
    }
}
