package com.dddong.net.type;

/**
 * Created by dddong on 2017/5/25.
 */
public class PointerType extends Type {
    protected long size;
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
    public boolean isSameType(Type other) {
        if(! (other.isPointer())) return false;
        return baseType.isSameType(other.getPointerType().baseType());
    }

    @Override
    public boolean isCompatible(Type other) {
        //TODO
        return false;
    }

    @Override
    public boolean isCastableTo(Type target) {
        //TODO
        return false;
    }
}
