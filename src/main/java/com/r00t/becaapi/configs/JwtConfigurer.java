package com.r00t.becaapi.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfigurer {
    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.prefix}")
    private String prefix;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.secret-key}")
    private String secret;

    public String getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix + " ";
    }

    public long getExpiration() {
        return expiration;
    }

    public String getSecret() {
        return secret;
    }
}
