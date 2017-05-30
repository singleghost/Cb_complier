package com.dddong.net.type;

import com.dddong.net.ast.Location;

/**
 * Created by dddong on 2017/5/25.
 */
public class ArrayType extends Type {
    protected Type baseType;
    protected long length;
    protected long pointerSize;
    protected Location loc;
    static final protected long undefined = -1;

    public ArrayType(Location location, Type baseType, long pointerSize) {
        this(location, baseType, undefined, pointerSize);
    }

    public ArrayType(Location location, Type baseType, long length, long pointerSize) {
        this.baseType = baseType;
        this.length = length;
        this.pointerSize = pointerSize;
        this.loc = location;
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

    public Location location() {
        return loc;
    }

    @Override
    public String toString() {
        return baseType.toString() + " [" + (length == undefined ? "" : length) + "]";
    }

    public long length() {
        return length;
    }
}
