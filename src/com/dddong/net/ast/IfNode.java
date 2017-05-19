package com.dddong.net.ast;

/**
 * Created by dddong on 2017/5/18.
 */
public class IfNode extends StmtNode {
    ExprNode cond;
    StmtNode thenBody;
    StmtNode elseBody;

    public IfNode(Location loc, ExprNode cond, StmtNode thenBody, StmtNode elseBody) {
        super(loc);
        this.cond = cond;
        this.thenBody = thenBody;
        this.elseBody = elseBody;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("cond", cond);
        d.printMember("thenBody", thenBody);
        d.printMember("elseBody", elseBody);
    }
}
