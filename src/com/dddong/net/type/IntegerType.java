package com.dddong.net.type;

/**
 * Created by dddong on 2017/5/25.
 */
public class IntegerType extends Type {
    long size;
    boolean isSigned;
    String typename;

    public IntegerType(long size, boolean isSigned, String typename) {
        super();
        this.size = size;
        this.isSigned = isSigned;
        this.typename = typename;
    }

    public boolean isInteger() {
        return true;
    }
    @Override
    public long size() {
        return size;
    }

    @Override
    public boolean isSameType(Type other) {
        if(!other.isInteger()) return false;
        return equals(other.getIntegerType());  //TODO: 疑问,这里为什么使用默认的 equals,默认的 equals 不是比较的两个引用吗
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

    @Override
    public boolean isSigned() {
        return isSigned;
    }
}
