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

import com.codewitharjun.fullstackbackend.exception.HotelNotFoundException;
import com.codewitharjun.fullstackbackend.model.Hotel;
import com.codewitharjun.fullstackbackend.repository.HotelRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class HotelController {

    @Autowired
    private HotelRepository hotelRepository;

    @PostMapping("/hotel")
    Hotel newHotel(@RequestBody Hotel newHotel) {
        return hotelRepository.save(newHotel);
    }

    @GetMapping("/host/{id}")
    List<Hotel> getHotelsByHost(@PathVariable("id") Long id) {
        return hotelRepository.findAllByUserId(id);
    }

    @GetMapping("/city/{city}")
    public List<Hotel> getAllHotelsByCity(@PathVariable String city) {
        return hotelRepository.findByCity(city);
    }

    @GetMapping("/hotels")
    List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @GetMapping("/hotel/{hotelid}")
    Hotel getUserById(@PathVariable Long hotelid) {
        return hotelRepository.findById(hotelid)
                .orElseThrow(() -> new HotelNotFoundException(hotelid));
    }

    @PutMapping("/hotel/{hotelid}")
    Hotel updateHotel(@RequestBody Hotel newHotel, @PathVariable Long hotelid) {
        return hotelRepository.findById(hotelid)
                .map(hotel -> {
                	hotel.setHotelName(newHotel.getHotelName());
                	hotel.setCity(newHotel.getCity());
                	hotel.setCountry(newHotel.getCountry());
                	hotel.setEmail(newHotel.getEmail());
                	hotel.setContactNumber(newHotel.getContactNumber());
                	hotel.setAddress(newHotel.getAddress());
                	hotel.setHotelurl(newHotel.getHotelurl());
                	hotel.setImage1url(newHotel.getImage1url());
                	hotel.setImage2url(newHotel.getImage2url());
                	hotel.setImage3url(newHotel.getImage3url());
                    return hotelRepository.save(hotel);
                }).orElseThrow(() -> new HotelNotFoundException(hotelid));
    }

    @DeleteMapping("/hotel/{hotelid}")
    String deleteHotel(@PathVariable Long hotelid){
        if(!hotelRepository.existsById(hotelid)){
            throw new HotelNotFoundException(hotelid);
        }
        hotelRepository.deleteById(hotelid);
        return  "Hotel with id "+hotelid+" has been deleted success.";
    }

}

