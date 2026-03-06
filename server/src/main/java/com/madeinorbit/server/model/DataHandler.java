package com.madeinorbit.server.model;

import java.util.ArrayList;

public class DataHandler{
    ArrayList<Lecture> lectures = new ArrayList<>();

    public String handleRequest(String requestRaw) throws IncorrectActionException{
        String request[] = requestRaw.split("|"); // or in any other way

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
                return "OK;ADDED SUCCESSFULLY";
            case "REMOVE":
                Lecture removee = new Lecture(request);

                for (int i = 0; i < lectures.size(); i++) {
                    if (lectures.get(i).compareTo(removee) == 0) {
                        lectures.remove(i);
                        return "OK;SUCCESSFULLY REMOVED";
                    }
                }
                throw new IncorrectActionException("INCORRECT;LECTURE NOT FOUND");
            case "DISPLAY":
                String output = "OK;";
                for (Lecture l : lectures) {
                    output += l.toString() + ";";
                }
                return output;
            case "OTHER":
                return "OK; AND?";
            default:
                throw new IncorrectActionException("INCORRECT;UNKNOWN REQUEST");
        }
        //this should not be reached
    }
}