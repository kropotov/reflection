package dev.kropotov.study.state;

import lombok.EqualsAndHashCode;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public class State {
    private final Object obj;
    private final Map<Field, Object> fieldValues = new HashMap<>();

    public State(Object obj) {
        this.obj = obj;
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                fieldValues.put(field, field.get(obj));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
