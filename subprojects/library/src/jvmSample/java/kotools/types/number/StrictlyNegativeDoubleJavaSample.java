package kotools.types.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
class StrictlyNegativeDoubleJavaSample {
    @Test
    void equalsOverride() {
        final Number number = -23;
        final StrictlyNegativeDouble first =
                StrictlyNegativeDouble.Companion.create(number);
        final StrictlyNegativeDouble second =
                StrictlyNegativeDouble.Companion.create(number);
        final boolean actual = first.equals(second);
        Assertions.assertTrue(actual);
    }

    @Test
    void hashCodeOverride() {
        final Number number = -23;
        final int firstHashCode = StrictlyNegativeDouble.Companion
                .create(number)
                .hashCode();
        final int secondHashCode = StrictlyNegativeDouble.Companion
                .create(number)
                .hashCode();
        Assertions.assertEquals(firstHashCode, secondHashCode);
    }

    @Test
    void toDouble() {
        final Number number = -7;
        final double actual = StrictlyNegativeDouble.Companion.create(number)
                .toDouble();
        final double expected = number.doubleValue();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toStringOverride() {
        final Number number = -23.0;
        final String actual = StrictlyNegativeDouble.Companion.create(number)
                .toString();
        final String expected = number.toString();
        Assertions.assertEquals(expected, actual);
    }
}
