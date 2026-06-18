package org.kotools.types.number;

import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
public class NonZeroIntegerJavaSample {
    // ------------------------------- Creations -------------------------------

    @Test
    void fromLong() {
        final NonZeroInteger result = NonZeroInteger.fromLong(42L);
        final boolean check = String.valueOf(result).equals("42");
        if (!check) throw new IllegalStateException("Check failed.");

        try {
            NonZeroInteger.fromLong(0L);
            throw new IllegalStateException("Check failed.");
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    void parsing() {
        final NonZeroInteger result = NonZeroInteger.parse("+00042");
        final boolean check = String.valueOf(result).equals("42");
        if (!check) throw new IllegalStateException("Check failed.");

        try {
            NonZeroInteger.parse("3.14");
            throw new IllegalStateException("Check failed.");
        } catch (NumberFormatException ignored) {
        }

        try {
            NonZeroInteger.parse("0");
            throw new IllegalStateException("Check failed.");
        } catch (IllegalArgumentException ignored) {
        }
    }

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

    // ------------------------------ Comparisons ------------------------------

    @Test
    void structuralEquality() {
        final NonZeroInteger x = NonZeroInteger.fromLong(42L);
        final NonZeroInteger y = NonZeroInteger.parse("+00042");
        final boolean equality = x.equals(y) && x.hashCode() == y.hashCode();
        if (!equality) throw new IllegalStateException("Check failed.");

        final NonZeroInteger z = NonZeroInteger.fromLong(-42L);
        final boolean inequality =
                !x.equals(z) && x.hashCode() != z.hashCode();
        if (!inequality) throw new IllegalStateException("Check failed.");
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
