package com.example.tripplanner.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dayDate;
    private Double startLat;
    private Double startLon;
    private Double endLat;
    private Double endLon;
    private LocalTime startTime;
    private LocalTime latestEndTime;
    private Integer maxMinutesPerDay;

    @ManyToOne
    @JoinColumn(name = "leg_id")
    private Leg leg;

    @OneToMany(mappedBy = "day")
    private List<Stop> stops = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDayDate() {
        return dayDate;
    }

    public void setDayDate(LocalDate dayDate) {
        this.dayDate = dayDate;
    }

    public Double getStartLat() {
        return startLat;
    }

    public void setStartLat(Double startLat) {
        this.startLat = startLat;
    }

    public Double getStartLon() {
        return startLon;
    }

    public void setStartLon(Double startLon) {
        this.startLon = startLon;
    }

    public Double getEndLat() {
        return endLat;
    }

    public void setEndLat(Double endLat) {
        this.endLat = endLat;
    }

    public Double getEndLon() {
        return endLon;
    }

    public void setEndLon(Double endLon) {
        this.endLon = endLon;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getLatestEndTime() {
        return latestEndTime;
    }

    public void setLatestEndTime(LocalTime latestEndTime) {
        this.latestEndTime = latestEndTime;
    }

    public Integer getMaxMinutesPerDay() {
        return maxMinutesPerDay;
    }

    public void setMaxMinutesPerDay(Integer maxMinutesPerDay) {
        this.maxMinutesPerDay = maxMinutesPerDay;
    }

    public Leg getLeg() {
        return leg;
    }

    public void setLeg(Leg leg) {
        this.leg = leg;
    }

    public List<Stop> getStops() {
        return stops;
    }
}
