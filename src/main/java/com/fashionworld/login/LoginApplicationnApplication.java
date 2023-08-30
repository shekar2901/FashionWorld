package com.fashionworld.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fashionworld.login.config.TwilioConfig;
import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class LoginApplicationnApplication {

	@Autowired
	private TwilioConfig twilioConfig;
	
	@PostConstruct
	public void setUp() {
		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
	}
	
	public static void main(String[] args) {
		SpringApplication.run(LoginApplicationnApplication.class, args);
	}

}
