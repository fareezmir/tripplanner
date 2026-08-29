package com.example.tripplanner.repository;

import com.example.tripplanner.model.Day;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<Day, Long> {
}
