package dev.kropotov.study.cache;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GlobalCacheRepository {
    private static final Map<Object, Map<Method, ExpirationValue>> cachedMethodValues = new HashMap<>();

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
            cachedMethodValues.put(object, new HashMap<>());
        }
        cachedMethodValues.get(object).put(method, new ExpirationValue(value, LocalDateTime.now().plus(lifetime, ChronoUnit.MILLIS)));
    }

    public static void clearExpiredCaches() {
        cachedMethodValues.values().forEach(methodExpirationValues -> {
            Iterator<Map.Entry<Method, ExpirationValue>> iter = methodExpirationValues.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<Method, ExpirationValue> methodExpirationValueEntry = iter.next();
                ExpirationValue expirationValue = methodExpirationValueEntry.getValue();
                Method method = methodExpirationValueEntry.getKey();
                if (LocalDateTime.now().isAfter(expirationValue.getExpiration())) {
                    methodExpirationValues.remove(method, expirationValue);
                }
            }
        });


    }
}
