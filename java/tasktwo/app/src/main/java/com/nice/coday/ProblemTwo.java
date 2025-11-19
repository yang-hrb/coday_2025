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

        // Store indices for each value - array is much faster than HashMap for small range
        List<Integer>[] positions = new ArrayList[MAX_VAL + 1];
        for (int i = 1; i <= MAX_VAL; i++) {
            positions[i] = new ArrayList<>();
        }

        int[] totalFreq = new int[MAX_VAL + 1];
        for (int i = 0; i < n; i++) {
            int val = array[i];
            positions[val].add(i);
            totalFreq[val]++;
        }

        int maxLen = 0;

        // Single type max
        for (int v = 1; v <= MAX_VAL; v++) {
            if (totalFreq[v] > maxLen) {
                maxLen = totalFreq[v];
            }
        }

        // Dual type - reusable counts array for O(1) access
        int[] counts = new int[MAX_VAL + 1];

        for (int A = 1; A <= MAX_VAL; A++) {
            List<Integer> idxs = positions[A];
            int kMax = idxs.size() / 2;
            if (kMax == 0) continue;

            // Initialize counts for the widest range (K=1)
            int left = idxs.get(0);
            int right = idxs.get(idxs.size() - 1);

            // Reset counts - Arrays.fill is very fast
            java.util.Arrays.fill(counts, 0);

            // Only scan if there's a middle section
            if (left < right - 1) {
                for (int i = left + 1; i < right; i++) {
                    counts[array[i]]++;
                }
            }

            for (int k = 1; k <= kMax; k++) {
                // Current middle max - array scan is very cache-friendly
                int middleMax = 0;
                for (int v = 1; v <= MAX_VAL; v++) {
                    if (counts[v] > middleMax) {
                        middleMax = counts[v];
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

                    // Remove elements from left side - O(1) array access
                    for (int i = currentLeftIdx + 1; i <= nextLeftIdx; i++) {
                        counts[array[i]]--;
                    }

                    // Remove elements from right side - O(1) array access
                    for (int i = nextRightIdx; i < currentRightIdx; i++) {
                        counts[array[i]]--;
                    }
                }
            }
        }

        return maxLen;
    }
}
