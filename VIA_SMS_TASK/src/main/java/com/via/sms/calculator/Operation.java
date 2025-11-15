package com.via.sms.calculator;

import java.util.function.DoubleBinaryOperator;

public enum Operation {

    ADD("+", Double::sum),
    SUB("-", (a, b) -> a - b),
    MUL("*", (a, b) -> a * b),
    DIV("/", (a, b) -> a / b),
    MOD("%", (a, b) -> a % b),
    POW("^", Math::pow);

    private final static String ERROR_MESSAGE = "Invalid operator: ";
    public final String symbol;
    public final DoubleBinaryOperator op;

    Operation(String symbol, DoubleBinaryOperator op) {
        this.symbol = symbol;
        this.op = op;
    }

    public static Operation fromSymbol(String symbol) {
        for (Operation o : values()) {
            if (o.symbol.equals(symbol)) return o;
        }
        throw new IllegalArgumentException(ERROR_MESSAGE + symbol);
    }

    public double apply(double a, double b) {
        return op.applyAsDouble(a, b);
    }

    @Override
    public String toString() {
        return symbol;
    }
}
