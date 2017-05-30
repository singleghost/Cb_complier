package com.dddong.net.ast;

import com.dddong.net.entity.Entity;
import com.dddong.net.type.Type;

/**
 * Created by dddong on 2017/5/19.
 */
public class VariableNode extends LHSNode {
    Location loc;
    String name;
    private Entity entity;

    public VariableNode(Location loc, String name) {
        this.loc = loc;
        this.name = name;
    }

    @Override
    public Location location() {
        return loc;
    }

    public String name() {
        return name;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("name", name);
    }

    @Override
    public <S, E> E accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    protected Type origType() {
        return entity.type();
    }

    public Entity entity() {
        return entity;
    }
}
