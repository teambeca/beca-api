package com.r00t.becaapi.services;

import com.r00t.becaapi.exceptions.AlreadyExistException;
import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.models.ProfileCredentials;
import com.r00t.becaapi.repository.ProfileCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private ProfileCredentialsRepository profileCredentialsRepository;

    public ProfileCredentials getCredentialsById(String id) throws NotFoundException {
        return profileCredentialsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("profileService.getCredentialsById"));
    }

    public ProfileCredentials getCredentialsByUserId(String userId) throws NotFoundException {
        return profileCredentialsRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("profileService.getCredentialsByUserId"));
    }

    public ProfileCredentials createEmptyCredentialsWithUserId(String userId) throws AlreadyExistException {
        if (profileCredentialsRepository.findByUserId(userId).isPresent())
            throw new AlreadyExistException("profileService.createEmptyCredentialsWithUserId");

        ProfileCredentials p = new ProfileCredentials();
        p.setUserId(userId);
        p.setCreationDate(System.currentTimeMillis());
        return profileCredentialsRepository.insert(p);
    }

    public ProfileCredentials updateCredentials(ProfileCredentials requestCredentials) throws NotFoundException {
        ProfileCredentials p = profileCredentialsRepository.findByUserId(requestCredentials.getUserId())
                .orElseThrow(() -> new NotFoundException("profileService.updateCredentials"));

        p.setEmail(requestCredentials.getEmail());
        p.setImageURL(requestCredentials.getImageURL());
        p.setFullName(requestCredentials.getFullName());
        p.setJob(requestCredentials.getJob());
        p.setCityId(requestCredentials.getCityId());
        p.setGender(requestCredentials.getGender());
        p.setAge(requestCredentials.getAge());
        p.setUpdatedDate(System.currentTimeMillis());
        return profileCredentialsRepository.save(p);
    }
}