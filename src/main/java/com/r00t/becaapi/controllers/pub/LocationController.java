package com.r00t.becaapi.controllers.pub;

import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<?> getLocationCredentials() {
        return ResponseEntity.ok().body(
                locationService.getCredentials());
    }

    @GetMapping("/by/id/{locationId}")
    public ResponseEntity<?> getLocationCredentialsById(
            @PathVariable String locationId) throws NotFoundException {
        return ResponseEntity.ok().body(
                locationService.getCredentialsById(locationId));
    }
}
