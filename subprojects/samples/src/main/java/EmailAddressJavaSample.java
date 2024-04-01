package org.kotools.types;

class EmailAddressJavaSample {
    void toString_override() {
        final Object value = "contact@kotools.org";
        final EmailAddress address = EmailAddress.fromString(value);
        final String addressAsString = address.toString();
        System.out.println(addressAsString == value); // true
    } // END
}
