package com.r00t.becaapi.services;

import com.r00t.becaapi.exceptions.AlreadyExistException;
import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.exceptions.ServiceUnavailableException;
import com.r00t.becaapi.models.ProfileCredentials;
import com.r00t.becaapi.models.UserLoginCredentials;
import com.r00t.becaapi.models.requests.UserLoginRequestCredentials;
import com.r00t.becaapi.repository.UserLoginCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserLoginCredentialsRepository userLoginCredentialsRepository;
    @Autowired
    private ProfileService profileService;

    public boolean checkUsernameExist(String username) {
        return userLoginCredentialsRepository.findByUsername(username).isPresent();
    }

    public Integer countCredentialsByScoreGreaterThan(long score) throws ServiceUnavailableException {
        return userLoginCredentialsRepository.countAllByScoreGreaterThanAndActive(score, true)
                .orElseThrow(() -> new ServiceUnavailableException("userService.countCredentialsByScoreGreaterThan"));
    }

    public Page<UserLoginCredentials> getCredentials(int page) {
        return userLoginCredentialsRepository.findAll(PageRequest.of(page, 50));
    }

    public List<UserLoginCredentials> getTopCredentials() throws ServiceUnavailableException {
        return userLoginCredentialsRepository.findTop15ByActiveOrderByScoreDesc(true)
                .orElseThrow(() -> new ServiceUnavailableException("userService.getTopCredentials"));
    }

    public List<UserLoginCredentials> getCredentialsByUsername(String username) throws ServiceUnavailableException {
        return userLoginCredentialsRepository.findAllByUsername(username)
                .orElseThrow(() -> new ServiceUnavailableException("userService.getCredentialsByUsername"));
    }

    public List<UserLoginCredentials> getCredentialsByRole(String role) throws ServiceUnavailableException {
        return userLoginCredentialsRepository.findAllByRole(role)
                .orElseThrow(() -> new ServiceUnavailableException("userService.getCredentialsByRole"));
    }

    public List<UserLoginCredentials> getCredentialsByScoreGreaterThan(int score) throws ServiceUnavailableException {
        return userLoginCredentialsRepository.findAllByScoreGreaterThan(score)
                .orElseThrow(() -> new ServiceUnavailableException("userService.getCredentialsByScoreGreaterThan"));
    }

    public List<UserLoginCredentials> getCredentialsByScoreLessThan(int score) throws ServiceUnavailableException {
        return userLoginCredentialsRepository.findAllByScoreLessThan(score)
                .orElseThrow(() -> new ServiceUnavailableException("userService.getCredentialsByScoreLessThan"));
    }

    public List<UserLoginCredentials> getCredentialsByScoreBetween(int start, int end) throws ServiceUnavailableException {
        return userLoginCredentialsRepository.findAllByScoreBetween(start, end)
                .orElseThrow(() -> new ServiceUnavailableException("userService.getCredentialsByScoreBetween"));
    }

    public List<UserLoginCredentials> getCredentialsByCreationDateGreaterThan(long creationDate) throws ServiceUnavailableException {
        return userLoginCredentialsRepository.findAllByCreationDateGreaterThan(creationDate)
                .orElseThrow(() -> new ServiceUnavailableException("userService.getCredentialsByCreationDateGreaterThan"));
    }

    public List<UserLoginCredentials> getCredentialsByCreationDateLessThan(long creationDate) throws ServiceUnavailableException {
        return userLoginCredentialsRepository.findAllByCreationDateLessThan(creationDate)
                .orElseThrow(() -> new ServiceUnavailableException("userService.getCredentialsByCreationDateLessThan"));
    }

    public List<UserLoginCredentials> getCredentialsByCreationDateBetween(long start, long end) throws ServiceUnavailableException {
        return userLoginCredentialsRepository.findAllByCreationDateBetween(start, end)
                .orElseThrow(() -> new ServiceUnavailableException("userService.getCredentialsByCreationDateBetween"));
    }

    public UserLoginCredentials getCredentialsById(String id) throws NotFoundException {
        return userLoginCredentialsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("userService.getCredentialsById"));
    }

    public UserLoginCredentials insertCredentials(UserLoginRequestCredentials requestCredentials) throws AlreadyExistException, NotFoundException {
        if (checkUsernameExist(requestCredentials.getUsername()))
            throw new AlreadyExistException("userService.insertCredentials");

        UserLoginCredentials u = new UserLoginCredentials();
        u.setUsername(requestCredentials.getUsername());
        if (requestCredentials.getPassword() != null)
            u.setPassword(passwordEncoder.encode(requestCredentials.getPassword()));
        u.setRole("ROLE_USER");
        u.setAvatarTag(requestCredentials.getAvatarTag());
        u.setActive(true);
        u.setScore(0);
        u.setCreationDate(System.currentTimeMillis());

        u = userLoginCredentialsRepository.insert(u);
        ProfileCredentials p = profileService.createEmptyCredentialsWithUserId(u.getId());
        if (requestCredentials.getEmail() != null) {
            p.setEmail(requestCredentials.getEmail());
            profileService.updateCredentials(p);
        }
        return u;
    }

    public UserLoginCredentials updateCredentialsByRequest(UserLoginCredentials requestCredentials) throws NotFoundException, AlreadyExistException {
        UserLoginCredentials u = getCredentialsById(requestCredentials.getId());
        if (!u.getUsername().equals(requestCredentials.getUsername()))
            if (userLoginCredentialsRepository.findByUsername(requestCredentials.getUsername()).isPresent())
                throw new AlreadyExistException("userService.updateCredentialsByRequest");
            else
                u.setUsername(requestCredentials.getUsername());
        if (requestCredentials.getPassword() != null)
            u.setPassword(passwordEncoder.encode(requestCredentials.getPassword()));
        u.setAvatarTag(requestCredentials.getAvatarTag());
        u.setUpdatedDate(System.currentTimeMillis());
        return userLoginCredentialsRepository.save(u);
    }

    public UserLoginCredentials updateCredentials(UserLoginCredentials requestCredentials) {
        requestCredentials.setUpdatedDate(System.currentTimeMillis());
        return userLoginCredentialsRepository.save(requestCredentials);
    }

    public void deleteCredentials(String userId) throws NotFoundException {
        UserLoginCredentials u = getCredentialsById(userId);
        u.setActive(false);
        updateCredentials(u);
        profileService.deleteCredentialsByUserId(userId);
    }
}
