package com.dddong.net.type;

import com.dddong.net.ast.Location;
import com.dddong.net.ast.TypeNode;

/**
 * Created by dddong on 2017/5/25.
 */
public class UserType extends NamedType {
    protected TypeNode real;

    public UserType(String name, TypeNode real, Location location) {
        super(name, location);
        this.real = real;
    }

    public TypeNode realType() {
        return real;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public long size() {
        return realType().size();
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
}
