package com.dddong.net.ir;

import com.dddong.net.asm.Type;
import com.dddong.net.ast.Dumper;
import com.dddong.net.entity.DefinedVariable;
import com.dddong.net.entity.Entity;

/**
 * Created by dddong on 2017/5/28.
 */
public class Var extends Expr {
    Entity entity;

    public Var(Type type, Entity entity) {
        super(type);
        this.entity = entity;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("entity", entity);
    }

    @Override
    public boolean isVar() {
        return true;
    }

    @Override
    public Addr addressNode(Type type) {
        return new Addr(type, entity);
    }
}
