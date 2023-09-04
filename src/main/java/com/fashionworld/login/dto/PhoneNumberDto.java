package com.fashionworld.login.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberDto {

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid phone number format")
    private String phoneNumber;

}
