package com.interfaces;

public interface WindowSingleHandler<Record> extends WindowHandlerEvent {
    void onInput(Record record);
}