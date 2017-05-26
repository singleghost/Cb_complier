package com.dddong.net.type;

import com.dddong.net.ast.Location;
import com.dddong.net.entity.ParamSlots;

import java.util.List;

/**
 * Created by dddong on 2017/5/25.
 */
public class ParamTypes extends ParamSlots<Type> {
    public ParamTypes(Location loc, List<Type> paramDescs, boolean vararg) {
        super(loc, paramDescs, vararg);
    }

    public List<Type> types() {
        return paramDescriptors;
    }

    public boolean isSameType(ParamTypes other) {
        for(int i = 0; i < paramDescriptors.size(); i++) {
            if(! paramDescriptors.get(i).isSameType(other.paramDescriptors.get(i))) return false;
        }
        if(vararg != other.vararg) return false;
        return true;
    }
}
