package com.helpers.records;

import com.interfaces.Displayable;

public record Note(String label, long amount) implements Displayable {

}