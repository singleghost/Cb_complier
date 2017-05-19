package com.dddong.net.ast;

/**
 * Created by dddong on 2017/5/13.
 */
public class ForNode extends StmtNode {
    Location location;
    ExprNode initExpr;
    ExprNode condExpr;
    ExprNode actionExpr;
    StmtNode bodyStmt;

    @Override
    protected void _dump(Dumper d) {

    }

    public ForNode(Location loc, ExprNode init, ExprNode cond, ExprNode action, StmtNode bodyStmt) {
        super(loc);
        this.location = loc;
        this.initExpr = init;
        this.condExpr = cond;
        this.actionExpr = action;
        this.bodyStmt = bodyStmt;
    }
}
