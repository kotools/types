package org.kotools.types.number;

import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
public class NonPositiveIntegerJavaSample {
    // --------------------------- Factory functions ---------------------------

    @Test
    void fromLong() {
        final NonPositiveInteger result = NonPositiveInteger.fromLong(-42L);
        final boolean check = result.toInteger().equals(Integer.fromLong(-42));
        if (!check) throw new IllegalStateException("Check failed.");

        try {
            NonPositiveInteger.fromLong(1L);
            throw new IllegalStateException("Check failed.");
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    void fromInteger() {
        final Integer value = Integer.fromLong(-42);
        final NonPositiveInteger result =
                NonPositiveInteger.fromInteger(value);
        final boolean check = result.toInteger().equals(value);
        if (!check) throw new IllegalStateException("Check failed.");

        final Integer positive = Integer.fromLong(1);
        try {
            NonPositiveInteger.fromInteger(positive);
            throw new IllegalStateException("Check failed.");
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    void parsing() {
        final NonPositiveInteger result = NonPositiveInteger.parse("-00042");
        final boolean check = result.toInteger().equals(Integer.fromLong(-42));
        if (!check) throw new IllegalStateException("Check failed.");

        try {
            NonPositiveInteger.parse("3.14");
            throw new IllegalStateException("Check failed.");
        } catch (NumberFormatException ignored) {
        }

        try {
            NonPositiveInteger.parse("1");
            throw new IllegalStateException("Check failed.");
        } catch (IllegalArgumentException ignored) {
        }
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    void toInteger() {
        final Integer value = Integer.fromLong(-42);
        final NonPositiveInteger nonPositiveInteger =
                NonPositiveInteger.fromInteger(value);
        final Integer result = nonPositiveInteger.toInteger();
        final boolean check = result.equals(value);
        if (!check) throw new IllegalStateException("Check failed.");
    }
}
