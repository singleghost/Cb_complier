package com.dddong.net.ast;

/**
 * Created by dddong on 2017/5/18.
 */
public class WhileNode extends StmtNode {
    ExprNode condExpr;
    StmtNode body;

    public WhileNode(Location loc, ExprNode condExpr, StmtNode body) {
        super(loc);
        this.condExpr = condExpr;
        this.body = body;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("condExpr", condExpr);
        d.printMember("body", body);
    }
}
