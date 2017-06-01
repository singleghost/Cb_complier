package com.dddong.net.ir;

import com.dddong.net.asm.Type;

/**
 * Created by dddong on 2017/5/28.
 */
public class Mem extends Expr {
    Expr expr;

    public Mem(Type type, Expr expr) {
        super(type);
        this.expr = expr;
    }



    @Override
    protected void _dump(Dumper d) {
        d.printMember("expr", expr);
    }

    @Override
    public boolean isMem() {
        return true;
    }

    @Override
    public Expr addressNode(Type type) {
        return expr;
    }
}
