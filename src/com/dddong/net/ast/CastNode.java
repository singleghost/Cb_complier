package com.dddong.net.ast;

import com.dddong.net.type.Type;

/**
 * Created by dddong on 2017/5/18.
 */
public class CastNode extends ExprNode {
    TypeNode castType;
    ExprNode expr;

    @Override
    public Type type() {
        return castType.type();
    }

    @Override
    public <S, E> E accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Location location() {
        return castType.location();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("castType", castType);
        d.printMember("expr", expr);
    }

    public CastNode(TypeNode castType, ExprNode expr) {
        this.castType = castType;
        this.expr = expr;
    }

    public ExprNode expr() {
        return expr;
    }

    public TypeNode castType() {
        return castType;
    }
}
