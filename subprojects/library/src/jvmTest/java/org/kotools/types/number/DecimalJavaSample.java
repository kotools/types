package org.kotools.types.number;

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SuppressWarnings("NewClassNamingConvention")
public class DecimalJavaSample {
    // ------------------------------- Creations -------------------------------

    @Test
    void fromLong() {
        final BiConsumer<Long, String> createsFromLong = (input, expected) -> {
            final Decimal result = Decimal.fromLong(input);
            final boolean check = String.valueOf(result).equals(expected);
            if (!check) throw new IllegalStateException("Check failed.");
        };

        createsFromLong.accept(0L, "0");
        createsFromLong.accept(42L, "42");
        createsFromLong.accept(-42L, "-42");
        createsFromLong.accept(Long.MAX_VALUE, "9223372036854775807");
        createsFromLong.accept(Long.MIN_VALUE, "-9223372036854775808");
    }

    @Test
    void parsing() {
        final BiConsumer<String, String> parsesTo = (input, expected) -> {
            final boolean check = String.valueOf(Decimal.parse(input))
                    .equals(expected);
            if (!check) throw new IllegalStateException("Check failed.");
        };

        final Consumer<String> parsingFailsWith = (input) -> {
            try {
                Decimal.parse(input);
                throw new IllegalStateException("Check failed.");
            } catch (NumberFormatException ignored) {
            }
        };

        // Parsing normalizes zero:
        parsesTo.accept("0", "0");
        parsesTo.accept("+0", "0");
        parsesTo.accept("-0", "0");
        parsesTo.accept("0.0", "0");
        parsesTo.accept("-0.0", "0");

        // Parsing removes leading plus sign:
        parsesTo.accept("+3.14", "3.14");

        // Parsing removes leading zeros:
        parsesTo.accept("00042", "42");
        parsesTo.accept("-00042", "-42");

        // Parsing removes trailing fractional zeros:
        parsesTo.accept("3.10", "3.1");
        parsesTo.accept("5.00", "5");

        // Parsing preserves canonical representation:
        parsesTo.accept("3.14", "3.14");
        parsesTo.accept("-2.5", "-2.5");
        parsesTo.accept("0.001", "0.001");

        // Parsing fails with non-decimal string:
        parsingFailsWith.accept("");
        parsingFailsWith.accept("+");
        parsingFailsWith.accept("-");
        parsingFailsWith.accept(".");
        parsingFailsWith.accept(".5");
        parsingFailsWith.accept("3.");
        parsingFailsWith.accept("1.2.3");
        parsingFailsWith.accept("1.5E3");
        parsingFailsWith.accept("12a");
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    void structuralEquality() {
        final BiConsumer<Decimal, Object> checkEquality = (decimal, other) -> {
            final boolean equality = Objects.equals(decimal, other);
            final boolean hashConformity =
                    Objects.hashCode(decimal) == Objects.hashCode(other);
            final boolean check = equality && hashConformity;
            if (!check) throw new IllegalStateException("Check failed.");
        };

        final BiConsumer<Decimal, Object> checkDiff = (decimal, other) -> {
            final boolean equality = !Objects.equals(decimal, other);
            final boolean hashConformity =
                    Objects.hashCode(decimal) != Objects.hashCode(other);
            final boolean check = equality && hashConformity;
            if (!check) throw new IllegalStateException("Check failed.");
        };

        // Scale-normalised values are equal:
        checkEquality.accept(Decimal.fromLong(0), Decimal.parse("-0.0"));
        checkEquality.accept(Decimal.parse("3.14"), Decimal.parse("3.140"));
        checkEquality.accept(Decimal.parse("-2.5"), Decimal.parse("-2.50"));

        // Different values or types are not equal:
        checkDiff.accept(Decimal.fromLong(0), Decimal.fromLong(1));
        checkDiff.accept(Decimal.parse("3.14"), 3.14);
        checkDiff.accept(Decimal.fromLong(-42), null);
    }

    @Test
    void compareTo() {
        final Decimal x = Decimal.parse("-99999999999999999999.99");
        final Decimal y = Decimal.parse("99999999999999999999.99");
        final boolean result = x.compareTo(y) < 0;
        if (!result) throw new IllegalStateException("Check failed.");
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    void unaryMinus() {
        final Decimal x = Decimal.parse("3.14");
        final Decimal result = x.unaryMinus();
        final Decimal expected = Decimal.parse("-3.14");
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void plus() {
        final Decimal x = Decimal.parse("1.5");
        final Decimal y = Decimal.parse("1.25");
        final Decimal result = x.plus(y);
        final Decimal expected = Decimal.parse("2.75");
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void minus() {
        final Decimal x = Decimal.parse("3.14");
        final Decimal y = Decimal.parse("1.5");
        final Decimal result = x.minus(y);
        final Decimal expected = Decimal.parse("1.64");
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void times() {
        final Decimal x = Decimal.parse("1.1");
        final Decimal y = Decimal.parse("1.1");
        final Decimal result = x.times(y);
        final Decimal expected = Decimal.parse("1.21");
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    void toStringOverride() {
        final BiConsumer<String, String> checkToString = (input, expected) -> {
            final boolean check = String.valueOf(Decimal.parse(input))
                    .equals(expected);
            if (!check) throw new IllegalStateException("Check failed.");
        };

        // Zero is always "0":
        checkToString.accept("0", "0");
        checkToString.accept("+0", "0");
        checkToString.accept("-0", "0");
        checkToString.accept("0.0", "0");

        // Plus sign removed, leading zeros removed:
        checkToString.accept("+42", "42");
        checkToString.accept("00042", "42");

        // Trailing fractional zeros removed:
        checkToString.accept("3.10", "3.1");
        checkToString.accept("5.00", "5");

        // Canonical representations preserved:
        checkToString.accept("3.14", "3.14");
        checkToString.accept("-2.5", "-2.5");
        checkToString.accept("0.001", "0.001");
    }
}
