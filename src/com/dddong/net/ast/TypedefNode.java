package com.dddong.net.ast;

import com.dddong.net.type.Type;
import com.dddong.net.type.TypeRef;
import com.dddong.net.type.UserType;
import com.dddong.net.type.UserTypeRef;

/**
 * Created by dddong on 2017/5/15.
 */
public class TypedefNode extends TypeDefinition {
    protected TypeNode real;

    public TypedefNode(Location loc, TypeRef real, String name) {
        super(loc, new UserTypeRef(loc, name), name);
        this.real = new TypeNode(real);
    }

    public boolean isUserType() {
        return true;
    }

    public TypeNode realTypeNode() {
        return real;
    }

    public Type realType() {
        return real.type();
    }

    public TypeRef realTypeRef() {
        return real.typeRef();
    }

    // #@@range/definingType{
    public Type definingType() {
        return new UserType(name(), realTypeNode(), location());
    }
    // #@@}

    protected void _dump(Dumper d) {
        d.printMember("name", name);
        d.printMember("typeNode", typeNode);
    }

    public <T> T accept(DeclarationVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
