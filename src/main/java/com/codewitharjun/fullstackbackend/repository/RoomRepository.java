package com.codewitharjun.fullstackbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewitharjun.fullstackbackend.model.Room;

public interface RoomRepository extends JpaRepository<Room,Long> {
	/* List<Hotel> findByCity(String city); */
	List<Room> findAllByHotel_HotelId(Long hotelId);

}