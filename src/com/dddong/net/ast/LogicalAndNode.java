package com.dddong.net.ast;

/**
 * Created by dddong on 2017/5/18.
 */
public class LogicalAndNode extends BinaryOpNode {
    public LogicalAndNode(ExprNode left, ExprNode right) {
        super(left, "&&", right);
    }

}
