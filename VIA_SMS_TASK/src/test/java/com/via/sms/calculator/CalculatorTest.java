package com.via.sms.calculator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.via.sms.calculator.Operation.*;
import static java.lang.Double.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @ParameterizedTest(name = "{0} {2} {1} = {3}")
    @MethodSource("basicCases")
    void shouldCalculateCorrectly(double a, double b, Operation op, double expected) {
        double result = calculator.calculate(a, b, op.symbol);
        assertThat(result).isEqualTo(expected);
    }

    static Stream<Arguments> basicCases() {
        return Stream.of(
                arguments(5, 3, ADD, 8.0),
                arguments(5, 3, SUB, 2.0),
                arguments(5, 3, MUL, 15.0),
                arguments(6, 3, DIV, 2.0),
                arguments(2, 3, POW, 8.0),
                arguments(5, 3, MOD, 2.0),
                arguments(10, 4, DIV, 2.5),
                arguments(7.5, 2.5, ADD, 10.0),
                arguments(-5, 3, SUB, -8.0),
                arguments(5, -3, MUL, -15.0)
        );
    }

    @ParameterizedTest(name = "Invalid operator: ''{0}''")
    @ValueSource(strings = {"&", "!", "@", "**", "++", "xor", ""})
    void shouldThrowExceptionForInvalidOperator(String operator) {
        assertThatThrownBy(() -> calculator.calculate(5, 3, operator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid operator: " + operator);
    }

    @ParameterizedTest(name = "{0} {2} {1} = {3}")
    @MethodSource("edgeCases")
    void shouldHandleEdgeCases(double a, double b, Operation op, double expected) {
        double result = calculator.calculate(a, b, op.symbol);

        if (Double.isNaN(expected)) {
            assertThat(result).isNaN();
        } else {
            assertThat(result).isCloseTo(expected, within(0.001));
        }
    }

    static Stream<Arguments> edgeCases() {
        return Stream.of(
                arguments(2.0, 0.0, POW, 1.0),
                arguments(0.0, 5.0, POW, 0.0),
                arguments(-1.0, 0.5, POW, NaN),
                arguments(5.5, 2.0, MOD, 1.5),
                arguments(-5.0, 3.0, MOD, -2.0),
                arguments(0.0, 5.0, DIV, 0.0),
                arguments(MAX_VALUE, 2.0, MUL, POSITIVE_INFINITY),
                arguments(10.0, 1000.0, POW, POSITIVE_INFINITY),
                arguments(1.0, 0.0, DIV, POSITIVE_INFINITY),
                arguments(-1.01, 0.0, DIV, NEGATIVE_INFINITY),
                arguments(5.0, 3.0, DIV, 1.666),
                arguments(NEGATIVE_INFINITY, POSITIVE_INFINITY, SUB, NEGATIVE_INFINITY),
                arguments(NEGATIVE_INFINITY, POSITIVE_INFINITY, ADD, NaN),
                arguments(NEGATIVE_INFINITY, POSITIVE_INFINITY, MUL, NEGATIVE_INFINITY),
                arguments(NEGATIVE_INFINITY, POSITIVE_INFINITY, DIV, NaN)
        );
    }
}
