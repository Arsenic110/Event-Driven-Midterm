package com.madeinorbit.client.model;

import java.time.LocalDate;

public class Lecture {
    LocalDate date;
    String time;
    String room;
    String moduleName;

    private Lecture(String[] info) {
        //eg:
        //{"2026", "3", "6", "09:00-10:00", "AG01", "CS404"}
        
        this.date = LocalDate.parse(info[0] + "-" + info[1] + "-" + info[2]);
        this.time = info[3];
        this.room = info[4];
        this.moduleName = info[5];
    }

    @Override
    public String toString(){
        return date.getYear() + "|" + date.getMonth() + "|" + date.getDayOfMonth() + "|" + room + "|" + moduleName;
    }
    
    public Lecture fromString(String info) {
        //"2026|3|6|09:00-10:00|AG01|CS404"
        
        String[] list = info.split("|");
        
        return new Lecture(list);
    }
    
    public LocalDate getDate() {return this.date;}
    
    public String getTime() {return this.time;}
    
    public String getRoom() {return this.room;}
    
    public String getModule() {return this.moduleName;}
}