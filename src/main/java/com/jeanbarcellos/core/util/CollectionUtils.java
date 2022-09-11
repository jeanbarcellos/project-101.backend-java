package com.jeanbarcellos.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionUtils {

    private CollectionUtils() {
    }

    public static <T extends Object, R> List<R> mapToList(Collection<T> collection, Function<T, ? extends R> mapper) {
        if (collection == null) {
            collection = new ArrayList<>();
        }

        if (mapper == null) {
            return new ArrayList<>();
        }

        Stream<T> stream = collection.stream();

        return stream.map(mapper).collect(Collectors.toList());
    }

    public static <T extends Object, R> Set<R> mapToSet(Collection<T> collection, Function<T, ? extends R> mapper) {
        if (collection == null) {
            collection = new HashSet<>();
        }

        if (mapper == null) {
            return new HashSet<>();
        }

        Stream<T> stream = collection.stream();

        return stream.map(mapper).collect(Collectors.toSet());
    }

}
