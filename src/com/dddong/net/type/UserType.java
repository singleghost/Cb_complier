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

    public Type realType() {
        return real.type();
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
    public boolean isVoid() {
        return realType().isVoid();
    }

    @Override
    public boolean isUserType() {
       return true;
    }
}
