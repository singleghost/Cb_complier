package com.dddong.net.ast;

import com.dddong.net.type.Type;
import com.dddong.net.type.TypeRef;

/**
 * Created by dddong on 2017/5/15.
 */
abstract public class TypeDefinition extends Node {
    protected String name;
    protected Location location;
    protected TypeNode typeNode;

    public TypeDefinition(Location loc, TypeRef ref, String name) {
        this.name = name;
        this.location = loc;
        this.typeNode = new TypeNode(ref);
    }

    public String name() {
        return name;
    }

    public Location location() {
        return location;
    }

    public TypeNode typeNode() {
        return typeNode;
    }

    public TypeRef typeRef() {
        return typeNode.typeRef();
    }

//    public Type type() {
//        return typeNode.type();
//    }

//    abstract public Type definingType();
//    abstract public <T> T accept(DeclarationVisitor<T> visitor);

}
