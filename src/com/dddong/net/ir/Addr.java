package com.dddong.net.ir;

import com.dddong.net.asm.Type;
import com.dddong.net.ast.Dumper;
import com.dddong.net.entity.Entity;

/**
 * Created by dddong on 2017/5/28.
 */
public class Addr extends Expr {
    Entity entity;

    public Addr(Type type, Entity entity) {
        super(type);
        this.entity = entity;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("entity", entity.name());
    }
}
