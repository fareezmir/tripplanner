package com.example.tripplanner.service;

import com.example.tripplanner.optimizer.DayPlan;
import com.example.tripplanner.optimizer.ScheduledStop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DayPlanningServiceTest {

    @Autowired
    private DayPlanningService dayPlanningService;

    @Test
    void plansARealDay() {
        DayPlan plan = dayPlanningService.planDay(1L);

        if (plan == null) {
            System.out.println("No feasible plan found");
            return;
        }

        System.out.println("Feasible: " + plan.feasible());
        System.out.println("Walking: " + plan.totalWalkingMinutes() + " min");
        System.out.println("Elapsed: " + plan.totalElapsedMinutes() + " min");

        for (ScheduledStop s : plan.schedule()) {
            System.out.printf("%s  %s (walk %d, wait %d) until %s%n",
                s.arrival(), s.name(), s.walkMinutesFromPrevious(), s.waitMinutes(), s.end());
        }
    }
}