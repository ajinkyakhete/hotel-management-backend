package com.codewitharjun.fullstackbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewitharjun.fullstackbackend.model.Location;

public interface LocationRepository extends JpaRepository<Location,Long> {
	/* List<Hotel> findByCity(String city); */
	List<Location> findAllByHotel_HotelId(Long hotelId);

}