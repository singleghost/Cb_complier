package com.dddong.net.entity;

import com.dddong.net.ast.Location;

import java.util.List;

/**
 * Created by dddong on 2017/5/15.
 */
abstract public class ParamSlots<T> {
    protected Location location;
    protected List<T> paramDescriptors;
    protected boolean vararg;

    public ParamSlots(List<T> paramDescs) {
        this(null, paramDescs);
    }

    public ParamSlots(Location loc, List<T> paramDescs) {
        this(loc, paramDescs, false);
    }

    protected ParamSlots(Location loc, List<T> paramDescs, boolean vararg) {
        super();
        this.location = loc;
        this.paramDescriptors = paramDescs;
        this.vararg = vararg;
    }

    public int argc() {
        if (vararg) {
            throw new Error("must not happen: Param#argc for vararg");
        }
        return paramDescriptors.size();
    }

    public int minArgc() {
        return paramDescriptors.size();
    }

    public void acceptVarargs() {
        this.vararg = true;
    }

    public boolean isVararg() {
        return vararg;
    }

    public Location location() {
        return location;
    }

}
