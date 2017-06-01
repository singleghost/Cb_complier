package com.dddong.net.ast;

/**
 * Created by dddong on 2017/5/12.
 */
public class UnaryArithmeticOpNode extends UnaryOpNode {
    protected long amount;

    public UnaryArithmeticOpNode(String op, ExprNode expr) {
        super(op, expr);
        amount = 1;
    }

    public void setExpr(ExprNode expr) {
        this.expr = expr;
    }

    public long amount() {
        return this.amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public <S, E> E accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }
}
