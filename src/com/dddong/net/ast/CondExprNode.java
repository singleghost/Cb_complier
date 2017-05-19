package com.dddong.net.ast;

import com.dddong.net.type.Type;

/**
 * Created by dddong on 2017/5/18.
 */
public class CondExprNode extends ExprNode {
    ExprNode cond, trueExpr, falseExpr;

    @Override
    public Type type() {
        return trueExpr.type();
    }

    @Override
    public Location location() {
        return cond.location();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("cond", cond);
        d.printMember("trueExpr", trueExpr);
        d.printMember("falseExpr", falseExpr);
    }

    public CondExprNode(ExprNode cond, ExprNode trueExpr, ExprNode falseExpr) {
        this.cond = cond;
        this.trueExpr = trueExpr;
        this.falseExpr = falseExpr;
    }
}
