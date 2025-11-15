package com.kuza.kuzasokoni.security.jwtRequestFilter;

    import com.kuza.kuzasokoni.common.authentication.services.CustomerUserDetailsService;
    import com.kuza.kuzasokoni.security.jwtUtil.JwtUtil;
    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
    import org.springframework.stereotype.Component;
    import org.springframework.web.filter.OncePerRequestFilter;

    import java.io.IOException;

    @Component
    @RequiredArgsConstructor
    public class JwtRequestFilter extends OncePerRequestFilter {

        private final CustomerUserDetailsService userDetailsService;
        private final JwtUtil jwtUtil;

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain filterChain)
                throws ServletException, IOException {

            final String authHeader = request.getHeader("Authorization");
            String username = null;
            String jwtToken = null;

            // 🔹 Angalia kama header ipo na inaanza na "Bearer "
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwtToken = authHeader.substring(7);
                username = jwtUtil.extractUsername(jwtToken);
            }

            // 🔹 Hakikisha user bado hajathibitishwa kwenye context
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwtToken, userDetails)) {
                    // 🔹 Tengeneza authentication object
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                    // 🔹 Weka authentication kwenye context
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            // 🔹 Endelea na filter chain
            filterChain.doFilter(request, response);
        }
    }
