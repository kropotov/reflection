package dev.kropotov.study.cache;

import java.util.HashMap;
import java.util.Map;

public class ExecutionsCounter {
    private static final Map<Object, Integer> countExecutions = new HashMap<>();

    public static void shift(Object object) {
        int count = 0;
        if (countExecutions.containsKey(object)) {
            count = countExecutions.get(object);
        }
        count++;
        countExecutions.put(object, count);
    }

    public static int getCountExecutions(Object object) {
        return countExecutions.get(object);
    }
}
