package com.dddong.net.type;

/**
 * Created by dddong on 2017/5/25.
 */
public class VoidType extends Type {
    public VoidType() {
    }

    @Override
    public long size() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof VoidType);
    }

    @Override
    public String toString() {
        return "void";
    }

    @Override
    public boolean isVoid() {
        return true;
    }
}
