package com.fashionworld.login.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        log.info("Received request to send OTP for phone number: {}", phoneNumberDTO.getPhoneNumber());

        int otp = otpService.generateOTP();
        log.info("Generated OTP: {}", otp);

        OtpResponseDto response = otpService.sendOTPviaSMS(phoneNumberDTO.getPhoneNumber(), otp);
        log.info("Sent OTP response: {}", response);

        return response;
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<String> validateOTP(@RequestBody OtpValidationRequest otpValidationRequest) {
        String userPhoneNumber = "+919640620864";
        log.info("Received request to validate OTP for user: {}", userPhoneNumber);

        boolean isValid = otpService.validateOTP(userPhoneNumber, otpValidationRequest.getOtpNumber());
        
        if (isValid) {
            log.info("OTP is valid for user: {}", userPhoneNumber);
            return ResponseEntity.ok("OTP is valid.");
        } else {
            log.warn("Invalid OTP received for user: {}", userPhoneNumber);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP must be exactly 6 digits.");
        }
    }
    

    @GetMapping(value = "/generateQr/{content}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generateQrCode(@PathVariable String content) throws IOException {
    	int width = 200;
    	int height = 200;
    	return otpService.generateQrCodeImage(content,width,height);
    }
}
