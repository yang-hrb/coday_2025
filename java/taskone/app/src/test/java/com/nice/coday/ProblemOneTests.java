package com.nice.coday;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProblemOneTests {
    String testsFolder = "";

    @BeforeEach
    public void setUp() {
        try {
            URL resource = getClass().getClassLoader().getResource("");
            if (resource != null) {
                testsFolder = Paths.get(resource.toURI()).toString() + System.getProperty("file.separator");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to set up test folder", e);
        }
    }

    @Test
    public void test0() throws IOException {
        long expected = 5;
        ProblemOne solution = new ProblemOne();
        long actual = solution.solve(Paths.get(testsFolder + "Test0.txt"));
        assertEquals(expected, actual);
    }

    @Test
    public void test1() throws IOException {
        long expected = 3;
        ProblemOne solution = new ProblemOne();
        long actual = solution.solve(Paths.get(testsFolder + "Test1.txt"));
        assertEquals(expected, actual);
    }

    @Test
    public void test2() throws IOException {
        long expected = 244;
        ProblemOne solution = new ProblemOne();
        long actual = solution.solve(Paths.get(testsFolder + "Test2.txt"));
        assertEquals(expected, actual);
    }

    @Test
    public void test3() throws IOException {
        long expected = 1;
        ProblemOne solution = new ProblemOne();
        long actual = solution.solve(Paths.get(testsFolder + "Test3.txt"));
        assertEquals(expected, actual);
    }
}
