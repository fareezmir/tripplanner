package com.example.tripplanner.service;

    import com.example.tripplanner.model.Day;
    import com.example.tripplanner.model.Stop;
    import com.example.tripplanner.optimizer.DayOptimizer;
    import com.example.tripplanner.optimizer.DayPlan;
    import com.example.tripplanner.optimizer.OptimizerStop;
    import com.example.tripplanner.optimizer.WalkingMatrix;
    import com.example.tripplanner.repository.DayRepository;
    import org.springframework.stereotype.Service;
    import com.example.tripplanner.routing.MatrixBuilder;
    import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

/**
 * Turns a stored Day into an optimised itinerary.
 *
 * This loads the day and its stops
 * from Postgres, fetches real walking times from OpenRouteService, converts the
 * JPA entities into the plain records the solver works with, and hands them to
 * DayOptimizer.
 *
 * The solver itself (DayOptimizer / DaySimulator) knows nothing about the
 * database or the network — that separation is what lets it be unit tested
 * with hand-built objects.
 */
@Service
public class DayPlanningService {

    private final DayRepository dayRepository;
    private final MatrixBuilder matrixBuilder;

    public DayPlanningService(DayRepository dayRepository, MatrixBuilder matrixBuilder) {
        this.dayRepository = dayRepository;
        this.matrixBuilder = matrixBuilder;
    }

    @Transactional(readOnly = true)
    public DayPlan planDay(Long dayId) {
        Day day = dayRepository.findById(dayId).orElseThrow();
        List<Stop> stops = day.getStops();

        // 1. build names + coordinates lists
        List<String> names = new ArrayList<>();
        List<double[]> coordinates = new ArrayList<>();

        names.add(day.getStartName());
        coordinates.add(new double[]{day.getStartLon(), day.getStartLat()});

        for (Stop stop : stops) {
            names.add(stop.getName());
            coordinates.add(new double[]{stop.getLongitude(), stop.getLatitude()});
        }

        // 2. get walking times between all of them
        WalkingMatrix matrix = matrixBuilder.build(names, coordinates);

        // 3. convert Stop entities into OptimizerStop records
        List<OptimizerStop> optimizerStops = new ArrayList<>();
        for (Stop stop : stops) {
            optimizerStops.add(new OptimizerStop(
                stop.getName(),
                stop.getDwellMinutes(),
                stop.getEarliestStart(),
                stop.getLatestStart()
            ));
        }

        // 4. run DayOptimizer and return the plan
        return DayOptimizer.optimize(optimizerStops, day.getStartName(), matrix, day.getStartTime(), day.getLatestEndTime(), day.getMaxMinutesPerDay());
    }
}