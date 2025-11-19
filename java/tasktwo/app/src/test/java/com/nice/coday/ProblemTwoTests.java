package com.nice.coday;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProblemTwoTests {
    String TestsFolder = "";

    @BeforeEach
    public void setUp() {
        String path = getClass().getClassLoader().getResource("").getPath();
        if (path.matches("^/[A-Z]:.*")) {
            path = path.substring(1);
        }
        TestsFolder = path;
    }

    @Test
    public void test0() throws IOException {
        List<Integer> expected = Arrays.asList(6, 5);
        ProblemTwo solution = new ProblemTwo();
        List<Integer> actual = solution.solve(Paths.get(TestsFolder + "Test0.txt"));
        assertEquals(expected, actual);
    }

    @Test
    public void test1() throws IOException {
        List<Integer> expected = Arrays.asList(3, 3, 4, 3);
        ProblemTwo solution = new ProblemTwo();
        List<Integer> actual = solution.solve(Paths.get(TestsFolder + "Test1.txt"));
        assertEquals(expected, actual);
    }

    @Test
    public void test2() throws IOException {
        List<Integer> expected = Arrays.asList(4, 5, 5, 3, 4);
        ProblemTwo solution = new ProblemTwo();
        List<Integer> actual = solution.solve(Paths.get(TestsFolder + "Test2.txt"));
        assertEquals(expected, actual);
    }

    @Test
    public void test3() throws IOException {
        List<Integer> expected = Arrays.asList(3, 3, 4, 2, 2, 3, 4, 3);
        ProblemTwo solution = new ProblemTwo();
        List<Integer> actual = solution.solve(Paths.get(TestsFolder + "Test3.txt"));
        assertEquals(expected, actual);
    }
}
