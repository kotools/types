package kotools.types.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
class PositiveIntCompanionJavaSample {
    @Test
    void create() {
        boolean isSuccess;
        try {
            PositiveInt.Companion.create(23);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void createOrNull() {
        final PositiveInt number = PositiveInt.Companion.createOrNull(23);
        Assertions.assertNotNull(number);
    }
}
