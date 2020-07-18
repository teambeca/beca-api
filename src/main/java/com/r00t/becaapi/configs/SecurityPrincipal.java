package com.r00t.becaapi.configs;

import com.r00t.becaapi.models.UserLoginCredentials;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class SecurityPrincipal implements UserDetails {
    private UserLoginCredentials userLoginCredentials;

    public SecurityPrincipal(UserLoginCredentials userLoginCredentials) {
        this.userLoginCredentials = userLoginCredentials;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(
                new SimpleGrantedAuthority(userLoginCredentials.getRole()));
    }

    public UserLoginCredentials getUserLoginCredentials() {
        return userLoginCredentials;
    }

    public String getId() {
        return userLoginCredentials.getId();
    }

    @Override
    public String getPassword() {
        return userLoginCredentials.getPassword();
    }

    @Override
    public String getUsername() {
        return userLoginCredentials.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userLoginCredentials.isActive();
    }
}
