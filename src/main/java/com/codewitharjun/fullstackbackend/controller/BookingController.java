package com.codewitharjun.fullstackbackend.controller;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codewitharjun.fullstackbackend.exception.BookingNotFoundException;
import com.codewitharjun.fullstackbackend.model.Booking;
import com.codewitharjun.fullstackbackend.model.Room;
import com.codewitharjun.fullstackbackend.repository.BookingRepository;
import com.codewitharjun.fullstackbackend.repository.RoomRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RoomRepository roomRepository;
    
    @PostMapping("/booking")
    Booking newBooking(@RequestBody Booking newBooking) {
        return bookingRepository.save(newBooking);
    }

    @GetMapping("/bookings")
    List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }

    @GetMapping("/booking/{bookingid}")
    Booking getBookingById(@PathVariable Long bookingid) {
        return bookingRepository.findById(bookingid)
                .orElseThrow(() -> new BookingNotFoundException(bookingid));
    }

    @PutMapping("/booking/{bookingid}")
    Booking updateBooking(@RequestBody Booking newBooking, @PathVariable Long bookingid) {
        return bookingRepository.findById(bookingid)
                .map(booking -> {
                	booking.setCheckInDate(newBooking.getCheckInDate());
                	booking.setCheckOutDate(newBooking.getCheckOutDate());
                	booking.setTotalPrice(newBooking.getTotalPrice());
                	booking.setBookingStatus(newBooking.getBookingStatus());
                    return bookingRepository.save(booking);
                }).orElseThrow(() -> new BookingNotFoundException(bookingid));
    }

    @DeleteMapping("/booking/{bookingid}")
    String deleteBooking(@PathVariable Long bookingid){
        if(!bookingRepository.existsById(bookingid)){
            throw new BookingNotFoundException(bookingid);
        }
        bookingRepository.deleteById(bookingid);
        return  "Booking with id "+bookingid+" has been deleted success.";
    }
    @PostMapping("/calculate-total-price/{roomId}")
    public ResponseEntity<Double> calculateTotalPrice(@PathVariable Long roomId, @RequestBody Booking booking) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            double roomPrice = room.getPrice();

            long bookingDuration = ChronoUnit.DAYS.between(
                    booking.getCheckInDate().toInstant(), booking.getCheckOutDate().toInstant()
            );

            double totalPrice = roomPrice * bookingDuration;
            return new ResponseEntity<>(totalPrice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
