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
}

