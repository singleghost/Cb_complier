package com.dddong.net.entity;

import com.dddong.net.ast.TypeNode;

/**
 * Created by dddong on 2017/5/15.
 */
abstract public class Variable extends Entity {
    public Variable(boolean priv, TypeNode type, String name) {
        super(priv, type, name);
    }

}
