package com.dddong.net.ast;

/**
 * Created by dddong on 2017/5/18.
 */
public class DoWhileNode extends StmtNode {
    BlockNode block;
    ExprNode expr;

    public DoWhileNode(Location loc, BlockNode block, ExprNode expr) {
        super(loc);
        this.block = block;
        this.expr = expr;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("block", block);
        d.printMember("expr", expr);
    }

    @Override
    public <S, E> S accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    public BlockNode block() {
        return block;
    }

    public ExprNode expr() {
        return expr;
    }
}
