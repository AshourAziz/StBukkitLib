package com.stealthyone.mcb.stbukkitlib.utils;

import java.util.*;

/**
 * Utility for creating Lists quickly and concisely.
 *
 * @param <T> The type for the contents of the List.
 */
public class QuickList<T> {

    private List<T> list;

    /**
     * Constructs a new QuickList using an empty ArrayList.
     */
    public QuickList() {
        list = new ArrayList<>();
    }

    /**
     * Constructs a new QuickList with a custom type of set.
     *
     * @param newList List to use.
     */
    public QuickList(List<T> newList) {
        this.list = newList;
    }

    /**
     * Constructs a new QuickSet using an initial value in a ArrayList.
     * Useful for sets that only require one item.
     *
     * @param initialValue The initial value.
     */
    public QuickList(T initialValue) {
        list = new ArrayList<>();
        list.add(initialValue);
    }

    /**
     * Constructs a new QuickSet with a variable amount of values.
     *
     * @param initialValues Values to add on creation.
     */
    public QuickList(T... initialValues) {
        list.addAll(Arrays.asList(initialValues));
    }

    public QuickList<T> add(T value) {
        list.add(value);
        return this;
    }

    public List<T> build() {
        return list;
    }

}