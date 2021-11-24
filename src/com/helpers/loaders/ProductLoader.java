package com.helpers.loaders;

import com.helpers.records.Note;
import com.helpers.records.Product;
import com.libs.RecordLoader;
import org.json.simple.JSONObject;

public class ProductLoader extends RecordLoader<Product> {
    private static ProductLoader loader;

    private ProductLoader() {
        super("products");
    }

    public static ProductLoader getLoader() {
        if (loader == null) {
            loader = new ProductLoader();
        }
        return loader;
    }

    @Override
    protected Product getRecord(JSONObject object) {
        return new Product((String) object.get("label"), (long) object.get("price"), (String) object.get("key"));
    }
}
