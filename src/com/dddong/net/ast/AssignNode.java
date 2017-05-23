package com.dddong.net.ast;

/**
 * Created by dddong on 2017/5/18.
 */
public class AssignNode extends AbstractAssignNode {
    public AssignNode(ExprNode lhs, ExprNode rhs) {
        super(lhs, rhs);
    }

    @Override
    public <S, E> E accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }
}
