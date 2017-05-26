package com.dddong.net.ast;

import com.dddong.net.type.Type;
import com.dddong.net.type.TypeRef;
import com.dddong.net.type.UnionType;

import java.util.List;

/**
 * Created by dddong on 2017/5/15.
 */
public class UnionNode extends CompositeTypeDefinition {
    public UnionNode(Location loc, TypeRef ref, String name, List<Slot> membs) {
        super(loc, ref, name, membs);
    }

    public String kind() {
        return "union";
    }

    public boolean isUnion() {
        return true;
    }

    public Type definingType() {
        return new UnionType(name(), members(), location());
    }

    public <T> T accept(DeclarationVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
