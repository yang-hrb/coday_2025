package com.nice.coday;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProblemTwo {

    private static final int MAX_VAL = 200;

    public List<Integer> solve(Path inputPath) throws IOException {
        List<Integer> result = new ArrayList<>();
        try (java.io.BufferedReader reader = java.nio.file.Files.newBufferedReader(inputPath)) {
            java.util.StringTokenizer tokenizer = null;

            // Read number of test cases
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                String line = reader.readLine();
                if (line == null) return result;
                tokenizer = new java.util.StringTokenizer(line);
            }
            int numArrays = Integer.parseInt(tokenizer.nextToken());

            for (int arr = 0; arr < numArrays; arr++) {
                // Read N
                while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                    String line = reader.readLine();
                    if (line == null) break;
                    tokenizer = new java.util.StringTokenizer(line);
                }
                if (tokenizer != null && tokenizer.hasMoreTokens()) {
                    int n = Integer.parseInt(tokenizer.nextToken());
                    int[] array = new int[n];
                    for (int i = 0; i < n; i++) {
                        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                            String line = reader.readLine();
                            if (line == null) break;
                            tokenizer = new java.util.StringTokenizer(line);
                        }
                        if (tokenizer != null && tokenizer.hasMoreTokens()) {
                            array[i] = Integer.parseInt(tokenizer.nextToken());
                        }
                    }
                    result.add(findLongestHarmonizedGroup(array));
                }
            }
        }
        return result;
    }

    private int findLongestHarmonizedGroup(int[] array) {
        int n = array.length;
        if (n == 0) return 0;

        // Use HashMap to store only values that actually appear
        java.util.HashMap<Integer, ArrayList<Integer>> positions = new java.util.HashMap<>();

        for (int i = 0; i < n; i++) {
            int val = array[i];
            positions.computeIfAbsent(val, k -> new ArrayList<>()).add(i);
        }

        int maxLen = 0;

        // Single type max - only check values that exist
        for (ArrayList<Integer> posList : positions.values()) {
            if (posList.size() > maxLen) {
                maxLen = posList.size();
            }
        }

        // Dual type - reusable counts map for actual values only
        java.util.HashMap<Integer, Integer> counts = new java.util.HashMap<>();

        for (java.util.Map.Entry<Integer, ArrayList<Integer>> entry : positions.entrySet()) {
            ArrayList<Integer> idxs = entry.getValue();
            int kMax = idxs.size() / 2;
            if (kMax == 0) continue;

            // Initialize counts for the widest range (K=1)
            int left = idxs.get(0);
            int right = idxs.get(idxs.size() - 1);

            // Reset counts
            counts.clear();

            // Only scan if there's a middle section
            if (left < right - 1) {
                for (int i = left + 1; i < right; i++) {
                    counts.merge(array[i], 1, Integer::sum);
                }
            }

            for (int k = 1; k <= kMax; k++) {
                // Current middle max - only iterate over actual values
                int middleMax = 0;
                for (int count : counts.values()) {
                    if (count > middleMax) {
                        middleMax = count;
                    }
                }

                int currentLen = 2 * k + middleMax;
                if (currentLen > maxLen) {
                    maxLen = currentLen;
                }

                // Prepare for next K (k+1)
                if (k < kMax) {
                    int currentLeftIdx = idxs.get(k - 1);
                    int nextLeftIdx = idxs.get(k);

                    int currentRightIdx = idxs.get(idxs.size() - k);
                    int nextRightIdx = idxs.get(idxs.size() - k - 1);

                    // Remove elements from left side
                    for (int i = currentLeftIdx + 1; i <= nextLeftIdx; i++) {
                        int val = array[i];
                        Integer currentCount = counts.get(val);
                        if (currentCount != null) {
                            if (currentCount == 1) {
                                counts.remove(val);
                            } else {
                                counts.put(val, currentCount - 1);
                            }
                        }
                    }

                    // Remove elements from right side
                    for (int i = nextRightIdx; i < currentRightIdx; i++) {
                        int val = array[i];
                        Integer currentCount = counts.get(val);
                        if (currentCount != null) {
                            if (currentCount == 1) {
                                counts.remove(val);
                            } else {
                                counts.put(val, currentCount - 1);
                            }
                        }
                    }
                }
            }
        }

        return maxLen;
    }
}
