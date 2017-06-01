package com.dddong.net.ir;

import com.dddong.net.asm.Type;

/**
 * Created by dddong on 2017/5/27.
 */
public class Bin extends Expr {
    Op op;
    Expr left;
    Expr right;

    public Bin(Type type, Op op, Expr left, Expr right) {
        super(type);
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("op", op.toString());
        d.printMember("left expr", left);
        d.printMember("right expr", right);
    }

    @Override
    public boolean isBin() {
        return true;
    }

}
