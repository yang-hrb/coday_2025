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
            
            // Helper to get next token
            // We can't easily make a helper method that modifies local tokenizer variable 
            // without wrapping it. So we'll inline or use a loop.
            
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
            
            long maxStory = 0;
            
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
                // We need an entry with w <= weight and len >= currentStory
                Map.Entry<Integer, Integer> floor = map.floorEntry(weight);
                if (floor != null && floor.getValue() >= currentStory) {
                    continue;
                }
                
                // Remove entries dominated by the current one
                // We need to remove entries with w >= weight and len <= currentStory
                // Since we maintain the invariant (w increases -> len increases),
                // any entry with w >= weight should have len > currentStory to be kept.
                // If we find one with len <= currentStory, it's dominated.
                // We can iterate from 'weight' upwards.
                
                // Optimization: tailMap gives a view.
                Iterator<Map.Entry<Integer, Integer>> it = map.tailMap(weight).entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Integer, Integer> entry = it.next();
                    if (entry.getValue() <= currentStory) {
                        it.remove();
                    } else {
                        // Since len strictly increases with w in our invariant (after pruning),
                        // if we find an entry with val > currentStory, all subsequent entries
                        // will also have val > currentStory. So we can stop.
                        break;
                    }
                }
                
                map.put(weight, currentStory);
            }
            
            return maxStory;
        }
    }
}
