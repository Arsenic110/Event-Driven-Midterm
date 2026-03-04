package com.madeinorbit.client.controller;

import com.madeinorbit.client.model.Request;
import java.io.*;
import java.net.*;

/**
 *
 * @author Kuba Rodak
 */
public class ConnectionManager implements AutoCloseable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean connected = false;

    public void connect(String host, int port) throws IOException, ConnectException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        connected = true;
    }

    public String sendAndReceive(String message, long timeoutMs) throws IOException {
        if (!connected)
            throw new IllegalStateException("Not Connected");

        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < timeoutMs) {
            if (in.ready()) {
                return in.readLine();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
                //handle later idk
            }
        }
        throw new SocketTimeoutException("Timeout while waiting for a reply...");
    }

    @Override
    public void close() {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                //unknown state
            }
        }
        connected = false;
    }
}
