package com.via.sms.calculator;

import java.util.Map;
import java.util.function.DoubleBinaryOperator;

import static java.util.Optional.ofNullable;

public class Calculator {

    private static final Map<String, DoubleBinaryOperator> OPERATIONS_MAP = Map.of(
            "+", Double::sum,
            "-", (double a, double b) -> a - b,
            "*", (double a, double b) -> a * b,
            "/", (double a, double b) -> a / b,
            "%", (double a, double b) -> a % b,
            "^", Math::pow
    );

    public static double calculate(double a, double b, String operator) {
        return ofNullable(OPERATIONS_MAP.get(operator))
                .orElseThrow(() -> new IllegalArgumentException("Invalid operator: " + operator))
                .applyAsDouble(a, b);
    }
}
