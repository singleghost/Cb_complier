package com.dddong.net.type;

import com.dddong.net.ast.Location;

/**
 * Created by dddong on 2017/5/25.
 */
abstract public class NamedType extends Type {
    protected String name;
    protected Location location;

    public NamedType(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String name() {
        return name;
    }

    public Location location() {
        return location;
    }
}
