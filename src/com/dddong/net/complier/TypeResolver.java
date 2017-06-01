package com.dddong.net.complier;

import com.dddong.net.ast.*;
import com.dddong.net.entity.*;
import com.dddong.net.type.*;
import com.dddong.net.utils.ErrorHandler;

import java.util.ArrayList;
import java.util.List;
import static com.dddong.net.utils.TextColor.*;

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

    private void bindType(TypeNode node) {
        if (node.isResolved()) return;
        Type type = typeTable.get(node.typeRef());
        if(type == null) {
            errorHandler.error(node.location(), "type " + node.typeRef().toString() + " cannot be resolved, may be undefined");
            return;
        }
//        System.out.println(ANSI_PURPLE + node.location() + ANSI_RESET + ": resovle type '"
//                            + ANSI_BLUE + node.typeRef().toString() + ANSI_RESET + "'");
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
        bindType(typedef.realTypeNode());
        bindType(typedef.typeNode());
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

    @Override
    public Void visit(SizeofTypeNode node) {
        bindType(node.operandTypeNode());
        bindType(node.typeNode());
        return null;
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

}
