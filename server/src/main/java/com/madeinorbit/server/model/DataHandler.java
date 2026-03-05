package com.madeinorbit.server.model;

import java.util.ArrayList;

class DataHandler{
    ArrayList<Lecture> lectures = new ArrayList<>();

    private void handleRequest(String requestRaw) throws IncorrectActionException{
        String request[] = requestRaw.split("|"); // or in any other way

        switch(request[0]){
            case "ADD":
                Lecture addee = new Lecture(request);
                //check for clashes
                for(Lecture l: lectures){
                    if(l.compareTo(addee) > -1){
                        // scream about clash type shii
                        break;
                    }
                }

                lectures.add(addee);
                break;
            case "REMOVE":
                Lecture removee = new Lecture(request);

                for(int i = 0; i < lectures.size(); i++){
                    if(lectures.get(i).compareTo(removee) == 0){
                        lectures.remove(i);
                        // scream about finding and removing
                        break;
                    }
                }
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