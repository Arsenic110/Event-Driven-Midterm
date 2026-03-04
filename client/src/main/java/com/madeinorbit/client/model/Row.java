package com.madeinorbit.client.model;

import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author Kuba Rodak
 */
public class Row {
    public final SimpleStringProperty date, time, room, module;
    
    public Row(String d, String t, String r, String m) {
        date = new SimpleStringProperty(d);
        time = new SimpleStringProperty(t);
        room = new SimpleStringProperty(r);
        module = new SimpleStringProperty(m);
    }
}
