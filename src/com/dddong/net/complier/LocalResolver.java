package com.dddong.net.complier;

import com.dddong.net.ast.*;
import com.dddong.net.entity.*;
import com.dddong.net.exception.SemanticException;
import com.dddong.net.parser.Parser;
import com.dddong.net.utils.ErrorHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dddong on 2017/5/22.
 */
public class LocalResolver extends Visitor {
    private final LinkedList<Scope> scopeStack;
    private final ConstantTable constantTable;
    private final ErrorHandler errorHandler;

    public LocalResolver(ErrorHandler h) {
        this.errorHandler = h;
        this.scopeStack = new LinkedList<Scope>();
        this.constantTable = new ConstantTable();
    }

    public void resolve(AST ast) throws SemanticException {
        ToplevelScope toplevel = new ToplevelScope();
        scopeStack.add(toplevel);

        for (Entity decl : ast.declarations()) {
            toplevel.declareEntity(decl);
        }

        for (Entity ent : ast.definitions()) {
            toplevel.defineEntity(ent);
        }

        resolveGvarInitializers(ast.definedVariables());
        resolveConstantValues(ast.constants());
        resolveFunctions(ast.definedFunctions());
        toplevel.checkReferences(errorHandler); //检查是否有未使用的变零
        if (errorHandler.errorOccured()) {
            throw new SemanticException("compile failed");
        }

        ast.setScope(toplevel);
        ast.setConstantTable(constantTable);
    }

    private void resolveConstantValues(List<Constant> constants) {
        for (Constant constant:
             constants) {
            resolve(constant.value());
        }
    }

    private void resolveGvarInitializers(List<DefinedVariable> vars) {
        for (DefinedVariable var :
                vars) {
            if(var.hasInitializer()) {
                resolve(var.initializer());
            }
        }
    }

    private void resolveFunctions(List<DefinedFunction> funcs) {
        for (DefinedFunction func : funcs) {
            pushScope(func.parameters());
            resolve(func.body());
            func.setScope(popScope());
        }
    }

    private void resolve(StmtNode stmtNode) {
        stmtNode.accept(this);
    }

    private void resolve(ExprNode exprNode) {
        exprNode.accept(this);
    }

    private void pushScope(List<? extends DefinedVariable> vars){
        LocalScope scope = new LocalScope(currentScope());
        for (DefinedVariable var : vars) {
            if (scope.isDefinedLocally(var.name())) {
                error(var.location(), "duplicated variable in scope: " + var.name());
            } else {
                scope.defineVariable(var);
            }
        }
        currentScope().addChildren(scope);
        scopeStack.addLast(scope);
    }

    private void error(Location location, String message) {
        errorHandler.error(location, message);
    }

    private Scope currentScope() {
        return scopeStack.getLast();
    }

    private LocalScope popScope() {
        //为什么这里就返回localScope了呢,而不考虑ToplevelScope的情况
        return (LocalScope)scopeStack.removeLast();
    }


    @Override
    public Void visit(VariableNode node) {
        try {
            Entity ent = currentScope().get(node.name());
            ent.refered();
            node.setEntity(ent);
        }
        catch (SemanticException ex) {
            error(node.location(), ex.getMessage());
        }
        return null;
    }

    @Override
    public Void visit(BlockNode node) {

        pushScope(node.variables());
        super.visit(node);
        for(DefinedVariable var : node.variables()) {
            if(var.hasInitializer()) {
                resolve(var.initializer());
            }
        }
        for (StmtNode stmt: node.stmts()) {
            resolve(stmt);
        }
        node.setScope(popScope());
        return null;
    }
    @Override
    public Void visit(ExprStmtNode node) {
        //TODO 疑问:为什么返回类型要定义成 Void,而不是 void
        super.visit(node);
        resolve(node.expr());
        return null;
    }

    @Override
    public Void visit(IfNode node) {
        super.visit(node);
        resolve(node.cond());
        resolve(node.thenBody());
        resolve(node.elseBody());
        return null;
    }

