package com.r00t.becaapi.services;

import com.r00t.becaapi.configs.JwtConfigurer;
import com.r00t.becaapi.configs.SecurityPrincipal;
import com.r00t.becaapi.models.UserLoginCredentials;
import com.r00t.becaapi.repository.UserLoginCredentialsRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class SecurityService implements UserDetailsService {
    @Autowired
    private JwtConfigurer jwtConfigurer;
    @Autowired
    private UserLoginCredentialsRepository userLoginCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userLoginCredentialsRepository.findByUsername(username)
                .map(SecurityPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtConfigurer.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    public Map<String, Object> createToken(UserLoginCredentials userLoginCredentials) {
        Map<String, Object> claims = new HashMap<>();

        long tokenStart = System.currentTimeMillis();
        long tokenEnd = tokenStart + (jwtConfigurer.getExpiration() * 1000);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(userLoginCredentials.getUsername())
                .setIssuedAt(new Date(tokenStart))
                .setExpiration(new Date(tokenEnd))
                .signWith(SignatureAlgorithm.HS512, jwtConfigurer.getSecret())
                .compact();

        Map<String, Object> retMap = new HashMap<>();
        retMap.put("access_token", token);
        retMap.put("token_type", jwtConfigurer.getPrefix().trim());
        retMap.put("expires_in", jwtConfigurer.getExpiration());
        retMap.put("role", userLoginCredentials.getRole());
        return retMap;
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getAllClaims(token));
    }

    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Boolean isTokenExpired(String token) {
        return getClaimFromToken(token, Claims::getExpiration)
                .before(new Date());
    }
}
