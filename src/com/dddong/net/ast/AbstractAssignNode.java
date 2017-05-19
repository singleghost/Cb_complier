package com.dddong.net.ast;

import com.dddong.net.type.Type;

/**
 * Created by dddong on 2017/5/18.
 */
abstract public class AbstractAssignNode extends ExprNode {
    ExprNode lhs, rhs;

    public AbstractAssignNode(ExprNode lhs, ExprNode rhs) {
        super();
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public Type type() {
        return lhs.type();
    }

    public ExprNode lhs() {
        return lhs;
    }

    public ExprNode rhs() {
        return rhs;
    }

    public void setRHS(ExprNode expr) {
        rhs = expr;
    }

    @Override
    public Location location() {
        return lhs.location();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("lhs", lhs);
        d.printMember("rhs", rhs);
    }
}
