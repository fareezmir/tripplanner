package com.example.tripplanner.repository;

import com.example.tripplanner.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
