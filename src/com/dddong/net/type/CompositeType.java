package com.dddong.net.type;

import com.dddong.net.ast.Location;
import com.dddong.net.ast.Slot;
import com.dddong.net.exception.SemanticError;

import java.util.List;

/**
 * Created by dddong on 2017/5/25.
 */
abstract public class CompositeType extends NamedType {
    protected List<Slot> members;
    protected long cachedSize;
    protected long cachedAlign;
    protected boolean isRecursiveChecked;
    public CompositeType(String name, List<Slot> membs, Location loc) {
        super(name, loc);
        this.members = membs;
        this.cachedSize = Type.sizeUnknown;
        this.cachedAlign = Type.sizeUnknown;
        this.isRecursiveChecked = false;
    }

    @Override
    public long size() {
        if(cachedSize == Type.sizeUnknown) {
            computeOffsets();
        }
        return cachedSize;
    }

    public List<Slot> members() {
        return members;
    }

    abstract protected void computeOffsets();

    public Type memberType(String name) {
        for(Slot s : members()) {
            if(s.name().equals(name)) {
                return s.type();
            }
        }
        throw new SemanticError("member " + name + " not exists");
    }

    public abstract long memberOffset(String member);

    @Override
    public boolean isCompositeType() {
       return true;
    }

    public abstract boolean hasMember(String memb);
}
