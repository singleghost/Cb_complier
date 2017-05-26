package com.dddong.net.complier;

import com.dddong.net.ast.*;
import com.dddong.net.entity.*;
import com.dddong.net.type.*;
import com.dddong.net.utils.ErrorHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dddong on 2017/5/25.
 */
public class TypeResolver extends Visitor implements DeclarationVisitor<Void>, EntityVisitor<Void> {
    private final TypeTable typeTable;
    private final ErrorHandler errorHandler;

    public TypeResolver(TypeTable typeTable, ErrorHandler errorHandler) {
        this.typeTable = typeTable;
        this.errorHandler = errorHandler;
    }

    public void resolve(AST ast) {
        defineTypes(ast.types());
        for (TypeDefinition t : ast.types()) {
            t.accept(this);
        }
        for (Entity e : ast.entities()) {
            e.accept(this);
        }
    }

    private void defineTypes(List<TypeDefinition> deftypes) {
        for (TypeDefinition def : deftypes) {
            if (typeTable.isDefined(def.typeRef())) {
                error(def, "duplicated type definition: " + def.typeRef());
            } else {
                typeTable.put(def.typeRef(), def.definingType());
            }
        }
    }

    private void error(TypeDefinition def, String s) {
        errorHandler.error(def.location(), s);
    }

    private void visitExpr(ExprNode exprNode) {
        exprNode.accept(this);
    }

    private void bindType(TypeNode node) {
        if (node.isResolved()) return;
        Type type = typeTable.get(node.typeRef());
        if(type == null) {
            errorHandler.error(node.location(), "type " + node.typeRef().toString() + " cannot be resolved, may be undefined");
            return;
        }
        node.setType(type);
    }


    //visit definitions
    @Override
    public Void visit(StructNode struct) {
        bindType(struct.typeNode());
        for(Slot slot : struct.members()) {
            bindType(slot.typeNode());
        }
        return null;
    }

    @Override
    public Void visit(UnionNode union) {
        bindType(union.typeNode());
        for(Slot slot : union.members()) {
            bindType(slot.typeNode());
        }
        return null;
    }

    @Override
    public Void visit(TypedefNode typedef) {
        bindType(typedef.typeNode());
        bindType(typedef.realTypeNode());
        return null;
    }

    //visit entities

    public Void visit(DefinedVariable var) {
        bindType(var.typeNode());
        if (var.hasInitializer()) {
            visitExpr(var.initializer());
        }
        return null;
    }

    @Override
    public Void visit(UndefinedVariable var) {
        bindType(var.typeNode());
        return null;
    }

    @Override
    public Void visit(DefinedFunction function) {
        resolveFunctionHeader(function);
        visitStmt(function.body());
        return null;
    }

    private void resolveFunctionHeader(Function func) {
        //解析函数的返回类型
        bindType(func.typeNode());
        //解析函数的参数类型

        for(CBCParameter param : func.parameters()) {
            Type t = typeTable.getParamType(param.typeNode().typeRef());
            param.typeNode().setType(t);
        }

    }

    @Override
    public Void visit(UndefinedFunction func) {
        resolveFunctionHeader(func);
        return null;
    }

    @Override
    public Void visit(Constant c) {
        bindType(c.typeNode());
        visitExpr(c.value());
        return null;
    }

    //visit other nodes which contains typeref
    @Override
    public Void visit(StringLiteralNode node) {
        bindType(node.typeNode());
        return null;
    }

    @Override
    public Void visit(IntegerLiteralNode node) {
        bindType(node.typeNode());
        return null;
    }

    @Override
    public Void visit(CastNode node) {
        bindType(node.castType());
        visitExpr(node.expr());
        return null;
    }


    //visit exprs
    @Override
    public Void visit(AssignNode node) {
        visitExpr(node.lhs());
        visitExpr(node.rhs());
        return null;
    }

    @Override
    public Void visit(OpAssignNode node) {
        visitExpr(node.lhs());
        visitExpr(node.rhs());
        return null;
    }

