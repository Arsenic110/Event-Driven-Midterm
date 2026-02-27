/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.madeinorbit.client.model;

import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;

/**
 *
 * @author Kuba Rodak
 */
public class Config {
    public String host = "127.0.0.1";
    public int port = 8080;

    private static final Gson gson = new Gson();
    
    public static Config loadFromJson(String path) throws IOException {
        try (FileReader reader = new FileReader(path)) {
            return gson.fromJson(reader, Config.class);
        }
    }
}
