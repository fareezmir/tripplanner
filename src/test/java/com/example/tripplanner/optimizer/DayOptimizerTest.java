package com.example.tripplanner.optimizer;

import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class DayOptimizerTest {

    // A, B and C sit in a line: HOTEL - A - B - C
    // Walking the line in order is cheap; jumping around is expensive.
    private final WalkingMatrix matrix = new WalkingMatrix(Map.of(
        "HOTEL", Map.of("A", 5,  "B", 15, "C", 25),
        "A",     Map.of("HOTEL", 5,  "B", 10, "C", 20),
        "B",     Map.of("HOTEL", 15, "A", 10, "C", 10),
        "C",     Map.of("HOTEL", 25, "A", 20, "B", 10)
    ));

    @Test
    void reordersStopsToMinimiseWalking() {
        // Deliberately the worst order: C first, then A, then B
        List<OptimizerStop> stops = List.of(
            new OptimizerStop("C", 30, null, null),
            new OptimizerStop("A", 30, null, null),
            new OptimizerStop("B", 30, null, null)
        );

        DayPlan plan = DayOptimizer.optimize(
            stops, "HOTEL", matrix,
            LocalTime.of(9, 0), LocalTime.of(20, 0), 480);

        assertNotNull(plan);
        assertTrue(plan.feasible());

        assertEquals(50, plan.totalWalkingMinutes());
    }

    @Test
    void returnsNullWhenNothingIsFeasible() {
        // Two stops both pinned to 10:00 — impossible
        List<OptimizerStop> stops = List.of(
            new OptimizerStop("A", 30, LocalTime.of(10, 0), LocalTime.of(10, 0)),
            new OptimizerStop("B", 30, LocalTime.of(10, 0), LocalTime.of(10, 0))
        );

        DayPlan plan = DayOptimizer.optimize(
            stops, "HOTEL", matrix,
            LocalTime.of(9, 0), LocalTime.of(20, 0), 480);

        assertNull(plan);
    }

    @Test
    void rejectsTooManyStops() {
        List<OptimizerStop> stops = List.of(
            new OptimizerStop("A", 30, null, null),
            new OptimizerStop("B", 30, null, null),
            new OptimizerStop("C", 30, null, null),
            new OptimizerStop("D", 30, null, null),
            new OptimizerStop("E", 30, null, null),
            new OptimizerStop("F", 30, null, null),
            new OptimizerStop("G", 30, null, null),
            new OptimizerStop("H", 30, null, null),
            new OptimizerStop("I", 30, null, null)
        );

        assertThrows(IllegalArgumentException.class, () ->
            DayOptimizer.optimize(stops, "HOTEL", matrix,
                LocalTime.of(9, 0), LocalTime.of(20, 0), 480));
    }

    @Test
    void respectsPinnedStopOrder() {
        // C is pinned to 09:30, which only works if it's visited first
        List<OptimizerStop> stops = List.of(
            new OptimizerStop("A", 30, null, null),
            new OptimizerStop("B", 30, null, null),
            new OptimizerStop("C", 30, LocalTime.of(9, 25), LocalTime.of(9, 30))
        );

        DayPlan plan = DayOptimizer.optimize(
            stops, "HOTEL", matrix,
            LocalTime.of(9, 0), LocalTime.of(20, 0), 480);

        assertNotNull(plan);
        assertEquals("C", plan.schedule().get(0).name());
    }
}