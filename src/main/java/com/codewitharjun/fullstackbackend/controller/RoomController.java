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

import com.codewitharjun.fullstackbackend.exception.RoomNotFoundException;
import com.codewitharjun.fullstackbackend.model.Room;
import com.codewitharjun.fullstackbackend.repository.RoomRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @PostMapping("/room")
    Room newRoom(@RequestBody Room newRoom) {
        return roomRepository.save(newRoom);
    }

    @GetMapping("/hotelroom/{hotelId}")
    List<Room> getRoomsByHotel(@PathVariable("hotelId") Long hotelId) {
        return roomRepository.findAllByHotel_HotelId(hotelId);
    }

	/*
	 * @GetMapping("/city/{city}") public List<Hotel>
	 * getAllHotelsByCity(@PathVariable String city) { return
	 * hotelRepository.findByCity(city); }
	 */

    @GetMapping("/rooms")
    List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/room/{roomid}")
    Room getUserById(@PathVariable Long roomid) {
        return roomRepository.findById(roomid)
                .orElseThrow(() -> new RoomNotFoundException(roomid));
    }

    @PutMapping("/room/{roomid}")
    Room updateRoom(@RequestBody Room newRoom, @PathVariable Long roomid) {
        return roomRepository.findById(roomid)
                .map(room -> {
                	room.setRoom_type(newRoom.getRoom_type());
                	room.setCapacity(newRoom.getCapacity());
                	room.setPrice(newRoom.getPrice());
                	room.setAvailability(newRoom.isAvailability());
                    return roomRepository.save(room);
                }).orElseThrow(() -> new RoomNotFoundException(roomid));
    }

    @DeleteMapping("/room/{roomid}")
    String deleteRoom(@PathVariable Long roomid){
        if(!roomRepository.existsById(roomid)){
            throw new RoomNotFoundException(roomid);
        }
        roomRepository.deleteById(roomid);
        return  "Room with id "+roomid+" has been deleted success.";
    }

}
