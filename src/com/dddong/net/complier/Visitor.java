package com.dddong.net.complier;

import com.dddong.net.ast.*;
import com.dddong.net.exception.SemanticException;

/**
 * Created by dddong on 2017/5/22.
 */
abstract public class Visitor implements ASTVisitor<Void, Void> {
    public Visitor() {
    }

    protected void visitStmt(StmtNode stmt) {
        stmt.accept(this);
    }

    @Override
    public Void visit(BlockNode node) {
        return null;
    }

    @Override
    public Void visit(ExprStmtNode node) {
        return null;
    }

    @Override
    public Void visit(IfNode node) {
        return null;
    }

    @Override
    public Void visit(SwitchNode node) {
        return null;
    }

    @Override
    public Void visit(CaseNode node) {
        return null;
    }

    @Override
    public Void visit(WhileNode node) {
        return null;
    }

    @Override
    public Void visit(DoWhileNode node) {
        return null;
    }

    @Override
    public Void visit(ForNode node) {
        return null;
    }

    @Override
    public Void visit(BreakNode node) {
        return null;
    }

    @Override
    public Void visit(ContinueNode node) {
        return null;
    }

    @Override
    public Void visit(GotoNode node) {
        return null;
    }

    @Override
    public Void visit(LabelNode node) {
        return null;
    }

    @Override
    public Void visit(ReturnNode node) {
        return null;
    }

    @Override
    public Void visit(AssignNode node) {
        return null;
    }

    @Override
    public Void visit(OpAssignNode node) {
        return null;
    }

    @Override
    public Void visit(CondExprNode node) {
        return null;
    }

    @Override
    public Void visit(LogicalOrNode node) {
        return null;
    }

    @Override
    public Void visit(LogicalAndNode node) {
        return null;
    }

    @Override
    public Void visit(BinaryOpNode node) {
        return null;
    }

    @Override
    public Void visit(UnaryOpNode node) {
        return null;
    }

    @Override
    public Void visit(PrefixOpNode node) {
        return null;
    }

    @Override
    public Void visit(SuffixOpNode node) {
        return null;
    }

    @Override
    public Void visit(ArefNode node) {
        return null;
    }

    @Override
    public Void visit(MemberNode node) {
        return null;
    }

    @Override
    public Void visit(PtrMemberNode node) {
        return null;
    }

    @Override
    public Void visit(FuncallNode node) {
        return null;
    }

    @Override
    public Void visit(DereferenceNode node) {
        return null;
    }

    @Override
    public Void visit(AddressNode node) {
        return null;
    }

    @Override
    public Void visit(CastNode node) {
        return null;
    }

    @Override
    public Void visit(SizeofExprNode node) {
        return null;
    }

    @Override
    public Void visit(SizeofTypeNode node) {
        return null;
    }

    @Override
    public Void visit(VariableNode node) {
        return null;
    }

    @Override
    public Void visit(IntegerLiteralNode node) {
        return null;
    }

    @Override
    public Void visit(StringLiteralNode node) {
        return null;
    }
}
