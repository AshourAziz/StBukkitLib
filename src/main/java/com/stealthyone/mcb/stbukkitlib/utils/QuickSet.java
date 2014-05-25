package com.stealthyone.mcb.stbukkitlib.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility for creating Sets quickly and concisely.
 *
 * @param <T> The type for the contents of the Set.
 */
public class QuickSet<T> {

    private Set<T> set;

    /**
     * Constructs a new QuickSet using an empty HashMap.
     */
    public QuickSet() {
        set = new HashSet<>();
    }

    /**
     * Constructs a new QuickSet with a custom type of set.
     *
     * @param newSet Set to use.
     */
    public QuickSet(Set<T> newSet) {
        this.set = newSet;
    }

    /**
     * Constructs a new QuickSet using an initial value in a HashSet.
     * Useful for sets that only require one item.
     *
     * @param initialValue The initial value.
     */
    public QuickSet(T initialValue) {
        set = new HashSet<>();
        set.add(initialValue);
    }

    /**
     * Constructs a new QuickSet with a variable amount of values.
     *
     * @param initialValues Values to add on creation.
     */
    public QuickSet(T... initialValues) {
        set.addAll(Arrays.asList(initialValues));
    }

    public QuickSet<T> add(T value) {
        set.add(value);
        return this;
    }

    public Set<T> build() {
        return set;
    }

}