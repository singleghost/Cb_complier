package com.dddong.net.type;

import com.dddong.net.ast.Location;
import com.dddong.net.ast.Slot;

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

//    @Override
//    protected void computeOffsets() {
//        long offset = 0;
//        long maxAlign = 1;
//        for (Slot s : members()) {
//            offset = AsmUtils.align(offset, s.allocSize());
//            s.setOffset(offset);
//            offset += s.allocSize();
//            maxAlign = Math.max(maxAlign, s.alignment());
//        }
//        cachedSize = AsmUtils.align(offset, maxAlign);
//        cachedAlign = maxAlign;
//
//    }


}
