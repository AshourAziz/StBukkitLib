package com.stealthyone.mcb.stbukkitlib.utils;

import java.util.HashMap;

/**
 * Utility for creating HashMaps quickly and concisely.
 *
 * @param <K> The type of keys for the HashMap.
 * @param <V> The type of values for the HashMap.
 */
public class QuickHashMap<K, V> {

    private HashMap<K, V> map;

    /**
     * Constructs a new QuickHashMap using an empty HashMap.
     */
    public QuickHashMap() {
        map = new HashMap<>();
    }

    /**
     * Constructs a new QuickHashMap using an initial key/value pair.
     * Useful for maps that only require one item.
     *
     * @param initialKey The initial key.
     * @param initialValue The initial value for the initial key.
     */
    public QuickHashMap(K initialKey, V initialValue) {
        map = new HashMap<>();
        map.put(initialKey, initialValue);
    }

    public QuickHashMap<K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }

    public HashMap<K, V> build() {
        return map;
    }

}