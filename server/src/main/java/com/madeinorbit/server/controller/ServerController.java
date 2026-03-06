package com.madeinorbit.server.controller;

import com.madeinorbit.server.model.*;
import com.madeinorbit.server.view.*;

import java.io.IOException;

public class ServerController{
    ConnectionHandler connectionHandler;
    Config config;
    ConsoleView view;
    DataHandler dataHandler;


    public ServerController(){
        config = new Config();
        connectionHandler = new ConnectionHandler();
        dataHandler = new DataHandler();
        view = new ConsoleView();
    }

    void start(){
        try {
            connectionHandler.open(config.port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        view.say("Started");
    }

    public void runLoop(){
        this.start();
        String request, response;

        try {
            this.connectionHandler.getClient();
        } catch (IOException e) {
            //couldnt find a client or smth
        }

        while(this.connectionHandler.isOpen){
            response = "";

            try {
                request = connectionHandler.receive();

                view.say("Received: " + request);
                if(request != null){
                    response = dataHandler.handleRequest(request);
                }
                view.say("Handled successfully");
            } catch (IOException e) {
                view.say("Server not open " + e.getMessage());
            } catch (IncorrectActionException e) {
                try {
                    connectionHandler.send(e.getMessage());
                } catch (IOException ex) {
                    view.say("Couldn't communicate with client " + e.getMessage());
                }
            }


            if(!response.isEmpty()){
                try {
                    connectionHandler.send(response);
                } catch (IOException e) {
                    view.say("Couldn't communicate with client " + e.getMessage());
                }
            }
        }
    }
}