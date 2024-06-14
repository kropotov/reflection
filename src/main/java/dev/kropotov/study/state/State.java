package dev.kropotov.study.state;

import lombok.EqualsAndHashCode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class State {
    private final Object obj;
    private final List<Object> fields = new ArrayList<>();

    public State(Object obj) {
        this.obj = obj;
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                fields.add(field.get(obj));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
