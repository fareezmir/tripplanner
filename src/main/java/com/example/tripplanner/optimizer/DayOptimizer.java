package com.example.tripplanner.optimizer;

import java.time.LocalTime;
import java.util.List;

public class DayOptimizer {

    private static boolean isBetter(DayPlan plan, DayPlan best) {
        if (!plan.feasible()) return false;
        if (best == null) return true;
        if (plan.totalWalkingMinutes() < best.totalWalkingMinutes()) return true; //Just walking time
        if (plan.totalWalkingMinutes() > best.totalWalkingMinutes()) return false;
        
        return plan.totalElapsedMinutes() < best.totalElapsedMinutes(); //Whichever is the least in total minutes from start to finish pick that (walking + waiting + dwell which is how long you're there for)
    }
    public static DayPlan optimize(
            List<OptimizerStop> stops,
            String startLocation,
            WalkingMatrix matrix,
            LocalTime dayStart,
            LocalTime latestEnd,
            int maxMinutes) {
        
        
        if (stops.size() > 8) {
            throw new IllegalArgumentException("Day has " + stops.size() + " stops, maximum is 8 to prevent large combinations.");
        }
        DayPlan best = null;

        List<List<OptimizerStop>> orderings = Permutations.generate(stops);

        for (List<OptimizerStop> order : orderings) {
            DayPlan plan = DaySimulator.simulate(order, startLocation, matrix, dayStart, latestEnd, maxMinutes);
            if (isBetter(plan, best)) {
                best = plan;
            }
        }

        return best; //finds the best pathing that is the shortest walking distance whilst being within the times
    }
}