package kotools.types.text;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NotBlankStringJavaCompatibilityTest {
    @Test
    public void toNotBlankString_should_pass_with_a_not_blank_String() {
        final String value = "hello world";
        final NotBlankString result =
                (NotBlankString) NotBlankStringKt.of(value);
        Assertions.assertEquals(result.toString(), value);
    }

    @Test
    public void toNotBlankString_should_fail_with_a_blank_String() {
        Assertions.assertThrows(ClassCastException.class, () -> {
            final NotBlankString result =
                    (NotBlankString) NotBlankStringKt.of(" ");
            Assertions.fail(
                    "Casting the " + result + " to NotBlankString should fail."
            );
        });
    }
}
