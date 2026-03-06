package com.madeinorbit.client.model;

import com.madeinorbit.client.model.Action;

/**
 *
 * @author Kuba Rodak
 */
public class Request {
    final Action action;
    final String day, time, room, module;
    
    private Request(Action a, String d, String t, String r, String m) {
        action = a;
        day = d;
        time = t;
        room = r;
        module = m;
    }
    
    public static Request fromUI(Action a, String d, String t, String r, String m) {
        return new Request(a, d, t, trim(r), trim(m));
    }
    
    public boolean isValid() {
        if (action == Action.ADD)
            return day != null && time != null && !room.isEmpty() && !module.isEmpty();
        if (action == Action.REMOVE)
            return day != null && time != null;
        return true;
    }
    
    @Override
    public String toString() {
        if (action == Action.ADD)
            return "ADD|" + day + "|" + time + "|" + room + "|" + module;
        else if (action == Action.REMOVE)
            return "REMOVE|" + day + "|" + time + "|" + room + "|" + module;
        else if (action == Action.DISPLAY)
            return "DISPLAY||||";
        else
            return action + "||||";
    }
    
    private static String trim(String s) { return s == null ? "" : s.trim(); }
}
