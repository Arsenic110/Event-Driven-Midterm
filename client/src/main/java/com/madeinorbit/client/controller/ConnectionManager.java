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
    private DataInputStream in;
    private DataOutputStream out;
    private boolean connected = false;

    public void connect(String host, int port) throws IOException, ConnectException {
        socket = new Socket(host, port);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        connected = true;
    }

    public String sendAndReceive(String message) throws IOException {
        if (!connected)
            throw new IllegalStateException("Not Connected");
        
        out.writeUTF(message);
        
        return in.readUTF();
    }

    @Override
    public void close() {
        if (socket != null && !socket.isClosed()) {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                //unknown state
            }
        }
        connected = false;
    }
}
