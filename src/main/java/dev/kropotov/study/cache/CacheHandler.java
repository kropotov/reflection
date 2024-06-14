package dev.kropotov.study.cache;

import dev.kropotov.study.annotations.Cache;
import dev.kropotov.study.annotations.Mutator;
import dev.kropotov.study.state.State;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CacheHandler<T> implements InvocationHandler {
    private final T obj;
    private State state;

    public CacheHandler(T obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method objMethod = obj.getClass().getMethod(method.getName(), method.getParameterTypes());

        if (objMethod.isAnnotationPresent(Cache.class)) {
            if (state == null) {
                state = new State(obj);
            }
            long lifetime = objMethod.getAnnotation(Cache.class).value();
            Object value = GlobalCacheRepository.getObjectCachedMethodValue(state, objMethod, lifetime);
            if (value != null) {
                return value;
            }
            value = method.invoke(obj, args);
            GlobalCacheRepository.setObjectCachedMethodValue(state, objMethod, value, lifetime);
            return value;
        } else if (objMethod.isAnnotationPresent(Mutator.class)) {
            state = null;
        }

        return method.invoke(obj, args);
    }
}
