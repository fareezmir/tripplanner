package com.example.tripplanner;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double latitude;
    private Double longitude;
    private Integer dwellMinutes;
    private String dwellSource;
    private LocalTime earliestStart;
    private LocalTime latestStart;  

    @ManyToOne
    @JoinColumn(name = "day_id")
    private Day day;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getDwellMinutes() {
        return dwellMinutes;
    }

    public void setDwellMinutes(Integer dwellMinutes) {
        this.dwellMinutes = dwellMinutes;
    }

    public String getDwellSource() {
        return dwellSource;
    }

    public void setDwellSource(String dwellSource) {
        this.dwellSource = dwellSource;
    }

    public LocalTime getEarliestStart() {
        return earliestStart;
    }

    public void setEarliestStart(LocalTime earliestStart) {
        this.earliestStart = earliestStart;
    }

    public LocalTime getLatestStart() {
        return latestStart;
    }

    public void setLatestStart(LocalTime latestStart) {
        this.latestStart = latestStart;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}
