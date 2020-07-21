package com.r00t.becaapi.services;

import com.r00t.becaapi.exceptions.AlreadyExistException;
import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.exceptions.ServiceUnavailableException;
import com.r00t.becaapi.models.ProfileCredentials;
import com.r00t.becaapi.repository.ProfileCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private ProfileCredentialsRepository profileCredentialsRepository;

    public List<ProfileCredentials> getCredentials(int page) {
        return profileCredentialsRepository.findAll(PageRequest.of(page, 50))
                .getContent();
    }

    public List<ProfileCredentials> getCredentialsByName(String name) throws ServiceUnavailableException {
        return profileCredentialsRepository.findAllByFullName(name)
                .orElseThrow(() -> new ServiceUnavailableException("profileService.getCredentialsByName"));
    }

    public List<ProfileCredentials> getCredentialsByJob(String job) throws ServiceUnavailableException {
        return profileCredentialsRepository.findAllByJob(job)
                .orElseThrow(() -> new ServiceUnavailableException("profileService.getCredentialsByJob"));
    }

    public List<ProfileCredentials> getCredentialsByCityId(String cityId) throws ServiceUnavailableException {
        return profileCredentialsRepository.findAllByCityId(cityId)
                .orElseThrow(() -> new ServiceUnavailableException("profileService.getCredentialsByCityId"));
    }

    public List<ProfileCredentials> getCredentialsByGender(int gender) throws ServiceUnavailableException {
        return profileCredentialsRepository.findAllByGender(gender)
                .orElseThrow(() -> new ServiceUnavailableException("profileService.getCredentialsByGender"));
    }

    public List<ProfileCredentials> getCredentialsByAge(int age) throws ServiceUnavailableException {
        return profileCredentialsRepository.findAllByAge(age)
                .orElseThrow(() -> new ServiceUnavailableException("profileService.getCredentialsByAge"));
    }

    public List<ProfileCredentials> getCredentialsByAgeGreaterThan(int age) throws ServiceUnavailableException {
        return profileCredentialsRepository.findAllByAgeGreaterThan(age)
                .orElseThrow(() -> new ServiceUnavailableException("profileService.getCredentialsByAgeGreaterThan"));
    }

    public List<ProfileCredentials> getCredentialsByAgeLessThan(int age) throws ServiceUnavailableException {
        return profileCredentialsRepository.findAllByAgeLessThan(age)
                .orElseThrow(() -> new ServiceUnavailableException("profileService.getCredentialsByAgeLessThan"));
    }

    public List<ProfileCredentials> getCredentialsByAgeBetween(int start, int end) throws ServiceUnavailableException {
        return profileCredentialsRepository.findAllByAgeBetween(start, end)
                .orElseThrow(() -> new ServiceUnavailableException("profileService.getCredentialsByAgeBetween"));
    }

    public List<ProfileCredentials> getCredentialsByCreationDateGreaterThan(long creationDate) throws ServiceUnavailableException {
        return profileCredentialsRepository.findAllByCreationDateGreaterThan(creationDate)
                .orElseThrow(() -> new ServiceUnavailableException("profileService.getCredentialsByCreationDateGreaterThan"));
    }

    public List<ProfileCredentials> getCredentialsByCreationDateLessThan(long creationDate) throws ServiceUnavailableException {
        return profileCredentialsRepository.findAllByCreationDateLessThan(creationDate)
                .orElseThrow(() -> new ServiceUnavailableException("profileService.getCredentialsByCreationDateLessThan"));
    }

    public List<ProfileCredentials> getCredentialsByCreationDateBetween(long start, long end) throws ServiceUnavailableException {
        return profileCredentialsRepository.findAllByCreationDateBetween(start, end)
                .orElseThrow(() -> new ServiceUnavailableException("profileService.getCredentialsByCreationDateBetween"));
    }

    public ProfileCredentials getCredentialsById(String id) throws NotFoundException {
        return profileCredentialsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("profileService.getCredentialsById"));
    }

    public ProfileCredentials getCredentialsByUserId(String userId) throws NotFoundException {
        return profileCredentialsRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("profileService.getCredentialsByUserId"));
    }

    public ProfileCredentials getCredentialsByEmail(String email) throws NotFoundException {
        return profileCredentialsRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("profileService.getCredentialsByEmail"));
    }

    public ProfileCredentials createEmptyCredentialsWithUserId(String userId) throws AlreadyExistException {
        if (profileCredentialsRepository.findByUserId(userId).isPresent())
            throw new AlreadyExistException("profileService.createEmptyCredentialsWithUserId");

        ProfileCredentials p = new ProfileCredentials();
        p.setUserId(userId);
        p.setGender(-1);
        p.setAge(-1);
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

    public void deleteCredentials(String profileId) throws NotFoundException {
        ProfileCredentials p = getCredentialsById(profileId);
        p.setEmail(null);
        p.setImageURL(null);
        p.setFullName(null);
        p.setJob(null);
        p.setCityId(null);
        p.setGender(-1);
        p.setAge(-1);
        p.setUpdatedDate(System.currentTimeMillis());
        profileCredentialsRepository.save(p);
    }

    public void deleteCredentialsByUserId(String userId) {
        profileCredentialsRepository.deleteByUserId(userId);
    }
}