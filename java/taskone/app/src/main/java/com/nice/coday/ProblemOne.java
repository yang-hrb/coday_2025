package com.nice.coday;

import java.io.IOException;
import java.nio.file.Path;

public class ProblemOne {
    public long solve(Path inputPath) throws IOException {
        try (java.io.BufferedReader reader = java.nio.file.Files.newBufferedReader(inputPath)) {
            java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(reader.readLine());
            
            // Helper to get next token
            // Since we are inside a method, we can't easily define a helper class/method that shares the tokenizer 
            // without passing it around or making it a class member. 
            // But the logic is simple: loop to fill tokenizer.
            
            // Read N
            while (!tokenizer.hasMoreTokens()) {
                String line = reader.readLine();
                if (line == null) return 0;
                tokenizer = new java.util.StringTokenizer(line);
            }
            int n = Integer.parseInt(tokenizer.nextToken());
            
            long[] cities = new long[n];
            for (int i = 0; i < n; i++) {
                while (!tokenizer.hasMoreTokens()) {
                    String line = reader.readLine();
                    if (line == null) break;
                    tokenizer = new java.util.StringTokenizer(line);
                }
                if (tokenizer.hasMoreTokens()) {
                    cities[i] = Long.parseLong(tokenizer.nextToken());
                }
            }

            // Read M
            while (!tokenizer.hasMoreTokens()) {
                String line = reader.readLine();
                if (line == null) break;
                tokenizer = new java.util.StringTokenizer(line);
            }
            int m = 0;
            if (tokenizer.hasMoreTokens()) {
                m = Integer.parseInt(tokenizer.nextToken());
            }

            long[] taverns = new long[m];
            for (int i = 0; i < m; i++) {
                while (!tokenizer.hasMoreTokens()) {
                    String line = reader.readLine();
                    if (line == null) break;
                    tokenizer = new java.util.StringTokenizer(line);
                }
                if (tokenizer.hasMoreTokens()) {
                    taverns[i] = Long.parseLong(tokenizer.nextToken());
                }
            }

            java.util.Arrays.sort(taverns);

            long maxMinDist = 0;

            for (long city : cities) {
                int idx = java.util.Arrays.binarySearch(taverns, city);
                long minDist;
                if (idx >= 0) {
                    minDist = 0;
                } else {
                    int insertionPoint = -(idx + 1);
                    long dist1 = Long.MAX_VALUE;
                    long dist2 = Long.MAX_VALUE;

                    // Taverns are sorted, so we can directly subtract
                    if (insertionPoint < m) {
                        dist1 = taverns[insertionPoint] - city;
                    }
                    if (insertionPoint > 0) {
                        dist2 = city - taverns[insertionPoint - 1];
                    }
                    minDist = Math.min(dist1, dist2);
                }
                if (minDist > maxMinDist) {
                    maxMinDist = minDist;
                }
            }

            return maxMinDist;
        }
    }
}
