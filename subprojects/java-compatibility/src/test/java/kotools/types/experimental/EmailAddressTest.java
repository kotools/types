package kotools.types.experimental;

import org.junit.jupiter.api.Test;

public class EmailAddressTest {
    @Test
    public void constructor_should_pass_with_String() {
        new EmailAddress("contact@kotools.org");
    }
}
