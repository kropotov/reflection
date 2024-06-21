package dev.kropotov.study.cache;

import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalCacheRepository {
    private static final int MAX_SIZE = 2; //TODO: сейчастак мало, чтобы точно запустить поток очистки для тестового App.java
    private static final Map<Object, Map<Method, ExpirationValue>> cachedMethodValues = new ConcurrentHashMap<>();

    private static Cleaner cleaner;

    public static Object getObjectCachedMethodValue(Object object, Method method, long lifetime) {
        if (cachedMethodValues.containsKey(object)) {
            Map<Method, ExpirationValue> methodExpirationValueMap = cachedMethodValues.get(object);
            if (methodExpirationValueMap.containsKey(method)) {
                ExpirationValue expirationValue = cachedMethodValues.get(object).get(method);
                expirationValue.setExpiration(LocalDateTime.now().plus(lifetime, ChronoUnit.MILLIS));
                return expirationValue.getValue();
            }
        }
        return null;
    }

    public static void setObjectCachedMethodValue(Object object, Method method, Object value, long lifetime) {
        if (!cachedMethodValues.containsKey(object)) {
            cachedMethodValues.put(object, new ConcurrentHashMap<>());
        }
        cachedMethodValues.get(object).put(method, new ExpirationValue(value, LocalDateTime.now().plus(lifetime, ChronoUnit.MILLIS)));
        if (cleaner == null && cachedMethodValues.size() >= MAX_SIZE) {
            cleaner = new Cleaner();
            cleaner.start();
        }
    }

    public static void clearExpiredCaches() {
        System.out.println("Clearing expired caches: " + Thread.currentThread().getName());//TODO: убрать отладку
        cachedMethodValues.forEach((object, methodExpirationValues) -> methodExpirationValues
                .forEach((method, expirationValue) -> {
                    if (LocalDateTime.now().isAfter(expirationValue.getExpiration())) {
                        System.out.println("Clearing expired cache: " + method.getName() + " = " + expirationValue.getValue());//TODO: убрать отладку
                        methodExpirationValues.remove(method, expirationValue);
                        if (methodExpirationValues.isEmpty()) {
                            cachedMethodValues.remove(object);
                        }
                    }
                }));
    }

    public static class Cleaner implements Runnable {
        public void start() {
            Thread cleanerThread = new Thread(this);
            cleanerThread.start();
        }

        @SneakyThrows//TODO: для тестового App.java
        @Override
        public void run() {
            Thread.sleep(1000); //TODO: для тестового App.java
            clearExpiredCaches();
            cleaner = null;
        }
    }
}
