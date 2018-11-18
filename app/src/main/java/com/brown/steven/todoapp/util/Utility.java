package com.brown.steven.todoapp.util;

import java.util.concurrent.ThreadLocalRandom;

public class Utility {
    public static int randomNumberBetween(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}