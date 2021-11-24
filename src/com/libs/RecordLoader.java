package com.libs;

import com.utils.JSONLoader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class RecordLoader<Item> {
    private final String KEY;

    public RecordLoader(String key){
        this.KEY = key;
    }

    public List<Item> load() {
        JSONObject json = JSONLoader.loadConfig(KEY);
        if (json == null) {
            return new ArrayList<>();
        }
        JSONArray jsonArray = (JSONArray) json.get(KEY);
        List<Item> notes = new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            notes.add(getRecord(jsonObject));
        }
        return notes;
    }

    protected abstract Item getRecord(JSONObject object);
}
