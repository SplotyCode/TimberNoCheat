package me.david.timbernocheat.util;

import me.david.api.objects.Pair;

import java.util.HashMap;
import java.util.Map;

public class LimitedMap<K, V> {

    private HashMap<K, Pair<Long, V>> map = new HashMap<>();
    private int maxSize;
    private long index = 0;

    public LimitedMap(int maxSize){
        this.maxSize = maxSize;
    }

    public void put(K key, V value){
        if(maxSize == size()){
            Map.Entry<K, Pair<Long, V>> longest = null;
            for(Map.Entry<K, Pair<Long, V>> entry : map.entrySet())
                if(longest == null || entry.getValue().getOne() < longest.getValue().getOne())
                    longest = entry;
            if(longest == null) throw new IllegalStateException();
            map.remove(longest.getKey());
        }
        map.put(key, new Pair<>(index, value));
        index++;
    }

    public V get(K key){
        Pair<Long, V> value = map.get(key);
        if(value == null) return null;
        return value.getTwo();
    }

    public int size(){
        return map.size();
    }

    public HashMap<K, Pair<Long, V>> getMap() {
        return map;
    }
}
