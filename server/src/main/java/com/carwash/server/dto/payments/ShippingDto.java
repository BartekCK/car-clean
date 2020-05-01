package com.carwash.server.dto.payments;

import lombok.Setter;
import lombok.Value;

@Setter
@Value
public class ShippingDto {

    private String name;
    private String surname;
    private String street;
    private String additional;
    private String city;
    private String postalCode;
    private String phone;
    private String countryCode = "PL";


}
