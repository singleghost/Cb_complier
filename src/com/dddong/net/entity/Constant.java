package com.dddong.net.entity;

import com.dddong.net.ast.Dumper;
import com.dddong.net.ast.ExprNode;
import com.dddong.net.ast.TypeNode;

/**
 * Created by dddong on 2017/5/15.
 */
public class Constant extends Entity {
    private ExprNode value;

    public Constant(TypeNode type, String name, ExprNode value) {
        super(true, type, name);
        this.value = value;
    }

    public boolean isAssignable() { return false; }
    public boolean isDefined() { return true; }
    public boolean isInitialized() { return true; }
    public boolean isConstant() { return true; }

    public ExprNode value() { return value; }

    protected void _dump(Dumper d) {
        super._dump(d);
        d.printMember("value", value);
    }

    public <T> T accept(EntityVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
