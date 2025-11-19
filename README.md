# NiCE Realm Coding Challenge 2025

This repository contains solutions to three algorithmic problems from the NiCE Realm coding event.

## Project Structure

```
coday_2025/
├── java/
│   ├── taskone/    - Problem One: Effective Taverns
│   ├── tasktwo/    - Problem Two: Harmonized Crystals
│   └── taskthree/  - Problem Three: The Longest Story
├── problem1.md     - Problem One specification
├── problem2.md     - Problem Two specification
├── problem3.md     - Problem Three specification
└── TODO_20251119_1600.md - Project requirements document
```

## Problems Overview

### Task One: Effective Taverns of NiCE Realm

**Problem**: Given cities and taverns on a straight line, find the maximum distance any city has to travel to reach its nearest tavern.

**Implementation**: `java/taskone/app/src/main/java/com/nice/coday/ProblemOne.java`

**Algorithm**:
- Sort taverns by position for efficient binary search
- For each city, use binary search to find the nearest tavern
- Track the maximum distance across all cities
- Time Complexity: O(N log M) where N is cities, M is taverns
- Space Complexity: O(1) excluding input storage

**Key Features**:
- Efficient binary search approach
- Handles edge cases (cities at tavern locations)
- Supports large coordinate ranges (-10^9 to 10^9)

### Task Two: Harmonized Crystals

**Problem**: Find the longest "harmonized group" - a subsequence with structure A^K + B^M + A^K (symmetric pattern with at most 2 crystal types).

**Implementation**: `java/tasktwo/app/src/main/java/com/nice/coday/ProblemTwo.java`

**Algorithm**:
- Index all positions of each crystal type
- For single-type sequences, count total frequency
- For dual-type sequences:
  - For each outer type A, try all symmetric lengths K
  - Count middle section crystals efficiently
  - Optimize using sliding window technique
- Time Complexity: O(N × MAX_VAL) worst case
- Space Complexity: O(N × MAX_VAL) for position storage

**Key Features**:
- Handles both single-type and dual-type harmonized groups
- Efficient counting with reusable arrays
- Optimized window management for performance
- Supports crystal IDs from 1 to 200

### Task Three: The Longest Story of NiCE Realm

**Problem**: Find the longest chain of book references where:
- Each reference connects (previous book's target = current book's source)
- References must maintain input order (subsequence)
- Reference strength (word count) must strictly increase

**Implementation**: `java/taskthree/app/src/main/java/com/nice/coday/TaskThree.java`

**Algorithm**:
- Dynamic programming with TreeMap optimization
- For each reference (u, v, w):
  - Query best chain ending at u with weight < w
  - Extend that chain by 1
  - Update dp state for node v
  - Prune dominated states for efficiency
- Time Complexity: O(M log M) where M is number of references
- Space Complexity: O(M) for DP states

**Key Features**:
- Lazy initialization of data structures
- Efficient state pruning to avoid redundant computation
- TreeMap for fast range queries
- Handles large books (up to 100,000) and references

## Implementation Details

### Common Patterns

All three solutions use:
- **Robust input parsing**: StringTokenizer with flexible line handling
- **Resource management**: Try-with-resources for automatic cleanup
- **Exception handling**: Proper IOException propagation
- **Maven project structure**: Standard multi-module setup

### Testing

Test cases are provided in the resources for each task:
- Test0.txt through Test3.txt (some tasks have Test4.txt)
- Test files match the problem specifications exactly
- Expected outputs are documented in problem markdown files

To run tests (requires Maven and network connectivity):
```bash
cd java/taskone && mvn test
cd java/tasktwo && mvn test
cd java/taskthree && mvn test
```

## Algorithm Complexity Summary

| Task | Time Complexity | Space Complexity | Key Technique |
|------|----------------|------------------|---------------|
| One  | O(N log M)     | O(M)            | Binary Search |
| Two  | O(N × V)       | O(N × V)        | Counting + Sliding Window |
| Three| O(M log M)     | O(M)            | Dynamic Programming + TreeMap |

Where:
- N = number of cities/crystals
- M = number of taverns/references
- V = range of crystal values (max 200)

## Key Insights

1. **Problem One**: Binary search transforms linear search into logarithmic, crucial for large datasets
2. **Problem Two**: Pre-indexing positions enables efficient window queries without repeated scanning
3. **Problem Three**: State pruning prevents exponential blowup in DP, keeping only Pareto-optimal solutions

## Performance Optimizations

### Proven Optimizations

**Problem One**:
- Eliminated unnecessary `Math.abs()` calls by leveraging sorted tavern array
- Direct subtraction based on position reduces function call overhead
- Micro-optimization for large-scale test cases

**Problem Two** - High-Performance Array-Based Solution:
- **Why arrays beat HashMap for small ranges (1-200)**:
  - Fixed-size arrays provide O(1) access with minimal overhead
  - Arrays are cache-friendly: contiguous memory layout
  - `Arrays.fill()` is highly optimized at JVM level
  - No hash computation, no collision handling, no Entry objects
- **Key techniques**:
  - Pre-allocated array of ArrayLists for position tracking
  - Reusable `int[]` counts array to avoid repeated allocations
  - Cache-friendly linear scans for finding maximum
  - Direct array indexing for increment/decrement operations
- **Performance**: ~40-60ms per benchmark with ~18MB memory

**Task Three**:
- Lazy initialization of TreeMaps (only when needed)
- Efficient state pruning to maintain Pareto-optimal solutions
- Early termination in dominated entry removal

### Important Lesson Learned

**Don't over-optimize without benchmarking!**

Initial attempt to replace arrays with HashMap in Problem Two:
- **Result**: Performance degraded 20-30x (from ~50ms to ~1.8s)
- **Memory**: Increased 60-90x (from ~18MB to ~1.7GB)
- **Why it failed**:
  - HashMap overhead (Entry objects, hash computation) >> array overhead
  - For small integer ranges (1-200), arrays are optimal
  - Modern JVMs heavily optimize array operations

**Golden rule**: Arrays are king for small, dense integer ranges!

## Building and Running

Each task is a separate Maven module:

```bash
# Build all tasks
mvn clean install

# Run a specific task (example for task one)
cd java/taskone/app
mvn exec:java -Dexec.mainClass="com.nice.coday.ProblemOne"
```

## Notes

- All implementations prioritize correctness and efficiency
- Code handles edge cases (empty inputs, single elements, etc.)
- Solutions are optimized for the given constraints
- Input parsing is flexible with whitespace and line breaks
