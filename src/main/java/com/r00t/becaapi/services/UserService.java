package com.r00t.becaapi.services;

import com.r00t.becaapi.models.UserLoginCredentials;
import com.r00t.becaapi.repository.UserLoginCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserLoginCredentialsRepository userLoginCredentialsRepository;

    public UserLoginCredentials getCredentialsById(String id) {
        return userLoginCredentialsRepository.findById(id)
                .orElseThrow();
    }

    public UserLoginCredentials insertCredentials(UserLoginCredentials requestCredentials) {
        UserLoginCredentials u = new UserLoginCredentials();
        u.setUsername(requestCredentials.getUsername());
        u.setPassword(requestCredentials.getPassword());
        u.setRole("ROLE_USER");
        u.setActive(true);
        u.setScore(0);
        u.setCreationDate(System.currentTimeMillis());
        return userLoginCredentialsRepository.insert(u);
    }

    public UserLoginCredentials updateCredentials(UserLoginCredentials requestCredentials) {
        UserLoginCredentials u = getCredentialsById(requestCredentials.getId());
        u.setUsername(requestCredentials.getUsername());
        u.setPassword(requestCredentials.getPassword());
        u.setUpdatedDate(System.currentTimeMillis());
        return userLoginCredentialsRepository.save(u);
    }
}
