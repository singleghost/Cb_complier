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
    public boolean isSigned() {
        return isSigned;
    }

    @Override
    public String toString() {
        return typename;
    }

    @Override
    public boolean isInt() {
        return true;
    }
}
