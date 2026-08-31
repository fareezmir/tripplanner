package com.example.tripplanner.optimizer;

import java.util.ArrayList;
import java.util.List;

public class Permutations {

    public static <T> List<List<T>> generate(List<T> items) {
        List<List<T>> result = new ArrayList<>();
        List<T> current = new ArrayList<>();
        boolean[] used = new boolean[items.size()];
        dfs(items, current, used, result);
        return result;
    }
    
    private static <T> void dfs(List<T> items, List<T> current, boolean[] used, List<List<T>> result) {
        if (current.size() == items.size()) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i=0; i < items.size(); i++) {
            if (used[i]) {
                continue;
            }
            current.add(items.get(i));
            used[i] = true;
            dfs(items, current, used, result);
            current.remove(current.size() - 1);
            used[i] = false;

        }
    }

    public static void main(String[] args) {
        List<String> stops = List.of("A", "B", "C");
        for (List<String> ordering : generate(stops)) {
            System.out.println(ordering);
        }
    }
}