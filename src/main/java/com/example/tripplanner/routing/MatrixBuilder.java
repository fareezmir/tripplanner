package com.example.tripplanner.routing;

import com.example.tripplanner.optimizer.WalkingMatrix;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Converts an ORS duration matrix into a WalkingMatrix.
 *
 * ORS returns seconds indexed by position but the optimizer wants minutes
 * keyed by stop name. Index i in both lists must refer to the same place,
 * and names must be unique.
 */

@Component
public class MatrixBuilder {

    private final OrsClient orsClient;

    public MatrixBuilder(OrsClient orsClient) {
        this.orsClient = orsClient;
    }

    public WalkingMatrix build(List<String> names, List<double[]> coordinates) {
        Map<String, Map<String, Integer>> walkTimes = new HashMap<>();
        double[][] durations = orsClient.durations(coordinates);

        for (int i = 0; i < names.size(); i++) {
            Map<String, Integer> row = new HashMap<>();

            for (int j = 0; j < names.size(); j++) {
                row.put(names.get(j), (int) Math.round(durations[i][j]  / 60));
            }
            walkTimes.put(names.get(i), row);
        }

        return new WalkingMatrix(walkTimes);
    }
}