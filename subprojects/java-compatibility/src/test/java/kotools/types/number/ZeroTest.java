package kotools.types.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ZeroTest {
    @Test
    public void equals_should_be_compatible_with_Java() {
        final Zero first = new Zero();
        final Zero second = new Zero();
        final boolean actual = first.equals(second);
        final String message = "Instances of Zero should be equal.";
        Assertions.assertTrue(actual, message);
    }

    @Test
    public void hashCode_should_be_compatible_with_Java() {
        final int actual = new Zero()
                .hashCode();
        final int expected = new Zero()
                .hashCode();
        final String message =
                "Instances of Zero should have the same hash code value.";
        Assertions.assertEquals(expected, actual, message);
    }

    @Test
    public void toString_should_be_compatible_with_Java() {
        final String actual = new Zero()
                .toString();
        final String expected = "0";
        final String message = "String representation of Zero should be '0'.";
        Assertions.assertEquals(expected, actual, message);
    }
}
