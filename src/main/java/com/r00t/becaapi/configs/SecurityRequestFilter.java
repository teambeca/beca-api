package com.r00t.becaapi.configs;

import com.r00t.becaapi.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtConfigurer jwtConfigurer;
    @Autowired
    private SecurityService securityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(jwtConfigurer.getHeader());

        String username = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith(jwtConfigurer.getPrefix())) {
            String token = requestTokenHeader.replace(jwtConfigurer.getPrefix(), "");

            try {
                username = securityService.getEmailFromToken(token);
            } catch (Exception ignored) {
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                SecurityPrincipal principal = (SecurityPrincipal) securityService.loadUserByUsername(username);
                if (principal.isEnabled()) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            principal.getUsername(), principal.getId(), principal.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (Exception ignored) {
            }
        }

        filterChain.doFilter(request, response);
    }
}
