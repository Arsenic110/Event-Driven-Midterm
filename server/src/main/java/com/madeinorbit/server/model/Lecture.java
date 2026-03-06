package com.madeinorbit.server.model;

class Lecture implements Comparable<Lecture> {
    String day;
    String time;
    String room;
    String moduleName;

    public Lecture(String[] info) {
        this.day = info[1];
        this.time = info[2];
        this.room = info[3];
        this.moduleName = info[4];
    }

    @Override
    public String toString(){
        return day + "|" + time + "|" + room + "|" + moduleName;
    }

    @Override // for clash check -1 means no overlap, 0 exact match, 1 overlap
    public int compareTo(Lecture other){
        if(other.day.equals(this.day)){
            if(other.time.equals(this.time)){
                return 0;
            }
        }
        return -1;
    }

    public void setDay(String day) {this.day = day;}


    public void setRoom(String room) {this.room = room;}

    public void setModuleName(String moduleName) {this.moduleName = moduleName;}

    public String getDay() {return this.day;}

    public String getRoom() {return room;}

    public String getModuleName() {return moduleName;}
}