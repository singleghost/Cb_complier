package com.dddong.net.ast;

/**
 * Created by dddong on 2017/5/18.
 */
public class DoWhileNode extends StmtNode {
    BlockNode body;
    ExprNode expr;

    public DoWhileNode(Location loc, BlockNode body, ExprNode expr) {
        super(loc);
        this.body = body;
        this.expr = expr;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("body", body);
        d.printMember("expr", expr);
    }

    @Override
    public <S, E> S accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    public BlockNode body() {
        return body;
    }

    public ExprNode expr() {
        return expr;
    }
}
