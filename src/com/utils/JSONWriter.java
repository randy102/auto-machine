package com.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;

public class JSONWriter {
    public static void writeData(String name, JSONObject data){
        try{
            URL url = JSONLoader.class.getResource("../data/" + name + ".json");
            if (url == null) throw new FileNotFoundException();

            FileWriter writer = new FileWriter(new File(url.getPath()));
            writer.write(data.toJSONString());
            writer.flush();
        } catch (IOException e) {
            Screen.writeln(e.getMessage());
        }
    }
}
