package com.example.EcommerceApp.validation;

import org.springframework.stereotype.Component;

@Component
public class GstValidation {
    private static final String pattern="^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$",message = "The first 2 digits denote the State Code (01-37) as defined in the Code List for Land Regions.\n" +
            "\n" +
            "The next 10 characters pertain to PAN Number in AAAAA9999X format.\n" +
            "\n" +
            "13th character indicates the number of registrations an entity has within a state for the same PAN.\n" +
            "\n" +
            "14th character is currently defaulted to \"Z\"\n" +
            "\n" +
            "15th character is a checksum digit";

    public boolean validateGst(String gst ) {
        return gst.matches(pattern);
    }
}
