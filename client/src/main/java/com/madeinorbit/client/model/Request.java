package com.madeinorbit.client.model;

import com.madeinorbit.client.model.Action;
import java.time.LocalDate;

/**
 *
 * @author Kuba Rodak
 */
public class Request {
    final Action action;
    final LocalDate date;
    final String time, room, module;
    
    private Request(Action a, LocalDate d, String t, String r, String m) {
        action = a;
        date = d;
        time = t;
        room = r;
        module = m;
    }
    
    public static Request fromUI(Action a, LocalDate d, String t, String r, String m) {
        return new Request(a, d, t, trim(r), trim(m));
    }
    
    public boolean isValid() {
        if (action == Action.ADD)
            return date != null && time != null && !room.isEmpty() && !module.isEmpty();
        if (action == Action.REMOVE)
            return date != null && time != null;
        return true;
    }
    
    @Override
    public String toString() {
        if (action == Action.ADD)
            return "ADD|" + date + "|" + time + "|" + room + "|" + module;
        else if (action == Action.REMOVE)
            return "REMOVE|" + date + "|" + time + "|" + room + "|" + module;
        else if (action == Action.DISPLAY)
            return "DISPLAY||||";
        else
            return action + "||||";
    }
    
    private static String trim(String s) { return s == null ? "" : s.trim(); }
}
