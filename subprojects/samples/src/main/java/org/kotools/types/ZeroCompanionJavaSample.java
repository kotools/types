package org.kotools.types;

import java.util.List;

class ZeroCompanionJavaSample {
    void pattern() {
        List<Object> numbers = List.of(
                0, 0.0,
                "+0", "+000", "+0.000", "+000.000", // with unary plus
                "-0", "-000", "-0.000", "-000.000" // with unary minus
        );
        final String regex = Zero.PATTERN;
        final boolean numbersAreValid = numbers.stream()
                .allMatch(number -> number.toString().matches(regex));
        System.out.println(numbersAreValid); // true
    } // END

    void orNull() {
        final Object number = "-000.000";
        final Zero zero = Zero.orNull(number);
        System.out.println(zero != null); // true
    } // END

    void orThrow() {
        final Object number = "-000.000";
        boolean isSuccess;
        try {
            Zero.orThrow(number);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        System.out.println(isSuccess); // true
    } // END
}
