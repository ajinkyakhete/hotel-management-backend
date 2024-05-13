package com.codewitharjun.fullstackbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codewitharjun.fullstackbackend.exception.LocationNotFoundException;
import com.codewitharjun.fullstackbackend.model.Location;
import com.codewitharjun.fullstackbackend.repository.LocationRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @PostMapping("/location")
    Location newLocation(@RequestBody Location newLocation) {
        return locationRepository.save(newLocation);
    }

    @GetMapping("/hotellocation/{hotelId}")
    List<Location> getLocationsByHotel(@PathVariable("hotelId") Long hotelId) {
        return locationRepository.findAllByHotel_HotelId(hotelId);
    }

    @GetMapping("/locations")
    List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @GetMapping("/location/{locationid}")
    Location getUserById(@PathVariable Long locationid) {
        return locationRepository.findById(locationid)
                .orElseThrow(() -> new LocationNotFoundException(locationid));
    }

    @PutMapping("/location/{locationid}")
    Location updateLocation(@RequestBody Location newLocation, @PathVariable Long locationid) {
        return locationRepository.findById(locationid)
                .map(location -> {
                	location.setLatitude(newLocation.getLatitude());
                	location.setLongitude(newLocation.getLongitude());
                    return locationRepository.save(location);
                }).orElseThrow(() -> new LocationNotFoundException(locationid));
    }

    @DeleteMapping("/location/{locationid}")
    String deleteLocation(@PathVariable Long locationid){
        if(!locationRepository.existsById(locationid)){
            throw new LocationNotFoundException(locationid);
        }
        locationRepository.deleteById(locationid);
        return  "Location with id "+locationid+" has been deleted success.";
    }

}
