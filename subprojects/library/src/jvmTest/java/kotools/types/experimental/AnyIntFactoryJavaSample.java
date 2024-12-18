package kotools.types.experimental;

import kotools.types.number.AnyInt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
class AnyIntFactoryJavaSample {
    @Test
    void constructorLikeInt() {
        final AnyInt number = AnyIntFactory.create(1);
        final String actual = number.toString();
        final String expected = "1";
        Assertions.assertEquals(expected, actual);
    }
}
