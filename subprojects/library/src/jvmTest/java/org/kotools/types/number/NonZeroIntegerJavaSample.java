package org.kotools.types.number;

import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
public class NonZeroIntegerJavaSample {
    // ------------------------------- Creations -------------------------------

    @Test
    void fromInteger() {
        final Integer value = Integer.of(42);
        final NonZeroInteger result = NonZeroInteger.fromInteger(value);
        final boolean check = String.valueOf(result).equals("42");
        if (!check) throw new IllegalStateException("Check failed.");

        final Integer zero = Integer.of(0);
        try {
            NonZeroInteger.fromInteger(zero);
            throw new IllegalStateException("Check failed.");
        } catch (IllegalArgumentException ignored) {
        }
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    void toStringOverride() {
        final Integer value = Integer.of(-42);
        final NonZeroInteger nonZeroInteger = NonZeroInteger.fromInteger(value);
        final String result = String.valueOf(nonZeroInteger);
        final boolean check = result.equals("-42");
        if (!check) throw new IllegalStateException("Check failed.");
    }
}
