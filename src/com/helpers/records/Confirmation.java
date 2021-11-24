package com.helpers.records;

import com.interfaces.Displayable;

public record Confirmation(String label, String key) implements Displayable {
}