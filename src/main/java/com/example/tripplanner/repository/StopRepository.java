package com.example.tripplanner.repository;

import com.example.tripplanner.model.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopRepository extends JpaRepository<Stop, Long> {
}
