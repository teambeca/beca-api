package com.r00t.becaapi.controllers.admin;

import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.models.LocationCredentials;
import com.r00t.becaapi.models.requests.Seed;
import com.r00t.becaapi.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/location")
public class AdminLocationController {
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

    @PostMapping
    public ResponseEntity<?> insertLocationCredentials(
            @RequestBody LocationCredentials requestCredentials) {
        return ResponseEntity.ok().body(
                locationService.insertCredentials(requestCredentials));
    }

    @PostMapping("/seed")
    public ResponseEntity<?> seed(
            @RequestBody Seed seed) {
        seed.getTexts()
                .forEach(s -> {
                    LocationCredentials l = new LocationCredentials();
                    l.setName(s);
                    locationService.insertCredentials(l);
                });

        return ResponseEntity.ok().body(
                Map.of("status", "ok"));
    }

    @PatchMapping
    public ResponseEntity<?> updateLocationCredentials(
            @RequestBody LocationCredentials requestCredentials) {
        return ResponseEntity.ok().body(
                locationService.updateCredentials(requestCredentials));
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<?> deleteLocationCredentials(
            @PathVariable String locationId) {
        locationService.deleteCredentials(locationId);
        return ResponseEntity.ok().body(
                Map.of("status", "ok"));
    }
}
