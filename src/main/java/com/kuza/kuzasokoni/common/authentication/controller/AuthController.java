package com.kuza.kuzasokoni.common.authentication.controller;


import com.kuza.kuzasokoni.common.authentication.Dto.CustomerLoginRequest;
import com.kuza.kuzasokoni.common.authentication.Dto.LoginRequest;
import com.kuza.kuzasokoni.common.authentication.Dto.RegisterRequest;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;

    // 🔹 Register endpoint (no OTP generated here)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body("Email already in use");
        }

        if(userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()){
            return ResponseEntity.badRequest().body("Phone number already in use");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully. Please login to receive OTP.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        // Generate OTP and send via SMS
        otpService.generateAndSendOtp(user);

        return ResponseEntity.ok("OTP sent to your phone");
    }

    @PostMapping("/customer/login")
    public ResponseEntity<?> loginCustomer(@RequestBody CustomerLoginRequest req) {
        User user = userRepository.findByPhoneNumber(req.getPhoneNumber())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setPhoneNumber(req.getPhoneNumber());
                    newUser.setPin(passwordEncoder.encode(req.getPin()));
                    newUser.setIsVerified(VerificationStatus.UNVERIFIED);
                    return userRepository.save(newUser);
                });

        if (!passwordEncoder.matches(req.getPin(), user.getPin()))
            return ResponseEntity.badRequest().body("Invalid PIN");

        otpService.generateAndSendOtp(user);
        return ResponseEntity.ok("OTP sent");
    }

}


