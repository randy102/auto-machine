package com.machine;

import com.helpers.records.Product;
import com.utils.JSONLoader;
import com.utils.JSONWriter;
import com.utils.Screen;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
    private String lastPurchased;
    private long currentConsecutivePurchased;

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
        lastPurchased = (String) json.get("lastPurchased");
        currentConsecutivePurchased = (long) json.get("currentConsecutivePurchased");
    }

    public void apply(Product product) {
        updateDailyData();

        boolean budgetExceeded = product.price() > budget - usedBudget;
        if (budgetExceeded) return;

        if (currentConsecutivePurchased == 0) {
            lastPurchased = product.key();
            currentConsecutivePurchased += 1;
            saveData();
            return;
        }

        boolean differentThanLastPurchased = !product.key().equals(lastPurchased);
        if (differentThanLastPurchased) {
            currentConsecutivePurchased = 1;
            lastPurchased = product.key();
            saveData();
            return;
        }

        boolean unsatisfiedConsecutiveCount = currentConsecutivePurchased < consecutiveCount - 1;
        if (unsatisfiedConsecutiveCount) {
            currentConsecutivePurchased += 1;
            saveData();
            return;
        }

        if (isWin()) {
            Screen.writeln("Congratulation! You get a free " + product.label());
            usedBudget += product.price();
            currentConsecutivePurchased = 0;
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
        object.put("currentConsecutivePurchased", currentConsecutivePurchased);
        object.put("lastPurchased", lastPurchased);
        JSONWriter.writeData(DATA_KEY, object);
    }

    private void updateDailyData() {
        String today = dateFormat.format(new Date());
        if (currentDate == null) {
            currentDate = today;
        }
        if (today.equals(currentDate)) return;

        boolean yesterdayBudgetNotReached = usedBudget < budget;
        if (yesterdayBudgetNotReached) {
            increaseWinProbability();
        }
        usedBudget = 0;
        currentDate = today;
    }

    private void increaseWinProbability(){
        promotionProbabilityPercent = min(promotionProbabilityPercent * (1 + increaseRate / 100.0), 99);
    }

}
