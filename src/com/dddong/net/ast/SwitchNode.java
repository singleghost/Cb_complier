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
        d.printMember("expr", expr);
    }

}
