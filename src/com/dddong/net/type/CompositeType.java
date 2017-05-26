package com.dddong.net.type;

import com.dddong.net.ast.Location;
import com.dddong.net.ast.Slot;

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
            //TODO
//            computeOffsets();
        }
        return cachedSize;
    }

    public List<Slot> members() {
        return members;
    }

//    abstract protected void computeOffsets();

    @Override
    public boolean isSameType(Type other) {
        //TODO
        return false;
    }

    @Override
    public boolean isCompatible(Type other) {
        //TODO
        return false;
    }

    @Override
    public boolean isCastableTo(Type target) {
        //TODO
        return false;
    }
}