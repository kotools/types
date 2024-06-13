package org.kotools.types;

class EmailAddressJavaSample {
    void equals_override() {
        final Object value = "contact@kotools.org";
        final EmailAddress first = EmailAddress.fromString(value);
        final EmailAddress second = EmailAddress.fromString(value);
        final boolean result = first.equals(second);
        System.out.println(result); // true
    } // END

    void hashCode_override() {
        final Object value = "contact@kotools.org";
        final int first = EmailAddress.fromString(value)
                .hashCode();
        final int second = EmailAddress.fromString(value)
                .hashCode();
        final boolean result = first == second;
        System.out.println(result); // true
    } // END

    void toString_override() {
        final Object value = "contact@kotools.org";
        final EmailAddress address = EmailAddress.fromString(value);
        final String addressAsString = address.toString();
        System.out.println(addressAsString == value); // true
    } // END
}
