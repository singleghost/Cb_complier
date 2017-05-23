package com.dddong.net.ast;

import com.dddong.net.entity.DefinedVariable;
import com.dddong.net.entity.LocalScope;

import java.util.List;

/**
 * Created by dddong on 2017/5/15.
 */
public class BlockNode extends StmtNode {
    protected List<DefinedVariable> variables;
    protected List<StmtNode> stmts;
    private LocalScope scope;

    public BlockNode(Location loc, List<DefinedVariable> vars, List<StmtNode> stmts) {
        super(loc);
        this.variables = vars;
        this.stmts = stmts;
    }

    public List<DefinedVariable> variables() {
        return variables;
    }

    public List<StmtNode> stmts() {
        return stmts;
    }

    public StmtNode tailStmt() {
        if (stmts.isEmpty()) return null;
        return stmts.get(stmts.size() - 1);
    }

    protected void _dump(Dumper d) {
        if(variables == null) {
            d.printPair("variables", null);
        } else {
            d.printNodeList("variables", variables);
        }
        d.printNodeList("stmts", stmts);
    }

    @Override
    public <S, E> S accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    public void setScope(LocalScope scope) {
        this.scope = scope;
    }
}
