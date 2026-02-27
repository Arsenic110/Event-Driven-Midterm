package com.madeinorbit.client.controller;

import java.io.IOException;
import com.madeinorbit.client.model.Config;
import com.madeinorbit.client.view.MainView;

/**
 *
 * @author Kuba Rodak
 */
public class ClientController {
    private Config config;
    private ConnectionManager conn;
    private MainView view;

    public ClientController() throws IOException {
        this.config = Config.loadFromJson("config.json");
        this.conn = new ConnectionManager();
    }

    public void setView(MainView view) {
        this.view = view;
    }

    public void connectAndHello() {
        try {
            conn.connect(config.host, config.port);
            String reply = conn.sendAndReceive("HELLO", 5000);

            if ("READY".equals(reply)) {
                view.onReady();
            } else {
                view.onError("Unexpected HELLO reply: " + reply);
                conn.close();
            }
        } catch (IOException e) {
            view.onError("Connection failed: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            String reply = conn.sendAndReceive("STOP", 1000);

            if ("TERMINATE".equals(reply)) {
                view.onDisconnected();
                conn.close();
            } else {
                view.onError("Unexpected STOP reply: " + reply);
            }
        } catch (IOException e) {
            view.onError("Failed to send STOP: " + e.getMessage());
        }
    }

    public String sendCommand(String cmd) throws IOException {
        return conn.sendAndReceive(cmd, 1000);
    }
}
