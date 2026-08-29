package com.example.tripplanner.repository;

import com.example.tripplanner.model.Leg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegRepository extends JpaRepository<Leg, Long> {
}
