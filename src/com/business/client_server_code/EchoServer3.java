package com.business.client_server_code;

import java.net.*;

/**
 * This module contains the application logic of an echo server
 * which uses a stream-mode socket for interprocess communication.
 * Unlike com.business.client_server_code.EchoServer2, this server services clients concurrently.
 * A command-line argument is required to specify the server port.
 *
 * @author M. L. Liu
 */

public class EchoServer3 {
    public static void main(String[] args) {
        int serverPort = 7;    // default port
        String message;

        if (args.length == 1)
            serverPort = Integer.parseInt(args[0]);
        try {
            ServerSocket myConnectionSocket =
                    new ServerSocket(serverPort);
            System.out.println("Echo server ready.");
            while (true) {
                System.out.println("Waiting for a connection.");
                MyStreamSocket myDataSocket = new MyStreamSocket
                        (myConnectionSocket.accept());
                /**/
                System.out.println("connection accepted");
                // Start a thread to handle this client's session
                Thread theThread =
                        new Thread(new EchoServerThread(myDataSocket));
                theThread.start();
                // and go on to the next client
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
