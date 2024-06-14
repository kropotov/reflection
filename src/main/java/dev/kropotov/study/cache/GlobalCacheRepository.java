package dev.kropotov.study.cache;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalCacheRepository {
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
        if (cleaner == null) {
            cleaner = new Cleaner();
            cleaner.start();
        }
    }

    public static void clearExpiredCaches() {
        cachedMethodValues.values().forEach(methodExpirationValues -> methodExpirationValues
                .forEach((method, expirationValue) -> {
                    if (LocalDateTime.now().isAfter(expirationValue.getExpiration())) {
                        methodExpirationValues.remove(method, expirationValue);
                    }
                }));
    }

    public static class Cleaner implements Runnable {
        public void start() {
            Thread cleanerThread = new Thread(this);
            cleanerThread.start();
        }

        @Override
        public void run() {
            //TODO: тестовые значения, ограничиваюшие время работы потока
            final int MAX_COUNT = 3;
            int count = 0;
            //

            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                clearExpiredCaches();

                //TODO: тестовые значения, ограничиваюшие время работы потока
                if (count++ > MAX_COUNT) {
                    break;
                }
                //
            }
        }
    }
}
