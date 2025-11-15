package com.via.sms.calculator;

public class Calculator {

    private static double calculate(double a, double b, Operation op) {
        return op.apply(a, b);
    }

    //should be static but task asks for an instance method
    public double calculate(double a, double b, String symbol) {
        return calculate(a, b, Operation.fromSymbol(symbol));
    }
}
