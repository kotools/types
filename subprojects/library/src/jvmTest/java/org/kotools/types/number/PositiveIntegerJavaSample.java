package org.kotools.types.number;

import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
public class PositiveIntegerJavaSample {
    // --------------------------- Factory functions ---------------------------

    @Test
    void fromLong() {
        final PositiveInteger result = PositiveInteger.fromLong(42L);
        final boolean check = result.toInteger().equals(Integer.fromLong(42));
        if (!check) throw new IllegalStateException("Check failed.");

        try {
            PositiveInteger.fromLong(0L);
            throw new IllegalStateException("Check failed.");
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    void fromInteger() {
        final Integer value = Integer.fromLong(42);
        final PositiveInteger result = PositiveInteger.fromInteger(value);
        final boolean check = result.toInteger().equals(value);
        if (!check) throw new IllegalStateException("Check failed.");

        final Integer zero = Integer.fromLong(0);
        try {
            PositiveInteger.fromInteger(zero);
            throw new IllegalStateException("Check failed.");
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    void parsing() {
        final PositiveInteger result = PositiveInteger.parse("+00042");
        final boolean check = result.toInteger().equals(Integer.fromLong(42));
        if (!check) throw new IllegalStateException("Check failed.");

        try {
            PositiveInteger.parse("3.14");
            throw new IllegalStateException("Check failed.");
        } catch (NumberFormatException ignored) {
        }

        try {
            PositiveInteger.parse("0");
            throw new IllegalStateException("Check failed.");
        } catch (IllegalArgumentException ignored) {
        }
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    void structuralEquality() {
        final Integer value = Integer.fromLong(42);
        final PositiveInteger x = PositiveInteger.fromInteger(value);
        final PositiveInteger y = PositiveInteger.fromInteger(value);
        final boolean equality = x.equals(y) && x.hashCode() == y.hashCode();
        if (!equality) throw new IllegalStateException("Check failed.");

        final Integer other = Integer.fromLong(7);
        final PositiveInteger z = PositiveInteger.fromInteger(other);
        final boolean inequality =
                !x.equals(z) && x.hashCode() != z.hashCode();
        if (!inequality) throw new IllegalStateException("Check failed.");
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    void toNonNegativeInteger() {
        final PositiveInteger positiveInteger = PositiveInteger.fromLong(42L);

        final NonNegativeInteger result =
                positiveInteger.toNonNegativeInteger();

        final boolean check = result.equals(NonNegativeInteger.fromLong(42L));
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void toNonZeroInteger() {
        final PositiveInteger positiveInteger = PositiveInteger.fromLong(42L);

        final NonZeroInteger result = positiveInteger.toNonZeroInteger();

        final boolean check = result.equals(NonZeroInteger.fromLong(42L));
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void toInteger() {
        final Integer value = Integer.fromLong(42);
        final PositiveInteger positiveInteger =
                PositiveInteger.fromInteger(value);
        final Integer result = positiveInteger.toInteger();
        final boolean check = result.equals(value);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void toStringOverride() {
        final Integer value = Integer.fromLong(42);
        final PositiveInteger positiveInteger =
                PositiveInteger.fromInteger(value);
        final String result = String.valueOf(positiveInteger);
        final boolean check = result.equals("42");
        if (!check) throw new IllegalStateException("Check failed.");
    }
}
