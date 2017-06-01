package com.dddong.net.type;

/**
 * Created by dddong on 2017/5/25.
 */
public class PointerType extends Type {
    protected long size;    //pointer size, not baseType size
    protected Type baseType;

    public PointerType(long size, Type baseType) {
        this.size = size;
        this.baseType = baseType;
    }

    @Override
    public boolean isPointer() {
        return true;
    }

    public Type baseType() {
        return baseType;
    }

    @Override
    public boolean equals(Object obj) {
        if(! (obj instanceof PointerType)) return false;
        return baseType.equals(((Type) obj).getPointerType().baseType());
    }

    @Override
    public long size() {
        return size;
    }


    @Override
    public String toString() {
        return baseType.toString() + "*";
    }
}
