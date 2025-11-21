package com.kuza.kuzasokoni.domain.authentication.dto.query;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Profile profile;
    private List<String> roles;
    private List<String> permissions;

    @Data
    @AllArgsConstructor
    public static class Profile {
        private String username;
        private String email;
        private String phoneNumber;
        private String name;
        private String userType;
        private Long userId;
    }
}
