package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

@SuppressWarnings("NewClassNamingConvention")
class ZeroCompanionJavaSample {
    @Test
    void pattern() {
        List<Object> numbers = List.of(
                0, 0.0,
                "+0", "+000", "+0.000", "+000.000", // with unary plus
                "-0", "-000", "-0.000", "-000.000" // with unary minus
        );
        final String regex = Zero.PATTERN;
        final boolean numbersAreValid = numbers.stream()
                .allMatch(number -> number.toString().matches(regex));
        Assertions.assertTrue(numbersAreValid);
    }

    @Test
    void orNull() {
        final Object number = "-000.000";
        final Zero actual = Zero.orNull(number);
        Assertions.assertNotNull(actual);
    }

    @Test
    void orThrow() {
        final Object number = "-000.000";
        boolean isSuccess;
        try {
            Zero.orThrow(number);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }
}
