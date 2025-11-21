package com.kuza.kuzasokoni.domain.authentication.services.command;

import com.kuza.kuzasokoni.domain.authentication.entity.Otp;
import com.kuza.kuzasokoni.domain.authentication.entity.User;
import com.kuza.kuzasokoni.domain.authentication.repositories.OtpRepository;
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
    private final EmailService emailService;

    private static final long OTP_EXPIRY_MINUTES = 3;
    private static final int OTP_LENGTH = 6;

    /**
     * Generate 6-digit OTP and send via SMS
     *
     * @return
     */
    public Otp  generateAndSendOtp(User user) {
        String otpCode = generateRandomOtp();

        Otp otp = otpRepository.findByUser(user).orElse(new Otp());
        otp.setUser(user);
        otp.setCode(otpCode);
        otp.setExpiryTime(System.currentTimeMillis() + OTP_EXPIRY_MINUTES * 60 * 1000);
        otp.setVerified(false);
        otpRepository.save(otp);

        String message = "Your OTP is " + otpCode + ". Use it to log in. Valid for 5 minutes.";

        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isBlank()) {
            try {
                smsService.sendSms(user.getPhoneNumber(), message);
                log.info("SMS OTP sent to: {}", user.getPhoneNumber());
            } catch (Exception e) {
                log.error("Failed to send SMS to {}: {}", user.getPhoneNumber(), e.getMessage());
                // Don't fail login – SMS ni optional
            }
        }

        // Send Email – SAFELY
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            try {
                emailService.sendEmail(user.getEmail(), "Your Kuza Sokoni Login OTP", message);
                log.info("Email OTP sent to: {}", user.getEmail());
            } catch (Exception e) {
                log.error("Failed to send email to {}: {}", user.getEmail(), e.getMessage(), e);
                // DON'T throw – email failure shouldn't block login
            }
        }

        return otp;
    }
        //smsService.sendSms(user.getPhoneNumber(), message);*/

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

    public void generateAndSendOtpEmailOnly(User user) {
        String otpCode = generateRandomOtp();

        Otp otp = otpRepository.findByUser(user).orElse(new Otp());
        otp.setUser(user);
        otp.setCode(otpCode);
        otp.setExpiryTime(System.currentTimeMillis() + OTP_EXPIRY_MINUTES * 60 * 1000);
        otp.setVerified(false);
        otpRepository.save(otp);

        String message = "Your Kuza Sokoni OTP is " + otpCode + ". Valid for 3 minutes.";


        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            try {
                emailService.sendEmail(
                        user.getEmail(),
                        "Your Login OTP - Kuza Sokoni",
                        message
                );
                log.info("ENTITY LOGIN: OTP sent via EMAIL ONLY to {}", user.getEmail());
            } catch (Exception e) {
                log.error("Failed to send OTP email to entity: {}", user.getEmail(), e);
                throw new RuntimeException("Failed to send OTP via email", e);

            }
        } else {
            throw new RuntimeException("User has no email address");
        }
    }

    /**
     * Generate secure 6-digit OTP using ThreadLocalRandom
     */
    private String generateRandomOtp() {
        int otp = ThreadLocalRandom.current().nextInt(1_000_000);
        return String.format("%0" + OTP_LENGTH + "d", otp); // Ensures 6 digits (e.g., 001234)
    }
}
