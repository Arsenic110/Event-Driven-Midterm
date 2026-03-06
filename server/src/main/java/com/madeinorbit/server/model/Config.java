package com.madeinorbit.server.model;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Kuba Rodak
 * @author Mykhailop Fedenko
 *
 */
public class Config {
    public int port = 8080;

    private static final Gson gson = new Gson();
    
    //will load host and port fields from the JSON file
    public static Config loadFromJson(String path) throws IOException {
        try (FileReader reader = new FileReader(path)) {
            return gson.fromJson(reader, Config.class);
        }
    }
}
