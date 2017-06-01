package com.dddong.net.ir;

import com.dddong.net.asm.Type;

/**
 * Created by dddong on 2017/5/27.
 */
public class Uni extends Expr {
    Op op;
    Expr expr;

    public Uni(Type type, Op op, Expr expr) {
        super(type);
        this.op = op;
        this.expr = expr;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("op", op.toString());
        d.printMember("expr", expr);
    }

    @Override
    public boolean isUni() {
        return true;
    }
}
