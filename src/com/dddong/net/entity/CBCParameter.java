package com.dddong.net.entity;

import com.dddong.net.ast.Dumper;
import com.dddong.net.ast.TypeNode;

/**
 * Created by dddong on 2017/5/15.
 */
public class CBCParameter extends DefinedVariable {
    public CBCParameter(TypeNode type, String name) {
        super(false, type, name, null);
    }

    public boolean isParameter() {
        return true;
    }

    protected void _dump(Dumper d) {
        d.printMember("name", name);
        d.printMember("typeNode", typeNode);
    }

}
