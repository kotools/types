package kotools.types.experimental;

import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
class AnyIntFactoryJavaSample {
    @Test
    void constructorLikeInt() {
        AnyIntFactory.create(1);
    }
}
