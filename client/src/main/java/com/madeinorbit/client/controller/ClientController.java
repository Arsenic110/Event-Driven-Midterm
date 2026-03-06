package com.madeinorbit.client.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
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
            reply = server.sendAndReceive("HELLO");
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
    public void onSend(Action a, LocalDate d, String t, String r, String m) {

        Request req = Request.fromUI(a, d, t, r, m);

        if (!req.isValid()) {
            view.onError("Request is invalid!");
            return;
        }

        System.out.println("" + req.isValid());

        view.say("Client", req.toString());
        String res = "";
        
        try {
            res = server.sendAndReceive(req.toString());
        } catch (IOException e) {
            view.onError("Cannot communicate with server: " + e);
            server.close();
            return;
        }

        view.say("Server", res);

        if (res.startsWith("TERMINATE")) {
            closeConnection();
            return;
        }

        if (res.startsWith("INCORRECT")) {
            //view.onError("Incorrect Action Exception");
            return;
        }

        if (res.startsWith("OK")) {
            //refreshData(res);
        }
    }

    public void onStop() {
        this.onSend(Action.STOP, LocalDate.now(), "", "", "");
    }

    private void refreshData(String res) {
        List<Lecture> list = new ArrayList<>();
        
        view.onError(res);

        //res.split("")

        /*
        List<Lecture> list = server.getLecturesSorted();

        view.refreshLectures(list);

        */
    }
}