    @Override
    public Void visit(CondExprNode node) {
        visitExpr(node.cond());
        visitExpr(node.trueExpr());
        visitExpr(node.falseExpr());
        return null;
    }

    @Override
    public Void visit(LogicalOrNode node) {
        visitExpr(node.left());
        visitExpr(node.right());
        return null;
    }

    @Override
    public Void visit(LogicalAndNode node) {
        visitExpr(node.left());
        visitExpr(node.right());
        return null;
    }

    @Override
    public Void visit(BinaryOpNode node) {
        visitExpr(node.left());
        visitExpr(node.right());
        return null;
    }

    @Override
    public Void visit(UnaryOpNode node) {
        visitExpr(node.expr());
        return null;
    }

    @Override
    public Void visit(PrefixOpNode node) {
        visitExpr(node.expr());
        return null;
    }

    @Override
    public Void visit(SuffixOpNode node) {
        visitExpr(node.expr());
        return null;
    }

    @Override
    public Void visit(ArefNode node) {
        visitExpr(node.expr());
        visitExpr(node.index());
        return null;
    }

    @Override
    public Void visit(MemberNode node) {
        visitExpr(node.expr());
        return null;
    }

    @Override
    public Void visit(PtrMemberNode node) {
        visitExpr(node.expr());
        return null;
    }

    @Override
    public Void visit(FuncallNode node) {
        visitExpr(node.nameExpr());
        for(ExprNode expr : node.args()) {
            visitExpr(expr);
        }
        return null;
    }

    @Override
    public Void visit(DereferenceNode node) {
        visitExpr(node.expr());
        return null;
    }

    @Override
    public Void visit(AddressNode node) {
        visitExpr(node.expr());
        return null;
    }

    @Override
    public Void visit(SizeofExprNode node) {
        visitExpr(node.expr());
        return null;
    }

    @Override
    public Void visit(SizeofTypeNode node) {
        bindType(node.operandTypeNode());
        bindType(node.typeNode());
        return null;
    }

    @Override
    public Void visit(VariableNode node) {
        return super.visit(node);
    }

    //visit stmts
    @Override
    public Void visit(BlockNode node) {
        for(DefinedVariable var : node.variables()) {
            visit(var);
        }
        for(StmtNode stmt : node.stmts()) {
            visitStmt(stmt);
        }
        return null;
    }

    @Override
    public Void visit(ExprStmtNode node) {
        visitExpr(node.expr());
        return null;
    }

    @Override
    public Void visit(IfNode node) {
        visitExpr(node.cond());
        visitStmt(node.thenBody());
        visitStmt(node.elseBody());
        return null;
    }

    @Override
    public Void visit(SwitchNode node) {
        visitExpr(node.expr());
        for(CaseNode caseNode : node.caseNodeList()) {
            visit(caseNode);
        }
        return null;
    }

    @Override
    public Void visit(CaseNode node) {
        for(ExprNode expr : node.caseValues()) {
            visitExpr(expr);
        }
        visit(node.body());
        return null;
    }

    @Override
    public Void visit(WhileNode node) {
        visitExpr(node.condExpr());
        visitStmt(node.body());
        return null;
    }

    @Override
    public Void visit(DoWhileNode node) {
        visitExpr(node.expr());
        visitStmt(node.body());
        return null;
    }

    @Override
    public Void visit(ForNode node) {
        visitExpr(node.initExpr());
        visitExpr(node.condExpr());
        visitExpr(node.actionExpr());
        visitStmt(node.bodyStmt());
        return null;
    }

    @Override
    public Void visit(BreakNode node) {
        return super.visit(node);
    }

    @Override
    public Void visit(ContinueNode node) {
        return super.visit(node);
    }

    @Override
    public Void visit(GotoNode node) {
        return super.visit(node);
    }

    @Override
    public Void visit(LabelNode node) {
        visitStmt(node.stmt());
        return null;
    }

    @Override
    public Void visit(ReturnNode node) {
        visitExpr(node.expr());
        return null;
    }
}
