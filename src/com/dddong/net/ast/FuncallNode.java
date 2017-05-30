package com.dddong.net.ast;

import com.dddong.net.exception.SemanticError;
import com.dddong.net.exception.SemanticException;
import com.dddong.net.type.FunctionType;
import com.dddong.net.type.Type;

import java.util.List;

/**
 * Created by dddong on 2017/5/18.
 */
public class FuncallNode extends ExprNode {
    ExprNode nameExpr;
    List<ExprNode> args;

    public FuncallNode(ExprNode nameExpr, List<ExprNode> args) {
        this.nameExpr = nameExpr;
        this.args = args;
    }

    @Override
    public Type type() {
        try {
            return functionType().returnType();
        } catch (ClassCastException err) {
            throw new SemanticError(err.getMessage());
        }
    }

    FunctionType functionType() {
        //TODO 这里还有疑问,如果无法转换成PointerType岂不是要报错?
        return nameExpr.type().getPointerType().baseType().getFunctionType();
    }

    @Override
    public Location location() {
        return nameExpr.location();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("funcNameExpr", nameExpr);
        d.printNodeList("args", args);
    }

    @Override
    public <S, E> E accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    public ExprNode nameExpr() {
        return nameExpr;
    }

    public List<ExprNode> args() {
        return args;
    }
}
