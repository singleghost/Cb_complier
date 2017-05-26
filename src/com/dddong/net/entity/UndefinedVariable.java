package com.dddong.net.entity;

import com.dddong.net.ast.Dumper;
import com.dddong.net.ast.TypeNode;

/**
 * Created by dddong on 2017/5/15.
 */
public class UndefinedVariable extends Variable {
    public UndefinedVariable(TypeNode t, String name) {
        super(false, t, name);
    }

    public boolean isDefined() { return false; }
    public boolean isPrivate() { return false; }
    public boolean isInitialized() { return false; }

    protected void _dump(Dumper d) {
        super._dump(d);
    }

    public <T> T accept(EntityVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
