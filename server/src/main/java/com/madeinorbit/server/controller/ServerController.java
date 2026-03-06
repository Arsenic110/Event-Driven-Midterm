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
            view.say("Error while opening: " + e.getMessage());
        }

        view.say("Started");
    }

    public void runLoop() {
        this.start();
        String request, response;

        while(connectionHandler.isOpen){

            try {
                this.connectionHandler.getClient();
            } catch (IOException e) {
                view.say("Error while looking for client: " + e.getMessage());
            }

            while(this.connectionHandler.isConnected){
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

                if(response.equals("TERMINATE")){
                    try {
                        connectionHandler.send(response);
                    } catch (IOException e) {
                        view.say("Couldn't communicate with client: " + e.getMessage());
                    }
                    connectionHandler.endConnection();
                }
                else if(!response.isEmpty()){
                    try {
                        connectionHandler.send(response);
                    } catch (IOException e) {
                        view.say("Couldn't communicate with client " + e.getMessage());
                    }
                }
            }
        }
    }
}