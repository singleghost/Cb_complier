package com.dddong.net.ast;

import java.util.List;

/**
 * Created by dddong on 2017/5/18.
 */
public class SwitchNode extends StmtNode {
    ExprNode expr;
    List<CaseNode> caseNodeList;

    public SwitchNode(Location loc, ExprNode expr, List<CaseNode> caseNodeList) {
        super(loc);
        this.expr = expr;
        this.caseNodeList = caseNodeList;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("condExpr", expr);
        d.printNodeList("cases", caseNodeList);
    }

    @Override
    public <S, E> S accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    public ExprNode expr() {
        return expr;
    }

    public List<CaseNode> caseNodeList() {
        return caseNodeList;
    }
}
