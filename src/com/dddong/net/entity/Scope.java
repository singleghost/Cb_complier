package com.dddong.net.entity;

import com.dddong.net.exception.SemanticException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dddong on 2017/5/22.
 */
abstract public class Scope {
    protected List<LocalScope> children;

    public abstract Entity get(String name) throws SemanticException;

    public void addChildren(LocalScope scope) {
        children.add(scope);
    }

    public Scope() {
        children = new LinkedList<>();
    }
}
