package com.example.tripplanner.optimizer;

import java.time.LocalTime;

public record ScheduledStop(
    String name,
    LocalTime arrival,
    LocalTime start,
    LocalTime end,
    int walkMinutesFromPrevious,
    int waitMinutes
) {}