package com.kuza.kuzasokoni.common.authentication.controller;

import com.kuza.kuzasokoni.common.authentication.Dto.OtpVerifyRequest;
import com.kuza.kuzasokoni.common.authentication.Response.AuthResponse;
import com.kuza.kuzasokoni.common.authentication.entity.Otp;
import com.kuza.kuzasokoni.common.authentication.entity.User;
import com.kuza.kuzasokoni.common.authentication.repositories.UserRepository;
import com.kuza.kuzasokoni.common.authentication.services.CustomerUserDetailsService;
import com.kuza.kuzasokoni.common.authentication.services.OtpService;
import com.kuza.kuzasokoni.domain.client.enums.VerificationStatus;
import com.kuza.kuzasokoni.security.jwtUtil.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OtpController {

    private final UserRepository userRepository;
    private final OtpService otpService;
    private final CustomerUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    // 🔹 Verify OTP endpoint (returns JWT if successful)
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpVerifyRequest request) {
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!otpService.verifyOtp(user, request.getOtp())) {
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
        }

        user.setIsVerified(VerificationStatus.VERIFIED);
        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getPhoneNumber());
        String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @GetMapping("/test-otp/{phone}")
    public ResponseEntity<?> testOtp(@PathVariable String phone) {
        User user = userRepository.findByPhoneNumber(phone)
                .orElse(null);
        if (user == null) return ResponseEntity.badRequest().body("User not found");

        otpService.generateAndSendOtp(user);
        return ResponseEntity.ok("OTP sent to " + phone);
    }
}
