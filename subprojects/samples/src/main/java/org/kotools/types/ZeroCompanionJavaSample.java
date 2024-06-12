package org.kotools.types;

import java.util.List;

class ZeroCompanionJavaSample {
    void pattern() {
        List<Object> numbers = List.of(
                0, 0.0, // TABS: 2
                "+0", "+000", "+0.000", "+000.000", // with unary plus // TABS: 2
                "-0", "-000", "-0.000", "-000.000" // with unary minus // TABS: 2
        );
        final String regex = Zero.PATTERN;
        final boolean numbersAreValid = numbers.stream()
                .allMatch(number -> number.toString().matches(regex)); // TABS: 2
        System.out.println(numbersAreValid); // true
    } // END

    void fromByte() {
        final byte number = 0;
        boolean isSuccess;
        try {
            Zero.fromByte(number); // TABS: 1
            isSuccess = true; // TABS: 1
        } catch (final IllegalArgumentException exception) {
            isSuccess = false; // TABS: 1
        }
        System.out.println(isSuccess); // true
    } // END

    void fromByteOrNull() {
        final byte number = 0;
        final Zero zero = Zero.fromByteOrNull(number);
        System.out.println(zero != null); // true
    } // END

    void fromShort() {
        final short number = 0;
        boolean isSuccess;
        try {
            Zero.fromShort(number); // TABS: 1
            isSuccess = true; // TABS: 1
        } catch (final IllegalArgumentException exception) {
            isSuccess = false; // TABS: 1
        }
        System.out.println(isSuccess); // true
    } // END

    void fromShortOrNull() {
        final short number = 0;
        final Zero zero = Zero.fromShortOrNull(number);
        System.out.println(zero != null); // true
    } // END

    void fromInt() {
        boolean isSuccess;
        try {
            Zero.fromInt(0); // TABS: 1
            isSuccess = true; // TABS: 1
        } catch (final IllegalArgumentException exception) {
            isSuccess = false; // TABS: 1
        }
        System.out.println(isSuccess); // true
    } // END

    void fromIntOrNull() {
        final Zero zero = Zero.fromIntOrNull(0);
        System.out.println(zero != null); // true
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
            Zero.orThrow(number); // TABS: 1
            isSuccess = true; // TABS: 1
        } catch (final IllegalArgumentException exception) {
            isSuccess = false; // TABS: 1
        }
        System.out.println(isSuccess); // true
    } // END
}
