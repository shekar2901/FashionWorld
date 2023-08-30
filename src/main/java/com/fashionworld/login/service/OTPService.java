package com.fashionworld.login.service;


import com.fashionworld.login.config.TwilioConfig;
import com.fashionworld.login.dto.OtpResponseDto;
import com.fashionworld.login.dto.OtpStatus;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OTPService {

    @Autowired
    private TwilioConfig twilioConfig;

    public int generateOTP() {
        
        return (int) (Math.random() * 900_000) + 100_000;
    }
    
    private Map<String, Integer> otpMap = new HashMap<>();

    public OtpResponseDto sendOTPviaSMS(String phoneNumber, int otp) {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
        
        OtpResponseDto otpResponseDto = null;

        String messageBody = "Your OTP is: " + otp;
        Message message = Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber("+15738594753"),
                messageBody
        ).create();
        otpMap.put(phoneNumber, otp);
        
        otpResponseDto = new OtpResponseDto(OtpStatus.Delivered,messageBody);
        
        return otpResponseDto;
    }

    public boolean validateOTP(String  phoneNumber, int userEnteredOTP) {
    	Integer generatedOTP = otpMap.get(phoneNumber);
    	return generatedOTP != null && generatedOTP == userEnteredOTP;
    }
}
