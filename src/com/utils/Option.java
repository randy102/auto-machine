package com.utils;

import com.exceptions.OptionOutRangeException;
import com.interfaces.Displayable;

import java.util.List;

public class Option<Record extends Displayable> {
    public static class Answer<Item> {
        private Item item;
        private final boolean exit;

        public Answer(Item item) {
            this.item = item;
            this.exit = false;
        }

        public Answer() {
            exit = true;
        }

        public boolean isExit() {
            return exit;
        }

        public Item get() {
            return item;
        }
    }

    private final List<Record> records;
    private final String title;
    private boolean backOption;

    public Option(List<Record> records, String title) {
        this.records = records;
        this.title = title;
        this.backOption = true;
    }

    public Option(List<Record> records, String title, boolean backOption) {
        this(records, title);
        this.backOption = backOption;
    }

    public Answer<Record> get() {
        if (this.records == null || this.records.size() == 0) {
            System.out.println("No options!");
            return null;
        }

        System.out.println(title);
        this.printOptions();

        int input = this.getInput();
        if (input == this.records.size()) {
            return new Answer<Record>();
        }
        return new Answer<Record>(this.records.get(input));
    }

    private void printOptions() {
        for (int i = 1; i <= this.records.size(); i++) {
            System.out.println(i + ": " + this.records.get(i - 1).label());
        }
        if (backOption)
            System.out.println((this.records.size() + 1) + ": Cancel");
    }

    private int getInput() {
        String input;
        do {
            System.out.print("Your option: ");
            input = Scanner.get();
        } while (!isValidInput(input));
        return Integer.parseInt(input) - 1;
    }

    private boolean isValidInput(String input) {
        if (input == null) return false;
        try {
            int d = Integer.parseInt(input);
            int limit =  backOption ? this.records.size() + 1 : this.records.size();
            if (d <= 0 || d > limit) throw new OptionOutRangeException();
        } catch (Exception e) {
            Screen.writeln("Invalid option!");
            return false;
        }
        return true;
    }
}
