package kotools.types.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

@SuppressWarnings("NewClassNamingConvention")
class StrictlyNegativeDoubleCompanionJavaSample {
    @Test
    void create() {
        final Number number = -23;
        boolean isSuccess;
        try {
            StrictlyNegativeDouble.Companion.create(number);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void createOrNull() {
        final Random random = new Random();
        final Number number = random.nextInt(Integer.MIN_VALUE, 0);
        final StrictlyNegativeDouble actual =
                StrictlyNegativeDouble.Companion.createOrNull(number);
        Assertions.assertNotNull(actual);
    }
}
