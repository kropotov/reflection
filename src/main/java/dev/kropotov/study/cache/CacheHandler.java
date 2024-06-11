package dev.kropotov.study.cache;

import dev.kropotov.study.annotations.Cache;
import dev.kropotov.study.annotations.Mutator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CacheHandler implements InvocationHandler {
    private final Object obj;
    private final Map<Method, Object> cachedMethodValues = new HashMap<>();

    public CacheHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method objMethod = obj.getClass().getMethod(method.getName(), method.getParameterTypes());

        if (objMethod.isAnnotationPresent(Cache.class)) {
            if (cachedMethodValues.containsKey(objMethod)) {
                return cachedMethodValues.get(objMethod);
            }
            Object value = method.invoke(obj, args);
            cachedMethodValues.put(objMethod, value);
            return value;
        } else if (objMethod.isAnnotationPresent(Mutator.class)) {
            cachedMethodValues.clear();
        }

        return method.invoke(obj, args);
    }
}
