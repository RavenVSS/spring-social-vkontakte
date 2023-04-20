package org.springframework.social.vkontakte.utils;

import java.util.function.Function;

/**
 * @author Sergei Vorona
 */
public final  class ObjectUtils {

    public static <T, R> R mapOrNull(T obj, Function<T, R> mapper) {
        return obj == null ? null : mapper.apply(obj);
    }
}
