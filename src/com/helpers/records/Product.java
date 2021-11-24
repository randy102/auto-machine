package com.helpers.records;

import com.interfaces.Displayable;

public record Product(String label, long price, String key) implements Displayable {

}