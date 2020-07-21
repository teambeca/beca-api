package com.r00t.becaapi.services;

import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.models.LocationCredentials;
import com.r00t.becaapi.repository.LocationCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    @Autowired
    private LocationCredentialsRepository locationCredentialsRepository;

    public List<LocationCredentials> getCredentials() {
        return locationCredentialsRepository.findAll();
    }

    public LocationCredentials getCredentialsById(String id) throws NotFoundException {
        return locationCredentialsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("locationService.getCredentialsById"));
    }

    public LocationCredentials insertCredentials(LocationCredentials requestCredentials) {
        requestCredentials.setId(null);
        return locationCredentialsRepository.insert(requestCredentials);
    }

    public LocationCredentials updateCredentials(LocationCredentials requestCredentials) {
        return locationCredentialsRepository.save(requestCredentials);
    }

    public void deleteCredentials(String id) {
        locationCredentialsRepository.deleteById(id);
    }
}
