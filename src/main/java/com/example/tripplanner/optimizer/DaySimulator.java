package com.example.tripplanner.optimizer;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DaySimulator {

    public static DayPlan simulate(
            List<OptimizerStop> ordering,
            String startLocation,
            WalkingMatrix matrix,
            LocalTime dayStart,
            LocalTime latestEnd,
            int maxMinutes) {
        
        LocalTime currentTime = dayStart;
        String currentLocation = startLocation;
        int totalWalking = 0;
        List<ScheduledStop> schedule = new ArrayList<>(); 

        for (OptimizerStop stop : ordering) {
            int walkTime = matrix.between(currentLocation, stop.name());
            totalWalking += walkTime;

            LocalTime arrivalTime = currentTime.plusMinutes(walkTime);

            LocalTime startTime = arrivalTime;
            if (stop.earliestStart() != null && startTime.isBefore(stop.earliestStart())) {
                startTime = stop.earliestStart(); // If we arrive earlier than expected, we'll have time to kill
            }

            if (stop.latestStart() != null && startTime.isAfter(stop.latestStart())) {
                return new DayPlan(false, List.of(), 0, 0,
                    "Cannot reach " + stop.name() + " by " + stop.latestStart()); // If we show up later then we can't start on time, so kill operation
            }

            int waitMinutes = (int) Duration.between(arrivalTime, startTime).toMinutes();

            LocalTime endTime = startTime.plusMinutes(stop.dwellMinutes());

            schedule.add(new ScheduledStop(
                stop.name(), arrivalTime, startTime, endTime, walkTime, waitMinutes));
            
            currentTime = endTime;
            currentLocation = stop.name();
        }

        int walkEndToStart = matrix.between(currentLocation, startLocation);
        totalWalking += walkEndToStart;
        
        LocalTime endTime = currentTime.plusMinutes(walkEndToStart);

        if (endTime.isAfter(latestEnd)) {
            return new DayPlan(false, List.of(), 0, 0,
            "Day ends at " + endTime + ", after latest end of " + latestEnd); //Ended past the expected end date
        }

        int totalElapsed = (int) Duration.between(dayStart, endTime).toMinutes();

        if (totalElapsed > maxMinutes) {
            return new DayPlan(false, List.of(), 0, 0,
                "Day takes " + totalElapsed + " minutes, over the limit of " + maxMinutes); //Day length is too long (for days where you arrive late or want a calm day)
        }

        return new DayPlan(true, schedule, totalWalking, totalElapsed, null); 
    }
}