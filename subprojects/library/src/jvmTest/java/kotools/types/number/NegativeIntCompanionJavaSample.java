package kotools.types.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
class NegativeIntCompanionJavaSample {
    @Test
    void createOrNull() {
        final NegativeInt number = NegativeInt.Companion.createOrNull(-7);
        Assertions.assertNotNull(number);
    }
}
