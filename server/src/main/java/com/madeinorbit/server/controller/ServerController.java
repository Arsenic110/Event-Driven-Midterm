package com.madeinorbit.server.controller;

import com.madeinorbit.server.model.*;
import com.madeinorbit.server.view.*;

import javax.swing.plaf.basic.BasicComboBoxUI;
import java.io.IOException;

class ServerController{
    ConnectionHandler connectionHandler;
    Config config;
    ConsoleView view;
    DataHandler dataHandler;


    public ServerController(){
        config = new Config();
        connectionHandler = new ConnectionHandler();
        view = new ConsoleView();
    }

    public void start(){
        try {
            connectionHandler.open(config.port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        view.say("Started");
    }

    void runLoop(){
        this.start();
        String request;

        while(this.connectionHandler.isOpen){
            try {
                request = connectionHandler.receive();

                view.say("Received: " + request);
                dataHandler.handleRequest(request);
                view.say("Handled successfully");
            } catch (IOException e) {
                view.say("Server not open " + e.getMessage());
            } catch (IncorrectActionException e) {
                //send
            }


        }
    }
}