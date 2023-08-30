package com.fashionworld.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fashionworld.login.dto.OtpResponseDto;
import com.fashionworld.login.dto.OtpValidationRequest;
import com.fashionworld.login.dto.PhoneNumberDto;
import com.fashionworld.login.service.OTPService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/otp")
@Slf4j
public class OtpController {

    @Autowired
    private OTPService otpService;

    @PostMapping("/send-otp")
    public OtpResponseDto sendOTP(@RequestBody PhoneNumberDto phoneNumberDTO) {
        int otp = otpService.generateOTP();
        return otpService.sendOTPviaSMS(phoneNumberDTO.getPhoneNumber(), otp);
        
    }
    @PostMapping("/validate-otp")
    public ResponseEntity<String> validateOTP(@RequestBody OtpValidationRequest OtpValidationRequest) {
    	String userPhoneNumber = "+919640620864";
        boolean isValid = otpService.validateOTP(userPhoneNumber,OtpValidationRequest.getOtpNumber());

        if (isValid) {
            return ResponseEntity.ok("OTP is valid.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
        }
    }
   
}
