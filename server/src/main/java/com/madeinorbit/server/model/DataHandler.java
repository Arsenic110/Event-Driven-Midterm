package com.madeinorbit.server.model;

import java.util.ArrayList;

class DataHandler{
    ArrayList<Lecture> lectures = new ArrayList<>();

    private void handleRequest(String requestRaw) throws IncorrectActionException{
        String request[] = requestRaw.split("|"); // or in any other way

        switch(request[0]){
            case "ADD":
                //check for clashes


                lectures.add(new Lecture(request));
                break;
            case "REMOVE":
                //try finding
                break;
            case "DISPLAY":
                for(Lecture l: lectures){
                    //send them l.toString();
                }
                break;
            case "OTHER":
                //no clue what shit
                break;
            default:
                throw new IncorrectActionException("Go and Fuck Yourself");
        }
    }
}