package com.dddong.net.type;

/**
 * Created by dddong on 2017/5/25.
 */
public class ArrayType extends Type {
    protected Type baseType;
    protected long length;
    protected long pointerSize;
    static final protected long undefined = -1;

    public ArrayType(Type baseType, long pointerSize) {
        this(baseType, undefined, pointerSize);
    }

    public ArrayType(Type baseType, long length, long pointerSize) {
        this.baseType = baseType;
        this.length = length;
        this.pointerSize = pointerSize;
    }

    @Override
    public long size() {
        return pointerSize;
    }

    @Override
    public boolean isSameType(Type other) {
        return false;
    }

    @Override
    public boolean isCompatible(Type other) {
        return false;
    }

    @Override
    public boolean isCastableTo(Type target) {
        return false;
    }

    public Type baseType() {
        return baseType;
    }
}
