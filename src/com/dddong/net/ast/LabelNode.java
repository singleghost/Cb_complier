package com.dddong.net.ast;

/**
 * Created by dddong on 2017/5/18.
 */
public class LabelNode extends StmtNode {
    String labelName;
    StmtNode stmtNode;

    public LabelNode(String labelName, StmtNode stmtNode) {
        this(null, labelName, stmtNode);
    }

    public LabelNode(Location loc, String labelName, StmtNode stmtNode) {
        super(loc);
        this.labelName = labelName;
        this.stmtNode = stmtNode;
    }

    String name() {
        return labelName;
    }

    public StmtNode stmt() {
        return stmtNode;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("labelName", labelName);
        d.printMember("stmt", stmtNode);
    }

    @Override
    public <S, E> S accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }
}
