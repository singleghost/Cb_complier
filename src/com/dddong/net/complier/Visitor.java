package com.dddong.net.complier;

import com.dddong.net.ast.*;
import com.dddong.net.entity.DefinedVariable;
import com.dddong.net.exception.SemanticException;

import java.util.List;

/**
 * Created by dddong on 2017/5/22.
 */
abstract public class Visitor implements ASTVisitor<Void, Void> {

    public Visitor() {
    }

    protected void visitStmt(StmtNode stmt) {
        stmt.accept(this);
    }

    protected void visitStmts(List<? extends StmtNode> stmts) {
        for (StmtNode s : stmts) {
            visitStmt(s);
        }
    }

    protected void visitExpr(ExprNode expr) {
        expr.accept(this);
    }

    protected void visitExprs(List<? extends ExprNode> exprs) {
        for (ExprNode e : exprs) {
            visitExpr(e);
        }
    }

    //
    // Statements
    //

    public Void visit(BlockNode node) {
        for (DefinedVariable var : node.variables()) {
            if (var.hasInitializer()) {
                visitExpr(var.initializer());
            }
        }
        visitStmts(node.stmts());
        return null;
    }

    public Void visit(ExprStmtNode node) {
        visitExpr(node.expr());
        return null;
    }

    public Void visit(IfNode n) {
        visitExpr(n.cond());
        visitStmt(n.thenBody());
        if (n.elseBody() != null) {
            visitStmt(n.elseBody());
        }
        return null;
    }

    public Void visit(SwitchNode n) {
        visitExpr(n.expr());
        visitStmts(n.caseNodeList());
        return null;
    }

    public Void visit(CaseNode n) {
        visitExprs(n.caseValues());
        visitStmt(n.body());
        return null;
    }

    public Void visit(WhileNode n) {
        visitExpr(n.condExpr());
        visitStmt(n.body());
        return null;
    }

    public Void visit(DoWhileNode n) {
        visitStmt(n.body());
        visitExpr(n.expr());
        return null;
    }

    public Void visit(ForNode n) {
        if (n.initExpr() != null) visitExpr(n.initExpr());
        if (n.condExpr() != null) visitExpr(n.condExpr());
        if (n.actionExpr() != null) visitExpr(n.actionExpr());
        visitStmt(n.bodyStmt());
        return null;
    }

    public Void visit(BreakNode n) {
        return null;
    }

    public Void visit(ContinueNode n) {
        return null;
    }

    public Void visit(GotoNode n) {
        return null;
    }

    public Void visit(LabelNode n) {
        visitStmt(n.stmt());
        return null;
    }

    public Void visit(ReturnNode n) {
        if (n.expr() != null) {
            visitExpr(n.expr());
        }
        return null;
    }

    //
    // Expressions
    //

    public Void visit(CondExprNode n) {
        visitExpr(n.cond());
        visitExpr(n.trueExpr());
        if (n.falseExpr() != null) {
            visitExpr(n.falseExpr());
        }
        return null;
    }

    public Void visit(LogicalOrNode node) {
        visitExpr(node.left());
        visitExpr(node.right());
        return null;
    }

    public Void visit(LogicalAndNode node) {
        visitExpr(node.left());
        visitExpr(node.right());
        return null;
    }

    public Void visit(AssignNode n) {
        visitExpr(n.lhs());
        visitExpr(n.rhs());
        return null;
    }

    public Void visit(OpAssignNode n) {
        visitExpr(n.lhs());
        visitExpr(n.rhs());
        return null;
    }

    public Void visit(BinaryOpNode n) {
        visitExpr(n.left());
        visitExpr(n.right());
        return null;
    }

    public Void visit(UnaryOpNode node) {
        visitExpr(node.expr());
        return null;
    }

    public Void visit(PrefixOpNode node) {
        visitExpr(node.expr());
        return null;
    }

    public Void visit(SuffixOpNode node) {
        visitExpr(node.expr());
        return null;
    }

    public Void visit(FuncallNode node) {
        visitExpr(node.nameExpr());
        visitExprs(node.args());
        return null;
    }

    public Void visit(ArefNode node) {
        visitExpr(node.expr());
        visitExpr(node.index());
        return null;
    }

    public Void visit(MemberNode node) {
        visitExpr(node.expr());
        return null;
    }

    public Void visit(PtrMemberNode node) {
        visitExpr(node.expr());
        return null;
    }

    public Void visit(DereferenceNode node) {
        visitExpr(node.expr());
        return null;
    }

    public Void visit(AddressNode node) {
        visitExpr(node.expr());
        return null;
    }

    public Void visit(CastNode node) {
        visitExpr(node.expr());
        return null;
    }

    public Void visit(SizeofExprNode node) {
        visitExpr(node.expr());
        return null;
    }

    public Void visit(SizeofTypeNode node) {
        return null;
    }

    public Void visit(VariableNode node) {
        return null;
    }

    public Void visit(IntegerLiteralNode node) {
        return null;
    }

    public Void visit(StringLiteralNode node) {
        return null;
    }
}

