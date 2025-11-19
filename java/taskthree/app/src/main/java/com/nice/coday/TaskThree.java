package com.nice.coday;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class TaskThree {
    
    public long solve(Path inputPath) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(inputPath)) {
            StringTokenizer tokenizer = null;

            // Read N and M
            String line = reader.readLine();
            while (line != null && line.trim().isEmpty()) {
                line = reader.readLine();
            }
            if (line == null) return 0;

            tokenizer = new StringTokenizer(line);
            int n = Integer.parseInt(tokenizer.nextToken());
            int m = Integer.parseInt(tokenizer.nextToken());

            // Lazy initialization: array of TreeMaps, null initially
            TreeMap<Integer, Integer>[] dpMaps = new TreeMap[n + 1];

            int maxStory = 0;

            for (int i = 0; i < m; i++) {
                while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                    line = reader.readLine();
                    if (line == null) break;
                    tokenizer = new StringTokenizer(line);
                }
                if (tokenizer == null || !tokenizer.hasMoreTokens()) break;

                int from = Integer.parseInt(tokenizer.nextToken());
                int to = Integer.parseInt(tokenizer.nextToken());
                int weight = Integer.parseInt(tokenizer.nextToken());

                // Query best previous story ending at 'from' with w < weight
                int bestPrev = 0;
                if (dpMaps[from] != null) {
                    Map.Entry<Integer, Integer> entry = dpMaps[from].lowerEntry(weight);
                    if (entry != null) {
                        bestPrev = entry.getValue();
                    }
                }

                int currentStory = bestPrev + 1;
                if (currentStory > maxStory) {
                    maxStory = currentStory;
                }

                // Update dpMaps[to]
                if (dpMaps[to] == null) {
                    dpMaps[to] = new TreeMap<>();
                }

                TreeMap<Integer, Integer> map = dpMaps[to];

                // Check if current solution is dominated by an existing one
                Map.Entry<Integer, Integer> floor = map.floorEntry(weight);
                if (floor != null && floor.getValue() >= currentStory) {
                    continue;
                }

                // Remove entries dominated by the current one
                // More efficient: use removeIf-like pattern
                Iterator<Map.Entry<Integer, Integer>> it = map.tailMap(weight).entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Integer, Integer> entry = it.next();
                    if (entry.getValue() <= currentStory) {
                        it.remove();
                    } else {
                        // Optimization: stop early since map is sorted
                        break;
                    }
                }

                map.put(weight, currentStory);
            }

            return maxStory;
        }
    }
}
