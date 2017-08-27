package com.company.util;

import java.lang.reflect.ParameterizedType;

/**
 * Простая обертка утилит рефлексии обобщенных классов
 */
public class GenericReflector {
    private GenericReflector() {
    }

    /**
     * Определяет класс параметра обобщенного класса, который является суперклассом для класса-аргумента
     *
     * @param generic
     * @return
     */
    public static Class getSuperclassParameterType(Class generic) {
        return ((Class) ((ParameterizedType) generic.getGenericSuperclass()).getActualTypeArguments()[0]);
    }
}
