package com.kuza.kuzasokoni.domain.authentication.services.query;

import com.kuza.kuzasokoni.domain.authentication.entity.User;
import com.kuza.kuzasokoni.domain.authentication.repositories.UserRepository;
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
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByPhoneNumber(username)
                    .or(() -> userRepository.findByEmail(username))
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

            Set<GrantedAuthority> authorities = user.getUserEntities().stream()
                    .flatMap(entity -> entity.getRoles().stream())
                    .flatMap(role -> role.getPermissions().stream())
                    .map(perm -> new SimpleGrantedAuthority(perm.getName()))
                    .collect(Collectors.toSet());

            return new org.springframework.security.core.userdetails.User(
                    user.getPhoneNumber() != null ? user.getPhoneNumber() : user.getEmail(),
                    user.getPassword() != null ? user.getPassword() : user.getPin(),
                    authorities
            );
        }
    }
