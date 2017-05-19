package com.dddong.net.type;

import com.dddong.net.ast.Location;

/**
 * Created by dddong on 2017/5/12.
 */
public abstract class TypeRef {
    protected Location location;

    public TypeRef(Location loc) {
        this.location = loc;
    }

    public Location location() {
        return location;
    }

    public int hashCode() {
        return toString().hashCode();
    }

}
