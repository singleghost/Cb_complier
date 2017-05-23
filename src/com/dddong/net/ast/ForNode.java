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
        d.printMember("initExpr", initExpr);
        d.printMember("condExpr", condExpr);
        d.printMember("actionExpr", actionExpr);
        d.printMember("body", bodyStmt);
    }

    public ForNode(Location loc, ExprNode init, ExprNode cond, ExprNode action, StmtNode bodyStmt) {
        super(loc);
        this.location = loc;
        this.initExpr = init;
        this.condExpr = cond;
        this.actionExpr = action;
        this.bodyStmt = bodyStmt;
    }

    @Override
    public <S, E> S accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    public ExprNode initExpr() {
        return initExpr;
    }

    public ExprNode condExpr() {
        return condExpr;
    }

    public ExprNode actionExpr() {
        return actionExpr;
    }

    public StmtNode bodyStmt() {
        return bodyStmt;
    }
}
