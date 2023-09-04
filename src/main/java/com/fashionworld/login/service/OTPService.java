package com.fashionworld.login.service;


import com.fashionworld.login.config.TwilioConfig;
import com.fashionworld.login.dto.OtpResponseDto;
import com.fashionworld.login.dto.OtpStatus;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.extern.slf4j.Slf4j;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OTPService {

    @Autowired
    private TwilioConfig twilioConfig;

    public int generateOTP() {
        int otp = (int) (Math.random() * 900_000) + 100_000;
        log.info("Generated OTP: {}", otp);
        return otp;
    }

    private Map<String, Integer> otpMap = new HashMap<>();

    public OtpResponseDto sendOTPviaSMS(String phoneNumber, int otp) {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());

        String messageBody = "Your OTP is: " + otp;
        Message message = Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber("+15738594753"),
                messageBody
        ).create();

        log.info("Sent OTP via SMS to phone number: {}", phoneNumber);

        otpMap.put(phoneNumber, otp);

        OtpResponseDto otpResponseDto = new OtpResponseDto(OtpStatus.Delivered, messageBody);
        log.info("Created OTP response DTO: {}", otpResponseDto);

        return otpResponseDto;
    }

    public boolean validateOTP(String phoneNumber, int userEnteredOTP) {
        Integer generatedOTP = otpMap.get(phoneNumber);
        if (generatedOTP != null && generatedOTP == userEnteredOTP) {
            log.info("Validated OTP for phone number: {}", phoneNumber);
            return true;
        } else {
            log.warn("Invalid OTP for phone number: {}", phoneNumber);
            return false;
        }
    }

	public byte[] generateQrCodeImage(String content, int width, int height) throws IOException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		Map<EncodeHintType, Object> hintsMap = new HashMap<>();
        hintsMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hintsMap);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate QR code image.", e);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage qrImage = toBufferedImage(bitMatrix);
        try {
            ImageIO.write(qrImage, "png", outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write QR code image to output stream.", e);
        }

        return outputStream.toByteArray();
	}
	
	private BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (matrix.get(x, y)) {
                    graphics.fillRect(x, y, 1, 1);
                }
            }
        }
        return image;
    }
}