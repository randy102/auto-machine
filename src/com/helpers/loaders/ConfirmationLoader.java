package com.helpers.loaders;

import com.helpers.records.Confirmation;
import com.libs.RecordLoader;
import org.json.simple.JSONObject;

public class ConfirmationLoader extends RecordLoader<Confirmation> {
    private static ConfirmationLoader loader;

    private ConfirmationLoader() {
        super("confirmation");
    }

    public static ConfirmationLoader getLoader() {
        if (loader == null) loader = new ConfirmationLoader();
        return loader;
    }

    @Override
    protected Confirmation getRecord(JSONObject object) {
        return new Confirmation((String) object.get("label"), (String) object.get("key"));
    }
}
