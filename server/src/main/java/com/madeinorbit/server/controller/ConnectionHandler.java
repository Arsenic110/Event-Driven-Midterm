package com.madeinorbit.server.controller;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

class ConnectionHandler{
    ServerSocket serverSocket;
    Socket clientSocket;
    DataOutputStream out;
    DataInputStream in;
    boolean isOpen = false, isConnected = false;


    public void open(int port) throws IOException{
        serverSocket = new ServerSocket(port);
        isOpen = true;
    }

    public void getClient() throws IOException {
        clientSocket = serverSocket.accept();
        isConnected = true;
    }

    public String receive() throws IOException {
        if(!isOpen){
            throw new IllegalStateException("Fuck off");
        }

        in = new DataInputStream(clientSocket.getInputStream());
        return in.readUTF();
    }

    public void send(String response)throws IOException{
        if(!isOpen){
            throw new IllegalStateException("Fuck off");
        }

        out = new DataOutputStream(clientSocket.getOutputStream());
        out.writeUTF(response);
    }

    public void endConnection(){
        isConnected = false;
    }

    public void shutdown(){
        //kill server

        isOpen = false;
    }
}