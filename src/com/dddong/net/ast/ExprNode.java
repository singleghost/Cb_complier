package com.dddong.net.ast;

import com.dddong.net.type.Type;

/**
 * Created by dddong on 2017/5/12.
 */
abstract public class ExprNode extends Node {
    public ExprNode() {
        super();
    }

    abstract public Type type();
    protected Type origType() { return type(); }

    public long allocSize() { return type().allocSize(); }

    public boolean isConstant() { return false; }
    public boolean isParameter() { return false; }

    public boolean isLvalue() { return false; }
    public boolean isAssignable() { return false; }
    public boolean isLoadable() { return false; }

    abstract public <S,E> E accept(ASTVisitor<S,E> visitor);
}
