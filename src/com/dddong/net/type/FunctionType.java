package com.dddong.net.type;

/**
 * Created by dddong on 2017/5/25.
 */
public class FunctionType extends Type {
    protected Type returnType;
    protected ParamTypes paramTypes;

    public FunctionType(Type returnType, ParamTypes paramTypes) {
        this.returnType = returnType;
        this.paramTypes = paramTypes;
    }

    @Override
    public long size() {
        throw new Error("FunctionType#size called");
    }

    @Override
    public boolean isFunction() {
        return true;
    }

    public Type returnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        return returnType.toString() + " (" + paramTypes.toString() + ")";
    }
}
