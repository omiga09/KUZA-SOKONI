package com.kuza.kuzasokoni.domain.authentication.controller.command;

import com.kuza.kuzasokoni.domain.authentication.dto.command.OtpVerifyRequest;
import com.kuza.kuzasokoni.domain.authentication.entity.Role;
import com.kuza.kuzasokoni.domain.authentication.dto.query.AuthResponse;
import com.kuza.kuzasokoni.domain.authentication.entity.User;
import com.kuza.kuzasokoni.domain.authentication.repositories.UserRepository;
import com.kuza.kuzasokoni.domain.authentication.services.query.CustomerUserDetailsService;
import com.kuza.kuzasokoni.domain.authentication.services.command.OtpService;
import com.kuza.kuzasokoni.security.jwtUtil.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/otp")
@RequiredArgsConstructor
public class OtpController {

    private final UserRepository userRepository;
    private final OtpService otpService;
    private final CustomerUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;



    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyEntityOtp(@RequestBody OtpVerifyRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!otpService.verifyOtp(user, request.getOtp())) {
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());


        Set<String> roleNames = user.getUserEntities().stream()
                .flatMap(ue -> ue.getRoles().stream())
                .map(Role::getName)
                .collect(Collectors.toSet());


        List<String> permissionNames = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .sorted() // optional: sort kwa uzuri
                .collect(Collectors.toList());


        String displayName = user.getUsername() != null ? user.getUsername() : user.getPhoneNumber();

       if (user.getClient() != null) {
            displayName = user.getClient().getFirstName() + " " +
                    (user.getClient().getSecondName() != null ? user.getClient().getSecondName() + " " : "") +
                    user.getClient().getLastName();
        }

        AuthResponse.Profile profile = new AuthResponse.Profile(
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                displayName,
                "ENTITY",
                user.getId()
        );

        String jwt = jwtUtil.generateToken(userDetails, "ENTITY");

        AuthResponse response = new AuthResponse(
                jwt,
                profile,
                new ArrayList<>(roleNames),
                permissionNames
        );
        ;

        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-customer-otp")
    public ResponseEntity<?> verifyCustomerOtp(@RequestBody OtpVerifyRequest request) {

        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .or(() -> userRepository.findByEmail(request.getEmail()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!otpService.verifyOtp(user, request.getOtp())) {
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getPhoneNumber());

        Set<String> roleNames = user.getUserEntities().stream()
                .flatMap(ue -> ue.getRoles().stream())
                .map(Role::getName)
                .collect(Collectors.toSet());

        List<String> permissionNames = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .sorted()
                .collect(Collectors.toList());

        String displayName = user.getPhoneNumber();
        if (user.getClient() != null) {
            displayName = user.getClient().getFirstName() + " " +
                    (user.getClient().getSecondName() != null ? user.getClient().getSecondName() + " " : "") +
                    user.getClient().getLastName();
        }

        AuthResponse.Profile profile = new AuthResponse.Profile(
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                displayName,
                "CUSTOMER",
                user.getId()
        );

        String jwt = jwtUtil.generateToken(userDetails, "CUSTOMER");

        AuthResponse response = new AuthResponse(jwt, profile, new ArrayList<>(roleNames), permissionNames);

        return ResponseEntity.ok(response);
    }
}
