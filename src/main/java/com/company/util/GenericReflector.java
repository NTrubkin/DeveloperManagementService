package com.company.util;

import java.lang.reflect.ParameterizedType;

public class GenericReflector {
    private GenericReflector() {}

    public static Class getParameterType(Class generic) {
        return ((Class) ((ParameterizedType) generic.getGenericSuperclass()).getActualTypeArguments()[0]);
    }
}
