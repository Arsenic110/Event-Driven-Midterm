package com.madeinorbit.client.controller;

import java.io.IOException;
import com.madeinorbit.client.model.*;
import com.madeinorbit.client.view.MainView;

/**
 *
 * @author Kuba Rodak
 */
public class ClientController {
    private Config config;
    private ConnectionManager server;
    private MainView view;

    private boolean connected = false;

    public ClientController(MainView view) throws IOException {
        this.config = Config.loadFromJson("config.json");
        this.server = new ConnectionManager();
        this.view = view;
    }

    public void connectAndHello() {
        view.say("Client", "Connecting...");
        try {
            server.connect(config.host, config.port);
        } catch (IOException e) {
            view.onError("Connection failed: " + e.getMessage());
        }

        String reply;
        
        try {
            reply = server.sendAndReceive("HELLO", 5000);
        } catch (IOException e) {
            view.onError("Cannot communicate with server: " + e);
            server.close();
            return;
        }

        if ("READY".equals(reply)) {
            view.onReady();
        } else {
            view.onError("Unexpected server reply: " + reply);
            server.close();
        }

        connected = true;
    }

    public void closeConnection() {
        view.onDisconnected();
        server.close();
        connected = false;
    }

    //once send button is clicked   
    public void onSend() {
        Request req = Request.fromUI(
            view.getActionBox().getValue(),
            view.getDatePicker().getValue(),
            view.getTimeBox().getValue(),
            view.getRoomField().getText(),
            view.getModuleField().getText()
        );

        if (!req.isValid()) {
            view.onError("Request is invalid!");
        }

        view.say("Client", req.toString());
        String res;
        
        try {
            res = server.sendAndReceive(req.toString(), 5000);
        } catch (IOException e) {
            view.onError("Cannot communicate with server: " + e);
            server.close();
            return;
        }

        view.say("Server", res);

        if (res.startsWith("TERMINATE")) {
            closeConnection();
        }

        if (res.startsWith("OK")) {
            refreshData();
        }
    }

    public void onStop() {

    }

    private void refreshData() {
        //something like:

        /*

        List list = server.getLecturesSorted();

        view.refreshLectures(list);

        */
    }
}
