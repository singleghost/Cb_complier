package com.dddong.net.ast;

import com.dddong.net.type.Type;
import com.dddong.net.type.TypeRef;

import java.util.List;

/**
 * Created by dddong on 2017/5/15.
 */
abstract public class CompositeTypeDefinition extends TypeDefinition {
    protected List<Slot> members;

    public CompositeTypeDefinition(Location loc, TypeRef ref,
                                   String name, List<Slot> membs) {
        super(loc, ref, name);
        members = membs;
    }

    public boolean isCompositeType() {
        return true;
    }

    abstract public String kind();

    public List<Slot> members() {
        return members;
    }

    protected void _dump(Dumper d) {
        d.printMember("name", name);
        d.printNodeList("members", members);
    }

}
