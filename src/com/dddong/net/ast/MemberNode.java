package com.dddong.net.ast;

/**
 * Created by dddong on 2017/5/18.
 */
public class MemberNode extends LHSNode {
    ExprNode expr;
    String name;

    public MemberNode(ExprNode expr, String name) {
        this.expr = expr;
        this.name = name;
    }

    @Override
    public Location location() {
        return expr.location();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("expr", expr);
        d.printMember("name", name);
    }

    @Override
    public <S, E> E accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    public ExprNode expr() {
        return expr;
    }
}
