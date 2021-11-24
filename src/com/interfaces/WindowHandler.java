package com.interfaces;

public interface WindowHandler<Record> extends WindowHandlerEvent {
    boolean onInput(Record record);
}
