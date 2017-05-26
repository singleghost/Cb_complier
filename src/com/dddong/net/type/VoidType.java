package com.dddong.net.type;

/**
 * Created by dddong on 2017/5/25.
 */
public class VoidType extends Type {
    public VoidType() {
    }

    @Override
    public long size() {
        return 1;//TODO 疑问
    }

    @Override
    public boolean isSameType(Type other) {
        return other.isVoid();
    }

    @Override
    public boolean isCompatible(Type other) {
        return other.isVoid();
    }

    @Override
    public boolean isCastableTo(Type target) {
        return target.isVoid();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof VoidType);
    }

    @Override
    public String toString() {
        return "void";
    }
}
