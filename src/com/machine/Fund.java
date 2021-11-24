package com.machine;

import com.helpers.loaders.NoteLoader;
import com.helpers.records.Note;
import com.utils.Option;
import com.utils.Screen;
import com.utils.Window;

public class Fund {

    private long total;

    public Fund(long initAmount) {
        total = initAmount;
    }

    public void add(long amount) {
        if (amount > 0) {
            total += amount;
        }
    }

    public void debit(long amount) {
        if (amount <= total) {
            total -= amount;
        }
    }

    public long refund() {
        long current = total;
        total = 0;
        return current;
    }

    public long getTotal() {
        return total;
    }


    public void printFundInfo() {
        Screen.writeln("Current funds: " + getTotal());
    }
}
