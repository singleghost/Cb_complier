package com.dddong.net.entity;


import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by dddong on 2017/5/22.
 */
public class ConstantTable implements Iterable<ConstantEntry> {
    private Map<String, ConstantEntry> table;

    public ConstantTable() {
        this.table = new LinkedHashMap<>();
    }


    @Override
    public Iterator<ConstantEntry> iterator() {
        return table.values().iterator();
    }

}
