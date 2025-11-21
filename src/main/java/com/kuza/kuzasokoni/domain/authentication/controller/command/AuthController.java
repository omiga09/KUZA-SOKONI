package com.kuza.kuzasokoni.domain.authentication.controller.command;


import com.kuza.kuzasokoni.domain.authentication.config.PasswordGenerator;
import com.kuza.kuzasokoni.domain.authentication.dto.command.CustomerLoginRequest;
import com.kuza.kuzasokoni.domain.authentication.dto.command.EntityLoginRequest;
import com.kuza.kuzasokoni.domain.authentication.dto.command.RegisterRequest;
import com.kuza.kuzasokoni.domain.authentication.dto.command.ResetPasswordRequest;
import com.kuza.kuzasokoni.domain.authentication.entity.User;
import com.kuza.kuzasokoni.domain.authentication.entity.UserEntity;
import com.kuza.kuzasokoni.domain.authentication.repositories.UserEntityRepository;
import com.kuza.kuzasokoni.domain.authentication.repositories.UserRepository;
import com.kuza.kuzasokoni.domain.authentication.services.command.EmailService;
import com.kuza.kuzasokoni.domain.authentication.services.command.OtpService;
import com.kuza.kuzasokoni.domain.client.enums.VerificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final UserEntityRepository userEntityRepository;
    private final OtpService otpService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        if (userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            return ResponseEntity.badRequest().body("Phone number already in use");
        }

        // Generate random password
        String generatedPassword = PasswordGenerator.generatePassword();

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setSecondName(request.getSecondName());
        user.setLastName(request.getLastName());

        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        user.setPassword(passwordEncoder.encode(generatedPassword));
       // user.setPin(request.getPin());

        user.setUserType(request.getUserType());
        user.setEntityType(request.getEntityType());

        // Default verification
        user.setIsVerified(VerificationStatus.VERIFIED);

        // Default enabled
        user.setEnabled(true);

        // Password expiry
        user.setPasswordExpiryDate(LocalDate.now().plusDays(90));
        user.setPasswordExpired(false);

        userRepository.save(user);

        UserEntity ue = new UserEntity();
        ue.setUser(user);

        ue.setEntityType(request.getEntityType().get(0));
        ue.setEntityId(0L);

        userEntityRepository.save(ue);

        // Send password to email
        emailService.sendEmail(
                request.getEmail(),
                "Your Kuza Sokoni Account Password",
                "Hello " + request.getFirstName() + ",\n\n"
                        + "Your account has been created successfully.\n"
                        + "Your temporary password is:\n\n"
                        + generatedPassword + "\n\n"
                        + "Please change your password after logging in.\n\n"
                        + "Regards,\nKuza Sokoni Team"
        );

        return ResponseEntity.ok("User registered successfully. Password sent to email.");
    }


    @PostMapping("/entity/login")
    public ResponseEntity<?> loginEntity(@RequestBody EntityLoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        // Check if password expired
        if (user.isPasswordExpired() || user.getPasswordExpiryDate().isBefore(LocalDate.now())) {

            // Mark as expired if date passed
            user.setPasswordExpired(true);
            userRepository.save(user);

            return ResponseEntity.status(403).body(
                    "Your password has expired. Please reset your password to continue."
            );
        }

        // Validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        //If password ok and not expired → send OTP
        otpService.generateAndSendOtpEmailOnly(user);

        return ResponseEntity.ok("OTP sent to your email");
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

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest req) {

        Optional<User> opt = userRepository.findByEmail(req.getEmail());
        if(opt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = opt.get();

        // Set new password
        user.setPassword(passwordEncoder.encode(req.getNewPassword()));

        // Set expiry 90 days ahead
        user.setPasswordExpiryDate(LocalDate.now().plusDays(90));
        user.setPasswordExpired(false);

        userRepository.save(user);

        return ResponseEntity.ok("Password reset successfully.");
    }
    // AuthController.java - ongeza hii tu



}


