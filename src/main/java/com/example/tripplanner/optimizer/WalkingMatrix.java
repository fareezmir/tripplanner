package com.example.tripplanner.optimizer;

import java.util.Map;

public class WalkingMatrix {
    private final Map<String, Map<String, Integer>> minutes;

    public WalkingMatrix(Map<String, Map<String, Integer>> minutes) {
        this.minutes = minutes;
    }

    public int between(String from, String to) {
        return minutes.get(from).get(to);
    }
}