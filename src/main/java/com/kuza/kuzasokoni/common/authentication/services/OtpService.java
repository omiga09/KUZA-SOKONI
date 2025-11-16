package com.kuza.kuzasokoni.common.authentication.services;

import com.kuza.kuzasokoni.common.authentication.entity.Otp;
import com.kuza.kuzasokoni.common.authentication.entity.User;
import com.kuza.kuzasokoni.common.authentication.repositories.OtpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {

    private final OtpRepository otpRepository;
    private final SmsService smsService;

    private static final long OTP_EXPIRY_MINUTES = 3;
    private static final int OTP_LENGTH = 6;

    /**
     * Generate 6-digit OTP and send via SMS
     */
    public void generateAndSendOtp(User user) {
        String otpCode = generateRandomOtp();

        Otp otp = otpRepository.findByUser(user).orElse(new Otp());
        otp.setUser(user);
        otp.setCode(otpCode);
        otp.setExpiryTime(System.currentTimeMillis() + OTP_EXPIRY_MINUTES * 60 * 1000);
        otp.setVerified(false);
        otpRepository.save(otp);

        String message = "Your OTP is " + otpCode + ". Use it to log in. Valid for 5 minutes.";

        smsService.sendSms(user.getPhoneNumber(), message);
    }


    /**
     * Verify OTP code
     */
    public boolean verifyOtp(User user, String code) {
        return otpRepository.findByUserAndCode(user, code)
                .filter(otp -> !otp.isVerified())
                .filter(otp -> otp.getExpiryTime() >= System.currentTimeMillis())
                .map(otp -> {
                    otp.setVerified(true);
                    otpRepository.save(otp);
                    log.info("OTP verified for user: {}", user.getUsername());
                    return true;
                })
                .orElseGet(() -> {
                    log.warn("Invalid or expired OTP for user: {}", user.getUsername());
                    return false;
                });
    }

    /**
     * Generate secure 6-digit OTP using ThreadLocalRandom
     */
    private String generateRandomOtp() {
        int otp = ThreadLocalRandom.current().nextInt(1_000_000);
        return String.format("%0" + OTP_LENGTH + "d", otp); // Ensures 6 digits (e.g., 001234)
    }
}
