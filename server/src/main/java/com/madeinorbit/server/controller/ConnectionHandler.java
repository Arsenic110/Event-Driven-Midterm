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
        isOpen = true;
    }

    public String receive() throws IOException {
        if(!isOpen){
            throw new IllegalStateException("Fuck off");
        }

        String inputRequest;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        inputRequest = in.readLine();

        return inputRequest;
    }

    public void send(String response)throws IOException{
        if(!isOpen){
            throw new IllegalStateException("Fuck off");
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println(response);
    }

    public void shutdown(){
        //kill server

        isOpen = false;
    }
}