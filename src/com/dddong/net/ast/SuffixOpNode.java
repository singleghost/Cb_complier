package com.dddong.net.ast;

import com.dddong.net.type.Type;

/**
 * Created by dddong on 2017/5/12.
 */
public class SuffixOpNode extends UnaryArithmeticOpNode {
    public SuffixOpNode(String op, ExprNode expr) {
        super(op, expr);
    }

    @Override
    public <S, E> E accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }
}
