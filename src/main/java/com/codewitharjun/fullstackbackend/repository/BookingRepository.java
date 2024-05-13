package com.codewitharjun.fullstackbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewitharjun.fullstackbackend.model.Booking;

public interface BookingRepository extends JpaRepository<Booking,Long> {

}
