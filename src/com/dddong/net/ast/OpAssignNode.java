package com.dddong.net.ast;

/**
 * Created by dddong on 2017/5/18.
 */
public class OpAssignNode extends AbstractAssignNode {
    String opName;

    public OpAssignNode(ExprNode lhs, String opName, ExprNode rhs) {
        super(lhs, rhs);
        this.opName = opName;
    }

    public String operator() {
        return opName;
    }
}
