package com.dddong.net.ir;

import com.dddong.net.asm.Type;

/**
 * Created by dddong on 2017/5/27.
 */
abstract public class Expr implements Dumpable {
    final Type type;

    public Expr(Type type) {
        this.type = type;
    }


    @Override
    public void dump(Dumper d) {
        d.printClass(this);
        d.printMember("type", type);
        _dump(d);
    }

    abstract protected void _dump(Dumper d);

    public Expr addressNode(Type type) {
        throw new Error("unexpected type for LHS:" + getClass());
    }

    public boolean isVar() { return false; };

    public boolean isUni() { return false; }

    public boolean isBin() { return false; }

    public boolean isCall() { return false; }

    public boolean isAddr() { return false; }

    public boolean isMem() { return false; }

    public boolean isInt() { return false; }

    public boolean isStr() { return false; }

    public Type type() {
        return type;
    }
}
