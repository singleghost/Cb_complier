package com.dddong.net.ast;

import com.dddong.net.asm.Label;

import java.util.List;

/**
 * Created by dddong on 2017/5/18.
 */
public class CaseNode extends StmtNode {

    List<ExprNode> caseValues;
    BlockNode body;
    Label label;

    @Override
    protected void _dump(Dumper d) {
        d.printNodeList("caseValues", caseValues);
        d.printMember("body", body);
    }

    public CaseNode(Location loc, List<ExprNode> caseValues, BlockNode body) {
        super(loc);
        this.caseValues = caseValues;
        this.body = body;
        this.label = new Label();
    }

    @Override
    public <S, E> S accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    public BlockNode body() {
        return body;
    }

    public List<ExprNode> caseValues() {
        return caseValues;
    }
    public boolean isDefault() {
        return caseValues.size() == 0;
    }

    public Label label() {
        return label;
    }
}
