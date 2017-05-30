package com.dddong.net.ir;

import com.dddong.net.asm.Type;
import com.dddong.net.ast.Dumpable;
import com.dddong.net.ast.Dumper;

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
        _dump(d);
    }

    abstract protected void _dump(Dumper d);

    public Expr addressNode(Type type) {
        throw new Error("unexpected type for LHS:" + getClass());
    }

    public boolean isVar() { return false; };

    public Type type() {
        return type;
    }
}
