package kotools.types.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
class NegativeIntCompanionJavaSample {
    @Test
    void create() {
        boolean isSuccess;
        try {
            NegativeInt.Companion.create(-7);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void createOrNull() {
        final NegativeInt number = NegativeInt.Companion.createOrNull(-7);
        Assertions.assertNotNull(number);
    }
}
