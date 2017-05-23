package com.dddong.net.entity;

import com.dddong.net.ast.*;
import com.dddong.net.type.Type;

/**
 * Created by dddong on 2017/5/15.
 */
abstract public class Entity implements Dumpable {
    protected String name;
    protected boolean isPrivate;
    protected TypeNode typeNode;
    protected long nRefered;
//    protected MemoryReference memref;
//    protected Operand address;

    public Entity(boolean priv, TypeNode type, String name) {
        this.name = name;
        this.isPrivate = priv;
        this.typeNode = type;
        this.nRefered = 0;
    }

    public String name() {
        return name;
    }

    public String symbolString() {
        return name();
    }

    abstract public boolean isDefined();
    abstract public boolean isInitialized();

    public boolean isConstant() { return false; }

    public ExprNode value() {
        throw new Error("Entity#value");
    }

    public boolean isParameter() { return false; }

    public boolean isPrivate() {
        return isPrivate;
    }

    public TypeNode typeNode() {
        return typeNode;
    }

    public Type type() {
        return typeNode.type();
    }

    public long allocSize() {
        return type().allocSize();
    }

    public long alignment() {
        return type().alignment();
    }

    public void refered() {
        nRefered++;
    }

    public boolean isRefered() {
        return (nRefered > 0);
    }

//    public void setMemref(MemoryReference mem) {
//        this.memref = mem;
//    }
//
//    public MemoryReference memref() {
//        checkAddress();
//        return memref;
//    }
//
//    public void setAddress(MemoryReference mem) {
//        this.address = mem;
//    }
//
//    public void setAddress(ImmediateValue imm) {
//        this.address = imm;
//    }
//
//    public Operand address() {
//        checkAddress();
//        return address;
//    }

//    protected void checkAddress() {
//        if (memref == null && address == null) {
//            throw new Error("address did not resolved: " + name);
//        }
//    }

    public Location location() {
        return typeNode.location();
    }

//    abstract public <T> T accept(EntityVisitor<T> visitor);

    public void dump(Dumper d) {
        d.printClass(this, location());
        _dump(d);
    }

    abstract protected void _dump(Dumper d);

}
