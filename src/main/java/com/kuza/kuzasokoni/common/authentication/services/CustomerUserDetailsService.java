package com.kuza.kuzasokoni.common.authentication.services;

import com.kuza.kuzasokoni.common.authentication.entity.User;
import com.kuza.kuzasokoni.common.authentication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + phoneNumber));

        Set<GrantedAuthority> authorities = user.getUserEntities().stream()
                .flatMap(entity -> entity.getRoles().stream())
                .flatMap(role -> role.getPermissions().stream())
                .map(perm -> new SimpleGrantedAuthority(perm.getName()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                user.getPhoneNumber(),
                user.getPassword(),
                authorities
        );
    }
}