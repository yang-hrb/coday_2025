package com.nice.coday;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskThreeTests {
    private Path getPathForFile(String file) {
        URL resourceUrl = getClass().getResource(file);
        Path resourcePath = null;
        try {
            resourcePath = Paths.get(resourceUrl.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return resourcePath;
    }

    @Test
    public void test0() {
        long expected = 4;
        TaskThree solution = new TaskThree();
        long actual = solution.solve(getPathForFile("/Test0.txt"));
        assertEquals(expected, actual);
    }

    @Test
    public void test1() {
        long expected = 3;
        TaskThree solution = new TaskThree();
        long actual = solution.solve(getPathForFile("/Test1.txt"));
        assertEquals(expected, actual);
    }

    @Test
    public void test2() {
        long expected = 3;
        TaskThree solution = new TaskThree();
        long actual = solution.solve(getPathForFile("/Test2.txt"));
        assertEquals(expected, actual);
    }

    @Test
    public void test3() {
        long expected = 2;
        TaskThree solution = new TaskThree();
        long actual = solution.solve(getPathForFile("/Test3.txt"));
        assertEquals(expected, actual);
    }
}
