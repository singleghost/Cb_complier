package com.dddong.net.ast;

/**
 * Created by dddong on 2017/5/18.
 */
public class ContinueNode extends StmtNode {
    public ContinueNode(Location loc) {
        super(loc);
    }

    @Override
    protected void _dump(Dumper d) {
        return;
    }

    @Override
    public <S, E> S accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }
}
