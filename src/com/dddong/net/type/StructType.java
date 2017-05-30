package com.dddong.net.type;

import com.dddong.net.ast.Location;
import com.dddong.net.ast.Slot;
import com.dddong.net.exception.SemanticError;
import com.dddong.net.utils.AsmUtils;

import java.util.List;

/**
 * Created by dddong on 2017/5/25.
 */
public class StructType extends CompositeType {
    public StructType(String name, List<Slot> membs, Location loc) {
        super(name, membs, loc);
    }

    @Override
    public boolean isSameType(Type other) {
        return false;
    }

    @Override
    public boolean isCompatible(Type other) {
        return false;
    }

    @Override
    public boolean isCastableTo(Type target) {
        return false;
    }

    @Override
    protected void computeOffsets() {
        long offset = 0;
        long maxAlign = 1;
        for (Slot s : members()) {
            offset = AsmUtils.align(offset, s.allocSize());
            s.setOffset(offset);
            offset += s.allocSize();
            maxAlign = Math.max(maxAlign, s.alignment());
        }
        cachedSize = AsmUtils.align(offset, maxAlign);
        cachedAlign = maxAlign;

    }


    @Override
    public String toString() {
        return "struct " + name();
    }

    @Override
    public long memberOffset(String member) {
        for(Slot s : members()) {
            if(s.name().equals(member)) {
                return s.offset();
            }
        }
        throw new SemanticError("no such member " + member + "in " + this);
    }
}
