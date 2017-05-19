package com.dddong.net.type;

import com.dddong.net.ast.Location;

/**
 * Created by dddong on 2017/5/14.
 */
public class StructTypeRef extends TypeRef {
    protected String name;
    public StructTypeRef(String name) {
        this(null, name);
    }
    public StructTypeRef(Location loc, String name) {
        super(loc);
        this.name = name;
    }

    public boolean isStruct() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof StructTypeRef)) return false;
        return this.name.equals(((StructTypeRef) obj).name);
    }

    @Override
    public String toString() {
        return "struct " + name;
    }
}
