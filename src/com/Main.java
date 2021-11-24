package com;

import com.machine.Machine;
import com.utils.JSONWriter;
import org.json.simple.JSONObject;

public class Main {
    public static void main(String[] args) {
        Machine machine = new Machine();
        machine.start();
    }
}
