package com.r00t.becaapi.services;

import com.r00t.becaapi.exceptions.AlreadyExistException;
import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.models.UserLoginCredentials;
import com.r00t.becaapi.repository.UserLoginCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserLoginCredentialsRepository userLoginCredentialsRepository;

    public boolean checkUsernameExist(String username) {
        return userLoginCredentialsRepository.findByUsername(username).isPresent();
    }

    public UserLoginCredentials getCredentialsById(String id) throws NotFoundException {
        return userLoginCredentialsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("userService.getCredentialsById"));
    }

    public UserLoginCredentials insertCredentials(UserLoginCredentials requestCredentials) throws AlreadyExistException {
        if (checkUsernameExist(requestCredentials.getUsername()))
            throw new AlreadyExistException("userService.insertCredentials");

        UserLoginCredentials u = new UserLoginCredentials();
        u.setUsername(requestCredentials.getUsername());
        u.setPassword(requestCredentials.getPassword());
        u.setRole("ROLE_USER");
        u.setActive(true);
        u.setScore(0);
        u.setCreationDate(System.currentTimeMillis());
        return userLoginCredentialsRepository.insert(u);
    }

    public UserLoginCredentials updateCredentials(UserLoginCredentials requestCredentials) throws NotFoundException {
        UserLoginCredentials u = getCredentialsById(requestCredentials.getId());
        u.setUsername(requestCredentials.getUsername());
        u.setPassword(requestCredentials.getPassword());
        u.setUpdatedDate(System.currentTimeMillis());
        return userLoginCredentialsRepository.save(u);
    }
}
