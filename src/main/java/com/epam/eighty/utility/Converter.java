package com.epam.eighty.utility;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public final class Converter {
    private static Converter instance;

    public static synchronized Converter getInstance() {
        if (instance == null) {
            instance = new Converter();
        }
        return instance;
    }

    private Converter() {
    }

    /**
     * @param iters
     *            (Iterable<T>)
     * @return new HashSet
     */
    public static <T> Set<T> convertToHashSet(final Iterable<T> iters) {
        Set<T> set = new HashSet<T>();
        return getSetFromIterable(set, iters);
    }

    /**
     * @param iters
     *            (Iterable<T>)
     * @return new TreeSet
     */
    public static <T> Set<T> convertToTreeSet(final Iterable<T> iters) {
        Set<T> set = new TreeSet<T>();
        return getSetFromIterable(set, iters);
    }

    private static <T> Set<T> getSetFromIterable(Set<T> set, final Iterable<T> iters) {
        iters.forEach((ob) -> set.add(ob));
        return set;
    }
}
