package com.example.tripplanner.optimizer;

import java.time.LocalTime;

public record OptimizerStop(
    String name,
    int dwellMinutes,
    LocalTime earliestStart,
    LocalTime latestStart
) {}