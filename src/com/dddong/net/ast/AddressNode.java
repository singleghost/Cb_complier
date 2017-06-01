package com.dddong.net.ast;

import com.dddong.net.type.Type;

/**
 * Created by dddong on 2017/5/12.
 */
public class AddressNode extends ExprNode {
    final ExprNode expr;
    Type type;

    public AddressNode(ExprNode expr) {
        this.expr = expr;
    }

    public ExprNode expr() {
        return expr;
    }

    public Type type() {
        if (type == null) throw new Error("type is null");
        return type;
    }

    /** Decides type of this node.
     * This method is called from DereferenceChecker. */
    public void setType(Type type) {
        if (this.type != null) throw new Error("type set twice");
        this.type = type;
    }

    public Location location() {
        return expr.location();
    }

    protected void _dump(Dumper d) {
        if (type != null) {
            d.printMember("type", type);
        }
        d.printMember("expr", expr);
    }

    @Override
    public <S, E> E accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }


}
