package com.dddong.net.ast;

import com.dddong.net.exception.SemanticError;
import com.dddong.net.type.CompositeType;
import com.dddong.net.type.Type;

/**
 * Created by dddong on 2017/5/18.
 */
public class MemberNode extends LHSNode {
    ExprNode expr;
    String name;

    public MemberNode(ExprNode expr, String name) {
        this.expr = expr;
        this.name = name;
    }

    @Override
    public Location location() {
        return expr.location();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("expr", expr);
        d.printMember("name", name);
    }

    @Override
    public <S, E> E accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    public ExprNode expr() {
        return expr;
    }

    @Override
    protected Type origType() {
        return baseType().memberType(name);
    }

    public CompositeType baseType() {
        try {
            return expr().type().getCompositeType();
        } catch (ClassCastException err) {
            throw new SemanticError(err.getMessage());
        }
    }

    public long offset() {
        return expr.type().getCompositeType().memberOffset(name);
    }

    public String name() {
        return name;
    }
}
