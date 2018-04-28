package me.david.timbernocheat.util;

import me.david.api.objects.Pair;

import java.util.ArrayList;
import java.util.List;

public class LimitedList<T> {

    private List<Pair<Long, T>> list = new ArrayList<>();

    private int maxSize;
    private long index = 0;

    public LimitedList(int maxSize){
        this.maxSize = maxSize;
    }

    public void add(T value){
        if(maxSize == list.size()){
            Pair<Long, T> longest = null;
            for(Pair<Long, T> entry : list)
                if(longest == null || entry.getOne() < longest.getOne())
                    longest = entry;
            if(longest == null) throw new IllegalStateException();
            list.remove(longest);
        }
        list.add(new Pair<>(index, value));
        index++;
    }

    public List<T> getValues(){
        List<T> values = new ArrayList<>();
        for(Pair<Long, T> entry : list)
            values.add(entry.getTwo());
        return values;
    }

    public List<Pair<Long, T>> getList() {
        return list;
    }
}
