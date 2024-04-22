package kotools.types.experimental;

import org.junit.jupiter.api.Test;
import org.kotools.types.Assert;

class AnyIntFactoryJavaSampleTest {
    @Test
    void constructorLikeInt() {
        final String expected = "1";
        final AnyIntFactoryJavaSample sample = new AnyIntFactoryJavaSample();
        Assert.prints(expected, sample::constructorLikeInt);
    }
}
