package com.dddong.net.type;

import com.dddong.net.ast.Location;

/**
 * Created by dddong on 2017/5/14.
 */
public class ArrayTypeRef extends TypeRef {
    protected TypeRef baseType;
    protected long length;
    static final long undefined = -1;

    public ArrayTypeRef(TypeRef baseType, long length) {
        super(baseType.location());
        this.baseType = baseType;
        this.length = length;
    }

    public ArrayTypeRef(TypeRef baseType) {
        super(baseType.location());
        this.baseType = baseType;
        this.length = undefined;
    }

    public boolean isArray() {
        return true;
    }

    public boolean equals(Object other) {
        //这里是不是少比较了一个域
        return (other instanceof ArrayTypeRef) &&
                (length == ((ArrayTypeRef)other).length);
    }

    public TypeRef baseType() {
        return baseType;
    }

    public long length() {
        return length;
    }

    public boolean isLengthUndefined() {
        return (length == undefined);
    }

    public String toString() {
        return baseType.toString()
                + "["
                + (length == undefined ? "" : "" + length)
                + "]";
    }

}
