package com.dddong.net.ast;

import com.dddong.net.type.Type;

/**
 * Created by dddong on 2017/5/12.
 */
abstract public class LHSNode extends ExprNode {
    protected Type type, origType;

    public Type type() {
        return type != null ? type : origType();
    }

    public void setType(Type t) {
        this.type = t;
    }

    abstract protected Type origType();

    public long allocSize() { return origType().allocSize(); }

    public boolean isLvalue() { return true; }
    public boolean isAssignable() { return isLoadable(); }

    public boolean isLoadable() {
        //处理左值的时候对数组和函数进行特殊处理
        Type t = origType();
        return !t.isArray() && !t.isFunction();
    }

}
