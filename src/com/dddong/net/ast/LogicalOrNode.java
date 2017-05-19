package com.dddong.net.ast;

/**
 * Created by dddong on 2017/5/18.
 */
public class LogicalOrNode extends BinaryOpNode {
    public LogicalOrNode(ExprNode left, ExprNode right) {
        super(left, "&&", right);
    }
}
