package com.kuza.kuzasokoni.security.jwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

    @Component
    public class JwtUtil {

        private final String SECRET_KEY = "CW4XAMzsfGInhHYOgCDj1tQToNbfc3SGwHw602yibCSDed63F3pQWcpw6ogFkrTyvkL/hOpiRJuEOPG0mYSSRA==";
        private final Key key = Keys.hmacShaKeyFor(io.jsonwebtoken.io.Decoders.BASE64.decode(SECRET_KEY));

        // 🔹 Tengeneza token
        public String generateToken(UserDetails userDetails) {
            Map<String, Object> claims = new HashMap<>();
            return createToken(claims, userDetails.getUsername());
        }

        // 🔹 Tumia claims na username kutengeneza token
        private String createToken(Map<String, Object> claims, String subject) {
            long expirationTime = 1000 * 60 * 60 * 10; // 10 hours

            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                    .compact();
        }

        // 🔹 Toa username kutoka JWT
        public String extractUsername(String token) {
            return extractClaim(token, Claims::getSubject);
        }

        // 🔹 Toa expiration
        public Date extractExpiration(String token) {
            return extractClaim(token, Claims::getExpiration);
        }

        // 🔹 Kutoa claims (kama subject, exp, n.k)
        public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        }

        private Claims extractAllClaims(String token) {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        }

        private Boolean isTokenExpired(String token) {
            return extractExpiration(token).before(new Date());
        }

        // 🔹 Thibitisha kama token ni valid
        public Boolean validateToken(String token, UserDetails userDetails) {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }
    }



