package com.business;

import javax.net.ssl.*;
import java.net.*;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * A wrapper class of Socket which contains
 * methods for sending and receiving messages
 *
 * @author M. L. Liu
 */
public class StreamSocket {
    private SSLSocket socket;
    private BufferedReader input;
    private PrintWriter output;

    StreamSocket(InetAddress acceptorHost, int acceptorPort) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, KeyManagementException {
        SSLSocketFactory sslSocketFactory = getClientSocketContext("truststore.jks").getSocketFactory();
        socket = (SSLSocket) sslSocketFactory.createSocket(acceptorHost, acceptorPort);
        setStreams();
    }

    public StreamSocket(Socket socket) throws IOException {
        this.socket = (SSLSocket) socket;
        setStreams();
    }

    private void setStreams() throws IOException {
        // get an input stream for reading from the data socket
        InputStream inStream = socket.getInputStream();
        input = new BufferedReader(new InputStreamReader(inStream));
        OutputStream outStream = socket.getOutputStream();
        // create a PrinterWriter object for character-mode output
        output = new PrintWriter(new OutputStreamWriter(outStream));
    }

    public void sendMessage(String message)
            throws IOException {
        output.print(message + "\n");
        //The ensuing flush method call is necessary for the data to
        // be written to the socket data stream before the
        // socket is closed.
        output.flush();
    }

    public String receiveMessage() throws IOException {
        return input.readLine();
    }

    public void close() throws IOException {
        socket.close();
    }

    public static SSLContext getClientSocketContext(String key) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, KeyManagementException {
        String truststorePassword = "password";
        // Load Truststore
        KeyStore trustStore = KeyStore.getInstance("JKS");
        try (FileInputStream trustStoreStream = new FileInputStream(key)) {
            trustStore.load(trustStoreStream, truststorePassword.toCharArray());
        }

        // Initialize TrustManagerFactory
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
        trustManagerFactory.init(trustStore);

        // Create SSL Context
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        return sslContext;
    }

    public static SSLContext getServerSocketContext(String key) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException {
        String keystorePassword = "password";
        // Load Truststore
        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (FileInputStream trustStoreStream = new FileInputStream(key)) {
            keyStore.load(trustStoreStream, keystorePassword.toCharArray());
        }

        // Initialize TrustManagerFactory
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, keystorePassword.toCharArray());

        // Create SSL Context
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

        return sslContext;
    }
}
