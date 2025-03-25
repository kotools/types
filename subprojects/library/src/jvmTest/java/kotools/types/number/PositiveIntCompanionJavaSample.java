package kotools.types.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
class PositiveIntCompanionJavaSample {
    @Test
    void createOrNull() {
        final PositiveInt number = PositiveInt.Companion.createOrNull(23);
        Assertions.assertNotNull(number);
    }
}
