package com.madeinorbit.server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;

class ConnectionHandler{
    ServerSocket serverSocket;
    Socket clientSocket;
    PrintWriter out;
    BufferedReader in;
    boolean isOpen = false;


    public void open(int port) throws IOException{
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        isOpen = true;
    }

    public void sendAndReceive() throws IOException {
        if(!isOpen){
            throw new IllegalStateException("Fuck off");
        }

        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
}