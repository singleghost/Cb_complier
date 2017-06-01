package com.dddong.net.type;

import com.dddong.net.ast.Location;
import com.dddong.net.entity.CBCParameter;
import com.dddong.net.entity.ParamSlots;

import java.util.Iterator;
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

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Iterator<Type> it = types().iterator(); it.hasNext(); ) {
            Type paramType = it.next();
            sb.append(paramType.toString());
            if(!it.hasNext()) {
                break;
            }
            sb.append(", ");
        }
        if(isVararg()) sb.append("...");
        return sb.toString();
    }
}
