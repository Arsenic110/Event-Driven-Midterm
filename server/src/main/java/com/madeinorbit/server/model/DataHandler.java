package com.madeinorbit.server.model;

import java.util.ArrayList;

public class DataHandler{
    ArrayList<Lecture> lectures = new ArrayList<>();

    public String handleRequest(String requestRaw) throws IncorrectActionException{
        if(requestRaw.equals("HELLO")){
            return "READY";
        }

        String request[] = requestRaw.split("\\|"); // or in any other way
        String output = "OK;";

        switch(request[0]) {
            case "ADD":
                Lecture addee = new Lecture(request);
                //check for clashes
                for (Lecture l : lectures) {
                    if (l.compareTo(addee) > -1) {
                        throw new IncorrectActionException("INCORRECT;CLASH DETECTED");
                    }
                }

                lectures.add(addee);
                output =  "OK ADDED SUCCESSFULLY;";
                appendLectures(output);
                return output;
            case "REMOVE":
                Lecture removee = new Lecture(request);

                for (int i = 0; i < lectures.size(); i++) {
                    if (lectures.get(i).compareTo(removee) == 0) {
                        lectures.remove(i);
                        output = "OK SUCCESSFULLY REMOVED;";
                        appendLectures(output);
                        return output;
                    }
                }
                throw new IncorrectActionException("INCORRECT;LECTURE NOT FOUND");
            case "DISPLAY":
                output = "OK;";
                appendLectures(output);
                return output;
            case "OTHER":
                // placeholder for future stuff
                return "OK;";
            case "STOP":
                return "TERMINATE";
            default:
                throw new IncorrectActionException("INCORRECT;UNKNOWN REQUEST");
        }
        //this should not be reached
    }

    private void appendLectures(String response){
        for(Lecture l: lectures){
            response += l.toString() + ";";
        }
    }
}