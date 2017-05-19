package com.dddong.net.entity;

import com.dddong.net.ast.Dumper;
import com.dddong.net.ast.TypeNode;

import java.util.List;

/**
 * Created by dddong on 2017/5/15.
 */
public class UndefinedFunction extends Function {
    Params params;

    public UndefinedFunction(boolean priv, TypeNode t, String name, Params params) {
        super(priv, t, name);
        this.params = params;
    }

    @Override
    public boolean isDefined() {
        return false;
    }

    @Override
    public List<CBCParameter> parameters() {
        return params.parameters();
    }

    @Override
    protected void _dump(Dumper d) {

    }
}
