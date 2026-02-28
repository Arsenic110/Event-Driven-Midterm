package com.madeinorbit.server.model;

import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;

/**
 *
 * @author Kuba Rodak
 * @author Mykhailop Fedenko
 *
 */
public class Config {
    public String host = "127.0.0.1";
    public int port = 8080;

    private static final Gson gson = new Gson();
    
    //will load host and port fields from the JSON file
    public static Config loadFromJson(String path) throws IOException {
        try (FileReader reader = new FileReader(path)) {
            return gson.fromJson(reader, Config.class);
        }
    }
}
