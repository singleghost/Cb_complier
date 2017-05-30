package com.dddong.net.ir;

import com.dddong.net.asm.Type;
import com.dddong.net.ast.Dumper;

import java.util.List;

/**
 * Created by dddong on 2017/5/27.
 */
public class Call extends Expr {
    Expr funcExpr;
    List<Expr> args;

    public Call(Type type, Expr funcExpr, List<Expr> args) {
        super(type);
        this.funcExpr = funcExpr;
        this.args = args;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("funcExpr", funcExpr);
        d.printNodeList("args", args);
    }
}
