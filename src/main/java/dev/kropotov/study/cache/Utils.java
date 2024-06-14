package dev.kropotov.study.cache;

import java.lang.reflect.Proxy;

public class Utils {
    @SuppressWarnings("unchecked")
    public static <T> T cache(T object) {
        return (T) Proxy.newProxyInstance(
                object.getClass().getClassLoader(), object.getClass().getInterfaces(), new CacheHandler<>(object));
    }
}
