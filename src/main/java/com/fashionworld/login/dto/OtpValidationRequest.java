package com.fashionworld.login.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpValidationRequest {

	@Size(min = 6, max = 6, message = "OTP must be exactly 6 digits")
	private int otpNumber;
}
