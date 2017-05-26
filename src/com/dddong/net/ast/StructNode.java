package com.dddong.net.ast;

import com.dddong.net.type.StructType;
import com.dddong.net.type.Type;
import com.dddong.net.type.TypeRef;

import java.util.List;

/**
 * Created by dddong on 2017/5/15.
 */
public class StructNode extends CompositeTypeDefinition {
    public StructNode(Location loc, TypeRef ref, String name, List<Slot> membs) {
        super(loc, ref, name, membs);
    }

    public String kind() {
        return "struct";
    }

    public boolean isStruct() {
        return true;
    }


    public Type definingType() {
        return new StructType(name(), members(), location());
    }

    public <T> T accept(DeclarationVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