    @Override
    public Void visit(SwitchNode node) {
        super.visit(node);
        resolve(node.expr());
        for(StmtNode stmt : node.caseNodeList()) {
            resolve(stmt);
        }
        return null;
    }

    @Override
    public Void visit(CaseNode node) {
        super.visit(node);
        for(ExprNode expr : node.caseValues()) {
            resolve(expr);
        }
        resolve(node.body());
        return null;
    }

    @Override
    public Void visit(WhileNode node) {
        super.visit(node);
        resolve(node.condExpr());
        resolve(node.body());
        return null;
    }

    @Override
    public Void visit(DoWhileNode node) {
        super.visit(node);
        resolve(node.body());
        resolve(node.expr());
        return null;
    }

    @Override
    public Void visit(ForNode node) {
        super.visit(node);
        resolve(node.initExpr());
        resolve(node.condExpr());
        resolve(node.actionExpr());
        resolve(node.bodyStmt());
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
        super.visit(node);
        resolve(node.stmt());
        return null;
    }

    @Override
    public Void visit(ReturnNode node) {
        super.visit(node);
        resolve(node.expr());
        return null;
    }

    @Override
    public Void visit(AssignNode node) {
        resolve(node.lhs());
        resolve(node.rhs());
        super.visit(node);
        return null;
    }

    @Override
    public Void visit(OpAssignNode node) {
        resolve(node.lhs());
        resolve(node.rhs());
        super.visit(node);
        return null;
    }

    @Override
    public Void visit(CondExprNode node) {
        resolve(node.cond());
        resolve(node.trueExpr());
        resolve(node.falseExpr());
        super.visit(node);
        return null;
    }

    @Override
    public Void visit(LogicalOrNode node) {
        super.visit(node);
        visit((BinaryOpNode)node);
        return null;
    }

    @Override
    public Void visit(LogicalAndNode node) {
        super.visit(node);
        visit((BinaryOpNode)node);
        return null;
    }

    @Override
    public Void visit(BinaryOpNode node) {
        resolve(node.left());
        resolve(node.right());
        super.visit(node);
        return null;
    }

    @Override
    public Void visit(UnaryOpNode node) {
        super.visit(node);
        resolve(node.expr());
        return null;
    }

    @Override
    public Void visit(PrefixOpNode node) {
        super.visit(node);
        resolve(node.expr());
        return null;
    }

    @Override
    public Void visit(SuffixOpNode node) {
        super.visit(node);
        resolve(node.expr());
        return null;
    }

    @Override
    public Void visit(ArefNode node) {
        super.visit(node);
        resolve(node.expr());
        resolve(node.index());
        return null;
    }

    @Override
    public Void visit(MemberNode node) {
        super.visit(node);
        resolve(node.expr());
        return null;
    }

    @Override
    public Void visit(PtrMemberNode node) {
        super.visit(node);
        resolve(node.expr());
        return null;
    }

    @Override
    public Void visit(FuncallNode node) {
        super.visit(node);
        resolve(node.nameExpr());
        for (ExprNode expr :
                node.args()) {
            resolve(expr);
        }
        return null;
    }

    @Override
    public Void visit(DereferenceNode node) {
        resolve(node.expr());
        super.visit(node);
        return null;
    }

    @Override
    public Void visit(AddressNode node) {
        resolve(node.expr());
        super.visit(node);
        return null;
    }

    @Override
    public Void visit(CastNode node) {
        resolve(node.expr());
        super.visit(node);
        return null;
    }

    @Override
    public Void visit(SizeofExprNode node) {
        resolve(node.expr());
        return super.visit(node);
    }

    @Override
    public Void visit(SizeofTypeNode node) {
        return super.visit(node);
    }

    @Override
    public Void visit(IntegerLiteralNode node) {
        return super.visit(node);
    }

    @Override
    public Void visit(StringLiteralNode node) {
        return super.visit(node);
    }
}

