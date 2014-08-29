package com.stealthyone.mcb.stbukkitlib.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility for creating Maps quickly and concisely.
 *
 * @param <K> The type of keys for the Map.
 * @param <V> The type of values for the Map.
 */
public class QuickMap<K, V> {

    protected final Map<K, V> map;

    /**
     * Constructs a new QuickMap using an empty HashMap.
     */
    public QuickMap() {
        map = new HashMap<>();
    }

    /**
     * Constructs a new QuickMap using a different type of Map.
     *
     * @param map Map to use.
     */
    public QuickMap(Map<K, V> map) {
        this.map = map;
    }

    /**
     * Constructs a new QuickMap using an initial key/value pair in a HashMap.
     * Useful for maps that only require one item.
     *
     * @param initialKey The initial key.
     * @param initialValue The initial value for the initial key.
     */
    public QuickMap(K initialKey, V initialValue) {
        map = new HashMap<>();
        map.put(initialKey, initialValue);
    }

    public QuickMap<K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }

    public QuickMap<K, V> putAll(Map<K, V> values) {
        map.putAll(values);
        return this;
    }

    public Map<K, V> build() {
        return map;
    }

}