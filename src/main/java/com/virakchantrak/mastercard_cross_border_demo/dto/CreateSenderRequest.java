package com.virakchantrak.mastercard_cross_border_demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSenderRequest {

    @NotBlank(message = "Full name is required")
    @Size(max = 150)
    private String fullName;

    @Email(message = "Invalid email format")
    @Size(max = 150)
    private String email;

    @NotBlank(message = "Phone is required")
    @Size(max = 30)
    private String phone;

    @NotBlank(message = "Country code is required")
    @Size(min = 2, max = 3)
    private String countryCode;
}

