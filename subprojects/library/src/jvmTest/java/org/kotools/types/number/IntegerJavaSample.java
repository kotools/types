package org.kotools.types.number;

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SuppressWarnings("NewClassNamingConvention")
public class IntegerJavaSample {
    // ------------------------------- Creations -------------------------------

    @Test
    void of() {
        final BiConsumer<Long, String> createsFromLong = (input, expected) -> {
            final Integer result = Integer.of(input);
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
            final boolean check = String.valueOf(Integer.parse(input))
                    .equals(expected);
            if (!check) throw new IllegalStateException("Check failed.");
        };

        final Consumer<String> parsingFailsWith = (input) -> {
            try {
                Integer.parse(input);
                throw new IllegalStateException("Check failed.");
            } catch (NumberFormatException ignored) {
            }
        };

        // Parsing normalizes zero:
        parsesTo.accept("0", "0");
        parsesTo.accept("+0", "0");
        parsesTo.accept("-0", "0");
        parsesTo.accept("000", "0");
        parsesTo.accept("+000", "0");
        parsesTo.accept("-000", "0");

        // Parsing removes leading plus sign:
        parsesTo.accept("+42", "42");

        // Parsing removes leading zeros:
        parsesTo.accept("00042", "42");
        parsesTo.accept("-00042", "-42");

        // Parsing preserves canonical representation:
        parsesTo.accept("42", "42");
        parsesTo.accept("-42", "-42");

        // Parsing fails with noninteger string:
        parsingFailsWith.accept("");
        parsingFailsWith.accept("+");
        parsingFailsWith.accept("-");
        parsingFailsWith.accept("12a");
        parsingFailsWith.accept("3.14");
        parsingFailsWith.accept(" 42");
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    void structuralEquality() {
        final BiConsumer<Integer, Object> checkEquality = (integer, other) -> {
            final boolean equality = Objects.equals(integer, other);
            final boolean hashConformity =
                    Objects.hashCode(integer) == Objects.hashCode(other);
            final boolean check = equality && hashConformity;
            if (!check) throw new IllegalStateException("Check failed.");
        };

        final BiConsumer<Integer, Object> checkDiff = (integer, other) -> {
            final boolean equality = !Objects.equals(integer, other);
            final boolean hashConformity =
                    Objects.hashCode(integer) != Objects.hashCode(other);
            final boolean check = equality && hashConformity;
            if (!check) throw new IllegalStateException("Check failed.");
        };

        checkEquality.accept(Integer.of(0), Integer.parse("-000"));
        checkEquality.accept(Integer.of(42), Integer.parse("+00042"));
        checkEquality.accept(Integer.of(-42), Integer.parse("-0042"));

        checkDiff.accept(Integer.of(0), Integer.of(1));
        checkDiff.accept(Integer.of(42), 42);
        checkDiff.accept(Integer.of(-42), null);
    }

    @Test
    void compareTo() {
        final Integer x = Integer.parse("-99999999999999999999");
        final Integer y = Integer.parse("99999999999999999999");
        final boolean result = x.compareTo(y) < 0;
        if (!result) throw new IllegalStateException("Check failed.");
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    void unaryMinus() {
        // Given
        final Integer x = Integer.of(9223372036854775807L);
        // When
        final Integer result = x.unaryMinus();
        // Then
        final Integer expected = Integer.of(-9223372036854775807L);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void plus() {
        // Given
        final Integer x = Integer.of(9223372036854775807L);
        final Integer y = Integer.of(2);
        // When
        final Integer result = x.plus(y);
        // Then
        final Integer expected = Integer.parse("9223372036854775809");
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void minus() {
        // Given
        final Integer x = Integer.of(-9223372036854775807L);
        final Integer y = Integer.of(2);
        // When
        final Integer result = x.minus(y);
        // Then
        final Integer expected = Integer.parse("-9223372036854775809");
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void times() {
        // Given
        final Integer x = Integer.of(9223372036854775807L);
        final Integer y = Integer.of(10);
        // When
        final Integer result = x.times(y);
        // Then
        final Integer expected = Integer.parse("92233720368547758070");
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void div() {
        // Given
        final Integer x = Integer.parse("922337203685477580700");
        final Integer y = Integer.of(10);
        // When
        final Integer result = x.div(y);
        // Then
        final Integer expected = Integer.parse("92233720368547758070");
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void rem() {
        // Given
        final Integer x = Integer.of(42);
        final Integer y = Integer.of(5);
        // When
        final Integer result = x.rem(y);
        // Then
        final Integer expected = Integer.of(2);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    void toStringOverride() {
        final BiConsumer<Integer, String> checkToString = (input, expected) -> {
            final boolean check = String.valueOf(input).equals(expected);
            if (!check) throw new IllegalStateException("Check failed.");
        };

        checkToString.accept(Integer.of(0), "0");
        checkToString.accept(Integer.parse("+0"), "0");
        checkToString.accept(Integer.parse("-0"), "0");
        checkToString.accept(Integer.parse("+42"), "42");
        checkToString.accept(Integer.of(-42), "-42");
        checkToString.accept(Integer.parse("00042"), "42");
        checkToString.accept(Integer.parse("+00042"), "42");
        checkToString.accept(Integer.parse("-00042"), "-42");
    }
}
