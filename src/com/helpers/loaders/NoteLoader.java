package com.helpers.loaders;

import com.helpers.records.Note;
import com.libs.RecordLoader;
import org.json.simple.JSONObject;

public class NoteLoader extends RecordLoader<Note> {
    private static NoteLoader loader;

    private NoteLoader() {
        super("notes");
    }

     public static NoteLoader getLoader() {
        if (loader == null){
            loader = new NoteLoader();
        }
        return loader;
    }

    @Override
    protected Note getRecord(JSONObject object) {
        return new Note((String) object.get("label"), (long) object.get("amount"));
    }
}
