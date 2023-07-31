package kotools.types.text;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NotBlankStringJavaCompatibilityTest {
    @Test
    public void toNotBlankString_on_String() {
        String value = "hello world";
        NotBlankString result = (NotBlankString) NotBlankStringKt.of(value);
        Assertions.assertEquals(result.toString(), value);
    }
}
