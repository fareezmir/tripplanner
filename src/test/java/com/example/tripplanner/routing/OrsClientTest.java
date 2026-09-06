package com.example.tripplanner.routing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OrsClientTest {

    @Autowired
    private OrsClient orsClient;

    @Test
    void fetchesRealWalkingTimes() {
        List<double[]> coords = List.of(
            new double[]{-0.1195, 51.5033},  // London Eye
            new double[]{-0.1246, 51.5007}   // Big Ben
        );

        double[][] durations = orsClient.durations(coords);

        System.out.println("London Eye to Big Ben: " + durations[0][1] + " seconds");
    }
}