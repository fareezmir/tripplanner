package com.example.tripplanner.optimizer;

import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class DaySimulatorTest {

    private final WalkingMatrix matrix = new WalkingMatrix(Map.of(
        "HOTEL", Map.of("A", 10, "B", 20),
        "A",     Map.of("HOTEL", 10, "B", 15),
        "B",     Map.of("HOTEL", 20, "A", 15)
    ));

    @Test
    void simpleDayWithNoConstraints() {
        List<OptimizerStop> stops = List.of(
            new OptimizerStop("A", 60, null, null),
            new OptimizerStop("B", 30, null, null)
        );

        DayPlan plan = DaySimulator.simulate(
            stops, "HOTEL", matrix,
            LocalTime.of(9, 0), LocalTime.of(20, 0), 480);

        assertTrue(plan.feasible());
        assertEquals(45, plan.totalWalkingMinutes());
        assertEquals(135, plan.totalElapsedMinutes());
        assertEquals(2, plan.schedule().size());
    }

    @Test
    void rejectsUnreachablePin() {
        // A runs until 14:10, so B's 11:00 deadline is impossible
        List<OptimizerStop> stops = List.of(
            new OptimizerStop("A", 300, null, null),
            new OptimizerStop("B", 30, null, LocalTime.of(11, 0))
        );

        DayPlan plan = DaySimulator.simulate(
            stops, "HOTEL", matrix,
            LocalTime.of(9, 0), LocalTime.of(23, 0), 900);

        assertFalse(plan.feasible());
        assertNotNull(plan.failureReason());
    }

    @Test
    void waitsWhenArrivingEarly() {
        // Arrive at A at 09:10 but it can't start until 11:00
        List<OptimizerStop> stops = List.of(
            new OptimizerStop("A", 60, LocalTime.of(11, 0), LocalTime.of(11, 0))
        );

        DayPlan plan = DaySimulator.simulate(
            stops, "HOTEL", matrix,
            LocalTime.of(9, 0), LocalTime.of(20, 0), 480);

        assertTrue(plan.feasible());

        ScheduledStop a = plan.schedule().get(0);
        assertEquals(LocalTime.of(9, 10), a.arrival());
        assertEquals(LocalTime.of(11, 0), a.start());
        assertEquals(LocalTime.of(12, 0), a.end());
        assertEquals(110, a.waitMinutes());
    }

    @Test
    void rejectsWhenOverCapacity() {
        // 245 minutes of day against a 120 minute budget
        List<OptimizerStop> stops = List.of(
            new OptimizerStop("A", 120, null, null),
            new OptimizerStop("B", 60, null, null)
        );

        DayPlan plan = DaySimulator.simulate(
            stops, "HOTEL", matrix,
            LocalTime.of(9, 0), LocalTime.of(23, 0), 120);

        assertFalse(plan.feasible());
        assertTrue(plan.failureReason().contains("120"));
    }
}
