package com.codewitharjun.fullstackbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codewitharjun.fullstackbackend.model.Review;
import com.codewitharjun.fullstackbackend.model.User;

public interface ReviewRepository extends JpaRepository<Review,Long> {
	@Query("SELECT b.user FROM Review b WHERE b.hotel.hotelId = :hotelId")
    User findUserByHotelId(@Param("hotelId") Long hotelId);
	List<Review> findAllByUserId(Long id);
}

