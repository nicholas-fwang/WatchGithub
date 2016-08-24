package io.fisache.watchgithub.data.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruCacheImpl extends LinkedHashMap<String, UserRepoCache> {
    private int capacity;

    public LruCacheImpl(int capacity, float loadFactor) {
        super(capacity, loadFactor, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Entry<String, UserRepoCache> eldest) {
        return size() > this.capacity;
    }
}
