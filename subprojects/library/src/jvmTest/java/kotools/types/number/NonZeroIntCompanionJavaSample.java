package kotools.types.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
class NonZeroIntCompanionJavaSample {
    @Test
    void create() {
        boolean isSuccess;
        try {
            NonZeroInt.Companion.create(23);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void createOrNull() {
        final NonZeroInt number = NonZeroInt.Companion.createOrNull(23);
        Assertions.assertNotNull(number);
    }
}
