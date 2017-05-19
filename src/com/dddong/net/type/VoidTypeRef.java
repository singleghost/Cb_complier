package com.dddong.net.type;

import com.dddong.net.ast.Location;

/**
 * Created by dddong on 2017/5/14.
 */
public class VoidTypeRef extends TypeRef {
    public VoidTypeRef(Location loc) {
        super(loc);
    }
    public VoidTypeRef() {
        super(null);
    }

    public boolean isVoid() {
        return true;
    }

    public boolean equals(Object other) {
        return (other instanceof VoidTypeRef);
    }

    public String toString() {
        return "void";
    }

}
