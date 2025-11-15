package com.via.sms.calculator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.via.sms.calculator.Calculator.calculate;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CalculatorTest {

    @ParameterizedTest(name = "{0} {3} {1} = {2}")
    @CsvSource({
            "5, 3, 8.0, +",
            "5, 3, 2.0, -",
            "5, 3, 15.0, *",
            "6, 3, 2.0, /",
            "2, 3, 8.0, ^",
            "5, 3, 2.0, %",
            "10, 4, 2.5, /",
            "7.5, 2.5, 10.0, +",
            "-5, 3, -8.0, -",
            "5, -3, -15.0, *"
    })
    void shouldCalculateCorrectly(double a, double b, double expected, String operator) {
        double result = calculate(a, b, operator);
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest(name = "Invalid operator: ''{0}''")
    @ValueSource(strings = {"&", "!", "@", "**", "++", "xor", ""})
    void shouldThrowExceptionForInvalidOperator(String operator) {
        assertThatThrownBy(() -> calculate(5, 3, operator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid operator");
    }

    @ParameterizedTest
    @MethodSource("provideEdgeCases")
    void shouldHandleEdgeCases(double a, double b, String operator, double expected) {
        double result = calculate(a, b, operator);

        if (Double.isNaN(expected)) {
            assertThat(result).isNaN();
        } else {
            assertThat(result).isCloseTo(expected, within(0.001));
        }
    }

    private static Stream<Arguments> provideEdgeCases() {
        return Stream.of(
                arguments(2, 0, "^", 1.0),
                arguments(0, 5, "^", 0.0),
                arguments(-1, 0.5, "^", Double.NaN),
                arguments(5.5, 2.0, "%", 1.5),
                arguments(-5, 3, "%", -2.0),
                arguments(0, 5, "/", 0.0),
                arguments(Double.MAX_VALUE, 2, "*", Double.POSITIVE_INFINITY),
                arguments(10, 1000, "^", Double.POSITIVE_INFINITY),
                arguments(1, 0, "/", Double.POSITIVE_INFINITY),
                arguments(-1.01, 0, "/", Double.NEGATIVE_INFINITY),
                arguments(5, 3, "/", 1.666),
                arguments(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, "-", Double.NEGATIVE_INFINITY),
                arguments(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, "+", Double.NaN),
                arguments(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, "*", Double.NEGATIVE_INFINITY),
                arguments(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, "/", Double.NaN)
        );
    }
}