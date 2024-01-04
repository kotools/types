package kotools.types.experimental;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmailAddressTest {
    @Test
    public void constructor_should_pass() {
        Assertions.assertDoesNotThrow(
                () -> new EmailAddress("contact@kotools.org")
        );
    }

    @Test
    public void equals_should_pass() {
        final String text = "contact@kotools.org";
        final EmailAddress first = new EmailAddress(text);
        final EmailAddress second = new EmailAddress(text);
        Assertions.assertEquals(first, second);
    }

    @Test
    public void hashCode_should_pass() {
        final String text = "contact@kotools.org";
        final int first = new EmailAddress(text)
                .hashCode();
        final int second = new EmailAddress(text)
                .hashCode();
        Assertions.assertEquals(first, second);
    }

    @Test
    public void toString_should_pass() {
        final String expected = "contact@kotools.org";
        final EmailAddress address = new EmailAddress(expected);
        final String actual = address.toString();
        Assertions.assertEquals(expected, actual);
    }
}
