package com.codewitharjun.fullstackbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewitharjun.fullstackbackend.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
	List<Hotel> findByCity(String city);
	List<Hotel> findAllByUserId(Long id);
}