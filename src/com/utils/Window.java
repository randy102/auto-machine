package com.utils;

import com.interfaces.Displayable;
import com.interfaces.WindowHandler;
import com.interfaces.WindowSingleHandler;

public class Window<Record extends Displayable> {
    private final Option<Record> option;
    private boolean clearScreen;

    public Window(Option<Record> option) {
        this.option = option;
        this.clearScreen = true;
    }

    public Window(Option<Record> option, boolean clearScreen) {
        this(option);
        this.clearScreen = clearScreen;
    }

    public void showOnce(WindowSingleHandler<Record> handler) {
        Option.Answer<Record> answer;
        if (clearScreen) Screen.clear();
        handler.beforeInput();
        answer = this.option.get();
        if (answer == null || answer.isExit()) {
            handler.onCancel();
            return;
        }
        handler.onInput(answer.get());
    }

    public void show(WindowHandler<Record> handler) {
        Option.Answer<Record> answer;
        while (true) {
            if (clearScreen) Screen.clear();
            handler.beforeInput();
            answer = this.option.get();
            if (answer == null || answer.isExit()) {
                handler.onCancel();
                break;
            }
            ;
            if (!handler.onInput(answer.get())) break;
        }

    }
}
