package com.madeinorbit.client.model;

import java.time.LocalDate;

public class Lecture {
    String day;
    String time;
    String room;
    String moduleName;

    private Lecture(String[] info) {
        //eg:
        //{"Monday", "09:00-10:00", "AG01", "CS404"}
        
        this.day = info[0];
        this.time = info[1];
        this.room = info[2];
        this.moduleName = info[3];
    }

    @Override
    public String toString(){
        return day + "|" + time + "|" + room + "|" + moduleName;
    }
    
    public Lecture fromString(String info) {
        //"Monday|09:00-10:00|AG01|CS404"
        
        String[] list = info.split("\\|");
        
        return new Lecture(list);
    }
    
    public String getDay() {return this.day;}
    
    public String getTime() {return this.time;}
    
    public String getRoom() {return this.room;}
    
    public String getModule() {return this.moduleName;}
}