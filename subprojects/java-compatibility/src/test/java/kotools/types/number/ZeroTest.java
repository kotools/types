package kotools.types.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ZeroTest {
    @Test
    public void toString_should_be_compatible_with_Java() {
        final String actual = Zero.INSTANCE.toString();
        final String expected = "0";
        final String message = "String representation of Zero should be '0'.";
        Assertions.assertEquals(expected, actual, message);
    }
}
