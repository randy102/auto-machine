package com.machine;

import com.helpers.records.Product;
import com.utils.JSONLoader;
import com.utils.JSONWriter;
import com.utils.Screen;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Math.min;

public class Promotion {
    private final long budget;
    private long usedBudget;
    private final long consecutiveCount;
    private double promotionProbabilityPercent;
    private final long increaseRate;
    private String currentDate;
    private final List<String> purchasedList = new ArrayList<>();

    private final String DATA_KEY = "promotion";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public Promotion() {
        JSONObject json = JSONLoader.loadData(DATA_KEY);
        budget = (long) json.get("budget");
        usedBudget = (long) json.get("usedBudget");
        consecutiveCount = (long) json.get("consecutiveCount");
        promotionProbabilityPercent = (double) json.get("promotionProbabilityPercent");
        increaseRate = (long) json.get("increaseRate");
        currentDate = (String) json.get("currentDate");

        JSONArray purchaseArray = (JSONArray) json.get("purchasedList");
        for (Object o : purchaseArray) {
            purchasedList.add((String) o);
        }
    }

    public void apply(Product product) {
        updateDailyData();

        if (product.price() > budget - usedBudget) return;

        if (purchasedList.isEmpty()) {
            purchasedList.add(product.key());
            saveData();
            return;
        }

        String lastKey = purchasedList.get(purchasedList.size() - 1);
        boolean sameProduct = product.key().equals(lastKey);
        if (!sameProduct) {
            purchasedList.clear();
            purchasedList.add(product.key());
            saveData();
            return;
        }

        boolean enoughPurchaseCount = purchasedList.size() >= consecutiveCount - 1;
        if (!enoughPurchaseCount) {
            purchasedList.add(product.key());
            saveData();
            return;
        }

        if (isWin()) {
            Screen.writeln("Congratulation! You get a free " + product.label());
            usedBudget += product.price();
            purchasedList.clear();
            saveData();
        }
    }


    private boolean isWin() {
        Random random = new Random();
        return random.nextDouble() < promotionProbabilityPercent / 100;
    }


    private void saveData() {
        JSONObject object = new JSONObject();
        object.put("budget", budget);
        object.put("usedBudget", usedBudget);
        object.put("consecutiveCount", consecutiveCount);
        object.put("promotionProbabilityPercent", promotionProbabilityPercent);
        object.put("increaseRate", increaseRate);
        object.put("currentDate", currentDate);
        JSONArray purchaseArray = new JSONArray();
        purchaseArray.addAll(purchasedList);
        object.put("purchasedList", purchaseArray);
        JSONWriter.writeData(DATA_KEY, object);
    }

    private void updateDailyData() {
        String today = dateFormat.format(new Date());
        if (currentDate == null) {
            currentDate = today;
        }
        if (today.equals(currentDate)) return;

        if (usedBudget < budget) {
            promotionProbabilityPercent = min(promotionProbabilityPercent * (1 + increaseRate / 100.0), 99);
        }
        usedBudget = 0;
        currentDate = today;
    }

}
