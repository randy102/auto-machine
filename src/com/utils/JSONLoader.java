package com.utils;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class JSONLoader {
    public static JSONObject load(String path){
        JSONParser parser = new JSONParser();
        JSONObject json;
        try{
            URL url = JSONLoader.class.getResource(path);
            if (url == null) throw new FileNotFoundException();
            File file = new File(url.getPath());
            json = (JSONObject) parser.parse(new FileReader(file));
        } catch (IOException | ParseException e) {
            Screen.writeln(e.getMessage());
            return null;
        }
        return json;
    }

    public static JSONObject loadData(String name){
        return load("../data/" + name + ".json");
    }

    public static JSONObject loadConfig(String name)  {
        return load("../config/" + name + ".json");
    }
}
