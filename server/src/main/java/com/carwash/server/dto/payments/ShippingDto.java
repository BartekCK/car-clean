package com.carwash.server.dto.payments;

import com.carwash.server.models.Adress;
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

    public static ShippingDto build(Adress adress) {
        if (adress != null)
            return new ShippingDto(adress.getName(), adress.getSurname(), adress.getStreet(), adress.getAdditional(), adress.getCity(), adress.getPostalCode(), adress.getPhone());
        else
            return null;
    }
}



