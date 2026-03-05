package com.madeinorbit.server.model;

class Lecture implements Comparable<Lecture> {
    String day;
    int start;
    int end;
    String room;
    String moduleName;

    public Lecture(String[] info) {
        this.day = info[1];
        this.start = Integer.parseInt(info[2]);
        this.end = Integer.parseInt(info[3]);
        this.room = info[4];
        this.moduleName = info[5];
    }

    @Override
    public String toString(){
        return day + "|" + start + "|" + end + "|" + room + "|" + moduleName;
    }

    @Override // for clash check -1 means no overlap, 0 exact match, 1 overlap
    public int compareTo(Lecture other){
        if(other.day.equals(this.day)){
            if(other.end <= this.start || this.end <= other.start){
                return -1;
            }
            else if(other.start == this.start && other.end == this.end){
                return 0;
            }
            else{
                return 1;
            }
        }
        return -1;
    }

    public void setDay(String day) {this.day = day;}

    public void setStart(int start) {this.start = start;}

    public void setEnd(int end) {this.end = end;}

    public void setRoom(String room) {this.room = room;}

    public void setModuleName(String moduleName) {this.moduleName = moduleName;}

    public String getDay() {return this.day;}

    public int getStart() {return start;}

    public int getEnd() {return end;}

    public String getRoom() {return room;}

    public String getModuleName() {return moduleName;}
}