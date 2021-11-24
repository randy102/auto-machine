package com.exceptions;

public class OptionOutRangeException extends Exception{
    public OptionOutRangeException(){
        super("Option is out of range!");
    }
}
