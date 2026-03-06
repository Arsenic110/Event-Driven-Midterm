package com.madeinorbit.server.controller;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;

class ConnectionHandler{
    ServerSocket serverSocket;
    Socket clientSocket;
    DataOutputStream out;
    DataInputStream in;
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
        clientSocket = serverSocket.accept();
        in = new DataInputStream(clientSocket.getInputStream());
        inputRequest = in.readUTF();

        return inputRequest;
    }

    public void send(String response)throws IOException{
        if(!isOpen){
            throw new IllegalStateException("Fuck off");
        }

        out = new DataOutputStream(clientSocket.getOutputStream());
        out.writeUTF(response);
    }

    public void shutdown(){
        //kill server

        isOpen = false;
    }
}