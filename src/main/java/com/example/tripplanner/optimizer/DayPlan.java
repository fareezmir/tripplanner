package com.example.tripplanner.optimizer;

import java.util.List;

public record DayPlan(
    boolean feasible,
    List<ScheduledStop> schedule,
    int totalWalkingMinutes,
    int totalElapsedMinutes,
    String failureReason
) {}